package interfaces;
import business.*;

/**
 * Command class that implements command interface
 * Spymaster gives a hint to operatives
 * @author Willy
 *
 */
public class SpymasterGiveHintCommand implements Command{
	
	Spymaster sm;
	
	public SpymasterGiveHintCommand(Spymaster sm) {
		this.sm = sm;
	}
	
	public void execute() {
		//Spymaster should give a hint to operatives
		//For now testing
		sm.GiveHint();
	}
}
