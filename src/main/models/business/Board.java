package main.models.business;

import main.application.GameManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Board is represented by a 2 Dimensional Card array. It is able to populate
 * itself with card objects and is able to pick a card at a given coordinate.
 * 
 * @author William Ngo, Zijian Wang
 * @version 02/05/2019
 */
public class Board {

	public Card[][] board;
	private int typeFliped;// to passing the card type value to GameManager

	private RelationGraph redGraph;		//Red team's graph
	private RelationGraph blueGraph;	//Blue team's graph

	private List<String> redCards;		//Red team's word list
	private List<String> blueCards;		//Blue team's word list

	public Board() {
		board = new Card[5][5];
		this.redGraph = new RelationGraph();
		this.blueGraph = new RelationGraph();
	}

	/**
	 * This method inserts a card object in the board (Card 2D Array) at the
	 * specified row and column
	 * 
	 * @param card Card contained in the board
	 * @param row Int board row
	 * @param col Int board column
	 */
	public void setUpCardAt(Card card, int row, int col) {
		this.board[row][col] = card;
	}

	/**
	 * This method will flip a card at the specified row and col. For iteration 1,
	 * computer will simply pick a card at random/or next and there is no comparing
	 * of the colors or the word relation.
	 * 
	 * @param row Int board row
	 * @param col Int board column
	 */
	public void pickCardAt(int row, int col) {
		board[row][col].flip();

		if (board[row][col].getType() == GameManager.RED) {           // red
		    this.redGraph.deletevertex(this.board[row][col].getWord());
		    this.redCards.remove(this.board[row][col].getWord());
			typeFliped = GameManager.RED;
        } else if (board[row][col].getType() == GameManager.BLUE) {    //blue
            this.blueGraph.deletevertex(this.board[row][col].getWord());
            this.blueCards.remove(this.board[row][col].getWord());
            typeFliped = GameManager.BLUE;
		} else if (board[row][col].getType() == GameManager.BYSTANDER) {    //bystander
			typeFliped = GameManager.BYSTANDER;
		} else if (board[row][col].getType() == GameManager.ASSASSIN) {    //assassin
			typeFliped = GameManager.ASSASSIN;
		} // checking the type, ready to pass to gameManager

		Logger.getLogger("LOGGER").setLevel(Level.INFO);
		Logger.getLogger("LOGGER").info("Card at row " + row + " and column " + col + ".");
	}

    /**
     * Searches board for row & column position then calls pickCardAt() to flip
     * @param word String Codename
     */
	public void pickCardAt(String word){
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (this.board[row][col].getWord() == word) {
                    pickCardAt(row, col);
                }
            }
        }
    }

	// getter
	public int getTypeFliped() {
		return this.typeFliped;
	}
	public void setTypeFliped(int type0123) {
		this.typeFliped=type0123;
	}

	/**
	 * This method will return a boolean indicating whether or not the card at the
	 * specified row and col is already flipped.
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isCardFlippedAt(int row, int col) {
		return board[row][col].isFlipped;
	}
	
	// getters & setters
	public Card getCardAt(int row, int col){
		return board[row][col];
	}

	public RelationGraph getRedGraph() {
		return redGraph;
	}

	public void setRedGraph(List<String> teamcards, HashMap<String, ArrayList<String>> jsonfilestorage) {
		this.redGraph.generategraph(teamcards, jsonfilestorage);
	}

	public RelationGraph getBlueGraph() {
		return blueGraph;
	}

	public void setBlueGraph(List<String> teamcards, HashMap<String, ArrayList<String>> jsonfilestorage) {
		this.blueGraph.generategraph(teamcards, jsonfilestorage);
	}

	public List<String> getRedCards() {
		return redCards;
	}

	public void setRedCards(List<String> redCards) {
		this.redCards = redCards;
	}

	public List<String> getBlueCards() {
		return blueCards;
	}

	public void setBlueCards(List<String> blueCards) {
		this.blueCards = blueCards;
	}
}
