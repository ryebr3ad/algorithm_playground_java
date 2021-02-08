package org.ryebread.algorithmplayground.structures;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.structures.tree.BinaryNode;
import org.ryebread.algorithmplayground.structures.tree.BinarySearchTree;

public class TestBinaryTree {

	@Test
	public void testSingleElementAddAndSearch() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		tree.add(5);
		assertTrue(tree.find(5).equals(5));
	}

	@Test
	public void testMultipleElementAddAndSearch() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		tree.add(5);
		tree.add(10);
		assertTrue(tree.find(5).equals(5));
		assertTrue(tree.find(10).equals(10));
	}

	@Test
	public void testInOrderTraversal() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		tree.add(5);
		tree.add(10);
		tree.add(4);
		tree.add(1);
		tree.add(6);
		List<Integer> elements = tree.all();
		for (int i = 0; i < elements.size() - 1; i++) {
			assertTrue(elements.get(i).intValue() <= elements.get(i + 1).intValue());
		}
	}

	@Test
	public void testMin() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		tree.add(5);
		tree.add(10);
		tree.add(4);
		tree.add(1);
		tree.add(6);
		assertTrue(tree.min().equals(1));
	}

	@Test
	public void testMax() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		tree.add(5);
		tree.add(10);
		tree.add(4);
		tree.add(1);
		tree.add(6);
		assertTrue(tree.max().equals(10));
	}

	@Test
	public void testPredecessor() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		tree.add(5);
		tree.add(10);
		tree.add(4);
		tree.add(1);
		tree.add(6);
		List<Integer> elements = tree.all();
		for (int i = 1; i < elements.size(); i++) {
			BinaryNode<Integer> predecessorNode = tree.predecessor(elements.get(i));
			assertTrue(predecessorNode.getElement().equals(elements.get(i - 1)));
		}
	}

	@Test
	public void testSuccessor() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		tree.add(5);
		tree.add(10);
		tree.add(4);
		tree.add(1);
		tree.add(6);
		List<Integer> elements = tree.all();
		for (int i = 0; i < elements.size() - 1; i++) {
			BinaryNode<Integer> successorNode = tree.successor(elements.get(i));
			assertTrue(successorNode.getElement().equals(elements.get(i + 1)));
		}
	}

	@Test
	public void testDeleteWithNoChildren() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
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
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
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
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
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

}
