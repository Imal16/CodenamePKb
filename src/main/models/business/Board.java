package main.models.business;

public class Board {
	
	Card[][] board;
	
	public Board() {
		board = new Card[5][5];
	}
	
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
	
	public boolean isCardFlippedAt(int row, int col) {
		return board[row][col].isFlipped;
	}
}
