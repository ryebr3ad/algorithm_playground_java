package org.ryebread.algorithmplayground.structures.tree;

public class RedBlackNode<T extends Comparable<T>> {

	private T element;
	private Color color;

	private RedBlackNode<T> parent;
	private RedBlackNode<T> left;
	private RedBlackNode<T> right;

	public RedBlackNode() {
		this(null, Color.RED);
	}

	public RedBlackNode(Color color) {
		this(null, color);
	}

	public RedBlackNode(T element) {
		this(element, Color.RED);
	}

	public RedBlackNode(T element, Color color) {
		this.element = element;
		this.color = color;
	}

	public T getElement() {
		return this.element;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public RedBlackNode<T> getParent() {
		return parent;
	}

	public void setParent(RedBlackNode<T> parent) {
		this.parent = parent;
	}

	public RedBlackNode<T> getLeft() {
		return left;
	}

	public void setLeft(RedBlackNode<T> left) {
		this.left = left;
	}

	public RedBlackNode<T> getRight() {
		return right;
	}

	public void setRight(RedBlackNode<T> right) {
		this.right = right;
	}

}
