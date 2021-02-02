package org.ryebread.algorithmplayground.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.services.graph.GraphService;
import org.ryebread.algorithmplayground.services.graph.GraphServiceImpl;
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
	public void testStronglyConnectedComponents() {
		Graph<String> gph = new AdjacencyMapGraph<>();

		gph.addVertex("A");
		gph.addVertex("B");
		gph.addVertex("C");
		gph.addVertex("D");
		gph.addVertex("E");
		gph.addVertex("F");
		gph.addVertex("G");

		gph.addEdge("A", "B");

		gph.addEdge("B", "C");
		gph.addEdge("B", "E");
		gph.addEdge("B", "F");

		gph.addEdge("C", "G");

		gph.addEdge("D", "C");

		gph.addEdge("E", "A");

		gph.addEdge("F", "G");

		gph.addEdge("G", "D");

		List<List<String>> components = graphService.stronglyConnectedComponents(gph);
		assertEquals(components.size(), 3);
	}

	@Test
	public void testDijkstras() {
		WeightedGraph<String> gph = new WeightedAdjacencyMapGraph<>(EdgeType.UNDIRECTED);

		gph.addVertex("A");
		gph.addVertex("B");
		gph.addVertex("C");
		gph.addVertex("D");
		gph.addVertex("E");
		gph.addVertex("F");

		gph.addEdge("A", "B", 4L);
		gph.addEdge("A", "C", 1L);
		gph.addEdge("A", "D", 1L);

		gph.addEdge("B", "C", 2L);
		gph.addEdge("B", "F", 1L);

		gph.addEdge("C", "D", 4L);
		gph.addEdge("C", "E", 3L);
		gph.addEdge("C", "F", 4L);

		gph.addEdge("D", "E", 2L);

		gph.addEdge("E", "F", 1L);

		WeightedGraph<String> shortestPathGph = graphService.dijkstras(gph, "A");
		assertNull(shortestPathGph.getEdge("A", "B"));
		assertNotNull(shortestPathGph.getEdge("A", "C"));
	}

}
