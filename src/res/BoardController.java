package res;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * This is the Controller for generating the board cards.
 * @author ROSY
 *
 */
public class BoardController implements Initializable {
	
	@FXML private GridPane board; //use fx:id
	@FXML private Button enterBtn;
	
	private int[] keycardTypes;
	
	public BoardController() {
		System.out.println("BoardController()");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	    try {
	    	
	    KeyCardReader reader = new KeyCardReader("keycards/keycard4.txt",""); //Create a Keycard reader with the keycard text file.
	    
	    keycardTypes = reader.readKeycardTypes();
	    
	    } catch (IOException e) {
	    	System.err.println("Cannot read file.");
	    }		
	    
	    
	    int arrayCounter = 0; //array counter
    	for (int i=0;i<5;i++) {
    		for (int j=0;j<5;j++) {
    			//System.out.println("Add card");
    			board.add(new Card("Word", keycardTypes[arrayCounter++]), i, j); //Create a card with a word and a type.
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
