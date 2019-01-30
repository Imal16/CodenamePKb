package interfaces;
import java.util.ArrayList;
import java.util.List;

/**
 * Invoker class that takes command obj and executes it
 * @author Willy
 *
 */
public class CommandManager {
	Command cmd;
	//these lists are for testing, we may or may not use them
	List<Command> cmdArrayList = new ArrayList<Command>();
	Command[] cmdList = new Command[10];
	
	public CommandManager() { }
	
	public void setCommand(Command cmd) {
		this.cmd = cmd;
	}
	
	public void doIt() {
		cmd.execute();
	}
}
