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
	boolean redWinner;
	boolean isGameOver = false;
	
	private int redCardsLeft; //Forgot the actual number
	private int blueCardsLeft;
	
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
	//setters
	public void setRCLeft(int num) {
		this.redCardsLeft=num;
	}
	public void setBLeft(int num) {
		this.blueCardsLeft=num;
	}
	//adder
	public void addRCLeft() {
		this.redCardsLeft++;
	}
	public void addBCLeft() {
		this.blueCardsLeft++;
	}
	//Minus
	public void minusRCLeft() {
		this.redCardsLeft--;
	}
	public void minusBCLeft() {
		this.blueCardsLeft--;
	}
	//check if game end
	private void checkNumberOfCardsLeft() {
		if(redCardsLeft==0) {
			isGameOver=true;
			redWinner=true;
		}
		if(blueCardsLeft==0) {
			isGameOver=true;
			redWinner=false;
		}
		if(redCardsLeft==0) {
			isGameOver=true;
			if(redTurn) {
				redWinner=false;
			}else {
				redWinner=true;
			}
		}
	}
}
