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
import main.models.business.Operative;
import main.models.business.Spymaster;
import main.models.interfaces.PickNextCardStrategy;
import main.models.interfaces.PickRandomCardStrategy;
import main.models.interfaces.SmartHintStrategy;
import main.models.interfaces.SmartPickCardStrategy;

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
		game = new GameManager();
	}

/*	@Test
	void GameNotOverTest() {
		populateBoard();
		game.setAmountOfRedCards(6);
		game.setAmountOfBlueCards(5);
		game.playTurn();
		game.playTurn();
		assertTrue(!game.isGameOver());
	}
*/
	/**
	 * Blue teams wins when they pick their last blue card.
	 */
/*	@Test
	void BlueTeamWinByNormalTest() {
		populateBlueBoard();
		game.setOperativeStrategy(game.getRedOperative(),  new PickNextCardStrategy(testBoard));
		game.setAmountOfRedCards(9);
		game.setAmountOfBlueCards(1);
		game.playTurn();//red is picking first;
		assertTrue(game.isGameOver());
		assertTrue(!game.isRedWinner());
	}
*/
	/**
	 * Red teams wins when they pick their last red cards
	 */
/*	@Test
	void RedTeamWinByNormalTest() {
		populateRedBoard();
		game.setOperativeStrategy(game.getBlueOperative(),  new PickNextCardStrategy(testBoard));
		game.setAmountOfRedCards(1);
		game.setAmountOfBlueCards(9);
		game.playTurn();//blue is picking first;
		assertTrue(game.isGameOver());
		assertTrue(game.isRedWinner());
	}
*/

	/**
	 * Red wins when blue team picks assassin
	 */
/*	@Test
	void RedWinByAssassinTest() {
		populateAssassinBoard();
		game.setOperativeStrategy(game.getBlueOperative(),  new PickNextCardStrategy(testBoard));
		game.setAmountOfBlueCards(3);
		game.setAmountOfRedCards(2);
		game.playTurn();//Blue is picking first
		assertTrue(game.isGameOver());
		assertTrue(game.isRedWinner());
	}
*/
	/**
	 * Blue team wins when red picks assassin
	 */
