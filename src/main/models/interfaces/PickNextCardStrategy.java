package main.models.interfaces;
import main.models.business.*;

/**
 * This strategy simply picks each card one by one
 * 
 * @author William Ngo, Zijian Wang
 * @version 02/05/2019
 *
 */
public class PickNextCardStrategy implements PickCardStrategy{
	public Board board;
	
	//Next card strategy picks each card on the board one by one
	//In a linear manner
	private int row;
	private int col;
	
	public PickNextCardStrategy(Board board) {
		this.board = board;
		this.row = -1; //Start one card behind the first card on the upper left
		this.col = 0; 
	}
	
	/**
	 * This implementation simply picks the first card on the board that is located
	 * at [0][0] and any future execution will pick the next card that follows the current card.
	 * If the next card has already been flipped (by the other team) then it will skip it and
	 * flip the next one that follows.
	 */
	@Override
	public void execute(){
		//Normally, check the next card, but if you're on the last col, don't
		if(row != 4) row++;
		
		//If next card is already flipped, check for the following one.
		while(board.isCardFlippedAt(row, col)) {
			if(row == 4) {
				System.out.println("Going to next row!");
				col++; //if on last column, go to next row
				row = 0; //and go back to first col
			}
			else {
				System.out.println("Next card is already flipped! Going to following one!");
				row++;
			}
		}
		
		board.pickCardAt(row, col);
	}

	@Override
	public void execute(String word){
		execute();
	}
}
