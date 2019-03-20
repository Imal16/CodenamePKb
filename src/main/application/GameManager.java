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
		setOperativeStrategy(redOperative, new SmartPickCardStrategy(board, redOperative));
		setOperativeStrategy(blueOperative, new SmartPickCardStrategy(board, blueOperative));
		// Setting strategies for Spymaster
		setPlayerStrategy(redSpymaster, new SmartHintStrategy(board, redOperative));
		setPlayerStrategy(blueSpymaster, new SmartHintStrategy(board, blueOperative));

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


		if (redTurn) {
			hint = redSpymaster.GiveHint();
			for (Map.Entry<Integer, String> foo :
					hint.entrySet()) {
				redOperative.setTries(foo.getKey()+1);
			}

			do{
				redOperative.pickCard(hint);
				if (board.getTypeFliped() == 2) {
					redCardsLeft--;
                } else if (board.getTypeFliped() == 3) {
                    blueCardsLeft--;
                } else if (board.getTypeFliped() == 0) {
                    // byStander, do nothing
                } else if (board.getTypeFliped() == 1) {
                    // assassin, ends game
					isGameOver = true;
					redWinner = false;
				}
				redOperative.decTries();
				if (redOperative.getTries() == 0){
//					System.out.println("\tOUT OF TRIES");
				}
                checkNumberOfCardsLeft();
			}while (board.getTypeFliped() == 2 && redOperative.getTries() > 0 && !isGameOver);

		} else {
			hint = blueSpymaster.GiveHint();
			for (Map.Entry<Integer, String> foo :
					hint.entrySet()) {
				blueOperative.setTries(foo.getKey()+1);
			}
			do{
//				System.out.println("\tBLUE PICK");
				getBlueOperative().pickCard(hint);
				if (board.getTypeFliped() == 2) {
					redCardsLeft--;
                } else if (board.getTypeFliped() == 3) {
                    blueCardsLeft--;
                } else if (board.getTypeFliped() == 0) {
                    // byStander
                } else if (board.getTypeFliped() == 1) {
                    // assassin
                    isGameOver = true;
					redWinner = true;
				}
				blueOperative.decTries();
				if (blueOperative.getTries() == 0){
//					System.out.println("\tOUT OF TRIES");
				}
                checkNumberOfCardsLeft();
			}while (board.getTypeFliped() == 3 && blueOperative.getTries() >0 && !isGameOver);
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
		if (!isGameOver) {
			Logger.getLogger("LOGGER").setLevel(Level.INFO);
			Logger.getLogger("LOGGER").info("END OF TURN!\n");

			//switch turns
			redTurn = !redTurn;

			//end case
        } else {
            String side = (!redTurn) ? "Red" : "Blue";
            //assassin end case
            if (board.getTypeFliped() == 1) {
                side = "The assassin was picked, " + side;
            }
            //all cards used end case
            else{
                side = (redTurn) ? "Red" : "Blue";
            }
            Logger.getLogger("LOGGER").setLevel(Level.INFO);
            Logger.getLogger("LOGGER").info("End of the game. " + side + " team won!");
        }


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
