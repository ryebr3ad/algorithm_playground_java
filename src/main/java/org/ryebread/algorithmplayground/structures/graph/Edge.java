package org.ryebread.algorithmplayground.structures.graph;

public class Edge<T> implements Comparable<Edge<T>> {

	private static final Long UNWEIGHTED = 0L;

	private T from;
	private T to;
	private Long weight;

	public Edge(T from, T to) {
		this(from, to, UNWEIGHTED);
	}

	public Edge(T from, T to, Long weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public T to() {
		return this.to;
	}
	
	public T from() {
		return this.from;
	}

	public Long getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return "[ " + this.from + " -> " + this.to + " ](" + this.weight + ")";
	}

	@Override
	public int compareTo(Edge<T> o) {
		return this.getWeight().compareTo(o.getWeight());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
	
	

}
