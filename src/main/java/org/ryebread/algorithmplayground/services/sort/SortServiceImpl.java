package org.ryebread.algorithmplayground.services.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.ryebread.algorithmplayground.models.sort.ArrayPair;
import org.ryebread.algorithmplayground.models.sort.InsertionSortStats;
import org.ryebread.algorithmplayground.models.sort.MergeSortView;
import org.ryebread.algorithmplayground.structures.heap.Heap;
import org.ryebread.algorithmplayground.structures.heap.MinHeap;
import org.ryebread.algorithmplayground.utils.ArrayUtilities;
import org.springframework.stereotype.Service;

@Service
public class SortServiceImpl implements SortService {

	private static final int RADIX = 256;

	@Override
	public List<InsertionSortStats> insertionSort(Integer elements[]) {
		Integer clonedElements[] = elements.clone();
		List<InsertionSortStats> statsList = new ArrayList<>();

		InsertionSortStats stats = new InsertionSortStats();
		stats.setFocusIndex(0);
		stats.setSequence(clonedElements.clone());
		statsList.add(stats);

		for (int i = 1; i < clonedElements.length; i++) {
			int currentElement = clonedElements[i];
			int j = i - 1;
			for (; j >= 0 && clonedElements[j] > currentElement; j--) {
				ArrayUtilities.swap(clonedElements, j, j + 1);
			}
			stats = new InsertionSortStats();
			stats.setFocusIndex(j + 1);
			stats.setSequence(clonedElements.clone());
			statsList.add(stats);
		}
		return statsList;
	}

	@Override
	public MergeSortView mergeSort(Integer elements[]) {
		int lower = 0;
		int upper = elements.length - 1;
		int middle = (int) Math.floor((upper - lower) / 2);

		int size = pairListSize(elements.length);
		List<List<ArrayPair>> pairLists = new ArrayList<List<ArrayPair>>(size);
		for (int i = 0; i < size; i++) {
			pairLists.add(new ArrayList<>());
		}

		merge(pairLists, elements, lower, middle, upper, 1);
		pairLists.get(0).add(new ArrayPair(elements.clone(), null));
		Collections.reverse(pairLists);
		MergeSortView view = new MergeSortView();
		view.setPairLists(pairLists);
		return view;
	}

	@Override
	public Integer[][] heapSort(Integer[] elements) {
		Heap heap = new MinHeap(elements);
		Integer sorted[] = new Integer[elements.length];
		Integer[] iters[] = new Integer[elements.length][];
		for (int i = 0; i < elements.length; i++) {
			iters[i] = heap.getTree();
			sorted[i] = heap.pop();
		}
		return iters;
	}

	/**
	 * Gets the log base 2 value of the element length to determine the size of the
	 * pair list
	 * 
	 * Math.log() has no parameter to set the base, so use the property of logs:
	 * 
	 * log b (a) = log c (a) / log c (b)
	 * 
	 * b = 2 
	 * a = elements.length 
	 * c can be anything -- here, we're using Math.log(), which would make it `e`
	 */
	private int pairListSize(long length) {
		return (int) Math.ceil(Math.log(length) / Math.log(2)) + 1;
	}

	private void merge(List<List<ArrayPair>> pairLists, Integer sequence[], int lower, int middle, int upper,
			int level) {
		if (lower >= upper) {
			return;
		}

		int leftMiddle = (int) Math.floor((lower + middle) / 2);
		merge(pairLists, sequence, lower, leftMiddle, middle, level + 1);

		int rightMiddle = (int) Math.floor((middle + upper) / 2);
		merge(pairLists, sequence, middle + 1, rightMiddle, upper, level + 1);

		int leftLength = middle - lower + 1;
		int rightLength = upper - middle;

		Integer left[] = new Integer[leftLength + 1];
		Integer right[] = new Integer[rightLength + 1];

		for (int i = 0; i < leftLength; i++) {
			left[i] = sequence[lower + i];
		}

		for (int j = 0; j < rightLength; j++) {
			right[j] = sequence[middle + j + 1];
		}

		pairLists.get(level)
				.add(new ArrayPair(Arrays.copyOf(left, leftLength).clone(), Arrays.copyOf(right, rightLength).clone()));

		left[leftLength] = Integer.MAX_VALUE;
		right[rightLength] = Integer.MAX_VALUE;

		int leftIndex = 0;
		int rightIndex = 0;

		for (int k = lower; k <= upper; k++) {
			if (left[leftIndex] < right[rightIndex]) {
				sequence[k] = left[leftIndex++];
			} else {
				sequence[k] = right[rightIndex++];
			}
		}
	}

