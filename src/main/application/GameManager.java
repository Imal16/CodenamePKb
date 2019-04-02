package main.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import main.models.business.*;
import main.models.interfaces.*;

/**
 * The gameManager holds information about a game session such as whose turn is
 * it, the score for each team and when should a game finish.
 * 
 * @author William, Zijian
 * @version 02/05/2019
 *
 */
public class GameManager {	
	private Operative redOperative;
	private Operative blueOperative;
	private Spymaster redSpymaster;
	private Spymaster blueSpymaster;
	private Spymaster playerSpymaster;

	private Operative currentOperative;
	private Spymaster currentSpymaster;
	
	private Board board; //change this to board controller

	private boolean redTurn = false; // current turn for teams. true = red's, false = blue's
	private boolean redWinner = false;
	private boolean isGameOver = false;
	private boolean isPlayerPlaying = false;
	private boolean isPlayerRed = false; //player is blue if false

	private int redCardsLeft;
	private int blueCardsLeft;
	
	private HashMap<Integer, String> hintSet;
	
	public GameManager(Board board) {
		
		splashScreen();
		
		this.redOperative = new Operative(CardTypes.RED, 1);
		this.blueOperative = new Operative(CardTypes.BLUE, 1);
		this.redSpymaster = new Spymaster(CardTypes.RED);
		this.blueSpymaster = new Spymaster(CardTypes.BLUE);
		
		if(isPlayerPlaying){
			if(isPlayerRed)
				playerSpymaster = redSpymaster;
			else
				playerSpymaster = blueSpymaster;
		}

		this.board = board;

		// Setting strategies for operatives.
		setOperativeStrategy(redOperative, new SmartPickCardStrategy(board, redOperative));
		setOperativeStrategy(blueOperative, new SmartPickCardStrategy(board, blueOperative));
		
		// Setting strategies for Spymaster
		setPlayerStrategy(redSpymaster, new SmartHintStrategy(board, redOperative));
		setPlayerStrategy(blueSpymaster, new SmartHintStrategy(board, blueOperative));
		
		setupFirstTurn();
		
		//if the player's turn is first, get a hint
		if(isPlayerPlaying) {
			hintSet = playerSpymaster.GiveHint();
		}
	}

	/**
	 * Set spymaster strategy
	 * @param player
	 * @param strategy
	 */
	private void setPlayerStrategy(Spymaster player, SmartHintStrategy strategy) {
		player.setStrategy(strategy);
	}

	/**
	 * Designate a strategy to an operative;
	 * 
	 * @param op
	 * @param strat
	 */
	public void setOperativeStrategy(Operative op, PickCardStrategy strat) {
		op.setStrategy(strat);
	}

	public void splashScreen() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("CODENAMES");
		alert.setHeaderText("Select the operative you would liket to play as, or let the AI play.");
		
		ButtonType blueOpButton = new ButtonType("Blue Operative");
		ButtonType redOpButton = new ButtonType("Red Operative");
		ButtonType aiButton = new ButtonType("AI");
		
