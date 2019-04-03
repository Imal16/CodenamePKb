package main.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import main.application.GameManager;
import main.models.business.*;

/**
 * This is the Controller for generating the board cards.
 *
 * @author Rosy Teasdale, William Ngo, Zijian Wang, Olivier, Daniel
 * @version 03/04/2019
 */

//implements Initializable

public class BoardController implements Initializable{

	@FXML
	private GridPane board_view; // use fx:id
	@FXML
	private Button enterBtn;
	@FXML
	private Text spyHint;
	@FXML
	private Text playerSpyHint;
	@FXML
	private Text turnIndicator;
	@FXML
	private Text labelNumRedCards;
	@FXML
	private Text labelNumBlueCards;
	@FXML
	private Text playerPicksLeft;
	@FXML
	private Button skipBtn;
	
	private int[] keycardTypes; // Array that holds information about the location of the types of card
	// (bystander, assassin, ops)
	private String[] keycardWords; // array that holds information about the location of the words on the board

	private int numOfRedCards = 0;
	private int numOfBlueCards = 0;

	private Board board_model;
	private GameManager game;

	static HashMap<String,ArrayList<String>> wordRelation;      //json contents

	public BoardController() {
		// System.out.println("BoardController()");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		board_model = new Board();
		setupBoard();
		setupGraph();

		game = new GameManager(board_model);
		game.setAmountOfBlueCards(numOfBlueCards);
		game.setAmountOfRedCards(numOfRedCards);// passing number of cards to gameManager
		
		/*Additional UI elements*/
		turnIndicator.setText("Current turn: " + game.WhosTurnIsIt());
		labelNumRedCards.setText("Red cards: 0 / " + numOfRedCards);
		labelNumBlueCards.setText("Blue cards: 0 / " + numOfBlueCards);
		
		/*Don't show the skip button if ai vs ai*/
		if(!game.isPlayerPlaying()) {
			skipBtn.setVisible(false);
			playerSpyHint.setVisible(false);
			playerPicksLeft.setVisible(false);
		}
		
		/*Skip button is disabled if user is not playing*/
		if(!game.isPlayersTurn()) {
			skipBtn.setDisable(true);
		}
		
		//if the player starts first, get a hint
		if(game.isPlayerPlaying() && game.isPlayersTurn()) {
			enterBtn.setDisable(true);
			playerSpyHint.setText("Your Hint:\n" + game.getPlayerSpymaster().getClueWord());
			playerPicksLeft.setText("Picks left: " + game.getPlayer().getTries());
			updateUI();
		}
	}

	/**
	 * Manages the graph implementation
	 * Graph & cards for both red & blue team
	 */
	private void setupGraph() {
		List<String> redTeamCards = new ArrayList<>();
		List<String> blueTeamCards =  new ArrayList<>();

		// Set words for respective teams
		for(int row = 0; row < 5; row++){
			for (int col = 0; col < 5; col++){
				Card currentCard = board_model.getCardAt(row, col);
				
				if(currentCard.getType() == CardTypes.RED){
					redTeamCards.add(currentCard.getWord());
				}
				else if(currentCard.getType() == CardTypes.BLUE){
					blueTeamCards.add(currentCard.getWord());
				}
				
			}
		}
		// Set graph for respective team
        board_model.setRedGraph(redTeamCards, wordRelation);
        board_model.setBlueGraph(blueTeamCards, wordRelation);

		// Set card list for respective team
		board_model.setRedCards(redTeamCards);
		board_model.setBlueCards(blueTeamCards);
	}


