package org.ryebread.algorithmplayground.structures.tree;

public class BinaryNode<T extends Comparable<T>> {

	private T element;

	private BinaryNode<T> parent;
	private BinaryNode<T> left;
	private BinaryNode<T> right;

	public BinaryNode(T element) {
		this.element = element;
	}

	public T getElement() {
		return this.element;
	}

	public BinaryNode<T> getParent() {
		return parent;
	}

	public void setParent(BinaryNode<T> parent) {
		this.parent = parent;
	}

	public BinaryNode<T> getLeft() {
		return left;
	}

	public void setLeft(BinaryNode<T> left) {
		this.left = left;
	}

	public BinaryNode<T> getRight() {
		return right;
	}

	public void setRight(BinaryNode<T> right) {
		this.right = right;
	}

	public BinaryNode<T> getChild(Direction direction) {
		if (direction == Direction.LEFT) {
			return this.left;
		} else {
			return this.right;
		}
	}

	public String toString() {
		return "[ " + element + " ]";
	}

}
