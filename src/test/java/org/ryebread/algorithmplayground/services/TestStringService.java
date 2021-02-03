package org.ryebread.algorithmplayground.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.services.string.StringService;
import org.ryebread.algorithmplayground.services.string.StringServiceImpl;

public class TestStringService {

	StringService stringService = new StringServiceImpl();

	@Test
	public void testSuffixes() {
		String str = "Hello";
		String suffixes[] = stringService.suffixes(str);
		assertEquals(suffixes.length, 5);
		assertTrue(suffixes[0].equals("Hello"));
		assertTrue(suffixes[1].equals("ello"));
		assertTrue(suffixes[2].equals("llo"));
		assertTrue(suffixes[3].equals("lo"));
		assertTrue(suffixes[4].equals("o"));
	}

	@Test
	public void testSortedSuffixes() {
		String str = "abacabb";
		String suffixes[] = stringService.suffixes(str);
		Arrays.sort(suffixes);
		assertEquals(suffixes.length, 7);
		assertTrue(suffixes[0].equals("abacabb"));
		assertTrue(suffixes[1].equals("abb"));
		assertTrue(suffixes[2].equals("acabb"));
		assertTrue(suffixes[3].equals("b"));
		assertTrue(suffixes[4].equals("bacabb"));
		assertTrue(suffixes[5].equals("bb"));
		assertTrue(suffixes[6].equals("cabb"));
	}

	@Test
	public void testLongestCommonPrefix() {
		String a = "abcdyyyyyyy";
		String b = "abyyy";
		int lcp = stringService.longestCommonPrefix(a, b);
		assertEquals(lcp, 2);

		String empty = "";
		String any = "any";
		lcp = stringService.longestCommonPrefix(empty, any);
		assertEquals(lcp, 0);

		String zs = "zzzzzz";
		String as = "aaaaaa";
		lcp = stringService.longestCommonPrefix(zs, as);
		assertEquals(lcp, 0);
	}

	@Test
	public void testLongestRepeatedSubstring() {
		String str = "He sells sea shells by the sea shore";
		String lrs = stringService.longestRepeatedSubstring(str);
		assertTrue(lrs.equals(" sea sh"));
	}

	@Test
	public void testCreateDfaMatchingCases() {
		String str = "ABABAC";
		int[] dfa[] = stringService.createDfa(str);
		assertEquals(dfa['A'][0], 1);
		assertEquals(dfa['B'][1], 2);
		assertEquals(dfa['A'][2], 3);
		assertEquals(dfa['B'][3], 4);
		assertEquals(dfa['A'][4], 5);
		assertEquals(dfa['C'][5], 6);
	}

	@Test
	public void testBoyerMoore() {
		String str = "This is a very long string that a pattern will check";
		String pattern = "pattern";
		int position = stringService.boyerMoore(str, pattern);
		assertEquals(position, 34);

	}

}
