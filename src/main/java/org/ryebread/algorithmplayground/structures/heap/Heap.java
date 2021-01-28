package org.ryebread.algorithmplayground.structures.heap;

import java.util.Arrays;

import org.ryebread.algorithmplayground.exceptions.EmptyHeapException;

public abstract class Heap {

	protected int heapSize;
	protected Integer[] tree;

	public Heap(Integer[] tree) {
		if (tree == null) {
			this.tree = new Integer[] {};
		}
		this.tree = tree.clone();
		buildHeap();
	}

	protected abstract void heapify(int index);

	private void buildHeap() {
		this.heapSize = this.tree.length;
		for (int i = maxNonLeafNode(this.heapSize); i >= 0; i--) {
			heapify(i);
		}
	}
 
	protected int maxNonLeafNode(int heapSize) {

		return (int) Math.floor(heapSize / 2) - 1;
	}

	protected int left(int i) {
		return (i << 1) + 1;
	}

	protected int right(int i) {
		return (i << 1) + 2;
	}

	public int pop() {
		if (isEmpty()) {
			throw new EmptyHeapException();
		}
		int root = this.tree[0];
		this.tree[0] = this.tree[this.heapSize - 1];
		this.heapSize--;
		heapify(0);
		return root;
	}

	public boolean isEmpty() {
		return this.heapSize <= 0;
	}
	
	public Integer[] getTree() {
		return Arrays.copyOfRange(tree, 0, heapSize);
	}

}
