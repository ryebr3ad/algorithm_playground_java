package org.ryebread.algorithmplayground.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.services.sort.SortService;
import org.ryebread.algorithmplayground.services.sort.SortServiceImpl;

public class TestSortService {

	private SortService sortService = new SortServiceImpl();

	@Test
	public void testLsdRadixSort() {
		List<String> stringList = new ArrayList<>();

		stringList.add("FF");
		stringList.add("EF");
		stringList.add("DF");
		stringList.add("CF");
		stringList.add("BF");
		stringList.add("AF");

		stringList.add("FE");
		stringList.add("EE");
		stringList.add("DE");
		stringList.add("CE");
		stringList.add("BE");
		stringList.add("AE");

		stringList.add("FD");
		stringList.add("ED");
		stringList.add("DD");
		stringList.add("CD");
		stringList.add("BD");
		stringList.add("AD");

		stringList.add("FC");
		stringList.add("EC");
		stringList.add("DC");
		stringList.add("CC");
		stringList.add("BC");
		stringList.add("AC");

		stringList.add("FB");
		stringList.add("EB");
		stringList.add("DB");
		stringList.add("CB");
		stringList.add("BB");
		stringList.add("AB");

		stringList.add("FA");
		stringList.add("EA");
		stringList.add("DA");
		stringList.add("CA");
		stringList.add("BA");
		stringList.add("AA");

		String strings[] = new String[stringList.size()];
		stringList.toArray(strings);

		sortService.lsdRadixSort(strings, 2);

		assertTrue(strings[0].equals("AA"));
		assertTrue(strings[strings.length - 1].equals("FF"));
	}

	@Test
	public void testMsdRadixSort() {
		List<String> stringList = new ArrayList<>();

		stringList.add("This");
		stringList.add("sorting");
		stringList.add("strategy");
		stringList.add("doesn't");
		stringList.add("care");
		stringList.add("about");
		stringList.add("dumb");
		stringList.add("things");
		stringList.add("like");
		stringList.add("string");
		stringList.add("length");
		stringList.add("to");
		stringList.add("work");
		stringList.add("its");
		stringList.add("sort");

		String strings[] = new String[stringList.size()];
		stringList.toArray(strings);

		sortService.msdRadixSort(strings);

		assertTrue(strings[0].equals("This"));
		assertTrue(strings[strings.length - 1].equals("work"));

	}

	@Test
	public void testThreeWayRadixQuickSort() {
		List<String> stringList = new ArrayList<>();

		for (char c = 'a'; c <= 'z'; c++) {
			for (char d = 'a'; d <= 'z'; d++) {
				stringList.add("" + d + c);
			}
		}

		String strings[] = new String[stringList.size()];
		stringList.toArray(strings);

		sortService.threeWayRadixQuickSort(strings);

		assertTrue(strings[0].equals("aa"));
		assertTrue(strings[strings.length - 1].equals("zz"));

	}

}
