package business;

public class Card {
	private int type;//0 for Assassin, 1 for Bystander, 2 for red Spy and 3 for blue Spy.
	private String word;
	private boolean isFliped=false;
	
	public Card(String word) {
		this.word=word;
	}
	public void flip() {
		isFliped=!isFliped;
	}
	public int getType() {
		return type;
	}
	public String getWord() {
		return word;
	}
	public void setType(int type) {
		this.type=type;
	}
	public void setWord(String word) {
		this.word=word;
	}
	
}
