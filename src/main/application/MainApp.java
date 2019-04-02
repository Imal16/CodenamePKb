package main.application;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main application that launches the game.
 * 
 * @author William Ngo, Rosy Teasdale
 * @version 07/02/2019
 *
 */
public class MainApp extends Application{

	private GameManager game;
	private final static Logger LOGGER = Logger.getLogger(MainApp.class.getName()); //Create a logger for all the app
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("../view/board.fxml"));
	    String css = this.getClass().getResource("../view/style.css").toExternalForm();
	    
	    outputLog();
	    
		Scene scene = new Scene(root,1000,800);
		primaryStage.setTitle("Test FXML");
		primaryStage.setScene(scene); //stage contains a scene
		primaryStage.show(); //display
	}
	
	/**
	 * This method generates the log file.
	 */
	private static void outputLog() {
		
		Logger.getLogger("LOGGER").setUseParentHandlers(false); //False = do not display to console.
	    
		Logger logger = Logger.getLogger("LOGGER");
		FileHandler fh;  

	    try {  

	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler("LogFile.log");  
	        logger.addHandler(fh);
	        
	        fh.setFormatter(new CustomFormatter());


	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
	    
	}
	
	/**
	 * Main launch method
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
