package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import main.application.GameManager;
import main.models.business.Board;
import main.models.business.Card;
import main.models.business.Operative;
import main.models.business.Spymaster;
import main.models.interfaces.PickNextCardStrategy;
import main.models.interfaces.PickRandomCardStrategy;

/**
 * Unit testing for Game Manager
 * @author Zijian Wang
 * @version 02/06/2019
 *
 */
class TestGameManager {

	Board testBoard;
	Operative redOp;
	Operative blueOp;
	Spymaster redSpy;
	Spymaster blueSpy;
	GameManager game;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		JFXPanel jp = new JFXPanel();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {

	}

	@AfterEach
	void tearDown() throws Exception {

	}

	public TestGameManager() {
		testBoard = new Board();
		redOp = new Operative(1, 1);
		blueOp = new Operative(0, 1);
		redSpy = new Spymaster(1);
		blueSpy = new Spymaster(0);
		game = new GameManager(testBoard);
	}

	@Test
	void GameNotOverTest() {
		populateBoard();
		game.setAmountOfRedCards(6);
		game.setAmountOfBlueCards(5);
		game.playTurn();
		game.playTurn();
		assertTrue(!game.isGameOver());
	}

	/**
	 * Blue teams wins when they pick their last blue card.
	 */
	@Test
	void BlueTeamWinByNormalTest() {
		populateBlueBoard();
		game.setOperativeStrategy(game.getRedOperative(),  new PickNextCardStrategy(testBoard));
		game.setAmountOfRedCards(9);
		game.setAmountOfBlueCards(1);
		game.playTurn();//red is picking first;
		assertTrue(game.isGameOver());
		assertTrue(!game.isRedWinner());
	}

	/**
	 * Red teams wins when they pick their last red cards
	 */
	@Test
	void RedTeamWinByNormalTest() {
		populateRedBoard();
		game.setOperativeStrategy(game.getBlueOperative(),  new PickNextCardStrategy(testBoard));
		game.setAmountOfRedCards(1);
		game.setAmountOfBlueCards(9);
		game.playTurn();//blue is picking first;
		assertTrue(game.isGameOver());
		assertTrue(game.isRedWinner());
	}


	/**
	 * Red wins when blue team picks assassin
	 */
	@Test
	void RedWinByAssassinTest() {
		populateAssassinBoard();
		game.setOperativeStrategy(game.getBlueOperative(),  new PickNextCardStrategy(testBoard));
		game.setAmountOfBlueCards(3);
		game.setAmountOfRedCards(2);
		game.playTurn();//Blue is picking first
		assertTrue(game.isGameOver());
		assertTrue(game.isRedWinner());
	}

	/**
	 * Blue team wins when red picks assassin
	 */
	@Test
	void BlueWinByAssassinTest() {
		populateAssassinBoard();
		game.setOperativeStrategy(game.getRedOperative(),  new PickNextCardStrategy(testBoard));
		game.setAmountOfBlueCards(3);
		game.setAmountOfRedCards(4);
		game.playTurn();//Red is picking first
		assertTrue(game.isGameOver());
		assertTrue(!game.isRedWinner());
	}



	/**
	 * Utility method that populates the board with cards
	 */
	private void populateAssassinBoard() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Card c;
				if(i==0&j==0) {
					c = new Card("card_" + i + "_" + j, 1);
				}
				else {
					c = new Card("card_" + i + "_" + j, 0);
				}
				testBoard.setUpCardAt(c, i, j);
				testBoard.setUpCardAt(c, i, j);
				testBoard.setUpCardAt(c, i, j);
			}
		}
	}

	/**
	 * Method to populate the board so that the first and only
	 * red card will be at [0][0] so that the red operative
	 * can easily pick it to show that it wins the game
	 */
	private void populateRedBoard() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Card c;
				if(i==0&j==0) {
					c = new Card("card_" + i + "_" + j, 2);
				}
				else {
					c = new Card("card_" + i + "_" + j, 0);
				}
				testBoard.setUpCardAt(c, i, j);
				testBoard.setUpCardAt(c, i, j);
			}
		}
	}

	/**
	 * Method to populate the board so that the first and only
	 * blue card will be at [0][0] so that the blue operative
	 * can easily pick it to show that it wins the game
	 */
	private void populateBlueBoard() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Card c;
				if(i==0&j==0) {
					c = new Card("card_" + i + "_" + j, 3);
				}
				else {
					c = new Card("card_" + i + "_" + j, 0);
				}
				testBoard.setUpCardAt(c, i, j);
			}
		}
	}

	/**
	 * Populates board with random cards
	 */
	private void populateBoard() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Card c;
				if(i%2==0&j%22==0) {
					c = new Card("card_" + i + "_" + j, 3);
				}
				else {
					c = new Card("card_" + i + "_" + j, 2);
				}
				testBoard.setUpCardAt(c, i, j);
			}
		}
	}

}