package org.ryebread.algorithmplayground.structures.graph;

public interface WeightedGraph<T> extends Graph<T> {

	public abstract void addEdge(T from, T to, Long weight);

}