package org.ryebread.algorithmplayground.controllers.sort;

import java.util.List;

import org.ryebread.algorithmplayground.models.sort.InsertionSortStats;
import org.ryebread.algorithmplayground.models.sort.MergeSortView;
import org.ryebread.algorithmplayground.services.sort.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/sort")
public class SortController {

	@Autowired
	private SortService sortService;

	@PostMapping("/insertion")
	public @ResponseBody List<InsertionSortStats> insertionSort(@RequestBody Integer elements[]) {
		return sortService.insertionSort(elements);
	}

	@PostMapping("/merge")
	public @ResponseBody MergeSortView mergeSort(@RequestBody Integer elements[]) {
		return sortService.mergeSort(elements);
	}

	@PostMapping("/heap")
	public @ResponseBody Integer[][] heapSort(@RequestBody Integer elements[]) {
		return sortService.heapSort(elements);
	}

}
