package main.models.interfaces;
import main.models.business.Card;
public class PickNextCardStrategy implements PickCardStrategy{
	private int i=0;
	private int j=0;
	private int colRow=5;
	private int max=25;
	private Card[][] cards;
	
	public PickNextCardStrategy(Card[][] cards) {
		this.cards=cards;
	}
	
	public PickNextCardStrategy(int colRow,int max,Card[][] cards) {
		this.cards=cards;
		this.colRow=colRow;
		this.max=max;
	}//constructors
	
	@Override
	public void execute(){
		// TODO Auto-generated method stub
		for (;i<=max;i++) {
			for(;j<=colRow;j++) {
				cards[i][j].flip();
			}
			j=0;
		}
	}

}
