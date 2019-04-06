package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import main.models.business.Board;
import main.models.business.Card;
import main.models.business.CardTypes;
import main.models.business.RelationGraph;

/**
 * Unit testing for board
 * @author William Ngo
 * @version 10/02/2019
 *
 */
class TestBoard {

	Board testBoard;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//This sets up internal graphic for jfx testing
		//If this line is not here, unit testing doesn't work
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
		//resetCards();
	}

	public TestBoard() {
		testBoard = new Board();
	}

	@Test
	void setUpCardAtTest() {
		Card cardToBeSet = new Card("newCard", 1);
		testBoard.setUpCardAt(cardToBeSet, 0, 0);

		Card retrievedCard = testBoard.board[0][0];

		assertEquals(cardToBeSet, retrievedCard);
	}

	@Test
	void isCardFlippedAtTest() {
		testBoard.board[0][3].isFlipped = true;

		boolean hasCardBeenFlipped = testBoard.isCardFlippedAt(0, 3);
		assertTrue(hasCardBeenFlipped);
	}

	@Test
	void pickCardAtTest(){
		testBoard.pickCardAt(0, 1);

		boolean hasCardBeenPicked = testBoard.isCardFlippedAt(0, 1);
		assertTrue(hasCardBeenPicked);
	}

	@Test
	void getCardAtTest() {
		Card card1 = testBoard.getCardAt(0, 0);
		Card retrievedCard1 = testBoard.board[0][0];
		assertEquals(card1, retrievedCard1);
		
		Card card2 = testBoard.getCardAt(1, 0);
		Card retrievedCard2 = testBoard.board[1][0];
		assertEquals(card2, retrievedCard2);
		
		Card card3 = testBoard.getCardAt(2, 0);
		Card retrievedCard3 = testBoard.board[2][0];
		assertEquals(card3, retrievedCard3);
		assertNotEquals(card1, card2);
	}

	@Test
	void getRedGraphTest() {
		RelationGraph redGraph = testBoard.getRedGraph();
		assertEquals(redGraph, testBoard.getRedGraph());
	}
	
	@Test
	void getBlueGraphTest() {
		RelationGraph blueGraph = testBoard.getBlueGraph();
		assertEquals(blueGraph, testBoard.getBlueGraph());
	}
	
	@Test
	void getTeamCardsTest() {
		List<String> redCards = testBoard.getTeamCards(CardTypes.RED);
		assertEquals(testBoard.getRedCards(), redCards);
		
		List<String> blueCards = testBoard.getTeamCards(CardTypes.BLUE);
		assertEquals(testBoard.getBlueCards(), blueCards);
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

	private void resetCards() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				testBoard.board[i][j].isFlipped = false;
			}
		}
	}
}