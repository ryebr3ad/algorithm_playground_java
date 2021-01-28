package org.ryebread.algorithmplayground.structures.unionfind;

import java.util.HashMap;
import java.util.Map;

public class UnionFind<T> {

	private Map<T, T> parentMap;

	public UnionFind() {
		this.parentMap = new HashMap<>();
	}

	/**
	 * Add a new element to the set.  The element
	 * will form its own subtree as a result.
	 * 
	 * @param element -- the element to be added to the set
	 */
	public void add(T element) {
		if (!parentMap.containsKey(element)) {
			parentMap.put(element, null);
		}
	}

	/**
	 * Join the subtrees of two elements together, if separate.
	 * @param x
	 * @param y
	 * @return true if a join was conducted; false if the elements were already in the same subset
	 */
	public boolean join(T x, T y) {
		//Get the root nodes of x and y's subtrees
		T parentX = find(x);
		T parentY = find(y);

		//If the root nodes are the same, they are already joined. 
		if (parentX.equals(parentY)) {
			return false;
		}

		this.parentMap.put(parentX, parentY);

		return true;
	}

	/**
	 * Find the root of the subtree that contains 'element'
	 * @param element
	 * @return root of element's subtree
	 */
	public T find(T element) {
		T parent = parentMap.get(element);
		if (parent == null) {
			return element;
		} 
		T root = find(parent);
		parentMap.put(element, root);
		return root;
	}

}
