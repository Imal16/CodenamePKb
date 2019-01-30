package mytest;
import interfaces.*;
import business.*;

public class test {
	
	public static void main(String[] args) {
		CommandManager commander = new CommandManager();
		Operative op = new Operative(1, 5);
		Spymaster sm = new Spymaster(1);
		
		commander.setCommand(new OperativePickCardCommand(op));
		commander.doIt();
		
		commander.setCommand(new SpymasterGiveHintCommand(sm));
		commander.doIt();
	}
	
}
