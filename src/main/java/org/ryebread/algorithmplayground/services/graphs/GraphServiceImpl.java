package org.ryebread.algorithmplayground.services.graphs;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import org.ryebread.algorithmplayground.structures.graph.Edge;
import org.ryebread.algorithmplayground.structures.graph.EdgeType;
import org.ryebread.algorithmplayground.structures.graph.Graph;
import org.ryebread.algorithmplayground.structures.graph.WeightedAdjacencyMapGraph;
import org.ryebread.algorithmplayground.structures.graph.WeightedGraph;
import org.ryebread.algorithmplayground.structures.unionfind.UnionFind;
import org.springframework.stereotype.Service;

@Service
public class GraphServiceImpl implements GraphService {

	@Override
	public <T extends Comparable<T>> List<T> topologicalSort(Graph<T> graph) {
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
	public <T extends Comparable<T>> WeightedGraph<T> prims(WeightedGraph<T> graph) {
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
	public <T extends Comparable<T>> WeightedGraph<T> kruskals(WeightedGraph<T> graph) {

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
	public <T extends Comparable<T>> List<T> breadthFirstSearch(Graph<T> graph) {
		Queue<T> nextNodes = new LinkedList<>();
		List<T> orderedNodes = new LinkedList<>();
		Map<T, Boolean> visitedMap = new HashMap<>();

		for (T vertex : graph.getVertices()) {
			visitedMap.put(vertex, false);
		}

		for (T vertex : graph.getVertices()) {
			if (!visitedMap.get(vertex)) {
				bfs(graph, nextNodes, orderedNodes, visitedMap, vertex);
			}
		}

		return orderedNodes;
	}

	private <T extends Comparable<T>> void bfs(Graph<T> graph, Queue<T> nextNodes, List<T> orderedNodes,
			Map<T, Boolean> visitedMap, T vertex) {
		nextNodes.add(vertex);
		visitedMap.put(vertex, true);

		while (!nextNodes.isEmpty()) {
			T next = nextNodes.remove();
			orderedNodes.add(next);
			for (Edge<T> edge : graph.getEdges(next)) {
				T to = edge.to();
				if (!visitedMap.get(to)) {
					nextNodes.add(to);
					visitedMap.put(to, true);
				}
			}
		}
	}

	@Override
	public <T extends Comparable<T>> List<T> depthFirstSearch(Graph<T> graph) {
		List<T> orderedNodes = new LinkedList<>();

		Map<T, Boolean> visitedMap = initVisitedMap(graph);

		for (T vertex : graph.getVertices()) {
			if (!visitedMap.get(vertex)) {
				dfs(graph, vertex, visitedMap, orderedNodes);
			}
		}

		return orderedNodes;
	}

	@Override
	public <T extends Comparable<T>> List<List<T>> stronglyConnectedComponents(Graph<T> graph) {
		//1. Run a depth-first search and output the nodes in searched order
		List<T> dfsNodes = depthFirstSearch(graph);

		//2. Reverse the edges of the graph
		Graph<T> reversedGraph = graph.reverse();

		/*
		 * 3. reverse the order of the DFS-ordered nodes, then iterate 
		 * through that list and DFS on the reversed graph
		 */
		Collections.reverse(dfsNodes);

		Map<T, Boolean> visitedMap = initVisitedMap(reversedGraph);
		List<List<T>> componentList = new LinkedList<>();

		for (T vertex : dfsNodes) {
			if (!visitedMap.get(vertex)) {
				List<T> componentNodes = new LinkedList<>();
				dfs(reversedGraph, vertex, visitedMap, componentNodes);
				componentList.add(componentNodes);
			}
		}

		return componentList;
	}

	@Override
	public <T extends Comparable<T>> WeightedGraph<T> dijkstras(WeightedGraph<T> graph, T start) {
		Map<T, T> parentMap = new HashMap<>();
		Map<T, Boolean> scannedMap = new HashMap<>();
		final Map<T, Long> distanceMap = new HashMap<>();

		for (T vertex : graph.getVertices()) {
			parentMap.put(vertex, null);
			scannedMap.put(vertex, false);
			distanceMap.put(vertex, Long.MAX_VALUE);
		}

		// Use the value map as the comparator for the heap.
		AbstractQueue<T> heap = new PriorityQueue<>((a, b) -> {
			return distanceMap.get(a).compareTo(distanceMap.get(b));
		});

		//Distance from the starting vertex to itself is always 0
		distanceMap.put(start, 0L);
		heap.add(start);

		while (!heap.isEmpty()) {
			T parent = heap.remove();
			Long parentDistance = distanceMap.get(parent);
			for (Edge<T> edge : graph.getEdges(parent)) {
				if (!scannedMap.get(edge.to())) {
					Long proposedDistance = parentDistance + edge.getWeight();
					if (proposedDistance < distanceMap.get(edge.to())) {
						distanceMap.put(edge.to(), proposedDistance);
						parentMap.put(edge.to(), parent);
						heap.remove(edge.to());
						heap.add(edge.to());
					}
				}
			}
			scannedMap.put(parent, true);
		}

		WeightedGraph<T> shortestPathGraph = new WeightedAdjacencyMapGraph<>(graph.getVertices());

		for (T vertex : shortestPathGraph.getVertices()) {
			if (parentMap.get(vertex) != null) {
				T from = parentMap.get(vertex);
				T to = vertex;
				Long weight = graph.getEdge(from, to).getWeight();
				shortestPathGraph.addEdge(from, to, weight);
			}
		}

		return shortestPathGraph;
	}

	private <T extends Comparable<T>> Map<T, Boolean> initVisitedMap(Graph<T> graph) {
		Map<T, Boolean> visitedMap = new HashMap<>();
		for (T vertex : graph.getVertices()) {
			visitedMap.put(vertex, false);
		}
		return visitedMap;
	}

	private <T extends Comparable<T>> void dfs(Graph<T> graph, T vertex, Map<T, Boolean> visitedMap,
			List<T> orderedNodes) {
		visitedMap.put(vertex, true);
		for (Edge<T> edge : graph.getEdges(vertex)) {
			if (!visitedMap.get(edge.to())) {
				dfs(graph, edge.to(), visitedMap, orderedNodes);
			}
		}
		orderedNodes.add(vertex);
	}

}