	/**
	 * Sort a list of equal length `strings` in less than n*lg_n time
	 * up to the first `len` characters in each string
	 */
	@Override
	public void lsdRadixSort(String[] strings, int len) {
		if (strings.length == 0) {
			return;
		}
		String aux[] = new String[strings.length]; //Used for intermediate storage during the key-indexed sort
		for (int i = len - 1; i >= 0; i--) {
			/*
			 * Each index represents a character in the alphabet,
			 * and is the instrument of how this sort will operate.
			 */
			int count[] = new int[RADIX + 1];

			/*
			 * Count the occurrences of characters at the ith position of each string.
			 * It's weird that it uses the numerical value of c and adds a 1, but the
			 * reason that's done will be made clear in the next loop
			 */
			for (int j = 0; j < strings.length; j++) {
				char c = strings[j].charAt(i);
				count[c + 1]++;
			}

			/*
			 * Adjust the count array to represent the number of characters that appear before
			 * the start of r's occurrence in the future sorted array
			 * 
			 * before:
			 * count[0] = 0
			 * count[1] = 3
			 * count[2] = 2
			 * count[3] = 4
			 * ...
			 * 
			 * after:
			 * count[0] = 0
			 * count[1] = 3 + count[0] = 3
			 * count[2] = 2 + count[1] = 5
			 * count[3] = 4 + count[2] = 9
			 * 
			 * The value stored in count[c] after the loop is finished, is the starting index for
			 * strings that contain character c in the sorted array.
			 */
			for (int r = 0; r < RADIX; r++) {
				count[r + 1] += count[r];
			}

			/* 
			 * This loop takes the work done in the count array and starts placing strings
			 * in the aux array indexed at the current value returned by count[c].  That value
			 * will be incremented to shift the index up by one
			 */
			for (int j = 0; j < strings.length; j++) {
				char c = strings[j].charAt(i);
				aux[count[c]++] = strings[j];
			}

			/*
			 * Copy the state of the auxiliary array to the strings array,
			 * allowing the sort to be done 'in place'
			 */
			for (int j = 0; j < strings.length; j++) {
				strings[j] = aux[j];
			}
		}
	}

	@Override
	public void msdRadixSort(String[] strings) {
		String aux[] = new String[strings.length];
		msd(strings, aux, 0, strings.length - 1, 0);

	}

	@Override
	public void threeWayRadixQuickSort(String[] strings) {
		threeWayQuickSort(strings, 0, strings.length - 1, 0);

	}

	private void threeWayQuickSort(String[] strings, int low, int high, int charPosition) {
		if (high <= low) {
			return;
		}

		int lessThan = low;
		int greaterThan = high;
		int anchorChar = charAt(strings[low], charPosition);
		int i = low + 1;
		/*
		 * This loop should be familiar to those in the know of quick sort.
		 * 
		 * Pick an anchor character (in this case, just the first string's character at charPosition)
		 * then put all lesser than characters before it in the array and all greater characters after
		 * it in the array.
		 */
		while (i <= greaterThan) {
			int comparisonChar = charAt(strings[i], charPosition);
			if (comparisonChar < anchorChar) {
				ArrayUtilities.swap(strings, lessThan++, i++);
			} else if (anchorChar < comparisonChar) {
				ArrayUtilities.swap(strings, i, greaterThan--);
			} else {
				i++;
			}
		}

		/*
		 * The 'three-way' aspect of this sort.
		 * 
		 * The lesser-than strings will run quicksort on its own chunk of the
		 * array at the same character position, and same for the greater than portion.
		 * 
		 * However, the anchor character is assumed to be sorted to their proper relative
		 * positions in the array.  For this chunk, focus on the next character in each string.
		 * 
		 * If this character is -1, that implies a "blank" character and can end the recursion
		 */
		threeWayQuickSort(strings, low, lessThan - 1, charPosition);
		if (anchorChar >= 0) {
			threeWayQuickSort(strings, lessThan, greaterThan, charPosition + 1);
		}
		threeWayQuickSort(strings, greaterThan + 1, high, charPosition);
	}

	/**
	 * A recursive algorithm to run radix sort on an array of strings at the denoted 
	 * character position
	 * @param strings - an array of strings to be sorted
	 * @param aux - an auxiliary array to act as an intermediary while running the sort
	 * @param low - the starting index of concern in the strings array
	 * @param high - the ending index of concern in the strings array
	 * @param charPosition - the index of the character to be sorted in each string
	 */
	private void msd(String[] strings, String[] aux, int low, int high, int charPosition) {
		if (high <= low) {
			return;
		}

		/*
		 * The array will need to account for the 'non' character, shown visually as dashes below
		 * when sorting 'dog' and 'dogged'
		 * ...
		 * dog---
		 * dogged
		 * ...
		 * 
		 */
		int count[] = new int[RADIX + 2];

		/*
		 * Get the character c at charPosition of strings[i] (which could be -1 if the position
		 * is greater than the length), then increment the count of that character's index.
		 * 
		 * Like in LSD, it's a weird indexing strategy -- even weirder due to the extra -1 character
		 * requiring additional shifting of the index by 2
		 */
		for (int i = low; i <= high; i++) {
			count[charAt(strings[i], charPosition) + 2]++;
		}

		/*
		 * The next steps are similar to LSD's key-based sorting,
		 * except it only works within the ranges of low and high on
		 * the strings and aux arrays
		 */
		for (int i = 0; i < RADIX + 1; i++) {
			count[i + 1] += count[i];
		}

		for (int i = low; i <= high; i++) {
			aux[count[charAt(strings[i], charPosition) + 1]++] = strings[i];
		}

		for (int i = low; i <= high; i++) {
			strings[i] = aux[i - low];
		}

		/*
		 * Recursively sort the strings array, by grouping each set of strings
		 * with character c at position charPosition in the string, and focusing
		 * on the next character in each the string
		 */
		for (int c = 0; c < RADIX; c++) {
			msd(strings, aux, low + count[c], low + count[c + 1] - 1, charPosition + 1);
		}
	}

	/**
	 * A convenience method to return a '-1' if the character position is out of the strings range
	 * @param str
	 * @param pos
	 * @return str.charAt(pos), or -1
	 */
	private int charAt(String str, int pos) {
		if (pos < str.length()) {
			return str.charAt(pos);
		}
		return -1;
	}

}
