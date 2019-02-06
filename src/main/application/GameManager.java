package main.application;

import java.io.IOException;

import main.controllers.KeyCardReader;
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
	
	private int[] keycardTypes; // Array that holds information about the location of the types of card
	private String[] keycardWords; // array that holds information about the location of the words on the board

	boolean redTurn; //Which team's current turn. true = red's, false = blue's
	boolean isGameOver = false;

	public int redCardsLeft = 9; // Forgot the actual number
	int blueCardsLeft = 8;

	public GameManager(Board board) {
		this.redOperative = new Operative(1, 1);
		this.blueOperative = new Operative(0, 1);
		this.redSpymaster = new Spymaster(1);
		this.blueSpymaster = new Spymaster(0);

		this.board = board;

		// Setting strategies for operatives.
		setOperativeStrategy(redOperative, new PickNextCardStrategy(board));
		setOperativeStrategy(blueOperative, new PickNextCardStrategy(board));
	}

	/**
	 * Designate a strategy to an operative;
	 * 
	 * @param op
	 * @param strat
	 */
	private void setOperativeStrategy(Operative op, PickCardStrategy strat) {
		op.setStrategy(strat);
	}

	/**
	 * This method simulates a turn in a game session with two AIs. One turn
	 * includes a spymaster giving a hint and the operative choosing a card.
	 */
	public void playTurn() {
		// For iteration 1, there is minimal logic behind
		// AI's decision to pick cards.
		if (redTurn) {
			redSpymaster.GiveHint();
			redOperative.pickCard();// add function of checking card color
		} else {
			blueSpymaster.GiveHint();
			blueOperative.pickCard();
		}

		// Check how many cards left for each team and make a gameover by disabling
		// enter button
		checkNumberOfCardsLeft();

		// It's now the other team's turn
		redTurn = !redTurn;

		System.out.println("********************END OF TURN!\n");
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
				//board_view.add(cardToAdd, i, j); // add card on the view
				board.setUpCardAt(cardToAdd, i, j); // add card in the board class

				keyCardArrayCounter++;
			}
		}

		keyCardArrayCounter = 0;
	}
	
	
	private void checkNumberOfCardsLeft() {
		// TODO
	}
}
