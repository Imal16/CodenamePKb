package test;

import static org.junit.jupiter.api.Assertions.*;

import it.unimi.dsi.fastutil.Hash;
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
import main.models.interfaces.PickNextCardStrategy;

import java.util.HashMap;

/**
 *Unit testing for picking next card strategy
 *
 * @author William Ngo
 * @version 02/10/2019
 *
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
        HashMap<Integer, String> testHint = new HashMap<>();
        testHint.put(0,"A");
		redOp.pickCard(testHint);

		//Fisrt card at [0][0] should be flipped
		boolean hasCardBeenPicked = testBoard.board[0][0].isFlipped;
		assertTrue(hasCardBeenPicked);
	}

	@Test
	void PickNextCardTest() {
		//Operator will pick the first card then it will pick the next card
		redOp.setStrategy(new PickNextCardStrategy(testBoard));
        HashMap<Integer, String> testHint = new HashMap<>();
        testHint.put(0,"A");
		redOp.pickCard(testHint);
		redOp.pickCard(testHint);

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
        HashMap<Integer, String> testHint = new HashMap<>();
        testHint.put(0,"A");
		redOp.pickCard(testHint);

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
				Card c = new Card("card_" + i + "_" + j, 1);
				testBoard.setUpCardAt(c, i, j);
			}
		}
	}
}