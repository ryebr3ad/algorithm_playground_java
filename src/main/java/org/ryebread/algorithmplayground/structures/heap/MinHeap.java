package org.ryebread.algorithmplayground.structures.heap;

import org.ryebread.algorithmplayground.utils.ArrayUtilities;

public class MinHeap extends Heap {

	public MinHeap(Integer[] tree) {
		super(tree);
	}

	@Override
	protected void heapify(int index) {
		int left = left(index);
		int right = right(index);
		int smallest = index;
		if (left < this.heapSize && this.tree[left] < this.tree[index]) {
			smallest = left;
		}
		if (right < this.heapSize && this.tree[right] < this.tree[smallest]) {
			smallest = right;
		}
		if (smallest != index) {
			ArrayUtilities.swap(this.tree, index, smallest);
			heapify(smallest);
		}
	}

}
