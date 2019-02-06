package main.application;

import java.io.IOException;

import main.controllers.KeyCardReader;
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
	private Operative blueOperative;
	Spymaster redSpymaster;
	Spymaster blueSpymaster;

	Board board;

	private int[] keycardTypes; // Array that holds information about the location of the types of card
	private String[] keycardWords; // array that holds information about the location of the words on the board

	boolean isStarting = true;
	boolean redTurn; // current turn for teams. true = red's, false = blue's
	boolean redWinner;
	boolean isGameOver = false;

	private int redCardsLeft;
	private int blueCardsLeft;

	public GameManager(Board board) {
		this.redOperative = new Operative(1, 1);
		this.setBlueOperative(new Operative(0, 1));
		this.redSpymaster = new Spymaster(1);
		this.blueSpymaster = new Spymaster(0);

		this.board = board;

		// Setting strategies for operatives.
		// setOperativeStrategy(redOperative, new PickNextCardStrategy(board));
		// setOperativeStrategy(blueOperative, new PickNextCardStrategy(board));

		// Setting strategies for operatives.
		setOperativeStrategy(redOperative, new PickRandomCardStrategy(board));
		setOperativeStrategy(getBlueOperative(), new PickRandomCardStrategy(board));

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
			redSpymaster.GiveHint();
			redOperative.pickCard();
			if (board.getTypeFliped() == 2) {
				redCardsLeft--;
			} else if (board.getTypeFliped() == 3) {
				blueCardsLeft--;
			} else if (board.getTypeFliped() == 0) {
				// byStander
			} else if (board.getTypeFliped() == 1) {
				// assassin
				isGameOver = true;
				redWinner = false;
			}

		} else {
			blueSpymaster.GiveHint();
			getBlueOperative().pickCard();
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
			System.out.println("********************END OF TURN!\n");
		} else {
			String side = (!redTurn) ? "Red" : "Blue";
			if (board.getTypeFliped() == 1) {
				side = "The assassin was picked, " + side;
			}
			System.out.println("End of the game, " + side + " team won!");
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

	/**
	 * Method to be used later, sets up the cards in the board class
	 */
	private void setupBoard() {
		// Reading keycard text file
		try {
			// Create a Keycard reader with the Keycard text file
			KeyCardReader reader = new KeyCardReader("resources/keycards/keycard6.txt", "resources/keycards/words.txt");

			// keycardTypes array will be populated with information from text file.
			keycardTypes = reader.readKeycardTypes();
			keycardWords = reader.readKeycardWords();

		} catch (IOException e) {
			System.err.println("Cannot read file.");
		}

		int keyCardArrayCounter = 0;

		// Populate board with infos from keycard arrays
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				// System.out.println("Add card");

				// Create a card with a word and a type
				Card cardToAdd = new Card(keycardWords[keyCardArrayCounter], keycardTypes[keyCardArrayCounter]);
				// game.redCardsLeft++;
				// board_view.add(cardToAdd, i, j); // add card on the view
				board.setUpCardAt(cardToAdd, i, j); // add card in the board class

				keyCardArrayCounter++;
			}
		}

		keyCardArrayCounter = 0;
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
