package org.ryebread.algorithmplayground.structures;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.structures.tree.RedBlackTree;

public class TestRedBlackTree {

	@Test
	public void testSingleElementAddAndSearch() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.add(5);
		assertTrue(tree.find(5).equals(5));
	}

	@Test
	public void testMultipleElementAddAndSearch() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.add(5);
		tree.add(10);
		assertTrue(tree.find(5).equals(5));
		assertTrue(tree.find(10).equals(10));
	}

	@Test
	public void testInOrderTraversal() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		for (int i = 1000; i > 0; i--) {
			tree.add(i);
		}
		List<Integer> elements = tree.all();
		for (int i = 0; i < elements.size() - 1; i++) {
			assertTrue(elements.get(i).intValue() <= elements.get(i + 1).intValue());
		}
	}
}
