package org.ryebread.algorithmplayground.structures.graph;

import java.util.Collection;

/**
 * Abstract contract for a graph.  Implementations may vary, with certain ones
 * improving algorithm runtime over others.
 * 
 * Assumes all vertices are integers
 * 
 * @author Ryan
 */
public interface Graph<T> {

	public abstract void addVertex(T vertex);

	public abstract boolean hasVertex(T vertex);

	public abstract int numVertices();

	public abstract Collection<T> getVertices();

	public abstract void addEdge(T from, T to);

	public abstract Edge<T> getEdge(T from, T to);

	public abstract Collection<Edge<T>> getEdges(T vertex);

	public abstract boolean hasEdge(T from, T to);

	public abstract Graph<T> reverse();

}
