package org.ryebread.algorithmplayground.services.graphs;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Predicate;

import org.ryebread.algorithmplayground.structures.graph.AdjacencyMapGraph;
import org.ryebread.algorithmplayground.structures.graph.Edge;
import org.ryebread.algorithmplayground.structures.graph.EdgeType;
import org.ryebread.algorithmplayground.structures.graph.Graph;
import org.ryebread.algorithmplayground.structures.graph.WeightedAdjacencyMapGraph;
import org.ryebread.algorithmplayground.structures.graph.WeightedGraph;
import org.ryebread.algorithmplayground.structures.lists.Pushable;
import org.ryebread.algorithmplayground.structures.lists.PushableQueue;
import org.ryebread.algorithmplayground.structures.lists.PushableStack;
import org.ryebread.algorithmplayground.structures.unionfind.UnionFind;
import org.springframework.stereotype.Service;

@Service
public class GraphServiceImpl implements GraphService {

	@Override
	public <T> List<T> topologicalSort(Graph<T> graph) {
		Queue<T> next = new LinkedList<>();
		Map<T, Integer> inDegrees = new HashMap<>(graph.numVertices());

		for (T vertex : graph.getVertices()) {
			inDegrees.put(vertex, 0);
		}

		for (T vertex : graph.getVertices()) {
			for (Edge<T> edge : graph.getEdges(vertex)) {
				T element = edge.to();
				inDegrees.put(element, inDegrees.get(element) + 1);
			}
		}

		for (T vertex : graph.getVertices()) {
			if (inDegrees.get(vertex) == 0) {
				next.add(vertex);
			}
		}

		List<T> sorted = new ArrayList<>();

		while (!next.isEmpty()) {
			T head = next.remove();
			sorted.add(head);
			for (Edge<T> edge : graph.getEdges(head)) {
				T element = edge.to();
				inDegrees.put(element, inDegrees.get(element) - 1);
				if (inDegrees.get(element) == 0) {
					next.add(element);
				}
			}
		}

		return sorted;
	}

	@Override
	public <T> WeightedGraph<T> prims(WeightedGraph<T> graph) {
		final Map<T, Long> valueMap = new HashMap<>();
		Map<T, T> parentMap = new HashMap<>();

		// Starting out, all minimum weights need to be as large as possible
		// and all parents are set to 'null';
		for (T key : graph.getVertices()) {
			valueMap.put(key, Long.MAX_VALUE);
			parentMap.put(key, null);
		}

		// Use the value map as the comparator for the heap.
		AbstractQueue<T> heap = new PriorityQueue<>((a, b) -> {
			return valueMap.get(a).compareTo(valueMap.get(b));
		});

		// Fill the heap with every vertex in the graph.
		for (T key : graph.getVertices()) {
			heap.add(key);
		}

		/*
		 * While the heap is not empty:
		 * 
		 * - pop the top element as `head`, and the get the incident edges 
		 * - if the edge points to a vertex that still exists on the heap, compare the weight of this
		 * 	   edge against the currently stored minimum weight 
		 * - if the edge's weight is lesser than the stored weight for that vertex, update the parent
		 *     to be `head`, and update the minimum weight to be that of the edge
		 * - delete then add the edge's vertex from the heap to recalibrate the heap order
		 */
		while (!heap.isEmpty()) {
			T head = heap.remove();
			for (Edge<T> edge : graph.getEdges(head)) {
				T element = edge.to();
				if (heap.contains(element)) {
					Long currentWeight = valueMap.get(element);
					if (edge.getWeight() < currentWeight) {
						parentMap.put(element, head);
						valueMap.put(element, edge.getWeight());
						heap.remove(element);
						heap.add(element);
					}
				}
			}
		}

		WeightedGraph<T> minimumSpanningTree = new WeightedAdjacencyMapGraph<T>(graph.getVertices(),
				EdgeType.UNDIRECTED);

		for (T vertex : minimumSpanningTree.getVertices()) {
			T parentVertex = parentMap.get(vertex);
			if (parentVertex != null) {
				minimumSpanningTree.addEdge(parentVertex, vertex, valueMap.get(vertex));
			}
		}
		return minimumSpanningTree;

	}

	@Override
	public <T> WeightedGraph<T> kruskals(WeightedGraph<T> graph) {

		UnionFind<T> union = new UnionFind<>();
		AbstractQueue<Edge<T>> heap = new PriorityQueue<>();
		List<Edge<T>> minEdges = new ArrayList<>();

		//Populate the data structures with respective vertices and edges
		for (T vertex : graph.getVertices()) {
			union.add(vertex);
			for (Edge<T> edge : graph.getEdges(vertex)) {
				heap.add(edge);
			}
		}

		//Start popping edges off the heap, and do so until the heap is empty.
		while (!heap.isEmpty()) {
			Edge<T> minEdge = heap.remove();
			if (union.join(minEdge.from(), minEdge.to())) {
				minEdges.add(minEdge);
			}
		}

		WeightedGraph<T> minimumSpanningTree = new WeightedAdjacencyMapGraph<T>(graph.getVertices(),
				EdgeType.UNDIRECTED);

		for (Edge<T> minEdge : minEdges) {
			minimumSpanningTree.addEdge(minEdge.from(), minEdge.to(), minEdge.getWeight());
		}

		return minimumSpanningTree;
	}

	@Override
	public <T> List<T> breadthFirstSearch(Graph<T> graph, T start) {
		return graphSearch(graph, start, new PushableQueue<>());
	}

	@Override
	public <T> List<T> depthFirstSearch(Graph<T> graph, T start) {
		return graphSearch(graph, start, new PushableStack<>());
	}

	private <T> List<T> graphSearch(Graph<T> graph, T start, Pushable<T> nextNodes) {
		if (!graph.hasVertex(start)) {
			return null;
		}
		List<T> orderedNodes = new LinkedList<>();
		Map<T, Boolean> visitedMap = new HashMap<>();

		for (T vertex : graph.getVertices()) {
			visitedMap.put(vertex, false);
		}

		nextNodes.push(start);
		visitedMap.put(start, true);

		while (!nextNodes.isEmpty()) {
			T next = nextNodes.pop();
			orderedNodes.add(next);
			for (Edge<T> edge : graph.getEdges(next)) {
				T to = edge.to();
				if (!visitedMap.get(to)) {
					nextNodes.push(to);
					visitedMap.put(to, true);
				}
			}
		}

		return orderedNodes;
	}

}
