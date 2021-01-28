package org.ryebread.algorithmplayground.structures;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.structures.unionfind.UnionFind;

public class TestUnionFind {

	@Test
	public void testUnionFind() {
		UnionFind<String> union = new UnionFind<>();
		union.add("A");
		assertTrue(union.find("A").equals("A"));

		union.add("B");

		union.join("A", "B");

		assertTrue(union.find("A").equals(union.find("B")));
	}

}
