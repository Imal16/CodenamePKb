package main.models.business;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RelationGraph {
    public Graph<String, DefaultWeightedEdge> graph;

    public RelationGraph() {
        this.graph = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    }

    //This function takes the List of Codenames that are of the spymaster's team and the parsed dictionary from the Json file
    //It will create a graph of the relevant codenames and  their relation/clue words
    public void generategraph(List<String> teamcards, HashMap<String, ArrayList<String>> jsonfilestorage) {
        for(String temp : teamcards) {
            if(this.graph.containsVertex(temp) ==false) {
                this.graph.addVertex(temp);
            }

            ArrayList<String> values =jsonfilestorage.get(temp);
            for(int i=0; i<values.size();i++) {
                if(this.graph.containsVertex(values.get(i)) ==false) {
                    this.graph.addVertex(values.get(i));
                }
                if(this.graph.containsEdge(values.get(i),temp) ==false) {
                    this.graph.addEdge(values.get(i),temp);
                }
            }

        }
    }
    //This function is necessary, when a word is guessed and removed from the operative options to find a clueword if a word has been selected
    public void deletevertex(String wordselected) {
        this.graph.removeVertex(wordselected);
    }

    public Graph<String, DefaultWeightedEdge> getGraphObj() {
        return graph;
    }
}
