package main.models.interfaces;

import java.util.HashMap;
import java.util.Random;
import main.models.business.*;

/**
 * Strategy for picking a random card on the board
 * 
 * @author William, Zijian
 * @version 02/05/2019
 *
 */
public class PickRandomCardStrategy implements PickCardStrategy {

	public Board board;
	private Random r = new Random();

	public PickRandomCardStrategy(Board board) {
		this.board = board;
	}
	
	/**
	 * This method picks a random card on the board.
	 */
	@Override
	public void execute() {
		int row = r.nextInt(5);
		int col = r.nextInt(5);
		
		//Check if card at row and col is already flipped
		//If it is, pick a new row and col
		while(board.isCardFlippedAt(row, col)) {
			row = r.nextInt(5);
			col = r.nextInt(5);
		}
		
		board.pickCardAt(row, col);
	}

	@Override
	public void execute(HashMap<Integer, String> hint) {
		execute();
	}


}
