package res;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * This class defines a Card. The Card has a word and a type. 
 * @author Rosy
 *
 */
public class Card extends StackPane {

	/*
	 * Word: word the card has
	 * Type: Bystander=0, Assassin=1, Red=2, Blue=3
	 */
	private String word;
	private int type;
	private Rectangle rec;
	private Text text;
	
	private static final int CARD_SIZE = 100;
	private static final Color RED_COLOR = Color.RED;
	private static final Color BLUE_COLOR = Color.BLUE;
	private static final Color ASSASSIN_COLOR = Color.YELLOW;
	private static final Color BYSTANDER_COLOR = Color.LIGHTGREY;
	
	/*
	 * Default constructor
	 * A Card with a default word with default type (BYSTANDER)
	 */
	public Card() {
		System.out.println("Card()");
		this.rec = new Rectangle(CARD_SIZE, CARD_SIZE,BYSTANDER_COLOR);
		this.text = new Text("ABC");
		getChildren().addAll(rec,text);
	}
	
	/*
	 * Parameter constructor
	 * A Card with a type and a word
	 */
	public Card(String word, int type) {
		System.out.println("Card(word,type)");
		setWord(word);
		setType(type);

		Text text = new Text(word);
	}
	
	public Card(Card card) {
		System.out.println("Card(card)");
		setWord(card.getWord());
		setType(card.getType());
		
		Text text = new Text(word);
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
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public String getWord() {
		return this.word;
	}
	
//	public void setCard(StackPane card) {
//		this.card = card;
//	}
//	
//	public StackPane getCard() {
//		return this.getCard();
//	}
	
	/*
	 * String method
	 */
	@Override
	public String toString() {
		return "Card created. Word: " + getWord() + "Type: " + getType();
	}

}
