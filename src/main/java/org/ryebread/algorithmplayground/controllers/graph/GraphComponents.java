package org.ryebread.algorithmplayground.controllers.graph;

import java.util.List;

import org.ryebread.algorithmplayground.structures.graph.EdgeType;
import org.ryebread.algorithmplayground.structures.graph.EdgeWeight;

public class GraphComponents<T extends Comparable<T>> {

	private EdgeType edgeType;
	private EdgeWeight edgeWeight;
	private List<T> vertices;
	private List<EdgeComponents<T>> edges;

	public GraphComponents() {

	}

	public EdgeType getEdgeType() {
		return edgeType;
	}

	public void setEdgeType(EdgeType edgeType) {
		this.edgeType = edgeType;
	}

	public EdgeWeight getEdgeWeight() {
		return edgeWeight;
	}

	public void setEdgeWeight(EdgeWeight edgeWeight) {
		this.edgeWeight = edgeWeight;
	}

	public List<T> getVertices() {
		return vertices;
	}

	public void setVertices(List<T> vertices) {
		this.vertices = vertices;
	}

	public List<EdgeComponents<T>> getEdges() {
		return edges;
	}

	public void setEdges(List<EdgeComponents<T>> edges) {
		this.edges = edges;
	}

}
