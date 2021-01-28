package org.ryebread.algorithmplayground.structures.lists;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class PushableStack<T> implements Pushable<T> {

	Stack<T> stack = new Stack<>();

	@Override
	public T push(T element) {
		return stack.push(element);
	}

	@Override
	public T pop() {
		return stack.pop();
	}

	@Override
	public boolean isEmpty() {
		return stack.empty();
	}

}
