package org.ryebread.algorithmplayground.services.string;

import java.util.Arrays;

import org.springframework.stereotype.Service;

@Service
public class StringServiceImpl implements StringService {

	private static final int RADIX = 256;

	@Override
	public String[] suffixes(String str) {
		String suffixes[] = new String[str.length()];
		for (int i = 0; i < str.length(); i++) {
			suffixes[i] = str.substring(i, str.length());
		}
		return suffixes;
	}

	@Override
	public int longestCommonPrefix(String a, String b) {
		int smallestLength = Math.min(a.length(), b.length());
		for (int i = 0; i < smallestLength; i++) {
			if (a.charAt(i) != b.charAt(i)) {
				return i;
			}
		}
		return smallestLength;
	}

	@Override
	public String longestRepeatedSubstring(String str) {
		int n = str.length();
		String suffixes[] = suffixes(str);
		Arrays.sort(suffixes);

		String lrs = "";

		for (int i = 0; i < n - 1; i++) {
			int len = longestCommonPrefix(suffixes[i], suffixes[i + 1]);
			if (len > lrs.length()) {
				lrs = suffixes[i].substring(0, len);
			}
		}

		return lrs;
	}

	@Override
	public int[][] createDfa(String str) {
		int[] dfa[] = new int[RADIX][str.length()];

		dfa[str.charAt(0)][0] = 1;

		for (int x = 0, j = 1; j < str.length(); j++) {
			//Copy mismatch states from the restart state to the current state
			for (int c = 0; c < RADIX; c++) {
				dfa[c][j] = dfa[c][x];
			}
			//Overwrite the match case to move to the next state
			dfa[str.charAt(j)][j] = j + 1;

			//Update the restart state using the proposed state of the current character
			x = dfa[str.charAt(j)][x];
		}

		return dfa;
	}

	@Override
	public int knuthMorrisPratt(String str, String searchStr) {

		/*
		 * This would normally be done independently and injected in, changing
		 * as the search string changes as its a bit expensive to create.
		 */
		int[] dfa[] = createDfa(searchStr);

		int strLength = str.length();
		int searchStrLength = searchStr.length();

		int currentIndex = 0;
		int currentState = 0;
		for (; currentIndex < strLength && currentState < searchStrLength; currentIndex++) {
			currentState = dfa[str.charAt(currentIndex)][currentState];
		}
		return currentState == searchStrLength ? currentIndex - searchStrLength : strLength;
	}
}
