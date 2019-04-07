package main.models.business;

import main.models.interfaces.HintStrategy;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * a Spymaster's task is to give clues to operatives in order to help
 * them pick one or many cards.
 * 
 * @author William Ngo, Zijian Wang
 * @version 02/06/2019
 *
 */
public class Spymaster extends Player {
	private int team;				// 1 for red, 0 for blue.
	private String clueWord;
	private int clueNumber;
	
	private Random rand = new Random();
	private String[] hints;

	private HintStrategy strategy;

	public Spymaster(int team) {
		super(team);
	}
	
	/**
	 * Spymaster gives a random hint for iteration 1.
	 * In future iteration, it will have more logic in its decision
	 * to give a hint such as relating the hint to the words in the board
	 */
	public HashMap<Integer, String> GiveHint() {
		String word = "";
		int num = 0;
		HashMap<Integer, String> hint = strategy.execute();
		
		for (Map.Entry<Integer, String> foo :
				hint.entrySet()) {
			num = foo.getKey();
			word = foo.getValue();
		}
		
		clueNumber = num;
		clueWord = word + " " +num;

		Logger.getLogger("LOGGER").setLevel(Level.INFO);
		Logger.getLogger("LOGGER").info("Spymaster's hint is: " + clueWord + ", clue number " + clueNumber + ".");
		
		return hint;
	}
	

	/**
	 * Utility method, populates hint array with default hint
	 */
	private void giveDefaultHints() {
		for(int i = 0; i < 10; i++) {
			hints[i] = "hint_#" + i;
		}
	}

	/******* Getters and setters ************/



	public int getTeam() {
		return team;
	}
	
	public void setTeam(int teamNo) {
		this.team = teamNo;
	}
	
	public String getClueWord() {
		return clueWord;
	}

	public int getClueNumber() {
		return clueNumber;
	}

	public void setClueWord(String clue) {
		this.clueWord = clue;
	}

	public void setClueNumber(int clueNumber) {
		this.clueNumber = clueNumber;
	}

	public void setStrategy(HintStrategy strategy) {
		this.strategy = strategy;
	}
}
