package org.ryebread.algorithmplayground.structures.list;

/**
 * Doubly linked list node
 * @author Ryan
 *
 * @param <T>
 */
public class Node<T extends Comparable<T>> {

	private T element;
	private Node<T> prev;
	private Node<T> next;

	public Node(T element) {
		this.element = element;
	}

	public T getElement() {
		return element;
	}

	public Node<T> getPrev() {
		return prev;
	}

	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

}