		alert.getButtonTypes().setAll(blueOpButton, redOpButton, aiButton);

		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() != aiButton)
		{
			isPlayerPlaying = true;
			if(result.get() == redOpButton)
				isPlayerRed = true;
		}
	}

	public void setupFirstTurn() {
		if (redCardsLeft >= blueCardsLeft) {
			setRedTurn();
        } else {
        	setBlueTurn();
        }
	}
	
	/**
	 * This method simulates a turn in a game session with two AIs. One turn
	 * includes a spymaster giving a hint and the operative choosing a card.
	 */
	public void playTurn() {
		hintSet = currentSpymaster.GiveHint();
		
		for (Map.Entry<Integer, String> hint : hintSet.entrySet()) {
			currentOperative.setTries(hint.getKey()+1);
		}
		
		//printTurnInfo();
		
		do{
			currentOperative.pickCard(hintSet);
			
			switch(board.getTypeFliped()) {
				case CardTypes.BYSTANDER: //:shrug
					break;
				case CardTypes.ASSASSIN:
					endGame();
					break;
				case CardTypes.RED:
					redCardsLeft--;
					break;
				case CardTypes.BLUE:
					blueCardsLeft--;
					break;
			}
			
			currentOperative.decTries();
            checkNumberOfCardsLeft();
            
		}while (board.getTypeFliped() == currentOperative.getTeam() && currentOperative.getTries() > 0 && !isGameOver);
		
		/**
		 * Above here, when either of the op choose a card,we will check what did
		 * him/her pick then we decrease the number of cards of this type. If it was
		 * byStander do nothing if it was assassin, game ends and we get a winner here
		 * 
		 * 
		 * CheckNumberOfCardsLeft() below to check if the game ends and who's winner. In
		 * boardController.handleEnterButtonAction before playTurn(), the function now
		 * will check if the game ends
		 * 
		 * note from zijian
		 */
		
		// Check how many cards left for each team and make a game over by disabling
		// enter button
		
		if (!isGameOver) {
			Logger.getLogger("LOGGER").setLevel(Level.INFO);
			Logger.getLogger("LOGGER").info("END OF TURN!\n");

        } else {
            String side = (!redTurn) ? "Red" : "Blue";
            //assassin end case
            if (board.getTypeFliped() == 1) {
                side = "The assassin was picked, " + side;
            }
            
            Logger.getLogger("LOGGER").setLevel(Level.INFO);
            Logger.getLogger("LOGGER").info("End of the game. " + side + " team won!");
        }
	}
	
	public void printTurnInfo() {
		System.out.println("Current operative: " + currentOperative.getTeam() + " --- Current Spymaster: " + currentSpymaster.getTeam());
		System.out.println("Current hint: " + currentSpymaster.getClueWord() + " " + currentSpymaster.getClueNumber());
		
		System.out.println("Red hint: " + redSpymaster.getClueWord() + " " + redSpymaster.getClueNumber());
		System.out.println("Blue hint: " + blueSpymaster.getClueWord() + " " + blueSpymaster.getClueNumber());
	}
	
	public void endGame() {
		isGameOver = true;
		
		if(redTurn) {
			redWinner = true;
		}
	}
	
	public void switchSides() {
		if(redTurn) {
			setBlueTurn();
		} else {
			setRedTurn();
		}
	}
	
	public void setBlueTurn() {
		redTurn = false;
		currentSpymaster = blueSpymaster;
		currentOperative = blueOperative;
	}
	
	public void setRedTurn() {
		redTurn = true;
		currentSpymaster = redSpymaster;
		currentOperative = redOperative;
	}
	
	// setters
	public void setAmountOfRedCards(int num) {
		this.redCardsLeft = num;
	}
	
	public void removeRedCard() {
		this.redCardsLeft -= 1;
	}

	public void setAmountOfBlueCards(int num) {
		this.blueCardsLeft = num;
	}

	public void removeBlueCard() {
		this.blueCardsLeft -= 1;
	}

	public void setBlueOperative(Operative blueOperative) {
		this.blueOperative = blueOperative;
	}
	
	public void setGameOver() {
		isGameOver = true;
	}
	
	public void changeTurn() {
		redTurn = !redTurn;
	}
	
	// getter
	public Operative getBlueOperative() {
		return blueOperative;
	}
	public boolean isEnd() {
		return isGameOver;
	}

	public boolean isRedWinner() {
		return redWinner;
	}
	
	public Operative getRedOperative() {
		return this.redOperative;
	}
	
	public boolean isPlayerPlaying() {
		return isPlayerPlaying;
	}
	
	public boolean isPlayerRed() {
		return isPlayerRed;
	}
	
	public boolean isPlayerTurn() {
		if(isPlayerRed == redTurn)
			return true;
		return false;
	}

	// check if game end
	public void checkNumberOfCardsLeft() {
		if (redCardsLeft == 0) {
			isGameOver = true;
			redWinner = true;
		}
        if (blueCardsLeft == 0) {
            isGameOver = true;
			redWinner = false;
		}
	}
	
	public String WhosTurnIsIt() {
		if(redTurn) {
			return "Red";
		} else {
			return "Blue";
		}
	}
	
	public void givePlayerHint() {
		if(isPlayerPlaying && isPlayerTurn()) {
			hintSet = playerSpymaster.GiveHint();
		}
	}

	public int getRedCardsLeft() {
		return redCardsLeft;
	}

	public int getBlueCardsLeft() {
		return blueCardsLeft;
	}
	
	public Spymaster getPlayerSpymaster() {
		return playerSpymaster;
	}
	
	public boolean isRedTurn() {
		return redTurn;
	}

	public Spymaster getRedSpymaster() {
		return redSpymaster;
	}

	public void setRedSpymaster(Spymaster redSpymaster) {
		this.redSpymaster = redSpymaster;
	}

	public Spymaster getBlueSpymaster() {
		return blueSpymaster;
	}

	public void setBlueSpymaster(Spymaster blueSpymaster) {
		this.blueSpymaster = blueSpymaster;
	}

	public Operative getCurrentOperative() {
		return currentOperative;
	}

	public void setCurrentOperative(Operative currentOperative) {
		this.currentOperative = currentOperative;
	}

	public Spymaster getCurrentSpymaster() {
		return currentSpymaster;
	}

	public void setCurrentSpymaster(Spymaster currentSpymaster) {
		this.currentSpymaster = currentSpymaster;
	}
}
