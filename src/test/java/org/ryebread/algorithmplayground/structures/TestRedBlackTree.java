package org.ryebread.algorithmplayground.structures;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.structures.tree.BinarySearchTree;
import org.ryebread.algorithmplayground.structures.tree.RedBlackTree;

public class TestRedBlackTree {

	private static final int RANDOM_RANGE = 1000;

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

	@Test
	public void testDeleteWithNoChildren() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.add(5);
		tree.add(4);
		tree.add(6);
		assertTrue(tree.delete(6));
		assertFalse(tree.delete(6));
		assertNull(tree.find(6));
		List<Integer> elements = tree.all();
		for (int i = 0; i < elements.size() - 1; i++) {
			assertTrue(elements.get(i).intValue() <= elements.get(i + 1).intValue());
		}
	}

	@Test
	public void testDeleteWithOneChild() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.add(5);
		tree.add(4);
		tree.add(6);
		tree.add(7);
		assertTrue(tree.delete(6));
		assertFalse(tree.delete(6));
		assertNull(tree.find(6));
		List<Integer> elements = tree.all();
		for (int i = 0; i < elements.size() - 1; i++) {
			assertTrue(elements.get(i).intValue() <= elements.get(i + 1).intValue());
		}
	}

	@Test
	public void testDeleteWithTwoChildren() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.add(5);
		tree.add(4);
		tree.add(6);
		tree.add(7);
		tree.add(1);
		assertTrue(tree.delete(5));
		assertFalse(tree.delete(5));
		assertNull(tree.find(5));
		List<Integer> elements = tree.all();
		for (int i = 0; i < elements.size() - 1; i++) {
			assertTrue(elements.get(i).intValue() <= elements.get(i + 1).intValue());
		}
	}

	@Test
	public void testRandomDeletes() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		for (int i = (RANDOM_RANGE / 2); i > 0; i--) {
			tree.add(i);
		}

		for (int i = ((RANDOM_RANGE / 2) + 1); i <= RANDOM_RANGE; i++) {
			tree.add(i);
		}

		Set<Integer> alreadyDeleted = new HashSet<Integer>();
		Random rand = new Random((new Date()).getTime());
		for (int i = 0; i < 200; i++) {
			int randInt = rand.nextInt(RANDOM_RANGE) + 1;
			while (alreadyDeleted.contains(randInt)) {
				randInt = rand.nextInt(RANDOM_RANGE) + 1;
			}
			alreadyDeleted.add(randInt);
			assertTrue(tree.delete(randInt));
			assertNull(tree.find(randInt));
		}

		List<Integer> elements = tree.all();
		for (int i = 0; i < elements.size() - 1; i++) {
			assertTrue(elements.get(i).intValue() <= elements.get(i + 1).intValue());
		}
	}

}
