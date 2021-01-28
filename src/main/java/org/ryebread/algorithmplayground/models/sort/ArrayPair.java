package org.ryebread.algorithmplayground.models.sort;

public class ArrayPair {

	private Integer[] left;
	private Integer[] right;

	public ArrayPair() {}
	
	public ArrayPair(Integer[] left, Integer[] right) {
		this.left = left;
		this.right = right;
	}
	
	public Integer[] getLeft() {
		return left;
	}

	public void setLeft(Integer[] left) {
		this.left = left;
	}

	public Integer[] getRight() {
		return right;
	}

	public void setRight(Integer[] right) {
		this.right = right;
	}

}
