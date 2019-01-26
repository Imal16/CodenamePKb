package application;

import javafx.geometry.HPos; 
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Card extends Rectangle {
	
	private Text word;
	private Rectangle card;
	private Color color;
	private int SIZE_CARDS = 100;
	
	/**
	 * Default
	 */
	public Card() {
		this.card = new Rectangle(SIZE_CARDS, SIZE_CARDS, Color.LIGHTGRAY);
		this.word = new Text("Word");
		GridPane.setHalignment(this.card, HPos.CENTER);
		GridPane.setValignment(this.card, VPos.CENTER);
	}
	
	public Card(Text word, Color color) {
		this.card = new Rectangle(SIZE_CARDS, SIZE_CARDS, color);
		this.word = word;
		GridPane.setHalignment(this.card, HPos.CENTER);
		GridPane.setValignment(this.card, VPos.CENTER);
	}
	
	public void setColor(Color color) {
		this.card.setFill(color);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setWord(Text word) {
		this.word = word;
	}
	
	public Text getWord() {
		return this.word;
	}
	
	public void setCard(Rectangle card) {
		this.card = card;
	}
	
	public Rectangle getCard() {
		return this.card;
	}

}
