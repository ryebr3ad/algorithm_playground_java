package org.ryebread.algorithmplayground.structures;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.structures.point.Orientation;
import org.ryebread.algorithmplayground.structures.point.Point;

public class TestPoint {

	@Test
	public void testClockwisePoints() {
		Point origin = new Point();
		Orientation o = origin.orientation(new Point(4L, 5L), new Point(1L, 3L));
		assertEquals(o, Orientation.CLOCKWISE);
	}

	@Test
	public void testCounterClockwisePoints() {
		Point origin = new Point();
		Orientation o = origin.orientation(new Point(1L, 3L), new Point(4L, 5L));
		assertEquals(o, Orientation.COUNTER_CLOCKWISE);
	}

}
