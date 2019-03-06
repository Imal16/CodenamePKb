package test;

import static org.junit.jupiter.api.Assertions.*;

import main.models.business.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import main.models.interfaces.PickNextCardStrategy;

import java.util.HashSet;

/**
 *Unit testing for picking next card strategy
 *
 * @author William Ngo
 * @version 02/10/2019
 *
 *
 * EDIT: altered Unit testing, integrating new word class functionality
 * @author John Paulo Valerio
 * @version 02/27/2019
 */

class TestPickNextCardStrategy {

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

	@BeforeEach
	void setUp() throws Exception {
		populateBoard();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	public TestPickNextCardStrategy() {
		testBoard = new Board();
		redOp = new Operative(1, 1);
		blueOp = new Operative(0, 1);
		redSpy = new Spymaster(1);
		blueSpy = new Spymaster(0);
	}

	@Test
	void PickFirstCardTest() {
		// The pick next strategy picks each card individually starting from the first
		// card at (0,0)
		redOp.setStrategy(new PickNextCardStrategy(testBoard));
		redOp.pickCard();

		//Fisrt card at [0][0] should be flipped
		boolean hasCardBeenPicked = testBoard.board[0][0].isFlipped;
		assertTrue(hasCardBeenPicked);
	}

	@Test
	void PickNextCardTest() {
		//Operator will pick the first card then it will pick the next card
		redOp.setStrategy(new PickNextCardStrategy(testBoard));
		redOp.pickCard();
		redOp.pickCard();

		//Since it already picked card at [0][0], the next card is [1][0]
		boolean hasCardBeenPicked = testBoard.board[1][0].isFlipped;
		assertTrue(hasCardBeenPicked);
	}

	@Test
	void PickUnavailableCard() {
		// flip the first card
		testBoard.board[0][0].isFlipped = true;

		// When operator picks the first card, it should see that it's already
		// been picked
		redOp.setStrategy(new PickNextCardStrategy(testBoard));
		redOp.pickCard();

		// So it will pick the following card instead
		boolean isSecondCardPicked = testBoard.board[1][0].isFlipped;
		assertTrue(isSecondCardPicked);
	}

	/**
	 * Utility method that populates the board with cards
	 */
	private void populateBoard() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Card c = new Card(new Word("card_" + i + "_" + j,new HashSet()), 1);
				testBoard.setUpCardAt(c, i, j);
			}
		}
	}
}
