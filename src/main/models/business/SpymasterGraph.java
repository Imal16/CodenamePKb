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
 * Contains the Spymaster graph class and the functions to call onto the graph to find the hint words(Might be in the wrong package???)
 * 
 * @author Ihsaan Malek
 * @version 03/09/2019
 */

public class SpymasterGraph {

	//Each Spymaster will have a Graph as its Data Structure to store words and their relations
	private Graph<String, DefaultWeightedEdge> SpymasterGraph;
	
	
	public SpymasterGraph() {
		this.SpymasterGraph = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
	//This function takes the List of Codenames that are of the spymaster's team and the parsed dictionary from the Json file
	//It will create a graph of the relevant codenames and  their relation/clue words
	public void generategraph(List<String> teamcards, HashMap<String,ArrayList<String>> jsonfilestorage) {
		for(String temp : teamcards) {
			if(this.SpymasterGraph.containsVertex(temp) ==false) {
				this.SpymasterGraph.addVertex(temp);
			}
			ArrayList<String> values =jsonfilestorage.get(temp);
			for(int i=0; i<values.size();i++) {
				if(this.SpymasterGraph.containsVertex(values.get(i)) ==false) {
					this.SpymasterGraph.addVertex(values.get(i));
				}
				if(this.SpymasterGraph.containsEdge(values.get(i),temp) ==false) {
					this.SpymasterGraph.addEdge(values.get(i),temp);
				}
			}
			
		}
	}
	//This function is necessary, when a word is guessed and removed from the spymasters options to find a clueword
	public void deletevertex(String wordselected) {
		this.SpymasterGraph.removeVertex(wordselected);
	}
	
	//This function is the logic for the spymaster to select its clue word
	//It is dependent on four other  functions.
	//It takes in the list of unselected codenames and finds a hint to give to the operative
	public HashMap<Integer, String> checkforhints(List<String> teamcards){
		HashMap<Integer,String> hint = new HashMap<Integer,String>();			//hint consisting of an integer=Number of words available for the specific clue provided
		List<List<String>> PowerSet= new ArrayList<List<String>>();
		
		PowerSet=powerSet(teamcards);								//Calls a function that generates a powerset of all the Code names
		Collections.sort(PowerSet, new ListComparator<>()); 		//Sorts the Powerset in terms of Largest to Smallest
		
		//System.out.println("PowerSet: "+PowerSet );
		for(List<String> set : PowerSet) {							//Iterate throught the powerset
			if(set.size()>2) {
				hint=LCAmultiple(set);								//IF the length of the subset if Larger than two, we Call LCAMultiple
				if(!hint.isEmpty()) {								//If An ancestor is found of that subset return the hint
					
					//System.out.println("Hint provided by LCA: "+hint );
					return hint;
				}
			}
			if(set.size()==2) {										//IF subset is of size 2, use JGraphT's  built-in function for find the set of Common Ancestors/Clues
				  NaiveLCAFinder<String, DefaultWeightedEdge> lca= new NaiveLCAFinder<String, DefaultWeightedEdge>(this.SpymasterGraph); 
				  List<String> listofhints =new ArrayList<String>();			//Above is Necessary to  find Clues/Ancestors, need to create an instance of an object of NaiveLCAFinder 
				try {
					Set<String> setofhints= new HashSet<String>();
			        setofhints=lca.getLCASet(set.get(0),set.get(1));	//Gets the LCA of two elements of  the subset
			        //System.out.println(setofhints);
			        listofhints.addAll(setofhints);					//Converts the set to a list
					Collections.shuffle(listofhints);				//Shuffle, allows randomization if there is multiple available clue words
					hint.put(2,listofhints.get(0));					//Makes the hint, adds to the HashMap, saying 2 words can be selected, with the hint word
					//System.out.println("Hint provided: "+ hint);
			        return hint;
				}
			        catch(Exception e) {
			        	 System.out.println("No common ancestors");
			        }
			}
			if(set.size()==1) {																//IF the subset only has 1 word, it cannot have a common Clue/Ancestor with
				Set<DefaultWeightedEdge> ancestoredges = new HashSet<DefaultWeightedEdge>();		//Another word, therefor, a random clue word is selected from
				List<String> listofhintsforasingle =new ArrayList<String>();						//its own set of clue words
				ancestoredges=this.SpymasterGraph.edgesOf(set.get(0));
		        for(DefaultWeightedEdge edge1:ancestoredges) {
		        	String Sourcevert = this.SpymasterGraph.getEdgeSource(edge1);
					listofhintsforasingle.add(Sourcevert);
				Collections.shuffle(listofhintsforasingle);
				hint.put(1,listofhintsforasingle.get(0));
				//System.out.println("Hint provided: "+ hint);
		        return hint;
		        }
				
			}
		}

		
			
		
		//System.out.println("Hint provided by LCA: "+hint );

		return hint;
		
	}
	
	//Function that Return the power of the list of all available codenames
	public static List<List<String>> powerSet(List<String> teamcards){
		List<List<String>> combinations= new ArrayList<List<String>>();
	
		if(teamcards.isEmpty()) {
			combinations.add(new ArrayList<String>());
			return combinations;
		}
		
		List<String> subset= new ArrayList<String>(teamcards);
		String head =subset.get(0);
        System.out.println(subset);

		List<String> restoflist= new ArrayList<String>(subset.subList(1, subset.size()));
		
		for(List<String> set : powerSet(restoflist)) {
			List<String> newSet = new ArrayList<String>();
			newSet.add(head);
			newSet.addAll(set);
			combinations.add(newSet);
			combinations.add(set);
		}

		return combinations;
		
	}
	
	//Function finds the Lowest Common Ancestor of multiple nodes/vertices in a graph
	//Takes in the list of nodes or in this case the list of available  team cards.
	public HashMap<Integer, String> LCAmultiple(List<String> teamcards){
		HashMap<Integer,String> hint = new HashMap<Integer,String>();
		HashMap<Integer,ArrayList<String>> hashlistofhints = new HashMap<Integer,ArrayList<String>>();
		ArrayList<String> Listofhints = new ArrayList<String>();
		if(teamcards.size()>0) {
			String first = teamcards.get(0);
			System.out.println("first vert: "+ first);
			ArrayList<String> firstA =getallancestors(first);			//Takes the first element of the list and gets a list of the ancestors of that word/node
			System.out.println("First list of ancestors: "+ firstA);
						
			
			for(int k=1; k< teamcards.size() && !firstA.isEmpty();k++) {
				String currentvert =teamcards.get(k);									//Iterates through the rest of the  list of Codenames
				System.out.println("current vert: "+ currentvert);							
				hashlistofhints=lookForCommonAncestors(teamcards,firstA,currentvert);	//And check if there are common ancestors between the Codenames
			}											
			if(!firstA.isEmpty()) {									//If the list is not empty then common word(s) have been found
				for(int key : hashlistofhints.keySet()) {			//The  key will be the number of guesses for the hint
					Listofhints= hashlistofhints.get(key);
					if(!Listofhints.isEmpty()) {
						Collections.shuffle(Listofhints);			//Because a hint can only be one word, generate a random element of the list
						hint.put(key, Listofhints.get(0));
						System.out.println("Hint! :");
						System.out.println(hint);
						
					}
				}
			}
			firstA.clear();			//clear memory
		}
		
		if(hint.isEmpty()) {
			//System.out.println("No hint  found!!!!!!!!!");			//IF hashmap is empty then there was no common ancestors in this set of codenames
			
		}
		//System.out.println(hint);

		return hint;
	
	}
	//Function that finds all ancestors/clue words for a given Codename
	public ArrayList<String> getallancestors(String word){
		
		ArrayList<String> ancestors = new ArrayList<String>();
			
		Set<DefaultWeightedEdge> ancestoredges = new HashSet<DefaultWeightedEdge>();
		///equvalent of getting parent/hypernym.
        ancestoredges=this.SpymasterGraph.edgesOf(word);				//finds all  connecting edges of a word (set)
		//System.out.println("New key word: "+ word);
        //System.out.println("Set fo edges: "+ this.SpymasterGraph.edgesOf(word).toString());
        for(DefaultWeightedEdge edge1:ancestoredges) {
        	String Sourcevert = this.SpymasterGraph.getEdgeSource(edge1);	//Iterate through the set and find the ancestor/clue word
			//System.out.println("Individual Source edge: "+ Sourcevert);
            if(ancestors.contains(Sourcevert) ==false) {
            	//System.out.println("adding hypernym: "+ Sourcevert);
            	ancestors.add(Sourcevert);									//If the word is not already in the list of ancestors add it to the list
            }
        }
        
        //System.out.println("List of hypernym: "+ ancestors);
		return ancestors;
	}
	//Function that finds common ancestors between the list of Codenames provided and the List of Ancestors of a word/Codename
	//Essentially, takes the list of Ancestors from the first Codename from the Teamcards list and then tries to find commmon Clue words between
	//that list  and the rest of the words in the teamcards list.
    public HashMap<Integer,ArrayList<String>> lookForCommonAncestors(List<String> teamcards,ArrayList<String> commonAncestors, String CurrentVert) {
    	ArrayList<String> commonbetweenall = new ArrayList<String>(commonAncestors);
    	ArrayList<String> commonbetweenallbackup = new ArrayList<String>(commonAncestors);

		HashMap<Integer,ArrayList<String>> Hintwords = new HashMap<Integer,ArrayList<String>>();		//clue in format integer(#guesses), string(word)
		int numberofguesses = 0;
    	   		for(int i=1; i< teamcards.size();i++) {		//Iterate from the second element  of the list of teamcards to the end
        		
    			String vert1=teamcards.get(i);
    			//System.out.println("Look for common: "+ vert1);
    			
    			ArrayList<String> ancestors2 = new ArrayList<String>();
    			ancestors2=getallancestors(vert1);											//find the ancestors/clue words for the ith element in the list
    			//System.out.println("Ancestors of: "+ vert1 + " = "+ancestors2);

    			commonbetweenall.retainAll(ancestors2);							//keeps  the common elements between the two lists

    			if(commonbetweenall.isEmpty()==false) {						//if the common list is not empty-> common clues
    				if(Hintwords.containsKey(numberofguesses+1)) {							
    					Hintwords.remove(numberofguesses+1);						//remove first entry to provide the correct hint
    				}
    				commonbetweenallbackup=commonbetweenall;
    				numberofguesses++;												//the more commonality between lists, means more words to be guesses for that clue
        			//System.out.println("Number of hints "+ numberofguesses);
        			Hintwords.put(numberofguesses+1, commonbetweenallbackup);			//gets the clue consisting the #guesses and clue word

    			}
    			if(commonbetweenall.isEmpty()==true) {									//if no commonality if found, no clue word, return a empty list
    				System.out.println("No common set: "+ Hintwords);

    				return Hintwords;
    			}
    		}
    	System.out.println("Found common set: "+ Hintwords);

		return Hintwords;
    	
    }
	


}
