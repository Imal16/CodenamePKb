package main.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import main.models.business.*;
import main.models.interfaces.*;

/**
 * This is the Controller for generating the board cards.
 * 
 * @author ROSY Last update: 1 Feb 2019
 */
public class BoardController implements Initializable {

	@FXML
	private GridPane board; // use fx:id
	@FXML
	private Button enterBtn;

	private int[] keycardTypes; //Array that holds information about the location of the types of card (bystander, assassin, ops)
	private String[] keycardWords; //array that holds information about the location of the words on the board

	private Board board_model;
	private CommandManager cmd;

	public BoardController() {
		System.out.println("BoardController()");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			// Create a Keycard reader with the Keycard text file
			KeyCardReader reader = new KeyCardReader("resources/keycards/keycard6.txt", "resources/keycards/words.txt");
			
			//keycardTypes array will be populated with information from text file.
			keycardTypes = reader.readKeycardTypes();
			keycardWords = reader.readKeycardWords();

		} catch (IOException e) {
			System.err.println("Cannot read file.");
		}

		board_model = new Board();
		cmd = new CommandManager(); //For testing

		int keyCardArrayCounter = 0;
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				// System.out.println("Add card");
				
				// Create a card with a word and a type
				Card cardToAdd = new Card(keycardWords[keyCardArrayCounter], keycardTypes[keyCardArrayCounter]);
				
				board.add(cardToAdd, i, j); //add card on the view 
				board_model.setUpCardAt(cardToAdd, i, j); //add card in the board class
				

				keyCardArrayCounter++;
			}
		}
		keyCardArrayCounter = 0; // reset counter
	}

	@FXML
	protected void handleEnterButtonAction(ActionEvent event) {
		System.out.println("Enter button pressed.");
		cmd.setStrategy(new PickRandomCardStrategy(board_model));
		cmd.executeStrategy();
	}
	
	@FXML
	protected void setRandomStrategy(ActionEvent event) {
		cmd.setStrategy(new PickRandomCardStrategy(board_model));
	}
	
	@FXML
	protected void setNextRandomStrategy(ActionEvent event) {
		cmd.setStrategy(new PickRandomCardStrategy(board_model));
	}

}
