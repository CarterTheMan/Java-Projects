package AStarDone2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class ArrayBasedQueue<E> {

	int front, rear, size; 
	int capacity; 
	E[] array; 

	public ArrayBasedQueue(int capacity) { 
		this.capacity = capacity; 
		front = this.size = 0;  
		rear = capacity - 1; 
		array = (E[]) new Object[capacity]; 

	}

	/**
	 * Queue is full when size becomes equal to  
	 * @return size == capacity
	 */
	boolean isFull() {
		return (size == capacity);
	} 

	/**
	 * Queue is empty when size is 0 
	 * @return size == 0
	 */
	boolean isEmpty() {
		return (size == 0); 
	} 

	/**
	 * Method to add an item to the queue. Changes the rear and size 
	 * @param item
	 */
	void enqueue(E item) 
	{ 
		if (isFull()) 
			return; 
		rear = (rear + 1)%capacity; 
		array[this.rear] = item; 
		size = size + 1; 
	} 

	/**
	 * Method to remove an item from queue. Changes front and size 
	 * @return item
	 */
	E dequeue() 
	{ 
		if (isEmpty()) 
			return null; 

		E item = array[front]; 
		front = (front + 1)%capacity; 
		size = size - 1; 
		return item; 
	} 

	/**
	 * Method to get front of queue 
	 * @return null or array[front]
	 */
	E front() 
	{ 
		if (isEmpty()) 
			return null; 

		return array[front]; 
	} 

	/**
	 * Method to get rear of queue 
	 * @return null or array[rear]
	 */
	E rear() 
	{ 
		if (isEmpty()) 
			return null; 

		return array[rear]; 
	} 

	/**
	 * Checks the array to see if it contains an item
	 * @param data
	 * @return true or false
	 */
	boolean contains(E data) {
		for (int i = 0; i < capacity; i++) {
			if (data.equals(array[i])) {
				return true;
			}
		}
		return false;
	}


}
