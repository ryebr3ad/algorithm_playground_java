package org.ryebread.algorithmplayground.structures.tree;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<T extends Comparable<T>> {

	//Sentinel node, used in place of null to make resolution of rule violations easier to manage
	private final RedBlackNode<T> nil = new RedBlackNode<>(Color.BLACK);
	private RedBlackNode<T> root;

	public RedBlackTree() {
		this.root = this.nil;
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

	public T min() {
		return min(this.root).getElement();
	}

	private RedBlackNode<T> min(RedBlackNode<T> node) {
		return drillInDirection(node, Direction.LEFT);
	}

	public T max() {
		return max(this.root).getElement();
	}

	private RedBlackNode<T> max(RedBlackNode<T> node) {
		return drillInDirection(node, Direction.RIGHT);
	}

	public void add(T element) {
		RedBlackNode<T> newNode = createNewNode(element);
		RedBlackNode<T> newParent = this.nil;
		RedBlackNode<T> currentNode = this.root;
		/*
		 * Starting at the root, traverse the tree, going left if element is less
		 * than the current node's value; otherwise, go right.
		 */
		while (currentNode != this.nil) {
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
		if (newParent == this.nil) {
			this.root = newNode;
		}
		//Otherwise, determine whether the new node is the left or right child of the parent
		else if (element.compareTo(newParent.getElement()) < 0) {
			newParent.setLeft(newNode);
		} else {
			newParent.setRight(newNode);
		}

		fixRedBlackTreeAfterAdd(newNode);
	}

	private RedBlackNode<T> createNewNode(T element) {
		RedBlackNode<T> node = new RedBlackNode<>(element, Color.RED);
		node.setLeft(this.nil);
		node.setRight(this.nil);
		return node;
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
	private void fixRedBlackTreeAfterAdd(RedBlackNode<T> node) {
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
			RedBlackNode<T> grandParent = parent.getParent();
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

	public boolean delete(T element) {
		RedBlackNode<T> nodeToBeRemoved = findNode(root, element);
		if (nodeToBeRemoved == null) {
			return false;
		}

		/*
		 * `movedNode` will either reference the node being removed from the tree entirely, 
		 * or the successor node that would be moved within the tree to replace the removed node
		 */
		RedBlackNode<T> movedNode = nodeToBeRemoved;
		Color movedColor = movedNode.getColor();
		RedBlackNode<T> replacementNode = null;

		/*
		 * Like a regular binary search tree, if `nodeToBeRemoved` has one or zero descendants,
		 * replace it with its child that could potentially exist.  With no descendants,
		 * the node will be replaced with `nil` which is acceptable
		 */
		if (nodeToBeRemoved.getLeft() == this.nil) {
			replacementNode = nodeToBeRemoved.getRight();
			transplant(nodeToBeRemoved, nodeToBeRemoved.getRight());
		} else if (nodeToBeRemoved.getRight() == this.nil) {
			replacementNode = nodeToBeRemoved.getLeft();
			transplant(nodeToBeRemoved, nodeToBeRemoved.getLeft());
		}
		/*
		 * The fun case.
		 */
		else {
			/*
			 * `movedNode` will now reference the successor of the node being deleted, which
			 * requires an update of the `movedColor`.
			 */
			movedNode = max(nodeToBeRemoved.getLeft());
			movedColor = movedNode.getColor();

			/*
			 * `replacementNode` will now reference the left child of `movedNode`, which again could be nil.
			 */
			replacementNode = movedNode.getLeft();

			/*
			 * If the successor node of `nodeToBeRemoved` is its right child,
			 * ensure that the right child of `movedNode` refers to `movedNode` as its parent.
			 * 
			 * This step is profoundly confusing as one would think: the parent of `replacementNode` was set
			 * to be the right child of `movedNode`, so `movedNode` should already be the parent, right?
			 * 
			 * However, if the right child of `movedNode` is `nil`, then the parent will not be set.
			 * Since `nil` is a sentinel node, it could be pointing to anything as it's parent.  This block, then, assures
			 * that the replacement node always points to the moved node.
			 * 
			 * This means the parent of `nil` could constantly be changing, but since its parent only matters from now
			 * until the end of this method's execution, that's no problem
			 */
			if (movedNode.getParent() == nodeToBeRemoved) {
				replacementNode.setParent(movedNode);
			}
			/*
			 * Otherwise, `movedNode` is some number of levels down the tree.
			 * 
			 * Swap `movedNode` with its right child (which, again, could be `nil`, but that's fine,
			 * then update the right child pointer to `nodeToBeRemoved's`
			 */
			else {
				transplant(movedNode, movedNode.getLeft());
				movedNode.setLeft(nodeToBeRemoved.getLeft());
				movedNode.getLeft().setParent(movedNode);
			}
			//Swap `nodeToBeRemoved` with `movedNode`
			transplant(nodeToBeRemoved, movedNode);

			//Update the left child pointer to be that of `nodeToBeRemoved's`
			movedNode.setRight(nodeToBeRemoved.getRight());
			movedNode.getRight().setParent(movedNode);

			/*
			 * `movedNode` has now taken `nodeToBeRemoved's place in the tree.  To keep everything
			 *  kosher, update the color of `movedNode` to be that of `nodeToBeRemoved`
			 */
			movedNode.setColor(nodeToBeRemoved.getColor());
		}
		/*
		 * If `movedNode` was originally black, `replacementNode` might now be causing a rule violation.
		 * Check for that now.
		 */
		if (movedColor == Color.BLACK) {
			fixRedBlackTreeAfterDelete(replacementNode);
		}
		return true;
	}

	/**
	 * Replace oldNode with newNode
	 * @param oldNode - the node being replaced
	 * @param newNode - the replacement node
	 */
	private void transplant(RedBlackNode<T> oldNode, RedBlackNode<T> newNode) {
		//Check if the old node was the root.  The new node, as a consequence, will be the root
		if (oldNode.getParent() == this.nil) {
			this.root = newNode;
		}
		/*
		 * Otherwise, determine the relative descendant position of the old node against its parent and replace
		 * it with the new node
		 */
		else if (oldNode == oldNode.getParent().getLeft()) {
			oldNode.getParent().setLeft(newNode);
		} else {
			oldNode.getParent().setRight(newNode);
		}

		//No matter what, the old node's parent will become the new node's parent.  If it's the root, it will be nil.
		newNode.setParent(oldNode.getParent());
	}

	private void fixRedBlackTreeAfterDelete(RedBlackNode<T> node) {
		RedBlackNode<T> currentNode = node;

		/*
		 * Loop only if `currentNode` is not the root, and if it is BLACK
		 * 
		 * Technically, it'd be "doubly" BLACK -- meaning that, since a black node was removed,
		 * the current node has absorbed it's BLACKness.  The goal of the loop is to rearrange
		 * the tree so that "double" BLACKness is not required.
		 */
		while (currentNode != this.root && currentNode.getColor() == Color.BLACK) {
			RedBlackNode<T> parentNode = currentNode.getParent();

			/*
			 * The first set of cases will only deal with the scenario where the current
			 * node is the left child of its parent.
			 * 
			 * Since `currentNode` cannot be the root, there is sure to be a parent that
			 * is not `nil`.
			 */
			if (currentNode == parentNode.getLeft()) {
				/*
				 * Store a reference to the sibling of `currentNode`.
				 * 
				 * We know that the sibling cannot equal `nil` due to the BLACK height rule.  Since
				 * `currentNode` is, in effect, "doubly" BLACK, if its sibling is `nil` then the sibling would only
				 * increment the BLACK height by 1.  `currentNode`, in effect, increments it by 2; already eclipsing
				 * the height that the potential `nil` sibling would add.
				 */
				RedBlackNode<T> siblingNode = parentNode.getRight();

				/*
				 * This block can be considered a precursor that needs to be run if the sibling is RED.  In this case
				 * the state of the tree will be mutated into a case that will be resolved further down the loop.
				 * 
				 * If `siblingNode` is RED, then its children are BLACK.  This case switches the colors of `siblingNode`
				 * and `currentNode`, painting `siblingNode` BLACK and `currentNode` RED.  A left rotation of `parentNode`
				 * follows, then a reassignment of `siblingNode` to the new right child of `parentNode, which will have
				 * changed after the rotation.
				 */
				if (siblingNode.getColor() == Color.RED) {
					siblingNode.setColor(Color.BLACK);
					parentNode.setColor(Color.RED);
					leftRotate(parentNode);
					siblingNode = parentNode.getRight();
				}
				/*
				 * Both of `siblingNode's` children are BLACK.  We know, also, that `siblingNode` is BLACK.  Set
				 * `siblingNode` to be RED, then repeat the loop with `parentNode` being assigned to `currentNode`.
				 * 
				 * If the first case was encountered during execution of this loop, then the loop will not run again
				 * as `parentNode` was set to RED.
				 */
				if (siblingNode.getLeft().getColor() == Color.BLACK
						&& siblingNode.getRight().getColor() == Color.BLACK) {
					siblingNode.setColor(Color.RED);
					currentNode = parentNode;
				}
				//One of `siblingNode's` children is BLACK 
				else {
					/*
					 * The case that's confirmed to resolve all red-black tree issues exists when `siblingNode` is BLACK
					 * and its same side child is BLACK.  If the opposite side child is BLACK, the a step must be done
					 * to mutate the tree in a way so that the same side child is BLACK, which is covered for here.
					 * 
					 * `siblingNode's` left child is set to BLACK, and `siblingNode` is set to RED.  A right rotation
					 * is performed on `siblingNode`, pusing the left child up a level.
					 */
					if (siblingNode.getRight().getColor() == Color.BLACK) {
						siblingNode.getLeft().setColor(Color.BLACK);
						siblingNode.setColor(Color.RED);
						rightRotate(siblingNode);
						siblingNode = parentNode.getRight();
					}
					/*
					 * This case will resolve all violations of red-black rules.  `siblingNode` takes on the same color
					 * as `parentNode`, as `siblingNode` will be taking its spot in the tree after rotation.  `parentNode`
					 * and `siblingNode's` right child, as the new children of `siblingNode` after rotation, will be colored
					 * BLACK.
					 * 
					 * A left rotation is performed on the parent, resolving the conflicts of the tree.
					 */
					siblingNode.setColor(parentNode.getColor());
					parentNode.setColor(Color.BLACK);
					siblingNode.getRight().setColor(Color.BLACK);
					leftRotate(parentNode);
					currentNode = this.root;

				}
			}
			/*
			 * Same code as above, with the orientations flipped.
			 */
			else {
				RedBlackNode<T> siblingNode = parentNode.getLeft();

				/*
				 * If `siblingNode` is RED, it is guaranteed to have BLACK children.
				 */
				if (siblingNode.getColor() == Color.RED) {
					siblingNode.setColor(Color.BLACK);
					parentNode.setColor(Color.RED);
					rightRotate(parentNode);
					siblingNode = parentNode.getLeft();
				}
				if (siblingNode.getRight().getColor() == Color.BLACK
						&& siblingNode.getLeft().getColor() == Color.BLACK) {
					siblingNode.setColor(Color.RED);
					currentNode = parentNode;
				} else {
					if (siblingNode.getLeft().getColor() == Color.BLACK) {
						siblingNode.getRight().setColor(Color.BLACK);
						siblingNode.setColor(Color.RED);
						leftRotate(siblingNode);
						siblingNode = parentNode.getLeft();
					}
					siblingNode.setColor(parentNode.getColor());
					parentNode.setColor(Color.BLACK);
					siblingNode.getLeft().setColor(Color.BLACK);
					rightRotate(parentNode);
					currentNode = this.root;
				}
			}
		}
		//Always set the current node's color to BLACK
		currentNode.setColor(Color.BLACK);
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
		if (y.getLeft() != this.nil) {
			y.getLeft().setParent(node);
		}

		//Replace the parent pointer of `y` to be that of `node`
		y.setParent(node.getParent());

		//If the parent of `node` is nil, then it's the root.  Make `y` the new root
		if (node.getParent() == this.nil) {
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
		if (y.getRight() != this.nil) {
			y.getRight().setParent(node);
		}

		//Replace the parent pointer of `y` to be that of `node`
		y.setParent(node.getParent());

		//If the parent of `node` is nil, then it's the root.  Make `y` the new root
		if (node.getParent() == this.nil) {
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

	}

	private void inOrderTraversalWithList(List<T> elements, RedBlackNode<T> node) {
		if (node != this.nil) {
			inOrderTraversalWithList(elements, node.getLeft());
			elements.add(node.getElement());
			inOrderTraversalWithList(elements, node.getRight());
		}
	}

	private RedBlackNode<T> findNode(RedBlackNode<T> node, T element) {
		if (node == this.nil) {
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

	/**
	 * Return the element of the node farthest from the passed in node
	 * from the indicated direction
	 * @param node
	 * @param direction
	 * @return
	 */
	private RedBlackNode<T> drillInDirection(RedBlackNode<T> node, Direction direction) {
		RedBlackNode<T> currNode = node;
		while (currNode != this.nil && currNode.getChild(direction) != this.nil) {
			currNode = currNode.getChild(direction);
		}
		if (currNode == this.nil) {
			return this.nil;
		} else {
			return currNode;
		}
	}

	public T getRoot() {
		return this.root.getElement();
	}

}
