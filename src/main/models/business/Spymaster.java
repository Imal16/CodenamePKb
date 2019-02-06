package main.models.business;

import java.util.Random;

/**
 * a Spymaster's task is to give clues to operatives in order to help
 * them pick one or many cards.
 * 
 * @author William Ngo
 * @version 02/06/2019
 *
 */
public class Spymaster extends Player {
	private int team;// 1 for red, 0 for blue.
	private String clueWord;
	private int clueNumber;
	
	private Random rand = new Random();
	private String[] hints;

	public Spymaster(int team) {
		this.team = team;
		hints = new String[10];
		giveDefaultHints();
	}
	
	/**
	 * Spymaster gives a random hint for iteration 1.
	 * In future iteration, it will have more logic in its decision
	 * to give a hint such as relating the hint to the words in the board
	 */
	public void GiveHint() {
		int hintNo = rand.nextInt(10);
		int clueNumber = 1 + rand.nextInt(3); // clue number between 1 and 3
		String side = (team == 1) ? "red" : "blue";
		System.out.println(side + " spymaster's hint is : " + hints[hintNo] + ", " + clueNumber);
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

}
