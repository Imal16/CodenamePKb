package main.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class that launched the application. It uses FXML Loader, which initializes the Controller.
 * @author Rosy
 * Last update: 1 Feb 2019
 */
public class Test extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("board.fxml"));
	    String css = this.getClass().getResource("style.css").toExternalForm();
	    
		Scene scene = new Scene(root,1000,800);
		primaryStage.setTitle("Test FXML");
		primaryStage.setScene(scene); //stage contains a scene
		primaryStage.show(); //display		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}


}
