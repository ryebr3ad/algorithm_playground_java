package org.ryebread.algorithmplayground.structures.graph;

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
	
	public abstract int numVertices();
	
	public abstract Iterable<T> getVertices();
	
	public abstract void addEdge(T from, T to);
	
	public abstract Edge<T> getEdge(T from, T to);
	
	public abstract Iterable<Edge<T>> getEdges(T vertex);
	
	public abstract boolean hasEdge(T from, T to);

}
