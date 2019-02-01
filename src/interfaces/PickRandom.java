package interfaces;
import java.util.Random;
import business.Card;

public class PickRandom implements PickCard{
	private int colRow=5;
	private int max=25;
	private Card[][] cards;
	private Random Rand=new Random();
	public PickRandom(Card[][] cards) {
		this.cards=cards;
	}
	
	public PickRandom(int colRow,int max,Card[][] cards) {
		this.cards=cards;
		this.colRow=colRow;
		this.max=max;
	}//constructors
	@Override
	public void pick() {
		// TODO Auto-generated method stub
		
	}

}
