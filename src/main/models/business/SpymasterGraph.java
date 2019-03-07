package main.models.business;

import org.jgrapht.*;
import org.jgrapht.alg.connectivity.*;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.*;
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
 * WIP
 * 
 * @author Ihsaan Malek
 * @version 03/07/2019
 */

public class SpymasterGraph {

	
	private Graph<String, DefaultWeightedEdge> SpymasterGraph;
	
	
	public SpymasterGraph() {
		this.SpymasterGraph = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
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
				if(this.SpymasterGraph.containsEdge(temp,values.get(i)) ==false) {
					this.SpymasterGraph.addEdge(temp,values.get(i));
				}
			}
			
		}
	}
	public void deletevertex(String wordselected) {
		this.SpymasterGraph.removeVertex(wordselected);
	}
	


}
