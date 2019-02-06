package main.models.business;

/**
 * Board is represented by a 2 Dimensional Card array. It is able to populate
 * itself with card objects and is able to pick a card at a given coordinate.
 * 
 * @author William Ngo, Zijian Wang
 * @version 02/05/2019
 */
public class Board {

	public Card[][] board;

	private int typeFliped;// to passing the card type value to GameManager
	private int redCardsNum;
	private int blueCardsNum;

	public Board() {
		board = new Card[5][5];
	}

	/**
	 * This method inserts a card object in the board (Card 2D Array) at the
	 * specified row and column
	 * 
	 * @param card
	 * @param row
	 * @param col
	 */
	public void setUpCardAt(Card card, int row, int col) {
		this.board[row][col] = card;
	}

	/**
	 * This method will flip a card at the specified row and col. For iteration 1,
	 * computer will simply pick a card at random/or next and there is no comparing
	 * of the colors or the word relation.
	 * 
	 * @param row
	 * @param col
	 */
	public void pickCardAt(int row, int col) {
		board[row][col].flip();
		if (board[row][col].getType() == 2) {
			typeFliped = 2;
		} else if (board[row][col].getType() == 3) {
			typeFliped = 3;
		} else if (board[row][col].getType() == 0) {
			typeFliped = 0;
		} else if (board[row][col].getType() == 1) {
			typeFliped = 1;
		} // checking the type, ready to pass to gameManager
		System.out.println("Card at " + row + " row and " + col + " col");
	}

	// getter
	public int getTypeFliped() {
		return this.typeFliped;
	}

	/**
	 * This method will return a boolean indicating whether or not the card at the
	 * specified row and col is already flipped.
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isCardFlippedAt(int row, int col) {
		return board[row][col].isFlipped;
	}

	// getters & setters
	public int getBlueCardsNum() {
		return blueCardsNum;
	}

	public void setBlueCardsNum(int blueCardsnum) {
		this.blueCardsNum = blueCardsnum;
	}

	public int getRedCardsNum() {
		return redCardsNum;
	}

	public void setRedCardsNum(int redCardsNum) {
		this.redCardsNum = redCardsNum;
	}
}
