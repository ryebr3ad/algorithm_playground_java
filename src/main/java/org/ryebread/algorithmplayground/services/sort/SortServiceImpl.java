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
		for(int i = 0; i < elements.length; i++) {
			iters[i] = heap.getTree();
			sorted[i] = heap.pop();
		}
		return iters;
	}

	/**
	 * Gets the log base 2 value of the element length to determine the size of the
	 * list
	 * 
	 * Math.log() has no parameter to set the base, so use the property of logs:
	 * 
	 * log b (a) = log c (a) / log c (b)
	 * 
	 * b = 2, a = elements.length, c can be anything -- here, we're using Math.log(),
	 * which would make it `e`
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

}
