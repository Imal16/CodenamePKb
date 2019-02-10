package main.models.business;

/**
 * Parent class for operative and spymaster
 * @author William Ngo, Zijian Wang
 * @version 01/30/2019
 *
 */
public class Player {
	private int team;// 1 for red, 0 for blue.

	public int getTeam() {
		return team;
	}

	public void setTeam(int teamType) {
		this.team = teamType;
	}
}
