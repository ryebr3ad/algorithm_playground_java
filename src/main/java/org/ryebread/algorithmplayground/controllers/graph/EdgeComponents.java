package org.ryebread.algorithmplayground.controllers.graph;

public class EdgeComponents<T extends Comparable<T>> {

	private T from;
	private T to;
	private Long weight;

	public EdgeComponents() {
	}

	public T getFrom() {
		return from;
	}

	public void setFrom(T from) {
		this.from = from;
	}

	public T getTo() {
		return to;
	}

	public void setTo(T to) {
		this.to = to;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

}
