package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
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

public class TestSpymaster {
	
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

	public TestSpymaster() {
		testBoard = new Board();
		redOp = new Operative(1, 1);
		blueOp = new Operative(0, 1);
		redSpy = new Spymaster(1);
		blueSpy = new Spymaster(0);
		game = new GameManager();
	}
	
	@Test
	void clueWordTest() {
		blueSpy.setClueWord("Clue");
		assertEquals("Clue", blueSpy.getClueWord());
	}
	
	@Test
	void clueNumberTest() {
		blueSpy.setClueNumber(1);
		assertEquals(1, blueSpy.getClueNumber());
	}

}
