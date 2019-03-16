package main.application;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

	//private int[] keycardTypes; // Array that holds information about the location of the types of card
	//private String[] keycardWords; // array that holds information about the location of the words on the board

	boolean isStarting = true;
	boolean redTurn; // current turn for teams. true = red's, false = blue's
	boolean redWinner;
	boolean isGameOver = false;

	private int redCardsLeft;
	private int blueCardsLeft;

	public GameManager(Board board) {
		this.redOperative = new Operative(1, 1);
		this.blueOperative = new Operative(0, 1);
		this.redSpymaster = new Spymaster(1);
		this.blueSpymaster = new Spymaster(0);

		this.board = board;

		// Setting strategies for operatives.
		setOperativeStrategy(redOperative, new PickNextCardStrategy(board));
		setOperativeStrategy(blueOperative, new PickRandomCardStrategy(board));

		// Setting strategies for Spymaster
		setPlayerStrategy(redSpymaster, new SmartHintStrategy(board, redOperative,2));
		setPlayerStrategy(blueSpymaster, new SmartHintStrategy(board, blueOperative,3));

	}

	/**
	 * Set spymaster strategy
	 * @param player
	 * @param strategy
	 */
	private void setPlayerStrategy(Spymaster player, SmartHintStrategy strategy) {
		player.setStrategy(strategy);
	}

	/**
	 * Designate a strategy to an operative;
	 * 
	 * @param op
	 * @param strat
	 */
	public void setOperativeStrategy(Operative op, PickCardStrategy strat) {
		op.setStrategy(strat);
	}



	/**
	 * This method simulates a turn in a game session with two AIs. One turn
	 * includes a spymaster giving a hint and the operative choosing a card.
	 */
	public void playTurn() {
		HashMap<Integer, String> hint;
		//Determine which team goes first based on the number of cards
		//for each team that is predetermined in the keycard
		if (isStarting) {
			if (redCardsLeft >= blueCardsLeft) {
				redTurn = true;
			} else {
				redTurn = false;
			}
			isStarting = false;
		}

		// For iteration 1, there is minimal logic behind
		// AI's decision to pick cards.

		if (redTurn) {
			System.out.println("RED SPY - HINT");
			hint = redSpymaster.GiveHint();
			for (Map.Entry<Integer, String> foo :
					hint.entrySet()) {
				redOperative.setTries(foo.getKey());
			}
			//todo: send hint to ops

			do{
				System.out.println("\tRED PICK");
				redOperative.pickCard();
				if (board.getTypeFliped() == 2) {
					redCardsLeft--;
					System.out.println("\tRED GO AGAIN");
				} else if (board.getTypeFliped() == 3) {
					System.out.println("\tWRONG TEAM - CHANGE");
					blueCardsLeft--;
				} else if (board.getTypeFliped() == 0) {
					System.out.println("\tBYSTANDER - CHANGE");
					// byStander, do nothing
				} else if (board.getTypeFliped() == 1) {
					// assassin, ends game
					isGameOver = true;
					redWinner = false;
				}
				redOperative.decTries();
				if (redOperative.getTries() == 0){
					System.out.println("\tOUT OF TRIES");
				}
			}while (board.getTypeFliped() == 2 && redOperative.getTries() > 0);

		} else {
			System.out.println("BLUE SPY - HINT");
			hint = blueSpymaster.GiveHint();
			for (Map.Entry<Integer, String> foo :
					hint.entrySet()) {
				blueOperative.setTries(foo.getKey());
			}
			do{
				System.out.println("\tBLUE PICK");
				getBlueOperative().pickCard();
				if (board.getTypeFliped() == 2) {
					redCardsLeft--;
					System.out.println("\tWRONG TEAM - change");
				} else if (board.getTypeFliped() == 3) {
					blueCardsLeft--;
					System.out.println("\tBLUE GO AGAIN");
				} else if (board.getTypeFliped() == 0) {
					System.out.println("\tBYSTANDER - CHANGE");
					// byStander
				} else if (board.getTypeFliped() == 1) {
					// assassin
					isGameOver = true;
					redWinner = true;
				}
				blueOperative.decTries();
				if (blueOperative.getTries() == 0){
					System.out.println("\tOUT OF TRIES");
				}
			}while (board.getTypeFliped() == 3 && blueOperative.getTries() >0);
		}


		/**
		 * Above here, when either of the op choose a card,we will check what did
		 * him/her pick then we decrease the number of cards of this type. If it was
		 * byStander do nothing if it was assassin, game ends and we get a winner here
		 * 
		 * 
		 * CheckNumberOfCardsLeft() below to check if the game ends and who's winner. In
		 * boardController.handleEnterButtonAction before playTurn(), the function now
		 * will check if the game ends
		 * 
		 * note from zijian
		 */

		// Check how many cards left for each team and make a game over by disabling
		// enter button
		checkNumberOfCardsLeft();
		if (!isGameOver) {
			//System.out.println("********************END OF TURN!\n");
			Logger.getLogger("LOGGER").setLevel(Level.INFO);
			Logger.getLogger("LOGGER").info("END OF TURN!\n");
		} else {
			String side = (!redTurn) ? "Red" : "Blue";
			if (board.getTypeFliped() == 1) {
				side = "The assassin was picked, " + side;
			}
			//System.out.println("End of the game, " + side + " team won!");
			Logger.getLogger("LOGGER").setLevel(Level.INFO);
			Logger.getLogger("LOGGER").info("End of the game. " + side + " team won!");
		}
		// It's now the other team's turn
		redTurn = !redTurn;

	}

	// setters
	public void setAmountOfRedCards(int num) {
		this.redCardsLeft = num;
	}

	public void setAmountOfBlueCards(int num) {
		this.blueCardsLeft = num;
	}


	public void setBlueOperative(Operative blueOperative) {
		this.blueOperative = blueOperative;
	}
	// getter
	public Operative getBlueOperative() {
		return blueOperative;
	}
	public boolean isEnd() {
		return isGameOver;
	}

	public boolean isRedWinner() {
		return redWinner;
	}
	
	public Operative getRedOperative() {
		return this.redOperative;
	}

	// check if game end
	private void checkNumberOfCardsLeft() {
		if (redCardsLeft == 0) {
			isGameOver = true;
			redWinner = true;
		}
		if (blueCardsLeft == 0) {
			isGameOver = true;
			redWinner = false;
		}
	}
}
