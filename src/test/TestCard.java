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

public class TestCard {
	
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

	public TestCard() {
		testBoard = new Board();
	}
	
	@Test
	void flipTest() { 
		Card card = testBoard.getCardAt(0, 0);
		card.flip();
		assertTrue(card.isFlipped);
	}
	
	//tests for the type of card (red operative, blue operative, etc...)
	@Test
	void setTypeTest() {
		Card card = testBoard.getCardAt(0, 0);
		Card card2 = testBoard.getCardAt(1, 1);
		Card card3 = testBoard.getCardAt(2, 2);
		Card card4 = testBoard.getCardAt(3, 3);
		
		card.setType(0);
		card2.setType(1);
		card3.setType(2);
		card4.setType(3);
		
		assertEquals(card.getType(), CardTypes.BYSTANDER);
		assertEquals(card2.getType(), CardTypes.ASSASSIN);
		assertEquals(card3.getType(), CardTypes.RED);
		assertEquals(card4.getType(), CardTypes.BLUE);		
	}
	
	@Test
	void setWordTest() {
		Card card = testBoard.getCardAt(0, 0);
		Card card2 = testBoard.getCardAt(1, 1);
		
		card.setWord("Dog");
		card2.setWord("Cat");
		
		assertEquals("Dog", card.getWord());
		assertEquals("Cat", card2.getWord());
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
