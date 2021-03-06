package main.models.business;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.models.interfaces.*;

/**
 * An operator is an actor who picks a card on the board based on
 * a hint given by their spymaster.
 * 
 * It is also an invoker in the command pattern.
 * 
 * @author William, Zijian
 * @version 02/06/2019
 *
 */
public class Operative extends Player{
	public PickCardStrategy strategy;	//strategy for operative

	public Operative(int team, int tries) {
		super(team);
		setTries(tries);
	}

	/**
	 * This method will execute the strategy that is has been set with
	 * in order to pick a card.
	 *
	 */
	public void pickCard(HashMap<Integer, String> hint) {
		//Testing log
		String side = (getTeam() == 2) ? "Red" : "Blue";
		Logger.getLogger("LOGGER").setLevel(Level.INFO);
		Logger.getLogger("LOGGER").info(side + " operative picks a card!");
		
		strategy.execute(hint);
	}
	
	/**
	 * This method allows the operative to set a strategy
	 * @param strategy
	 */
	public void setStrategy(PickCardStrategy strategy) {
		this.strategy = strategy;
	}

	public void decTries(){
		setTries(getTries() - 1);
	}
}
