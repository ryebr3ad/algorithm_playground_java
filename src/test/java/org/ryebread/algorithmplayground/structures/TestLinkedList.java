package org.ryebread.algorithmplayground.structures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.structures.list.LinkedList;

public class TestLinkedList {

	@Test
	public void testAdd() {
		LinkedList<Integer> list = new LinkedList<>();
		assertNull(list.find(4));
		list.add(4);
		assertEquals(list.find(4), 4);
	}

	@Test
	public void testAll() {
		LinkedList<Integer> list = new LinkedList<>();
		list.add(5);
		list.add(3);
		list.add(4);
		list.add(1);
		list.add(2);
		Integer all[] = list.all(new Integer[0]);
		assertEquals(all[0], 5);
		assertEquals(all[1], 3);
		assertEquals(all[2], 4);
		assertEquals(all[3], 1);
		assertEquals(all[4], 2);
	}

	@Test
	public void testRemove() {
		LinkedList<Integer> list = new LinkedList<>();
		list.add(5);
		list.add(3);
		list.add(4);
		list.add(1);
		list.add(2);

		assertTrue(list.remove(4));
		assertFalse(list.remove(4));

		Integer all[] = list.all(new Integer[0]);
		assertEquals(all[0], 5);
		assertEquals(all[1], 3);
		assertEquals(all[2], 1);
		assertEquals(all[3], 2);
	}
	
	@Test
	public void testReverse() {
		LinkedList<Integer> list = new LinkedList<>();
		list.add(5);
		list.add(3);
		list.add(4);
		list.add(1);
		list.add(2);
		
		Integer all[] = list.reverse().all(new Integer[0]);
		assertEquals(all[0], 2);
		assertEquals(all[1], 1);
		assertEquals(all[2], 4);
		assertEquals(all[3], 3);
		assertEquals(all[4], 5);
	}

}
