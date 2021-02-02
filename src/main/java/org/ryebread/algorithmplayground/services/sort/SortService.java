package org.ryebread.algorithmplayground.services.sort;

import java.util.List;

import org.ryebread.algorithmplayground.models.sort.InsertionSortStats;
import org.ryebread.algorithmplayground.models.sort.MergeSortView;

public interface SortService {

	public List<InsertionSortStats> insertionSort(Integer elements[]);

	public MergeSortView mergeSort(Integer elements[]);

	public Integer[][] heapSort(Integer elements[]);

	public void lsdRadixSort(String[] strings, int len);

	public void msdRadixSort(String[] strings);
	
	public void threeWayRadixQuickSort(String[] strings);

}
