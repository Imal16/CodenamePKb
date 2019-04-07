package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import main.application.GameManager;
import main.models.business.Board;
import main.models.business.Card;
import main.models.business.CardTypes;
import main.models.business.RelationGraph;
import main.controllers.*;

public class TestBoardController {
	
	Board testBoard;
	BoardController boardController;
	
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
		boardController.setBoard_model(testBoard);
	}

	@AfterEach
	void tearDown() throws Exception {
		//resetCards();
	}
	
	public TestBoardController() {
		boardController = new BoardController();
		testBoard = new Board();
	}
	
	@Test
	void flipAllCardsTest() {
		boardController.flipAllCards();
		assertTrue(testBoard.isCardFlippedAt(0, 0));
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
