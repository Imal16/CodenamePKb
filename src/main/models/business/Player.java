package main.models.business;

/**
 * Parent class for operative and spymaster
 * @author William Ngo, Zijian Wang
 * @version 01/30/2019
 *
 */
public class Player {
	private int team;
	private int tries;
	
	public Player(int team){
		this.team = team;
	}
	
	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int teamType) {
		this.team = teamType;
	}
}
