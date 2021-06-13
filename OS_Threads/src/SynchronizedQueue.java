
/**
 * A synchronized bounded-size queue for multithreaded producer-consumer applications.
 * 
 * @param <T> Type of data items
 */
/**
 *  Name: Yarden Toledano
 *  ID : 205576663
 */
public class SynchronizedQueue<T> {

	private T[] buffer;
	private int producers;
	private int capacity;
	private int index;
	private int tail;
	private int size;
	
	/**
	 * Constructor. Allocates a buffer (an array) with the given capacity and
	 * resets pointers and counters.
	 * @param capacity Buffer capacity
	 */
	@SuppressWarnings("unchecked")
	public SynchronizedQueue(int capacity) {
		this.buffer = (T[])(new Object[capacity]);
		this.producers = 0;
		this.capacity = capacity;
		this.index = 0;
		this.tail = 0;
		this.size = 0;
	}
	
	/**
	 * Dequeues the first item from the queue and returns it.
	 * If the queue is empty but producers are still registered to this queue, 
	 * this method blocks until some item is available.
	 * If the queue is empty and no more items are planned to be added to this 
	 * queue (because no producers are registered), this method returns null.
	 * 
	 * @return The first item, or null if there are no more items
	 * @see #registerProducer()
	 * @see #unregisterProducer()
	 */
	public T dequeue() {
		synchronized(this){

			if(this.size <= 0){
				if(this.producers == 0){
					return null;
				}

				while (this.producers > 0 && this.size == 0){
					try
						{
							wait();
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
				}
			}
			T element = this.buffer[this.index];
			this.index = (this.index + 1) % this.capacity;
			this.size--;
			notifyAll();
			return element;
		}
		
	}

	/**
	 * Enqueues an item to the end of this queue. If the queue is full, this 
	 * method blocks until some space becomes available.
	 * 
	 * @param item Item to enqueue
	 */
	public void enqueue(T item) {

		synchronized(this){
			if(this.size == this.capacity){
				try
						{
							wait();
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
			}
            int last = (this.index + this.size) % this.capacity;
			this.buffer[last] = item;
			this.size++;
			notifyAll();
		}
	}

	/**
	 * Returns the capacity of this queue
	 * @return queue capacity
	 */
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * Returns the current size of the queue (number of elements in it)
	 * @return queue size
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Registers a producer to this queue. This method actually increases the
	 * internal producers counter of this queue by 1. This counter is used to
	 * determine whether the queue is still active and to avoid blocking of
	 * consumer threads that try to dequeue elements from an empty queue, when
	 * no producer is expected to add any more items.
	 * Every producer of this queue must call this method before starting to 
	 * enqueue items, and must also call <see>{@link #unregisterProducer()}</see> when
	 * finishes to enqueue all items.
	 * 
	 * @see #dequeue()
	 * @see #unregisterProducer()
	 */
	public void registerProducer() {

		synchronized(this){
			this.producers++;
		}
	}

	/**
	 * Unregisters a producer from this queue. See <see>{@link #registerProducer()}</see>.
	 * 
	 * @see #dequeue()
	 * @see #registerProducer()
	 */
	public void unregisterProducer() {

		synchronized(this){
			this.producers--;
			if(this.producers == 0) notifyAll();

		}
		
	}
    public String toString() {
        int ptr = this.index;
        String s = "";
        for (int i = ptr; i < ptr + this.size; i++) {
            s = s.concat(this.buffer[i % this.buffer.length].toString());
        }
        return s;
    }
}
