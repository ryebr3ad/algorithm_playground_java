package org.ryebread.algorithmplayground.services.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.structures.graph.AdjacencyMapGraph;
import org.ryebread.algorithmplayground.structures.graph.EdgeType;
import org.ryebread.algorithmplayground.structures.graph.Graph;
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

	@Test
	public void testBfs() {
		Graph<String> gph = new AdjacencyMapGraph<>();

		gph.addVertex("A");
		gph.addVertex("B");
		gph.addVertex("C");
		gph.addVertex("D");
		gph.addVertex("E");
		gph.addVertex("F");

		gph.addEdge("A", "B");
		gph.addEdge("A", "C");
		gph.addEdge("A", "D");

		gph.addEdge("B", "D");

		gph.addEdge("C", "F");

		gph.addEdge("D", "E");

		gph.addEdge("E", "F");

		List<String> orderedNodes = graphService.breadthFirstSearch(gph, "A");
		System.out.println(orderedNodes);

	}
	
	@Test
	public void testDfs() {
		Graph<String> gph = new AdjacencyMapGraph<>();

		gph.addVertex("A");
		gph.addVertex("B");
		gph.addVertex("C");
		gph.addVertex("D");
		gph.addVertex("E");
		gph.addVertex("F");

		gph.addEdge("A", "B");
		gph.addEdge("A", "C");
		gph.addEdge("A", "D");

		gph.addEdge("B", "D");

		gph.addEdge("C", "F");

		gph.addEdge("D", "E");

		gph.addEdge("E", "F");

		List<String> orderedNodes = graphService.depthFirstSearch(gph, "A");
		System.out.println(orderedNodes);

	}

}
