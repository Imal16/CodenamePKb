package main.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("../view/board.fxml"));
	    String css = this.getClass().getResource("../view/style.css").toExternalForm();
	    
		Scene scene = new Scene(root,1000,800);
		primaryStage.setTitle("Test FXML");
		primaryStage.setScene(scene); //stage contains a scene
		primaryStage.show(); //display		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}


}
