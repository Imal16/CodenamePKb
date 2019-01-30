/**
 * GUI CodeName
 * 
 * Created by Rosy
 * Last updated on 23 Jan 2019, by Rosy
 * 
 * (Only for Eclipse) If code auto-complete is not working for JavaFX, refer to this site: 
 * https://www.chrisnewland.com/solved-eclipse-java-autocomplete-not-working-259
 * 
 * NOTE -> Be careful when adding imports, make sure you choose javafx and not java.awt
 */

package application;

import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import business.*;


public class CodenameGUI extends Application {
	
	//constants
	final int PADDING = 20;
	final int NUM_CARDS = 5;	
	boolean turn = true;
	
	Operative op;
	Spymaster sm;
	
	@Override
	public void start(Stage primaryStage) {

		try {

			//create a BorderPane
			BorderPane borderpane = new BorderPane();
			
			//create grid for the (25) main cards
			GridPane grid = new GridPane();

			//Create fonts for the texts
			Font font_cards = Font.font("Arial", 20);
			Font font_title = Font.font("Arial", 60);
			
			//Create a header
			Text head = new Text("CODENAME");
			head.setFont(font_title);
			HBox header = new HBox(head); //Hbox for the header
			header.setAlignment(Pos.CENTER);
			
			
			//Create a button for players' turns
			Button turn_button = new Button("End turn");
			BorderPane.setMargin(turn_button, new Insets(50));
			
			//Event handler for the turn button
			//Note the shorthand notation using 'e ->'
			turn_button.setOnAction(e -> {
				//Changes turn (Red or Blue)
				System.out.println("Button event...");
				//changeTurn();
				
			});
			
			//Set the position of the elements within the BorderPane
			borderpane.setCenter(grid); //set the grid as the center
			borderpane.setTop(header); //set the header as top
			borderpane.setRight(turn_button); //set the button at the right
			
			//Set the grid padding and positioning
			grid.setPadding(new Insets(PADDING));
			grid.setHgap(PADDING);
			grid.setVgap(PADDING);
			grid.setAlignment(Pos.CENTER);
			
			
			/*-----------------------------
			 * CARD GENERATION BEGIN
			 * For each card in the 5x5 grid...
			 * -----------------------------*/
			for (int i = 0; i < NUM_CARDS; i++) {
				for (int j = 0; j < NUM_CARDS; j++) {

					//Generate a random character (TESTING PURPOSES)
					Random r = new Random();
					char c = (char)(r.nextInt(26) + 'a');
					
					//Create a Text with a Font
					Text text = new Text(Character.toString(c));
					text.setFont(font_cards);
				
					//Create a Card
					Card card = new Card(text, Color.LIGHTGRAY);
					
					//Create a Stackpane for the Card
					StackPane stack = new StackPane();
					
					//Event handler for the Card
					//Note the shorthand notation using 'e ->'
			       	stack.setOnMouseClicked(e -> {
			       		//If a Card is clicked on, it becomes red or blue, depending the turn.
		            	if (turn)
		            		card.setColor(Color.RED);
		            	else
		            		card.setColor(Color.BLUE);
			        });
					
			        //Add the Card to the Stackpane
			        stack.getChildren().addAll(card.getCard(), card.getWord());
			        //Add the stack to the Gridpane
			        grid.add(stack,i,j);
				
				}
			}
			/*-----------------------------
			 * CARD GENERATION END
			 * -----------------------------*/
			
			//---JAVAFX NORMAL/necessary STUFF
			//Create a scene for the application
			Scene scene = new Scene(borderpane,1000,800);
			primaryStage.setTitle("CodeName");
			primaryStage.setScene(scene); //stage contains a scene
			primaryStage.show(); //display
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Changes the player turn (Red or Blue)
	 */
	private void changeTurn() {
		
		if (turn) {
			turn = false;
			System.out.println("Blue's turn!");
		}
		else {
			turn = true;
			System.out.println("Red's turn!");
		}
	}
	
	/**
	 * Launcher
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
