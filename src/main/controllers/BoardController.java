package main.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import main.application.GameManager;
import main.models.business.*;
import main.models.interfaces.*;

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
	private Text playerTurn;

	private int[] keycardTypes; // Array that holds information about the location of the types of card
								// (bystander, assassin, ops)
	private String[] keycardWords; // array that holds information about the location of the words on the board
	
	private int red=0;
	private int blue=0;
	
	private Board board_model;
	private GameManager game;

	public BoardController() {
		System.out.println("BoardController()");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		board_model = new Board();
		setupBoard();
		game = new GameManager(board_model);
		game.setBC(blue);
		game.setRC(red);//passing number of cards to gameManager
	}

	/**
	 * This method reads key card text file and populates board view with
	 * cards that contains a word and a color.
	 *
	 */
	private void setupBoard() {
		// Reading keycard text file
		
		playerTurn.setText("abc");
		
		try {
			// Create a Keycard reader with the Keycard text file
			KeyCardReader reader = new KeyCardReader("resources/keycards/keycard6.txt", "resources/keycards/words.txt");

			// keycardTypes array will be populated with information from text file.
			keycardTypes = reader.readKeycardTypes();
			keycardWords = reader.readKeycardWords();
			

		} catch (IOException e) {
			System.err.println("Cannot read file.");
		}

		int keyCardArrayCounter = 0;

		//Populate board with infos from keycard arrays
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				// System.out.println("Add card");

				// Create a card with a word and a type
				Card cardToAdd = new Card(keycardWords[keyCardArrayCounter], keycardTypes[keyCardArrayCounter]);
				//game.redCardsLeft++;
				board_view.add(cardToAdd, i, j); // add card on the view
				board_model.setUpCardAt(cardToAdd, i, j); // add card in the board class
				if(cardToAdd.getType()==2) {
					red++;
				}else if(cardToAdd.getType()==3) {
					blue++;
				}
				//using the card.getType, and local int red and blue 
				//we record the number of each team's cards.
				keyCardArrayCounter++;
			}
		}
		
		keyCardArrayCounter = 0;
	}

	@FXML
	protected void handleEnterButtonAction(ActionEvent event) {	
		//check if the game ends
		//if so we are not going to play turn since the program could crash if it overfloats
		if(!game.isEnd()) {
		game.playTurn();
		}
		else{
			//so not doing play turn but print a string on button
			String side = (game.isRedWinner()) ? "red" : "blue";
			System.out.println("\nEnd of the game, "+side+" team won!");
		}
		
	}
}
