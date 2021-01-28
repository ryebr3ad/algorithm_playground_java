package org.ryebread.algorithmplayground.services.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.structures.graph.EdgeType;
import org.ryebread.algorithmplayground.structures.graph.WeightedAdjacencyMapGraph;
import org.ryebread.algorithmplayground.structures.graph.WeightedGraph;

public class TestGraphService {

	private GraphService graphService = new GraphServiceImpl();
	
	private WeightedGraph<String> graph;
	
	@BeforeEach
	public void populateGraph() {
		graph = new WeightedAdjacencyMapGraph<>(EdgeType.UNDIRECTED);

		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.addVertex("F");
		graph.addVertex("G");

		graph.addEdge("A", "B", 1L);
		graph.addEdge("A", "F", 3L);
		graph.addEdge("A", "G", 2L);

		graph.addEdge("B", "C", 2L);
		graph.addEdge("B", "F", 1L);

		graph.addEdge("C", "D", 6L);

		graph.addEdge("D", "E", 3L);
		graph.addEdge("D", "F", 3L);

		graph.addEdge("E", "F", 4L);
		graph.addEdge("E", "G", 1L);

		graph.addEdge("F", "G", 1L);
	}
	
	@Test
	public void testPrims() {
		WeightedGraph<String> minSpanTree = graphService.prims(graph);
		assertEquals(minSpanTree.getEdge("A", "B").getWeight(), 1L);
		assertEquals(minSpanTree.getEdge("F", "G").getWeight(), 1L);
		assertEquals(minSpanTree.getEdge("B", "C").getWeight(), 2L);
	}
	
	@Test
	public void testKruskals() {
		WeightedGraph<String> minSpanTree = graphService.kruskals(graph);
		assertEquals(minSpanTree.getEdge("A", "B").getWeight(), 1L);
		assertEquals(minSpanTree.getEdge("F", "G").getWeight(), 1L);
		assertEquals(minSpanTree.getEdge("B", "C").getWeight(), 2L);
	}
	
}
