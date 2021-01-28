package org.ryebread.algorithmplayground.utils;

public class ArrayUtilities {

	public static final <T> void swap(T[] ary, int i, int j) {
		T elem = ary[i];
		ary[i] = ary[j];
		ary[j] = elem;
	}
	
}
