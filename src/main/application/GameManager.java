package main.application;

import main.models.business.*;
import main.models.interfaces.*;

/**
 * The gameManager holds information about a game session such as whose turn is
 * it, the score for each team and when should a game finish.
 * 
 * @author William
 * @version 02/05/2019
 *
 */
public class GameManager {
	Operative redOperative;
	Operative blueOperative;
	Spymaster redSpymaster;
	Spymaster blueSpymaster;

	Board board;
	CommandManager cmd;

	boolean redTurn; // current turn for teams. true = red's, false = blue's
	boolean isGameOver = false;
	
	int redCardsLeft = 9; //Forgot the actual number
	int blueCardsLeft = 8;
	
	public GameManager(Board board) {
		this.redOperative = new Operative(1, 1);
		this.blueOperative = new Operative(0, 1);
		this.redSpymaster = new Spymaster(1);
		this.blueSpymaster = new Spymaster(0);
		
		this.board = board;
		cmd = new CommandManager();//unsure if used or not
		
		//Setting strategies for operatives.
		setOperativeStrategy(redOperative, new PickNextCardStrategy(board));
		setOperativeStrategy(blueOperative, new PickNextCardStrategy(board));
	}

	/**
	 * Designate a strategy to an operative;
	 * @param op
	 * @param strat
	 */
	private void setOperativeStrategy(Operative op, PickCardStrategy strat) {
		op.setStrategy(strat);
	}
	
	/**
	 * This method simulates a turn in a game session with two AIs.
	 * One turn includes a spymaster giving a hint and the operative choosing a card.
	 */
	public void playTurn() {
		//For iteration 1, there is minimal logic behind 
		//AI's decision to pick cards.
		if(redTurn) {
			redSpymaster.GiveHint();
			redOperative.pickCard();
		}
		else {
			blueSpymaster.GiveHint();
			blueOperative.pickCard();
		}
		
		//Check how many cards left for each team and make a gameover by disabling enter button
		checkNumberOfCardsLeft();
		
		//It's now the other team's turn
		redTurn = !redTurn;
		

		System.out.println("********************END OF TURN!\n");
	}
	
	private void checkNumberOfCardsLeft() {
		//TODO
	}
}
