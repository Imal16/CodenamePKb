package main.models.interfaces;

import java.util.ArrayList;
import java.util.List;

import main.models.business.Board;

public class PickSmartStrategy implements PickCardStrategy{
	private Board board;
	private int team=2; 
	public PickSmartStrategy(Board board,int team) {
		this.board = board;
		this.team=team;
	}
	@Override
	public void execute() {
		System.out.println("Nothing input!----------------------------");
		
	}

	@Override
	public void execute(String word) {
		String value="aword";
		int j=0;
		if(team==3) {
		for(String temp:board.getBlueCards()) {
			if(board.getBlueGraph().getGraphObj().containsEdge(word,temp)==true) {
				value=temp;
				j++;
				
			}else {
				//System.out.println(temp+"is not in the edge with "+word);
			}
		}
		
		if(j>=0) {
			//System.out.println(value+"-----------"+board.findCard(value).getWord()+"----------"+word);
			board.pickCardAt(board.getCardRow(board.findCard(value)), board.getCardCol(board.findCard(value)));
			board.getBlueCards().remove(value);
			board.getBlueGraph().deletevertex(value);
		}	else {	
			System.out.println("Piiiiiiiiiiiiiiiiick Smmmmmmmmmaaaaart");
		}	
			
		
		
		}else if(team==2) {
			for(String temp:board.getRedCards()) {
				if(board.getRedGraph().getGraphObj().containsEdge(word,temp)) {
					value=temp;
					j++;
					board.getBlueGraph().deletevertex(temp);
				}else {
					//System.out.println(temp+"is not in the edge with "+word);
				}
			}
			
			if(j>=0) {
							//System.out.println(value+"----------"+board.findCard(value)+"-----------"+word);
							board.pickCardAt(board.getCardRow(board.findCard(value)), board.getCardCol(board.findCard(value)));
							board.getRedCards().remove(value);
			}else {	
				System.out.println("Piiiiiiiiiiiiiiiiick Smmmmmmmmmaaaaart");
			}
					
			
				
		}else {
			System.out.println("Invalid team number "+team+". Please check in PickSmartStrategy() or in PickSmartStrategy.execute() ");
		}
	}
}
