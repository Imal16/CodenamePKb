package main.models.business;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RelationGraph {
    public Graph<String, DefaultWeightedEdge> graph;

    /**
     * Default constructor
     */
    public RelationGraph() {
        this.graph = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    }

    /**
     * Creates graph of Codenames related to each other
     * @param teamcards
     * @param jsonfilestorage
     */
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

    /**
     * Deletes Codename from graph
     * @param wordselected
     */
    public void deletevertex(String wordselected) {
        this.graph.removeVertex(wordselected);
    }

    /**
     * graph getter
     * @return
     */
    public Graph<String, DefaultWeightedEdge> getGraphObj() {
        return graph;
    }
}
