package Codegame;

public class Operative extends Player{
	private int team;//1 for red, 0 for blue. 
	private int tries;
	public Operative(int team,int tries) {
		this.team=team;
		this.tries=tries;
	}
	public int getTeam() {
		return team;
	}
	public int getTries() {
		return tries;
	}
	public void setTries(int triesNumber) {
		this.tries=triesNumber;
	}
	public void getTries(int teamType) {
		this.team=teamType;
	}
}
