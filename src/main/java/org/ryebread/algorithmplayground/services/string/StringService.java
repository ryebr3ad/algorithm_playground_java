package org.ryebread.algorithmplayground.services.string;

public interface StringService {

	public String[] suffixes(String str);

	public int longestCommonPrefix(String a, String b);

	public String longestRepeatedSubstring(String str);

	public int[][] createDfa(String str);

	public int knuthMorrisPratt(String str, String searchStr);
	
	public int boyerMoore(String str, String pattern);
}
