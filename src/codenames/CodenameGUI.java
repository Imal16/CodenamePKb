package codenames;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import codenames.CodenameGame;

/**
 * 
 * @authors Rosy, chavind
 * 
 * Last updated on 28 Jan 2019, by Rosy
 * 
 * Note: (Only for Eclipse) If code auto-complete is not working for JavaFX, refer to this site: 
 * https://www.chrisnewland.com/solved-eclipse-java-autocomplete-not-working-259
 * 
 * Warning: Be careful when adding imports, make sure you choose javafx and not java.awt
 * 
 */
public class CodenameGUI extends Application {

	Text[][] text= new Text[5][5];
	boolean turn = true;
	static int blue = 0;
	static int red = 0;
	
	public void start(Stage primaryStage) {
	
	//constants
	int PADDING = 20;
	int NUM_CARDS = 5;
	int SIZE_CARDS = 100;

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
			
			Button Keycard = new Button("View KeyCard");
			
			/*
			 * Handler for the turn button - Currently not functional
			 */
			turn_button.setOnAction(e ->{
				changeturn();
			});
			
			/*
			 * Handler for clicking on a card.
			 */
			Keycard.setOnMousePressed(e ->{
				System.out.println("Pressed");
				for (int i = 0; i < NUM_CARDS; i++) {
					for (int j = 0; j < NUM_CARDS; j++) {
						//text = new Text(CodenameGame.cards[i][j].getWord().toString());
						switch(CodenameGame.cards[i][j].getColor()) {
							case("Red"): text[i][j].setFill(Color.RED); break;
							case("Blue"): text[i][j].setFill(Color.BLUE); break;
							case("Black"): text[i][j].setFill(Color.YELLOW); break;
						}
						
					}
				}
			});
			
			/*
			 * Handler for clicking on neutral card.
			 */
			Keycard.setOnMouseReleased(e -> {
				for (int i = 0; i < NUM_CARDS; i++) {
					for (int j = 0; j < NUM_CARDS; j++) {
						//text = new Text(CodenameGame.cards[i][j].getWord().toString());
						text[i][j].setFill(Color.BLACK); 				
					}
				}
			});
			
			
			//set the elements within the BorderPane
			borderpane.setCenter(grid); //set the grid as the center
			borderpane.setTop(header); //set header as top
			borderpane.setRight(turn_button);
			borderpane.setLeft(Keycard);
			
			//set grid padding and positioning
			grid.setPadding(new Insets(PADDING));
			grid.setHgap(PADDING);
			grid.setVgap(PADDING);
			grid.setAlignment(Pos.CENTER);
			
			
			/*
			 * For each card on the board.
			 */
			for ( int i = 0; i < NUM_CARDS; i++) {
				for (int j = 0; j < NUM_CARDS; j++) {
					
					//text sample
					text[i][j] = new Text(CodenameGame.cards[i][j].getWord().toString());
					
					//set alignment of text
					GridPane.setHalignment(text[i][j], HPos.CENTER);
					GridPane.setValignment(text[i][j], VPos.CENTER);
					
					//Cards in the grid
					//We could also use buttons instead of Rectangles
					Rectangle r = new Rectangle(SIZE_CARDS, SIZE_CARDS, Color.LIGHTGRAY);

					final int k =i;
					final int l =j;
			
					/*
					 * Handler for the cards. If a card is clicked, it changes color
					 */
			        r.setOnMouseClicked(e -> {
			        	if(blue != 8) {
			        		
			        		if(red != 9) {
			        			
			        			if (turn == true) {
			        				if((CodenameGame.cards[k][l].getColor().equals("Black"))) {
			        					r.setFill(Color.YELLOW);
			        					System.out.println("GameOver");
			        					head.setText("GameOver");
			        				}
			        				else if ((CodenameGame.cards[k][l].getColor().equals("Wild"))) {
			        					r.setFill(Color.GRAY);
			        					head.setText("You hit a bystander");
		        					}
			        				else if((CodenameGame.cards[k][l].getColor().equals("Blue"))) {
			        					r.setFill(Color.BLUE);
			        					color("Blue");
			        					changeturn();
			        					head.setText("You hit your rival's agent");
			        				}
			        				else {
			        					r.setFill(Color.RED);
			        					color("Red");
			        				}
		        				} else
		        					if((CodenameGame.cards[k][l].getColor().equals("Black"))) {
		        						r.setFill(Color.YELLOW);
		        						System.out.println("GameOver");			              			
		        						head.setText("GameOver");
	        						}
		        					else if((CodenameGame.cards[k][l].getColor().equals("Red"))) {
		        						r.setFill(Color.RED);
		        						color("Red");
		        						changeturn();
				              			head.setText("You hit your rival's agent");
			              			}
		        					else if((CodenameGame.cards[k][l].getColor().equals("Wild"))) {
		        						r.setFill(Color.GRAY);
		        						head.setText("You hit a bystander");
		              				}
		        					else {
		        						r.setFill(Color.BLUE);
		        						color("Blue");
	        						}
			        		}		        				 
			        		
			        		if(red == 9)head.setText("Red Wins");
			        		
			        	} else if (blue==8)head.setText("Blue Wins");

			        });

			        /*
			         * Add cards and words to the gridpane
			         */
			        grid.add(r, i, j);
					grid.add(text[i][j], i, j);
				
				}
			}
			
			
			/*
			 * FX stuff
			 */
			Scene scene = new Scene(borderpane,1000,800);
			primaryStage.setTitle("CodeName");
			primaryStage.setScene(scene); //stage contains a scene
			primaryStage.show(); //display
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Selects the card color.
	 * @param color
	 */
	public void color(String color) {
		if (color.equals("Blue")){
			blue++;
		}
		if (color.equals("Red")){
			red++;
		}
	}

	/**
	 * Changes the player turn.
	 */
	private void changeturn() {
		 if (turn == true) 
			 turn = false;
		 else 
			 turn = true;
	 }
	
	/**
	 * MAIN METHOD, RUNS THE APPLICATION.
	 * @param args
	 */
	public static void main(String[] args) {
		
		CodenameGame.main(args);
		launch(args);
		
		System.out.println(red+" "+blue);;

	}
	
}