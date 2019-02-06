package main.application;

import main.models.business.*;
import main.models.interfaces.*;

/**
 * The gameManager holds information about a game session such as whose turn is
 * it, the score for each team and when should a game finish.
 * 
 * @author William, Zijian
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
	
	boolean isStarting=true;
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
		setOperativeStrategy(redOperative, new PickRandomCardStrategy(board));
		setOperativeStrategy(blueOperative, new PickRandomCardStrategy(board));
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
		if(isStarting) {
			if(redCardsLeft>blueCardsLeft) {
				redTurn=true;
			}else {
				redTurn=false;
			}
			isStarting=false;
		}
		
		//For iteration 1, there is minimal logic behind 
		//AI's decision to pick cards.

		if(redTurn) {
			redSpymaster.GiveHint();
			redOperative.pickCard();
			if(board.getTypeFliped()==2) {
				redCardsLeft--;
			} else if(board.getTypeFliped()==3) {
				blueCardsLeft--;
			} else if(board.getTypeFliped()==0) {
				//byStander
			} else if(board.getTypeFliped()==1) {
				//assassin
				isGameOver=true;
			}
			
		}
		else {
			blueSpymaster.GiveHint();
			blueOperative.pickCard();
			if(board.getTypeFliped()==2) {
				redCardsLeft--;
			} else if(board.getTypeFliped()==3) {
				blueCardsLeft--;
			} else if(board.getTypeFliped()==0) {
				//byStander
			} else if(board.getTypeFliped()==1) {
				//assassin
				isGameOver=true;
				if(redTurn) {
					redWinner=false;
				}else {
					redWinner=true;
				}
			}
		}
		
		
		/**
		 * 
		 * checkNumberOfCardsLeft() below to check if the game ends and who's winner
		 * 	in playTurn() before , the function now will check if the game ends
		 */

		
		
		//Check how many cards left for each team and make a game over by disabling enter button
		checkNumberOfCardsLeft();
		if(!isGameOver) {			
		System.out.println("********************END OF TURN!\n");}
		else {
			String side = (!redTurn) ? "red" : "blue";
			 if(board.getTypeFliped()==1) {
				 side="the assassin was picked, "+side;
			 }
			System.out.println("End of the game, "+side+" team won!");
		}
		//It's now the other team's turn 
		redTurn = !redTurn;
		
	}
	//setters
	public void setRC(int num) {
		this.redCardsLeft=num;
	}
	public void setBC(int num) {
		this.blueCardsLeft=num;
	}
	//getter
	public boolean isEnd() {
		return isGameOver;
	}
	public boolean isRedWinner() {
		return redWinner;
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
		
	}
}
