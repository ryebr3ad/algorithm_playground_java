package org.ryebread.algorithmplayground.structures.point;

public enum Orientation {
	CLOCKWISE(-1), STRAIGHT_LINE(0), COUNTER_CLOCKWISE(1);

	private int relativeCompare;

	private Orientation(int relativeCompare) {
		this.relativeCompare = relativeCompare;
	}

	public static Orientation get(int relativeCompare) {
		for (Orientation o : Orientation.values()) {
			if (o.relativeCompare == relativeCompare) {
				return o;
			}
		}
		return null;
	}
}
