package org.ryebread.algorithmplayground.structures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.structures.heap.Heap;
import org.ryebread.algorithmplayground.structures.heap.MinHeap;

class TestHeap {

	@Test
	void testHeapCreation() {
		Integer tree[] = new Integer[] {5, 4, 3, 2, 6, 7, 8};
		Heap heap = new MinHeap(tree);
		int smallest = heap.pop();
		assertEquals(smallest, 2);
	}

}
