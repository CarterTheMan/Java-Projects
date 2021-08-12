package AStarDone2;

public interface PureQueue<E> {

	//FIFO
	
	//Adds an item onto the queue
	void enqueue(E item);
	
	//Deletes an item from the queue
	//returns deleted item
	E dequeue();
	
	//Allows user to peek at the top
	//Does not return the top
	E peek();
	
	//Returns true if empty
	boolean isEmpty();
	
	//Returns the number of elements in the queue
	int size();
}
