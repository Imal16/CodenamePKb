package main.models.interfaces;

import java.util.Random;
import main.models.business.*;

public class PickRandomCardStrategy implements PickCardStrategy {
	public Board board;
	
	private Random r = new Random();

	public PickRandomCardStrategy(Board board) {
		this.board = board;
	}
	
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

}
