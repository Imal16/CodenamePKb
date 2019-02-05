package main.models.business;

/**
 * 
 * @author William Ngo, Zijian Wang
 * @version 02/05/2019
 */
public class Board {
	
	Card[][] board;
	
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
	 * This method will flip a card at the specified row and col.
	 * For iteration 1, computer will simply pick a card at random/or next
	 * and there is no comparing of the colors or the word relation.
	 * 
	 * @param row
	 * @param col
	 */
	public void pickCardAt(int row, int col) {
		board[row][col].flip();
		System.out.println("Card at " + row + " row and " + col + " col");
	}
	
	/**
	 * This method will return a boolean indicating whether or not the card at 
	 * the specified row and col is already flipped.
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isCardFlippedAt(int row, int col) {
		return board[row][col].isFlipped;
	}
}
