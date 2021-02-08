package org.ryebread.algorithmplayground.structures.tree;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<T extends Comparable<T>> {

	//Sentinel node, used in place of null to make resolution of rule violations easier to manage
	private final RedBlackNode<T> nil = new RedBlackNode<>(Color.BLACK);
	private RedBlackNode<T> root;

	public RedBlackTree() {
		this.root = nil;
	}

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
	 * Find the element if it exists in the tree
	 * @param element
	 * @return returns the element if it exists, or null otherwise
	 */
	public T find(T element) {
		RedBlackNode<T> foundNode = findNode(root, element);
		return foundNode == null ? null : foundNode.getElement();
	}

	public void add(T element) {
		RedBlackNode<T> newNode = createNewNode(element);
		RedBlackNode<T> newParent = nil;
		RedBlackNode<T> currentNode = root;
		/*
		 * Starting at the root, traverse the tree, going left if element is less
		 * than the current node's value; otherwise, go right.
		 */
		while (currentNode != nil) {
			newParent = currentNode;
			if (element.compareTo(currentNode.getElement()) < 0) {
				currentNode = currentNode.getLeft();
			} else {
				currentNode = currentNode.getRight();
			}
		}
		//The parent will be the last node touched before a nil node was hit.
		newNode.setParent(newParent);

		//If the parent is nil, the loop was never entered.  Make the new node the root
		if (newParent == nil) {
			this.root = newNode;
		}
		//Otherwise, determine whether the new node is the left or right child of the parent
		else if (element.compareTo(newParent.getElement()) < 0) {
			newParent.setLeft(newNode);
		} else {
			newParent.setRight(newNode);
		}

		fixRedBlackTree(newNode);
	}

	/**
	 * Fix any conflict of red black tree rules, starting at node and cascading upwards
	 * 
	 * Red-Black rules:
	 *  1. Every node is either BLACK or RED
	 *  2. The root is BLACK
	 *  3. Every leaf is BLACK (`nil` nodes are leaves in this implementation)
	 *  4. If a node is RED, then both its children are BLACK
	 *  5. For each node, all paths from the node to its descendant leaves contain the same number of BLACK nodes
	 * @param node
	 */
	private void fixRedBlackTree(RedBlackNode<T> node) {
		RedBlackNode<T> currentNode = node;

		/*
		 * Inherently, this loop carries the following three invariants:
		 * 
		 *  1. `currentNode` is RED
		 *  2. If `parent` is the root, then `parent` is BLACK 
		 *  3. If the tree violates any of the red-black properties, then it violates at most one of them.
		 *        i. `currentNode` is a root and is RED
		 *       ii. `currentNode` and `parent` are both RED
		 */
		while (currentNode.getParent().getColor() == Color.RED) {
			/*
			 * The parent of the currentNode
			 * 
			 *    parent
			 *     /
			 *   currentNode
			 */
			RedBlackNode<T> parent = currentNode.getParent();

			/*
			 * The grandparent of the currentNode.  Or, the parent of the parent
			 * 
			 *     grandParent
			 *        /
			 *    parent
			 *      /
			 *   currentNode
			 *   
			 *  We know the grandparent exists since the root can only be BLACK.  If the
			 *  parent is the root, then it would be BLACK, and this loop would not have
			 *  been entered in the first place since that only happens when the parent
			 *  is RED
			 *  
			 *  We also know that the grandparent is BLACK -- due to the self-correcting nature
			 *  of the red-black tree, any violation can only happen between the current
			 *  node and its parent.
			 */
			RedBlackNode<T> grandParent = currentNode.getParent();
			if (parent == grandParent.getLeft()) {

				/*
				 * The uncle of the current node.  Or, the sibling of the parent
				 * 
				 *        grandParent
				 *         /      \
				 *      parent   uncle
				 *       /         
				 *    currentNode
				 */
				RedBlackNode<T> uncle = grandParent.getRight();
				/*
				 * Parent and uncle are RED.  This is the clean case, and only requires a recoloring
				 * of the grandparent and its children:
				 * 
				 *     B         R
				 *    / \  ->   / \
				 *   R   R     B   B
				 */
				if (uncle.getColor() == Color.RED) {
					parent.setColor(Color.BLACK);
					uncle.setColor(Color.BLACK);
					grandParent.setColor(Color.RED);

					/*
					 * Changing the color of `grandParent` might have introduced a new violation, so run
					 * the loop again with the grandParent as the new focal point.
					 */
					currentNode = grandParent;
				}
				/*
				 * If the uncle is BLACK, one or two rotations will be required depending on the current node's
				 * relative position in the tree
				 */
				else {
					/*
					 * If currentNode is its parent's right child, a left rotation is required
					 * 
					 * This rotation will focus on the parent -- as a side effect, currentNode will be reassigned 
					 * as the parent, causing the parent and grandparent references to need to be updated as well.
					 */
					if (currentNode == parent.getRight()) {
						currentNode = parent;
						parent = currentNode.getParent();
						grandParent = currentNode.getParent();
						leftRotate(currentNode);
					}
					/*
					 * Recolor the parent and grandparent (though not the uncle, as we already know it is BLACK.
					 * Then, rotate the grandparent to the right.
					 */
					parent.setColor(Color.BLACK);
					grandParent.setColor(Color.RED);
					rightRotate(grandParent);
				}
			}
			/*
			 * A repeat of the same code as above, with 'left' and 'right' flipped.
			 * Would prefer to reuse code here, though that would require an abuse of lambdas
			 * that would appear unreadable to me.  Refer to comments above to get the
			 * deeper details of the code.
			 */
			else {
				RedBlackNode<T> uncle = grandParent.getLeft();
				if (uncle.getColor() == Color.RED) {
					parent.setColor(Color.BLACK);
					uncle.setColor(Color.BLACK);
					grandParent.setColor(Color.RED);
					currentNode = grandParent;
				} else {
					if (currentNode == parent.getLeft()) {
						currentNode = parent;
						parent = currentNode.getParent();
						grandParent = currentNode.getParent();
						rightRotate(currentNode);
					}
					parent.setColor(Color.BLACK);
					grandParent.setColor(Color.RED);
					leftRotate(grandParent);
				}
			}
		}

		//the root must always be BLACK, and this is guaranteed to happen.
		this.root.setColor(Color.BLACK);
	}

	private RedBlackNode<T> createNewNode(T element) {
		RedBlackNode<T> node = new RedBlackNode<>(element, Color.RED);
		node.setLeft(nil);
		node.setRight(nil);
		return node;
	}

	/**
	 * Perform a left-rotation on the specified node.
	 * 
	 *   node          y
	 *   / \   -->    / \
	 *  a   y       node c
	 *     / \      / \
	 *    b   c    a   b
	 * 
	 * As seen in the picture --the assumption is that `y` is not nil, else a rotation
	 * cannot happen.
	 * 
	 * `node` will shift down to the left, and `y` will take `node's` old spot in the tree.
	 * 
	 * Regarding the other children:
	 * 
	 * 	1. The left child of `node`, `a`, will remain with `node`
	 *  2. The left child of `y`, `b`, will become the right child of `node`
	 *  3. `node` will become the left child of `y`
	 *  4. the right child of `y`, `c`, will remain with `y`
	 *  
	 *     
	 * @param node - the focal point of the rotation
	 */
	private void leftRotate(RedBlackNode<T> node) {
		RedBlackNode<T> y = node.getRight();

		//Move `y's` left child to `node's` right
		node.setRight(y.getLeft());
		if (y.getLeft() != nil) {
			y.getLeft().setParent(node);
		}

		//Replace the parent pointer of `y` to be that of `node`
		y.setParent(node.getParent());

		//If the parent of `node` is nil, then it's the root.  Make `y` the new root
		if (node.getParent() == nil) {
			this.root = y;
		}
		/*
		 * Otherwise a parent exists, so set `y` to the parent's left or right child reference
		 */
		else if (node == node.getParent().getLeft()) {
			node.getParent().setLeft(y);
		} else {
			node.getParent().setRight(y);
		}

		//Set `node` as `y's` left child and make `y` the parent of `node
		y.setLeft(node);
		node.setParent(y);
	}

	/**
	 * Perform a right-rotation on the specified node.
	 * 
	 *     node         y
	 *     / \   -->   / \
	 *    y   c       a  node
	 *   / \             / \
	 *  a   b           b   c
	 * 
	 * 
	 * `node` will shift up one level, and `y` will take the place of `node's` right child.
	 * 
	 * Regarding the subtrees:
	 * 
	 * 	1. The left child of `node`, `a`, will remain with `node`
	 *  2. The right child of `node`, `b`, will become `y's` left child
	 *  3. `y` will become the right child of `node`
	 *  4. the right child of `y`, `c`, will remain with `y`
	 *  
	 *     
	 * @param node - the focal point of the rotation
	 */
	private void rightRotate(RedBlackNode<T> node) {
		RedBlackNode<T> y = node.getLeft();

		//Move `y's` right child to `node's` left
		node.setLeft(y.getRight());
		if (y.getRight() != nil) {
			y.getRight().setParent(node);
		}

		//Replace the parent pointer of `y` to be that of `node`
		y.setParent(node.getParent());

		//If the parent of `node` is nil, then it's the root.  Make `y` the new root
		if (node.getParent() == nil) {
			this.root = y;
		}
		/*
		 * Otherwise a parent exists, so set `y` to the parent's left or right child reference
		 */
		else if (node == node.getParent().getLeft()) {
			node.getParent().setLeft(y);
		} else {
			node.getParent().setRight(y);
		}

		//Set `node` as `y's` left child and make `y` the parent of `node
		y.setRight(node);
		node.setParent(y);

		fixRedBlackTree(node);
	}

	private void inOrderTraversalWithList(List<T> elements, RedBlackNode<T> node) {
		if (node != nil) {
			inOrderTraversalWithList(elements, node.getLeft());
			elements.add(node.getElement());
			inOrderTraversalWithList(elements, node.getRight());
		}
	}

	private RedBlackNode<T> findNode(RedBlackNode<T> node, T element) {
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

}
