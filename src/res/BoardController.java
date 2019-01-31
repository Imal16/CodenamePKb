package res;

import java.net.URL;
import java.util.ResourceBundle;

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
	@FXML private Button changeTurn;
	
	public BoardController() {
		System.out.println("BoardController()");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){  
				board.add(new Card(), i, j);
			}
		}
		 
	}

}
