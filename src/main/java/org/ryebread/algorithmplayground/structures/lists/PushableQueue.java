package org.ryebread.algorithmplayground.structures.lists;

import java.util.LinkedList;
import java.util.Queue;

public class PushableQueue<T> implements Pushable<T> {

	Queue<T> queue = new LinkedList<>();
	
	@Override
	public T push(T element) {
		queue.add(element);
		return element;
	}

	@Override
	public T pop() {
		return queue.remove();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

}
