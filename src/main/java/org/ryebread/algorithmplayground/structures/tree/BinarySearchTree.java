package org.ryebread.algorithmplayground.structures.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BinarySearchTree<T extends Comparable<T>> {

	public BinaryNode<T> root = null;

	/**
	 * Get the tree's elements in sorted order
	 * @return
	 */
	public List<T> all() {
		List<T> elements = new ArrayList<>();
		inOrderTraversalWithList(elements, this.root);
		return elements;
	}

	/**
	 * Add an element to the binary search tree
	 * @param element
	 */
	public void add(T element) {
		BinaryNode<T> newNode = new BinaryNode<>(element);
		BinaryNode<T> parentNode = null;
		BinaryNode<T> currNode = root;

		//Traverse the tree, starting at the root
		while (currNode != null) {
			parentNode = currNode;
			if (element.compareTo(currNode.getElement()) < 0) {
				currNode = currNode.getLeft();
			} else {
				currNode = currNode.getRight();
			}
		}

		//A null parent implies that the tree could not be traversed, meaning that the root is null
		if (parentNode == null) {
			this.root = newNode;
		}
		// Otherwise, set the new node to the left or the right, depending on the ordering
		else if (element.compareTo(parentNode.getElement()) < 0) {
			parentNode.setLeft(newNode);
		} else {
			parentNode.setRight(newNode);
		}

		newNode.setParent(parentNode);

	}

	public T find(T element) {
		BinaryNode<T> foundNode = findNode(root, element);
		return foundNode == null ? null : foundNode.getElement();
	}

	public T min() {
		BinaryNode<T> minNode = min(this.root);
		return minNode == null ? null : minNode.getElement();
	}

	private BinaryNode<T> min(BinaryNode<T> node) {
		return drillInDirection(node, Direction.LEFT);
	}

	public T max() {
		BinaryNode<T> maxNode = max(this.root);
		return maxNode == null ? null : maxNode.getElement();
	}

	private BinaryNode<T> max(BinaryNode<T> node) {
		return drillInDirection(node, Direction.RIGHT);
	}

	public BinaryNode<T> predecessor(T element) {
		return adjacentElement(element, Direction.LEFT, this::max);
	}

	public BinaryNode<T> successor(T element) {
		return adjacentElement(element, Direction.RIGHT, this::min);
	}

	public boolean delete(T element) {
		BinaryNode<T> foundNode = this.findNode(this.root, element);
		if (foundNode == null) {
			return false;
		}
		//With one or no children, transplanting the deleted node with its child will suffice
		if (foundNode.getLeft() == null) {
			transplant(foundNode, foundNode.getRight());
		} else if (foundNode.getRight() == null) {
			transplant(foundNode, foundNode.getLeft());
		}
		//With two children, things get tricky.
		else {
			//Find the successor node, which will be the next largest element in the tree
			BinaryNode<T> successorNode = successor(element);

			/*
			 * If the deleted node is not the direct parent of the successor node, extra work
			 * needs to be done with the swap.  In this case, any right children of the successor
			 * node will need to take its old place in the tree
			 */
			if (successorNode.getParent() != foundNode) {
				transplant(successorNode, successorNode.getRight());

				//The successor node's new right child will be the right subtree of the deleted node
				successorNode.setRight(foundNode.getRight());
				successorNode.getRight().setParent(successorNode);
			}
			transplant(foundNode, successorNode);

			//Similar to above, the successor node's new left child will be the left subtree of the deleted node
			successorNode.setLeft(foundNode.getLeft());
			successorNode.getLeft().setParent(successorNode);
		}
		return true;
	}

	/**
	 * Replace oldNode with newNode in the tree hierarchy
	 * @param oldNode
	 * @param newNode
	 */
	private void transplant(BinaryNode<T> oldNode, BinaryNode<T> newNode) {
		//If the old node was the root, then the new node will take its place
		if (oldNode.getParent() == null) {
			this.root = newNode;
		}
		//Replace the child reference to the old node in the parent with the new node
		else if (oldNode == oldNode.getParent().getLeft()) {
			oldNode.getParent().setLeft(newNode);
		} else {
			oldNode.getParent().setRight(newNode);
		}

		/*
		 * The new node could be null, which just meant that the old node was being replaced with nothing.
		 * If the new node exists, then replace its parent with the parent of the old node.
		 */
		if (newNode != null) {
			newNode.setParent(oldNode.getParent());
		}
	}

	private void inOrderTraversalWithList(List<T> elements, BinaryNode<T> node) {
		if (node != null) {
			inOrderTraversalWithList(elements, node.getLeft());
			elements.add(node.getElement());
			inOrderTraversalWithList(elements, node.getRight());
		}

	}

	private BinaryNode<T> findNode(BinaryNode<T> node, T element) {
		if (node == null) {
			return null;
		}
		if (node.getElement().equals(element)) {
			return node;
		}
		if (element.compareTo(node.getElement()) < 0) {
			return findNode(node.getLeft(), element);
		} else {
			return findNode(node.getRight(), element);
		}
	}

	private BinaryNode<T> adjacentElement(T element, Direction direction, Function<BinaryNode<T>, BinaryNode<T>> func) {
		BinaryNode<T> foundNode = this.findNode(this.root, element);
		if (foundNode == null) {
			return null;
		}
		if (foundNode.getChild(direction) != null) {
			BinaryNode<T> childNode = foundNode.getChild(direction);
			return func.apply(childNode);
		}
		BinaryNode<T> parentNode = foundNode.getParent();
		while (parentNode != null && foundNode == parentNode.getChild(direction)) {
			foundNode = parentNode;
			parentNode = parentNode.getParent();
		}
		return parentNode == null ? null : parentNode;
	}

	/**
	 * Return the element of the node farthest from the passed in node
	 * from the indicated direction
	 * @param node
	 * @param direction
	 * @return
	 */
	private BinaryNode<T> drillInDirection(BinaryNode<T> node, Direction direction) {
		BinaryNode<T> currNode = node;
		while (currNode != null && currNode.getChild(direction) != null) {
			currNode = currNode.getChild(direction);
		}
		if (currNode == null) {
			return null;
		} else {
			return currNode;
		}
	}

}
