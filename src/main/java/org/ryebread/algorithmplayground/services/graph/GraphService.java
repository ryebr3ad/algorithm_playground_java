package org.ryebread.algorithmplayground.services.graph;

import java.util.List;
import java.util.Map;

import org.ryebread.algorithmplayground.structures.graph.Graph;
import org.ryebread.algorithmplayground.structures.graph.WeightedGraph;

public interface GraphService {

	public <T extends Comparable<T>> List<T> topologicalSort(Graph<T> graph);

	public <T extends Comparable<T>> WeightedGraph<T> prims(WeightedGraph<T> graph);

	public <T extends Comparable<T>> WeightedGraph<T> kruskals(WeightedGraph<T> graph);

	public <T extends Comparable<T>> List<T> breadthFirstSearch(Graph<T> graph);

	public <T extends Comparable<T>> List<T> depthFirstSearch(Graph<T> graph);

	public <T extends Comparable<T>> List<List<T>> stronglyConnectedComponents(Graph<T> graph);
	
	public <T extends Comparable<T>> WeightedGraph<T> dijkstras(WeightedGraph<T> graph, T start);
}
