package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import main.models.business.Board;
import main.models.business.Card;
import main.models.business.Operative;
import main.models.business.Spymaster;
import main.models.interfaces.PickRandomCardStrategy;

import java.util.HashMap;

/**
 *Unit testing for picking random card strategy
 *
 * @author William Ngo
 * @version 02/10/2019
 *
 */
class TestPickRandomCardStrategy {

	Board testBoard;
	Operative redOp;
	Operative blueOp;
	Spymaster redSpy;
	Spymaster blueSpy;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		JFXPanel jp = new JFXPanel();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * The board is populated before each test, thus resetting the state
	 * of all cards to their default unflipped state.
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		populateBoard();
	}

	@AfterEach
	void tearDown() throws Exception {
	}


	public TestPickRandomCardStrategy() {
		testBoard = new Board();
		redOp = new Operative(1, 1);
		blueOp = new Operative(0, 1);
		redSpy = new Spymaster(1);
		blueSpy = new Spymaster(0);
	}


	@Test
	void pickRandomTest() {
		redOp.setStrategy(new PickRandomCardStrategy(testBoard));
        HashMap<Integer, String> testHint = new HashMap<>();
        testHint.put(0,"A");
		redOp.pickCard(testHint);

		//Since a card has been pick at random, we cannot check for a specific card
		//Instead, we will check all the cards in the board and see if any of them has been picked.
		boolean cardHasBeenPicked = false;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(testBoard.board[i][j].isFlipped) {
					cardHasBeenPicked = true;
				}
			}
		}

		assertTrue(cardHasBeenPicked);
	}

	/**
	 * This test will see that if an operative picks a card at random but the
	 * card has already been flipped, the operative will
	 */
	@Test
	void pickRandomAlreadyFlippedTest() {

	}

	/**
	 * Utility method that populates the board with cards
	 */
	private void populateBoard() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Card c = new Card("card_" + i + "_" + j, 1);
				testBoard.setUpCardAt(c, i, j);
			}
		}
	}
}