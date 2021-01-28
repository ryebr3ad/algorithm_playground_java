package org.ryebread.algorithmplayground.services.graphs;

import java.util.List;
import java.util.Map;

import org.ryebread.algorithmplayground.structures.graph.Graph;
import org.ryebread.algorithmplayground.structures.graph.WeightedGraph;

public interface GraphService {

	public <T> List<T> topologicalSort(Graph<T> graph);

	public <T> WeightedGraph<T> prims(WeightedGraph<T> graph);

	public <T> WeightedGraph<T> kruskals(WeightedGraph<T> graph);

	public <T> List<T> breadthFirstSearch(Graph<T> graph, T start);

	public <T> List<T> depthFirstSearch(Graph<T> graph, T start);
}
