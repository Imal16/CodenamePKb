package main.models.business;

public class Spymaster extends Player {
	private int team;// 1 for red, 0 for blue.
	private String clueWord;
	private int clueNumber;

	public Spymaster(int team) {
		this.team = team;
	}

	public int getTeam() {
		return team;
	}
	
	public void GiveHint() {
		
		String side = (team == 1) ? "red" : "blue";
		System.out.println(side + " spymaster give hint!");
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
