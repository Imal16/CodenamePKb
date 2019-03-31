package main.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
	Spymaster playerSpymaster;

	Board board;

	boolean isStarting = true;
	boolean redTurn; // current turn for teams. true = red's, false = blue's
	boolean redWinner;
	boolean isGameOver = false;
	
	private boolean isPlayerPlaying = false;
	private boolean isPlayerRed = false; //player is blue if false

	private int redCardsLeft;
	private int blueCardsLeft;
	
	private HashMap<Integer, String> hint;
	
	public GameManager(Board board) {
		
		splashScreen();
		
		this.redOperative = new Operative(1, 1);
		this.blueOperative = new Operative(0, 1);
		this.redSpymaster = new Spymaster(1);
		this.blueSpymaster = new Spymaster(0);
		
		if(isPlayerPlaying)
		{
			if(isPlayerRed)
				playerSpymaster = redSpymaster;
			else
				playerSpymaster = blueSpymaster;
		}

		this.board = board;

		// Setting strategies for operatives.
		setOperativeStrategy(redOperative, new SmartPickCardStrategy(board, redOperative));
		setOperativeStrategy(blueOperative, new SmartPickCardStrategy(board, blueOperative));
		// Setting strategies for Spymaster
		setPlayerStrategy(redSpymaster, new SmartHintStrategy(board, redOperative));
		setPlayerStrategy(blueSpymaster, new SmartHintStrategy(board, blueOperative));
		
		//if the player's turn is first, get a hint
		if(isPlayerPlaying) {
			hint = playerSpymaster.GiveHint();
		}
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

	public void splashScreen() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("CODENAMES");
		alert.setHeaderText("Select the operative you would liket to play as, or let the AI play.");
		
		ButtonType blueOpButton = new ButtonType("Blue Operative");
		ButtonType redOpButton = new ButtonType("Red Operative");
		ButtonType aiButton = new ButtonType("AI");
		
		alert.getButtonTypes().setAll(blueOpButton, redOpButton, aiButton);

		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() != aiButton)
		{
			isPlayerPlaying = true;
			if(result.get() == redOpButton)
				isPlayerRed = true;
		}
	}


	/**
	 * This method simulates a turn in a game session with two AIs. One turn
	 * includes a spymaster giving a hint and the operative choosing a card.
	 */
	public void playTurn() {
		//Determine which team goes first based on the number of cards
		//for each team that is predetermined in the keycard
		if (isStarting && !isPlayerPlaying) {
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
            else {
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
	
	public void removeRedCard() {
		this.redCardsLeft -= 1;
	}

	public void setAmountOfBlueCards(int num) {
		this.blueCardsLeft = num;
	}

	public void removeBlueCard() {
		this.blueCardsLeft -= 1;
	}

	public void setBlueOperative(Operative blueOperative) {
		this.blueOperative = blueOperative;
	}
	
	public void setGameOver() {
		isGameOver = true;
	}
	
	public void changeTurn() {
		redTurn = !redTurn;
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
	
	public boolean isPlayerPlaying() {
		return isPlayerPlaying;
	}
	
	public boolean isPlayerRed() {
		return isPlayerRed;
	}
	
	public boolean isPlayerTurn() {
		if(isPlayerRed == redTurn)
			return true;
		return false;
	}

	// check if game end
	public void checkNumberOfCardsLeft() {
		if (redCardsLeft == 0) {
			isGameOver = true;
			redWinner = true;
		}
        if (blueCardsLeft == 0) {
            isGameOver = true;
			redWinner = false;
		}
	}
	
	public String WhosTurnIsIt() {
		if(redTurn) {
			return "Red";
		} else {
			return "Blue";
		}
	}
	
	public void givePlayerHint() {
		if(isPlayerPlaying && isPlayerTurn()) {
			hint = playerSpymaster.GiveHint();
		}
	}

	public int getRedCardsLeft() {
		return redCardsLeft;
	}

	public int getBlueCardsLeft() {
		return blueCardsLeft;
	}
	
}
