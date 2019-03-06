package test;

import static org.junit.jupiter.api.Assertions.*;

import main.models.business.Word;
import main.models.business.WordAssociation;
import org.junit.jupiter.api.*;

import javafx.embed.swing.JFXPanel;
import main.models.business.Board;
import main.models.business.Card;

import java.util.HashSet;

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
    @Disabled
	void setUpCardAtTest() {
		Card cardToBeSet = new Card(new Word("card",new HashSet()), 1);
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
	
	private void resetCards() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				testBoard.board[i][j].isFlipped = false;
			}
		}
	}
}
