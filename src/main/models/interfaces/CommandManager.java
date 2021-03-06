package main.models.interfaces;
import java.util.ArrayList;
import java.util.List;

/**
 * Invoker class that takes command obj and executes it.
 * Not used in current iteration but will be used for future ones.
 * @author William
 *
 */
public class CommandManager {
	Command cmd;
	PickCardStrategy strategy;
	
	//these lists are for testing, we may or may not use them
	List<Command> cmdArrayList = new ArrayList<Command>();
	Command[] cmdList = new Command[10];
	
	public CommandManager() {
		
	}
	
	public void setCommand(Command cmd) {
		this.cmd = cmd;
	}
	
	public void setStrategy(PickCardStrategy pcs) {
		this.strategy = pcs;
	}
	
	public void executeStrategy() {
		strategy.execute();
	}
	
	public void executeStrategy(PickCardStrategy strat) {
		strat.execute();
	}
	
	public void executeCommand() {
		cmd.execute();
	}
}
