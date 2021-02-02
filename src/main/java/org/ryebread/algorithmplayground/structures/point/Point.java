package org.ryebread.algorithmplayground.structures.point;

/**
 * Represents a Cartesian point
 * 
 * @author Ryan
 */
public class Point {

	private Long x;
	private Long y;

	/**
	 * Origin point
	 */
	public Point() {
		this(0L, 0L);
	}

	/**
	 * Point at (x, y)
	 * @param x
	 * @param y
	 */
	public Point(Long x, Long y) {
		this.x = x;
		this.y = y;
	}

	public Long getX() {
		return x;
	}

	/**
	 * Get x relative to originX
	 * @param originX
	 * @return Long, the relative x value
	 */
	public Long getX(Long originX) {
		return x - originX;
	}

	public Long getY() {
		return y;
	}

	public Long getY(Long originY) {
		return y - originY;
	}

	/**
	 * Relative to this, return whether 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public Orientation orientation(Point p1, Point p2) {
		Long left = p1.getY(this.y) * p2.getX(this.x);
		Long right = p2.getY(this.y) * p1.getX(this.x);
		return Orientation.get(left.compareTo(right));
	}

}
