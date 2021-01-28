package org.ryebread.algorithmplayground.structures.lists;

public interface Pushable<T> {

	public T push(T element);
	
	public T pop();
	
	public boolean isEmpty();
	
}
