package org.ryebread.algorithmplayground.structures.list;

import java.lang.reflect.Array;

public class LinkedList<T extends Comparable<T>> {

	/*
	 * Operates as a sentinel node, marking the beginning and end of the list.
	 * 
	 *  The "head" of the list will be pointed at by this.nil.next
	 *  The "tail" of the list will be pointed at by this.nil.prev.
	 * 
	 * Consequently:
	 *  The "head" node will point to this.nil as its previous sibling
	 *  The "tail" node will point to this.nil as its next sibling
	 */
	private Node<T> nil;
	private int size;

	public LinkedList() {
		this.nil = new Node<>(null);
		this.nil.setNext(this.nil);
		this.nil.setPrev(this.nil);
		size = 0;
	}

	/**
	 * Is the list empty?
	 * @return True if the list is empty; false if the list contains an item
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	public T find(T element) {
		return findNode(element) == this.nil ? null : element;
	}

	/**
	 * Adds an item to the list
	 * @param element
	 */
	public void add(T element) {
		Node<T> newNode = new Node<>(element);

		/*
		 * Set the old tail's next node to the new element
		 * 
		 * If the list is empty, the tail will be this.nil -- same as the head.
		 * Therefore, setting the next node here is equivalent to setting the head
		 * explicitly, as this.nil.next signifies the head of the list.
		 * 
		 */
		getTail().setNext(newNode);

		// Set the new node's previous reference to be the old tail
		newNode.setPrev(getTail());

		// Update the "tail" to be the new node.
		setTail(newNode);

		size++;
	}

	/**
	 * Returns the list in the order of insertion
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T[] all(T[] a) {
		T all[] = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
		Node<T> currNode = getHead();
		for (int i = 0; i < this.size; i++) {
			all[i] = currNode.getElement();
			currNode = currNode.getNext();
		}
		return all;
	}
	
	public boolean remove(T element) {
		Node<T> foundNode = findNode(element);
		if(foundNode == this.nil) {
			return false;
		}
		
		foundNode.getPrev().setNext(foundNode.getNext());
		foundNode.getNext().setPrev(foundNode.getPrev());
		
		foundNode.setNext(null);
		foundNode.setPrev(null);
		
		size--;
		
		return true;
	}

	private Node<T> getHead() {
		return this.nil.getNext();
	}

	/**
	 * The the 'tail' of the list.
	 * 
	 * If the list is empty, this will be `nil`
	 * @return
	 */
	private Node<T> getTail() {
		return this.nil.getPrev();
	}

	private void setTail(Node<T> node) {
		node.setNext(this.nil);
		this.nil.setPrev(node);
	}

	private Node<T> findNode(T element) {
		Node<T> currNode = getHead();
		while (currNode != this.nil) {
			if (currNode.getElement().equals(element)) {
				break;
			}
			currNode = currNode.getNext();
		}
		return currNode;
	}

}
