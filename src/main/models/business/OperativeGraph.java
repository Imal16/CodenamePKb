package main.models.business;

import org.jgrapht.*;
import org.jgrapht.alg.connectivity.*;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.*;
import org.jgrapht.alg.lca.NaiveLCAFinder;
import org.jgrapht.alg.interfaces.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.*;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.GraphExporter;
import org.jgrapht.*;
import org.jgrapht.generate.*;
import org.jgrapht.traverse.*;
import org.jgrapht.util.*;

import com.google.gson.JsonObject;

import java.util.*;
import java.util.function.*;

/**
 * Contains the Operative graph class and the functions to call onto the graph to find the word(s) to select(Might be in the wrong package???)
 * 
 * @author Ihsaan Malek
 * @version 03/09/2019
 */

//todo: move card picking implementation to strategy
public class OperativeGraph {

	//Each Operative will have a Graph as its Data Structure to store words and their relations
	private Graph<String, DefaultWeightedEdge> OperativesGraph;
	
	
	public OperativeGraph() {
		this.OperativesGraph = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
	//This function takes the List of Codenames that are of the spymaster's team and the parsed dictionary from the Json file
	//It will create a graph of the relevant codenames and  their relation/clue words
	public void generategraph(List<String> teamcards, HashMap<String,ArrayList<String>> jsonfilestorage) {
		for(String temp : teamcards) {
			if(this.OperativesGraph.containsVertex(temp) ==false) {
				this.OperativesGraph.addVertex(temp);
			}
			ArrayList<String> values =jsonfilestorage.get(temp);
			for(int i=0; i<values.size();i++) {
				if(this.OperativesGraph.containsVertex(values.get(i)) ==false) {
					this.OperativesGraph.addVertex(values.get(i));
				}
				if(this.OperativesGraph.containsEdge(values.get(i),temp) ==false) {
					this.OperativesGraph.addEdge(values.get(i),temp);
				}
			}
			
		}
	}
	//This function is necessary, when a word is guessed and removed from the operative options to find a clueword if a word has been selected
	public void deletevertex(String wordselected) {
		this.OperativesGraph.removeVertex(wordselected);
	}
	//todo: delete everything above, its replaced by RelationGraph class
	//todo: new op strat calls this
	//Function for the Operative to chose the word(s) to select given the hint (format of int(#guesses), string(Clue word))
	public List<String> OperativeChoices(HashMap<Integer,String> hint){
		List<String> wordschosen =new ArrayList<String>();
		Set<Integer> numberoguessesset = new HashSet<Integer>();
		ArrayList<String> Decendents = new ArrayList<String>();
		numberoguessesset=hint.keySet();								//extract the #ofguesses
		for(Integer key: numberoguessesset) {									
			Set<DefaultWeightedEdge> decendentedges = new HashSet<DefaultWeightedEdge>();
			String clue = hint.get(key);								//Gets the clue  word
			///equvalent of getting parent/hypernym.
			try {
			decendentedges=this.OperativesGraph.edgesOf(clue);						//Gets the edges connecting to the clueword (set)
			System.out.println("Clue word : "+ clue);
	        //System.out.println("Set of decscendent edges: "+ this.OperativesGraph.edgesOf(clue).toString());
	        for(DefaultWeightedEdge edge1:decendentedges) {								//iterate throught the edges
	        	String Sourcevert = this.OperativesGraph.getEdgeTarget(edge1);
				//System.out.println("Individual Target edge: "+ Sourcevert);
	            if(Decendents.contains(Sourcevert) ==false) {						//Handling if there are duplicates
	            	System.out.println("adding Possible selection: "+ Sourcevert);			//Add the vertex of the edge to the list of possible words to select
	            	Decendents.add(Sourcevert);
	            }
	        }
	        
	        System.out.println("List of possible cards to choose: "+ Decendents);
			Collections.shuffle(Decendents);									//Assuming there are more than one possible word to select, randomize
			try {
			wordschosen=Decendents.subList(0, key);								//Select # guesses elements
			}
			catch (Exception e){
				//System.out.println("Error, List of descendents: "+Decendents);

				if(wordschosen.isEmpty()) {												//if there is an error with the key/ #of guesses to choose
					wordschosen.add(Decendents.get(0));									//just  choose the first element of the list as a safe option
					System.out.println("Error, selecting: "+Decendents.get(0)+" so choosing"+wordschosen);
					
				}
			}
			System.out.println("list of words chosen:");
			System.out.println(wordschosen);
	        
			return wordschosen;										//if there is words to chose return the list
			
		}
		catch(Exception e){
			System.out.println("Clue words not in Database");					
			wordschosen= new ArrayList<String>();					//returns an empty list, if this is the case,  we call random pick or iterative.

		}
			
		}
		//todo: remove vertex after pick
		return wordschosen;
		
	}

}
