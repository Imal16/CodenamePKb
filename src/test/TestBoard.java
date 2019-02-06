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

class TestBoard {
	
	Board testBoard;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		JFXPanel jp = new JFXPanel(); //Set up internal graphic for jfx testing
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
	void setupBoardTest() {
		
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
