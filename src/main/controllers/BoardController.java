package main.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import main.models.business.Card;

/**
 * This is the Controller for generating the board cards.
 * @author ROSY
 * Last update: 1 Feb 2019
 */
public class BoardController implements Initializable {
	
	@FXML private GridPane board; //use fx:id
	@FXML private Button enterBtn;
	
	private int[] keycardTypes;
	private String[] keycardWords;
	
	public BoardController() {
		System.out.println("BoardController()");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	    try {
	    	
	    KeyCardReader reader = new KeyCardReader("resources/keycards/keycard6.txt","resources/keycards/words.txt"); //Create a Keycard reader with the Keycard text file
	    
	    keycardTypes = reader.readKeycardTypes();
	    
	    keycardWords = reader.readKeycardWords();
	    
	    
	    } catch (IOException e) {
	    	System.err.println("Cannot read file.");
	    }		
	    
	    
	    int arrayCounter = 0; //array counter
    	for (int i=0;i<5;i++) {
    		for (int j=0;j<5;j++) {
    			//System.out.println("Add card");
    			board.add(new Card(keycardWords[arrayCounter], keycardTypes[arrayCounter]), i, j); //Create a card with a word and a type.
    			arrayCounter++;
    		}
    	}
    	arrayCounter = 0; //reset counter
	}
	
    @FXML 
    protected void handleEnterButtonAction(ActionEvent event) {
    	System.out.println("Enter button pressed.");
    	//Play
    }

}
