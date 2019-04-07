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
import main.models.interfaces.SmartPickCardStrategy;


public class TestOperative {

	Operative redOp;
	Operative blueOp;
	Board testBoard;

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

	public TestOperative() {
		testBoard = new Board();
		redOp = new Operative(1, 1);
		blueOp = new Operative(0, 1);
	}
	
	@Test
	void setStrategyTest() {
		SmartPickCardStrategy strategy = new SmartPickCardStrategy(testBoard, blueOp);
		blueOp.setStrategy(strategy);
		assertEquals(strategy, blueOp.strategy);
	}
	
	@Test 
	void decTriesTest() {
		blueOp = new Operative(0, 2); //declare operative with 2 tries
		assertEquals(2, blueOp.getTries());
		blueOp.decTries();
		assertEquals(1, blueOp.getTries());
	}
}
