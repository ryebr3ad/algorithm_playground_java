package org.ryebread.algorithmplayground.structures.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AdjacencyMapGraph<T extends Comparable<T>> implements Graph<T> {

	protected Map<T, List<Edge<T>>> incidenceMap;
	protected EdgeType edgeType;

	public AdjacencyMapGraph() {
		this(EdgeType.DIRECTED);
	}

	public AdjacencyMapGraph(Iterable<T> vertices) {
		this(vertices, EdgeType.DIRECTED);
	}

	public AdjacencyMapGraph(EdgeType edgeType) {
		this(null, edgeType);
	}

	public AdjacencyMapGraph(Iterable<T> vertices, EdgeType edgeType) {
		this.incidenceMap = new HashMap<>();
		if (vertices != null) {
			for (T vertex : vertices) {
				this.incidenceMap.put(vertex, new LinkedList<>());
			}
		}
		this.edgeType = edgeType;
	}

	@Override
	public void addVertex(T vertex) {
		if (!hasVertex(vertex)) {
			incidenceMap.put(vertex, new LinkedList<>());
		}
	}

	@Override
	public int numVertices() {
		return incidenceMap.keySet().size();
	}
	
	@Override
	public boolean hasVertex(T vertex) {
		return incidenceMap.containsKey(vertex);
	}

	@Override
	public Iterable<T> getVertices() {
		return incidenceMap.keySet();
	}

	@Override
	public void addEdge(T from, T to) {
		if (this.edgeType == EdgeType.UNDIRECTED) {
			edgeFromAToB(to, from);
		}
		edgeFromAToB(from, to);
	}

	private void edgeFromAToB(T a, T b) {
		this.incidenceMap.get(a).add(new Edge<T>(a, b));
	}

	@Override
	public Edge<T> getEdge(T from, T to) {
		for (Edge<T> edge : getEdges(from)) {
			if (edge.to().equals(to)) {
				return edge;
			}
		}
		return null;
	}

	@Override
	public Iterable<Edge<T>> getEdges(T vertex) {
		return this.incidenceMap.get(vertex);
	}

	@Override
	public boolean hasEdge(T from, T to) {
		return getEdge(from, to) != null;
	}
	
	@Override
	public Graph<T> reverse() {
		Graph<T> reversedGraph = new AdjacencyMapGraph<T>(this.getVertices());

		for(T vertex : this.getVertices()) {
			for(Edge<T> edge : this.getEdges(vertex)) {
				reversedGraph.addEdge(edge.to(), edge.from());
			}
		}
		
		return reversedGraph;
	}

	public AdjacencyMapGraph<T> deepCopy() {
		AdjacencyMapGraph<T> graph = new AdjacencyMapGraph<>(this.edgeType);
		for (T key : this.incidenceMap.keySet()) {
			graph.addVertex(key);
			for (Edge<T> edge : getEdges(key)) {
				graph.addEdge(key, edge.to());
			}
		}
		return graph;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (T key : this.incidenceMap.keySet()) {
			sb.append(key);
			sb.append(":");
			for (Edge<T> edge : incidenceMap.get(key)) {
				sb.append(" -->");
				sb.append(formatEdgeString(key, edge));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	protected String formatEdgeString(T key, Edge<T> edge) {
		return edge.to().toString();
	}

}
