/**
 * GUI Skeleton
 * 
 * Created by Rosy
 * Last updated on 18 Jan 2019, by Rosy
 * 
 * (Only for Eclipse) If code auto-complete is not working for JavaFX, refer to this site: 
 * https://www.chrisnewland.com/solved-eclipse-java-autocomplete-not-working-259
 * 
 * NOTE -> Be careful when adding imports, make sure you choose javafx and not java.awt
 */

package application;
	 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class CodenameGUI extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		//constants
		int PADDING = 20;
		int NUM_CARDS = 5;
		int SIZE_CARDS = 100;
		
		boolean turn = true; //current player's turn
		
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
			
			//sample of event handler for the turn button
			turn_button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					//TODO: By pressing on the button "End turn",
					//it should switch the value of the boolean variable "turn"
					//False if it was true, or vice versa
					
					//Issue encountered: because of the enclosing scope of this handler, 
					//we cannot directly change the local variable "turn"
				}
				
			});
			
			//set the elements within the BorderPane
			borderpane.setCenter(grid); //set the grid as the center
			borderpane.setTop(header); //set header as top
			borderpane.setRight(turn_button);
			
			//set grid padding and positioning
			grid.setPadding(new Insets(PADDING));
			grid.setHgap(PADDING);
			grid.setVgap(PADDING);
			grid.setAlignment(Pos.CENTER);
			
			for (int i = 0; i < NUM_CARDS; i++) {
				for (int j = 0; j < NUM_CARDS; j++) {
					
					//text sample
					Text text = new Text("Word");
					text.setFont(font_cards);
					
					//set alignment of text
					GridPane.setHalignment(text, HPos.CENTER);
					GridPane.setValignment(text, VPos.CENTER);
					
					//Cards in the grid
					//We could also use buttons instead of Rectangles
					Rectangle r = new Rectangle(SIZE_CARDS, SIZE_CARDS, Color.LIGHTGRAY);

				
					//sample of event handler for the cards
					//if a card is clicked, it changes color
			        r.setOnMouseClicked(new EventHandler<MouseEvent>()
			        {
			            @Override
			            public void handle(MouseEvent t) {
			            	if (turn)
			            		r.setFill(Color.RED);
			            	else
			            		r.setFill(Color.BLUE);
			            }
			        });
			        
			        
			        //Major issue: it would be better if the text would be linked with the card/rectangle, 
			        //here they are "disconnected" from each other. 
			        //So do you think we should use buttons instead?
			        
			        //Example: clicking on the text does not trigger the action handler, 
			        //so we'd have to create 2 handlers for the text AND the rectangle, which doesn't 
			        //make sense with respect to our goal
					
			        //add elements to the grid
			        grid.add(r, i, j);
					grid.add(text, i, j);
				
				}
			}
			
			//Create a scene for the application
			Scene scene = new Scene(borderpane,1000,800);
			primaryStage.setTitle("CodeName");
			primaryStage.setScene(scene); //stage contains a scene
			primaryStage.show(); //display
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
