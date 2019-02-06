package main.models.business;

import main.models.interfaces.*;

/**
 * An operator is an actor who picks a card on the board based on
 * a hint given by their spymaster.
 * 
 * @author William, Zijian
 * @version 02/06/2019
 *
 */
public class Operative extends Player{
	private int team;// 1 for red, 0 for blue.
	private int tries;
	
	public Operative(int team, int tries) {
		this.team = team;
		this.tries = tries;
	}
	
	public PickCardStrategy strategy;
	
	/**
	 * 
	 * @param keyword
	 */
	public void pickCard() {
		String side = (team == 1) ? "red" : "blue";
		System.out.println(side + " op picks a card!");
		strategy.execute();
	}
	
	public void setStrategy(PickCardStrategy strategy) {
		this.strategy = strategy;
	}
	
	//Getters and setters
	public int getTeam() {
		return team;
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int triesNumber) {
		this.tries = triesNumber;
	}

	public void getTries(int teamType) {
		this.team = teamType;
	}
}