	/**
	 * This method reads key card text file and populates board view with cards that
	 * contains a word and a color.
	 *
	 */
	private void setupBoard(){
		// Reading keycard text file
		Jparser jparser = new Jparser();

		try {
			// Create a Keycard reader with the Keycard text file
			KeyCardReader reader = new KeyCardReader("resources/keycards/keycard6.txt", "resources/keycards/words.txt");
			//Get random keycards!!

			wordRelation = jparser.parseJson(jparser.readfile());

			// keycardTypes array will be populated with information from text file.
			keycardTypes = reader.readKeycardTypes();
			keycardWords = jparser.generaterandomkeycards(wordRelation).toArray(new String[0]);

		} catch (IOException | ParseException e) {
			System.err.println("Cannot read file.");
		}

		int keyCardArrayCounter = 0;

		// Populate board with infos from keycard arrays
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {

				// Create a card with a word and a type
				Card cardToAdd = new Card(keycardWords[keyCardArrayCounter], keycardTypes[keyCardArrayCounter]);

				//
				// game.redCardsLeft++;
				board_view.add(cardToAdd, i, j); // add card on the view
				board_model.setUpCardAt(cardToAdd, i, j); // add card in the board class
				if (cardToAdd.getType() == CardTypes.RED) {
					numOfRedCards++;
				} else if (cardToAdd.getType() == CardTypes.BLUE) {
					numOfBlueCards++;
				}
				// using the card.getType, and local int red and blue
				// we record the number of each team's cards.
				keyCardArrayCounter++;
				
				/*Click listener added to cards*/
				cardToAdd.setOnMouseClicked(new EventHandler<MouseEvent>()
			    {

					@Override
			        public void handle(MouseEvent t) {
						
						/*cards are only clickable if:
						 * 	its the players turn
						 * 	the card is not flipped
						 * 	the game is not over
						 * 	the player still has more than 0 picks*/
						
						if(game.isPlayersTurn() && !cardToAdd.isFlipped && !game.isGameOver() && game.getPlayer().getTries() > 0) {
							board_model.pickCardAt(cardToAdd.getWord());
							game.getPlayer().decTries();
							
			        		switch(cardToAdd.getType()) {
								case CardTypes.BYSTANDER:
									game.getPlayer().setTries(0);
									break;
									
								case CardTypes.ASSASSIN:
									game.switchTeams();
									game.endGame();
									break;
									
								case CardTypes.RED:
									game.removeRedCard();
				        			//change the turn if the card flipped is not the player color
				        			if(!game.isPlayerRed())
				        				game.getPlayer().setTries(0);
									break;
									
								case CardTypes.BLUE:
									game.removeBlueCard();
				        			//change the turn if the card flipped is not the player color
				        			if(game.isPlayerRed())
				        				game.getPlayer().setTries(0);
									break;
			        		}
			        		
			        					     
	
			        		//check if there are no more cards left for a color
			        		game.checkNumberOfCardsLeft();
			        		
			        		if(game.getPlayer().getTries() == 0) {
			        			enterBtn.setText("Play AI");
			        			enterBtn.setDisable(false);
			        			skipBtn.setDisable(true);
			        			game.switchTeams();
			        		}
			        		
			        		if(game.isGameOver()) {
			        			handleGameEnd();
			        		}
			        		
			        		updateUI();
			        	}
			        }
			    });
			}
		}

		keyCardArrayCounter = 0;
	}
	
	@FXML
	protected void handleEnterButtonAction(ActionEvent event) {
		/*No matter what state the game is in, the AI plays when the enter button is hit.*/
		game.playTurn();		
		spyHint.setText("AI Hint:\n" + game.getCurrentSpymaster().getClueWord());
		
		/*Things are different if the user is playing: need to disable the enter button for his next turn and re-enable the skip button.
		 * Then, give the player a new hint.*/
		if(game.isPlayerPlaying()) {
			enterBtn.setDisable(true);
			skipBtn.setDisable(false);
			game.givePlayerHint();
			playerSpyHint.setText("Your Hint:\n" + game.getPlayerSpymaster().getClueWord());
		}

		if(game.isGameOver()) {
			handleGameEnd();
		} else {
			game.switchTeams();
		}
		
		updateUI();
	}
	
	@FXML
	protected void handleSkipButtonAction(ActionEvent event) {
		/*When you skip: 
		 * End your turn
		 * AI plays turn
		 * Your turn starts again*/

		game.switchTeams();
		game.playTurn();
		spyHint.setText("AI Hint:\n" + game.getCurrentSpymaster().getClueWord());
		
		game.switchTeams();
		game.givePlayerHint();
		playerSpyHint.setText("Your Hint:\n" + game.getPlayerSpymaster().getClueWord());
		
		if(game.isGameOver()) {
			handleGameEnd();
		}
		
		updateUI();
	}
	
	/*Flip all the cards on the board that are not flipped.*/
	public void flipAllCards() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if(!board_model.isCardFlippedAt(i,j)) {
					board_model.getCardAt(i, j).flip();
				}
			}
		}
	}
	
	private void handleGameEnd() {
		String winner = (game.isRedWinner()) ? "Red" : "Blue";
		enterBtn.setDisable(true);
		skipBtn.setDisable(true);
		
		Logger.getLogger("LOGGER").setLevel(Level.INFO);
		Logger.getLogger("LOGGER").info("\nEnd of the game, " + winner + " team won!");
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game Over");
		alert.setHeaderText("End of the game, " + winner + " team won!");
		alert.showAndWait();
		
		flipAllCards();
		turnIndicator.setText("Winner: " + winner);
	}
	
	/*Some common UI elements that are updated at the end of a turn*/
	private void updateUI() {
		labelNumRedCards.setText("Red cards: " + (numOfRedCards - game.getRedCardsLeft()) + " / " + numOfRedCards);
		labelNumBlueCards.setText("Blue cards: " + (numOfBlueCards - game.getBlueCardsLeft()) + " / " + numOfBlueCards);
		
		if(!game.isGameOver()) {
			turnIndicator.setText("Current turn: " + game.WhosTurnIsIt());
		}
		
		if(game.isPlayerPlaying()) {
			playerPicksLeft.setText("Picks left: " + (game.getPlayer().getTries()));
		}
	}
}