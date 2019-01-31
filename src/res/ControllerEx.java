package res;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class ControllerEx {
	@FXML private GridPane grid;
	
	@FXML protected void handleSubmitButtonAction(ActionEvent event) {
        //Action handler
    }
	
    public void createBoard() {
    	
        for(int i=0;i<5;i++){
        	
           for(int j=0;j<5;j++){
        	   
               grid.add(new GridPane(), i, j);
               
           }
           
        }
        

    }

}
