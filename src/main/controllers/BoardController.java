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

import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import main.application.GameManager;
import main.models.business.*;

/**
 * This is the Controller for generating the board cards.
 *
 * @author Rosy Teasdale, William Ngo, Zijian Wang
 * @version 02/06/2019
 */
public class BoardController implements Initializable {

	@FXML
	private GridPane board_view; // use fx:id
	@FXML
	private Button enterBtn;
	@FXML
	private Text spyHint;

	private int[] keycardTypes; // Array that holds information about the location of the types of card
	// (bystander, assassin, ops)
	private String[] keycardWords; // array that holds information about the location of the words on the board

	private int numOfRedCards = 0;
	private int numOfBlueCards = 0;

	private Board board_model;
	private GameManager game;

	static HashMap<String,ArrayList<String>> wordRelation;

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
				if(currentCard.getType() == 2){
					redTeamCards.add(currentCard.getWord());
				}
				else if(currentCard.getType() == 3){
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

//		playerTurn.setText("");
		Jparser jparser = new Jparser();

		try {
			// Create a Keycard reader with the Keycard text file
			KeyCardReader reader = new KeyCardReader("resources/keycards/keycard6.txt", "resources/keycards/words.txt");

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
				// System.out.println("Add card");

				// Create a card with a word and a type
				Card cardToAdd = new Card(keycardWords[keyCardArrayCounter], keycardTypes[keyCardArrayCounter]);

				//
				// game.redCardsLeft++;
				board_view.add(cardToAdd, i, j); // add card on the view
				board_model.setUpCardAt(cardToAdd, i, j); // add card in the board class
				if (cardToAdd.getType() == 2) {
					numOfRedCards++;
				} else if (cardToAdd.getType() == 3) {
					numOfBlueCards++;
				}
				// using the card.getType, and local int red and blue
				// we record the number of each team's cards.
				keyCardArrayCounter++;
			}
		}

		keyCardArrayCounter = 0;

	}

	@FXML
	protected void handleEnterButtonAction(ActionEvent event) {
		// check if the game ends
		// if so we are not going to play turn since the program could crash if it
		// overfloats

		spyHint.setText("Given Hint:\n" + Spymaster.getClueWord());

		if (!game.isEnd()) {
			game.playTurn();
			spyHint.setText("Given Hint:\n" + Spymaster.getClueWord());
		} else {
			// so not doing play turn but print a string on button
			String side = (game.isRedWinner()) ? "Red" : "Blue";
			System.out.println("\nEnd of the game, " + side + " team won!");
			Logger.getLogger("LOGGER").setLevel(Level.INFO);
			Logger.getLogger("LOGGER").info("\nEnd of the game, " + side + " team won!");

			enterBtn.setDisable(true);
		}

	}
}