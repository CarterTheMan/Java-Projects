package AStarDone2;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Implementation of a stack using an expandable array as the
 * backing data structure.  Elements are added and removed at the
 * end of the array.
 */
public class ArrayBasedStack<E> implements PureStack<E>
{
	private static final int DEFAULT_SIZE = 10;

	/**
	 * Index of next available cell in array.
	 */
	private int top;

	/**
	 * The data store.
	 */
	private E[] data;

	/**
	 * Constructs an empty stack.
	 */
	public ArrayBasedStack()
	{
		// Unchecked warning is unavoidable
		data = (E[]) new Object[DEFAULT_SIZE];
	}

	/**
	 * Constructs an empty stack with a certain size
	 */
	public ArrayBasedStack(int size)
	{
		// Unchecked warning is unavoidable
		data = (E[]) new Object[size];
	}

	@Override
	public boolean isEmpty()
	{
		return top == 0;
	}

	@Override
	public E peek()
	{
		if (top == 0) throw new NoSuchElementException();
		return data[top - 1];
	}

	@Override
	public E pop()
	{
		if (top == 0) throw new NoSuchElementException();
		E ret = data[--top];
		data[top] = null;
		return ret;
	}

	@Override
	public void push(E item)
	{
		checkCapacity();
		data[top++] = item;
	}

	@Override
	public int size()
	{
		return top;
	}

	/**
	 * Ensures that the backing array has space to store at least 
	 * one additional element.
	 */
	private void checkCapacity()
	{
		if (top == data.length)
		{
			// create a copy of the data array with double the capacity
			data = Arrays.copyOf(data, data.length * 2);
		}
	}

	/**
	 * Checks to see if a piece of data is contained within the stack
	 * @param dataIn
	 * @return true or false
	 */
	public boolean contains(E dataIn) {
		for (int i = 0; i < data.length; i++) {
			if (dataIn.equals(data[i])) {
				return true;
			}
		}
		return false;
	}

}
