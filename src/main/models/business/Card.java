package main.models.business;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * This class defines a Card. The Card has a word and a type.
 * 
 * @author Rosy
 * @version 01/02/2019
 */
public class Card extends StackPane {

	/*
	 * Word: word the card has Type: Bystander=0, Assassin=1, Red=2, Blue=3
	 */
	private int type;
	private Rectangle rec;
	private Text text;
	public boolean isFlipped;

	Word wordObj;

	private static final int CARD_SIZE = 100;
	private static final Color RED_COLOR = Color.RED;
	private static final Color BLUE_COLOR = Color.BLUE;
	private static final Color ASSASSIN_COLOR = Color.YELLOW;
	private static final Color BYSTANDER_COLOR = Color.LIGHTGREY;
	private static final Color UNFLIPPED_COLOR = Color.WHITE;

	/*
	 * Default constructor A Card with a default word with default type (BYSTANDER)
	 */
	public Card() {
		this.isFlipped = false;// Cards all start in not flipped state
		this.rec = new Rectangle(CARD_SIZE, CARD_SIZE, BYSTANDER_COLOR);
		this.text = new Text("ABC");
		this.wordObj = new Word();
		getChildren().addAll(rec, text);
	}

	/*
	 * Parameter constructor A Card with a type and a word
	 */
	public Card(Word word, int type) {
        this.wordObj = word;
		this.type = type;
		this.rec = new Rectangle(CARD_SIZE, CARD_SIZE, UNFLIPPED_COLOR);
		Text text = new Text(word.getWord());
		getChildren().addAll(rec, text);
	}

	public void flip() {
		isFlipped = !isFlipped;
		revealCardColor();
		Logger.getLogger("LOGGER").setLevel(Level.INFO);
		Logger.getLogger("LOGGER").info("Card flipped! Word: \"" + wordObj.getWord() + "\", COLOR number: " + type);
	}

	private void revealCardColor() {
		switch (this.type) {
		case 0:
			this.rec.setFill(BYSTANDER_COLOR);
			break;
		case 1:
			this.rec.setFill(ASSASSIN_COLOR);
			break;
		case 2:
			this.rec.setFill(RED_COLOR);
			break;
		case 3:
			this.rec.setFill(BLUE_COLOR);
			break;
		}
	}

	/*
	 * Setters and getters
	 */
	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}


	public Word getWord() {
		return this.wordObj;
	}

	/*
	 * String method
	 */
	@Override
	public String toString() {
		return "Card created. Word: " + getWord().getWord() + "Type: " + getType();
	}

}
