package main.models.business;

public class Board {
	private int width;//idk how the board are going to apply with UI, so i just left this like what domain model says
	private int height;
	public Board(int width,int height) {
		this.width=width;
		this.height=height;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void setWidth(int width) {
		this.width=width;
	}
	public void setHeight(int height) {
		this.height=height;
	}
	
}
