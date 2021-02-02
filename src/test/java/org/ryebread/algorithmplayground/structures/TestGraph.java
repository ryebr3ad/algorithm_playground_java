package org.ryebread.algorithmplayground.structures;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.structures.graph.EdgeType;
import org.ryebread.algorithmplayground.structures.graph.Graph;
import org.ryebread.algorithmplayground.services.graph.GraphService;
import org.ryebread.algorithmplayground.services.graph.GraphServiceImpl;
import org.ryebread.algorithmplayground.structures.graph.AdjacencyMapGraph;

public class TestGraph {

	private Graph<Integer> directedGph;
	private Graph<Integer> undirectedGph;

	private GraphService graphService = new GraphServiceImpl();

	@BeforeEach
	public void initGraph() {
		directedGph = new AdjacencyMapGraph<>();
		undirectedGph = new AdjacencyMapGraph<>(EdgeType.UNDIRECTED);

		for (int i = 0; i < 5; i++) {
			directedGph.addVertex(i);
			undirectedGph.addVertex(i);
		}
	}

	@Test
	public void testAddingDirectedEdge() {
		directedGph.addEdge(0, 2);
		assertTrue(directedGph.hasEdge(0, 2));
		assertFalse(directedGph.hasEdge(2, 0));
	}

	@Test
	public void testAddingUndirectedEdge() {
		undirectedGph.addEdge(0, 2);
		assertTrue(undirectedGph.hasEdge(0, 2));
		assertTrue(undirectedGph.hasEdge(2, 0));
	}

	@Test
	public void testTopologicalSort() {
		Graph<Integer> gph = new AdjacencyMapGraph<>();
		gph.addVertex(1);
		gph.addVertex(2);
		gph.addVertex(3);
		gph.addVertex(4);
		gph.addVertex(5);
		gph.addVertex(6);

		// Only one solution
		gph.addEdge(1, 2);
		gph.addEdge(1, 4);
		gph.addEdge(4, 2);
		gph.addEdge(2, 3);
		gph.addEdge(4, 3);
		gph.addEdge(4, 5);
		gph.addEdge(3, 5);
		gph.addEdge(3, 6);
		gph.addEdge(5, 6);

		List<Integer> sorted = graphService.topologicalSort(gph);

		assertTrue(sorted.get(0) == 1);
		assertTrue(sorted.get(1) == 4);
		assertTrue(sorted.get(2) == 2);
		assertTrue(sorted.get(3) == 3);
		assertTrue(sorted.get(4) == 5);
		assertTrue(sorted.get(5) == 6);

	}

}
