package interfaces;
import business.*;

/**
 * Command class that implements the command interface.
 * It takes care of an operative picking a card.
 * @author Willy
 *
 */
public class OperativePickCardCommand implements Command{
	Operative op;
	
	public OperativePickCardCommand(Operative op) {
		this.op = op;
	}
	
	public void execute() {
		op.pickCard();
	}
	
	//we can add undo methods here
}
