package org.ryebread.algorithmplayground.structures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.structures.graph.WeightedAdjacencyMapGraph;
import org.ryebread.algorithmplayground.structures.graph.WeightedGraph;

public class TestWeightedGraph {

	@Test
	public void testAddingEdgeUsingDefaultWeight() {
		WeightedGraph<Integer> graph = new WeightedAdjacencyMapGraph<>();
		graph.addVertex(0);
		graph.addVertex(1);
		graph.addVertex(2);

		graph.addEdge(0, 1);

		assertEquals(graph.getEdge(0, 1).getWeight(), WeightedAdjacencyMapGraph.DEFAULT_WEIGHT);
	}

}