/*	@Test
	void BlueWinByAssassinTest() {
		populateAssassinBoard();
		game.setOperativeStrategy(game.getRedOperative(),  new PickNextCardStrategy(testBoard));
		game.setAmountOfBlueCards(3);
		game.setAmountOfRedCards(4);
		game.playTurn();//Red is picking first
		assertTrue(game.isGameOver());
		assertTrue(!game.isRedWinner());
	}
*/
	
	@Test
	void setOperativeStrategyTest() {	
		SmartPickCardStrategy strategy = new SmartPickCardStrategy(testBoard, blueOp);
		game.setOperativeStrategy(blueOp, strategy);
		assertEquals(strategy, blueOp.strategy);
		
		SmartPickCardStrategy strategy2 = new SmartPickCardStrategy(testBoard, redOp);
		game.setOperativeStrategy(redOp, strategy2);
		assertNotEquals(strategy, redOp.strategy);
		assertEquals(strategy2, redOp.strategy);
	}
	
	@Test
	void setUpPlayerTest() {
		game.setupPlayer(); //player will be set as blue by default
		assertEquals(game.getPlayer(), game.getBlueOperative());
		assertNotEquals(game.getPlayer(), game.getRedOperative());
	}
	
	@Test
	void setAmountOfRedCardsTest() {
		game.setAmountOfRedCards(9);
		assertEquals(9, game.getRedCardsLeft());
	}
	
	@Test 
	void setAmountOfBlueCardsTest() {
		game.setAmountOfBlueCards(9);
		assertEquals(9, game.getBlueCardsLeft());
	}
	
	@Test 
	void setUpFirstTurn() {
		game.setAmountOfBlueCards(8);
		game.setAmountOfRedCards(9);
		game.setupFirstTurn();
		assertTrue(game.isRedTurn());
		
		game.setAmountOfBlueCards(9);
		game.setAmountOfRedCards(8);
		game.setupFirstTurn();
		assertFalse(game.isRedTurn());
	}

	@Test
	void getRedCardsLeftTest() {
		game.setAmountOfRedCards(9);
		assertEquals(9, game.getRedCardsLeft());
		
		game.setAmountOfRedCards(0);
		assertEquals(0, game.getRedCardsLeft());
		
	}
	
	@Test 
	void getBlueCardsLeftTest() {
		game.setAmountOfBlueCards(1);
		assertEquals(1, game.getBlueCardsLeft());
		
		game.setAmountOfBlueCards(8);
		assertEquals(8, game.getBlueCardsLeft());
	}
	
	@Test
	void removeBlueCardTest() {
		game.setAmountOfBlueCards(9);
		game.removeBlueCard();
		assertEquals(8, game.getBlueCardsLeft());
		game.setAmountOfBlueCards(0);
		game.removeBlueCard();
		assertEquals(-1, game.getBlueCardsLeft());
	}
	
	@Test
	void removeRedCardTest() {
		game.setAmountOfRedCards(9);
		game.removeRedCard();
		assertEquals(8, game.getRedCardsLeft());
	}
	
	@Test 
	void TestCheckNumberOfCardsLeft() {
		game.setAmountOfBlueCards(0);
		assertEquals(0, game.getBlueCardsLeft());
		
		game.setAmountOfRedCards(5);
		assertEquals(5, game.getRedCardsLeft());
	}
	
	@Test
	void switchTeamsTest() {
		//turn is blue first
		assertTrue(!game.isRedTurn());
		game.switchTeams();
		assertTrue(game.isRedTurn());
	}
	
	@Test 
	void setBlueTeamTest() {
		//switch to red turn
		game.switchTeams();
		assertTrue(game.isRedTurn());
		game.setBlueTurn();
		assertTrue(!game.isRedTurn());
	}
	
	@Test 
	void isRedWinnerTest() {
		game.setAmountOfBlueCards(0);
		game.setAmountOfRedCards(6);
		game.checkNumberOfCardsLeft();
		assertTrue(!game.isRedWinner());

		game.setAmountOfBlueCards(9);
		game.setAmountOfRedCards(0);
		game.checkNumberOfCardsLeft();
		assertTrue(game.isRedWinner());
	}
	
	@Test 
	void getRedOperativeTest() {
		assertNotNull(game.getRedOperative());
	}
	
	@Test 
	void isPlayerPlaying() {
		assertTrue(game.isPlayerPlaying());
	}
	
	@Test 
	void isPlayerRedTest() {
		assertTrue(!game.isPlayerRed()); //player is blue by default
	}
	
	@Test
	void isGameOver() {
		game.endGame(); //set end game to true
		assertTrue(game.isGameOver());
	}
	
	@Test
	void getCurrentSpymasterTest() {
		game.setCurrentSpymaster(game.getBlueSpymaster()); //set current spymaster to blue
		assertEquals(game.getCurrentSpymaster(), game.getBlueSpymaster());
		
		game.setCurrentSpymaster(game.getRedSpymaster()); //set current spymaster to red
		assertEquals(game.getCurrentSpymaster(), game.getRedSpymaster());
	}
	
	@Test
	void getPlayerSpymasterTest() {
		game.setPlayerSpymaster(game.getRedSpymaster()); //set player spymaster to red
		assertEquals(game.getRedSpymaster(), game.getPlayerSpymaster());
		
		game.setPlayerSpymaster(game.getBlueSpymaster()); //set player spymaster to Blue
		assertEquals(game.getBlueSpymaster(), game.getPlayerSpymaster());
	}
	
	@Test
	void getPlayerTest() {
		game.setPlayer(game.getBlueOperative()); //set player to blue
		assertEquals(game.getBlueOperative(), game.getPlayer());
		
		game.setPlayer(game.getRedOperative()); //set player to red
		assertEquals(game.getRedOperative(), game.getPlayer());
	}
	
	@Test
	void isPlayersTurnTest() {
		assertTrue(!game.isPlayersTurn()); 
		game.switchTeams(); //switch to player's turn
		assertTrue(game.isPlayersTurn());
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