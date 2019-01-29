package codenames;

/**
 * 
 * @author chavind, Rosy
 * 
 * Every Card has a color and a word, which are the attributes for the class.
 * 
 */
public class Card {
	private String color;
	private String word;
	
	/**
	 * Default Constructor 
	 */
	public Card() {
		//super();
		this.color = "Wild";
		this.word = "Wild";
		}
	
	/**
	 * Parameter Constructor
	 */
	public Card(String color, String word) {
		//super();
		this.color = color;
		this.word = word;
	}
	
	/**
	 * Getters and setters
	 * 
	 */
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	/**
	 * ToString
	 */
	public String toString() {
		return "Color: " + color + ", Word: " + word;
	}
	
}
