package org.ryebread.algorithmplayground.utils;

/**
 * Common Array methods that I don't think exist in any standard package.
 * 
 * @author Ryan
 */
public class ArrayUtilities {

	/**
	 * Swap elements of any type of array
	 * @param <T>
	 * @param ary
	 * @param i
	 * @param j
	 */
	public static final <T> void swap(T[] ary, int i, int j) {
		T elem = ary[i];
		ary[i] = ary[j];
		ary[j] = elem;
	}

}
