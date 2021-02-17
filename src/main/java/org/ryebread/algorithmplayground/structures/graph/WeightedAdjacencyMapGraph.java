package org.ryebread.algorithmplayground.structures.graph;

import java.util.Collection;

public class WeightedAdjacencyMapGraph<T> extends AdjacencyMapGraph<T> implements WeightedGraph<T> {

	public static final Long DEFAULT_WEIGHT = 5L;

	public WeightedAdjacencyMapGraph() {
		super();
	}
	
	public WeightedAdjacencyMapGraph(Collection<T> vertices) {
		super(vertices);
	}

	public WeightedAdjacencyMapGraph(EdgeType edgeType) {
		super(edgeType);
	}

	public WeightedAdjacencyMapGraph(Collection<T> vertices, EdgeType undirected) {
		super(vertices, undirected);
	}

	@Override
	public void addEdge(T from, T to) {
		addEdge(from, to, DEFAULT_WEIGHT);
	}

	@Override
	public void addEdge(T from, T to, Long weight) {
		if (this.edgeType == EdgeType.UNDIRECTED) {
			edgeFromAToBWithWeight(to, from, weight);
		}
		edgeFromAToBWithWeight(from, to, weight);
	}

	private void edgeFromAToBWithWeight(T a, T b, Long weight) {
		this.incidenceMap.get(a).add(new Edge<T>(a, b, weight));
	}

	@Override
	protected String formatEdgeString(T key, Edge<T> edge) {
		StringBuilder sb = new StringBuilder();
		sb.append(edge.to());
		sb.append("(");
		sb.append(edge.getWeight());
		sb.append(")");
		return sb.toString();
	}

}
