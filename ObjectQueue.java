/**
 * This is a Queue class that uses methods to insert, remove, and query an object. It will also check your queue
 * to see if its full or empty.
 * 
 * @author Richard Stegman 
 * @version 3.1 - March 25, 2014
 */
public class ObjectQueue implements ObjectQueueInterface  {
    private Object[] item;
    private int front;
    private int rear;
    private int count;

    /**
     * This a ObjectQueue default constructor initilizing the values of the object[] to 1, front to 0, rear -1 and count to zero 
     */
    public ObjectQueue() {
        item = new Object[1];
        front = 0;
        rear  = -1;
        count = 0;
    }

     /**
     * This boolean method checks to see if the queue is empty
     * 
     * @return true or false depending on whether the queue is empty
     */
    public boolean isEmpty() {
        return count == 0;
    }
    
    
     /**
     * This boolean method checks to see if queue is full
     * 
     * return true or false depending on whether the queue is full
     */
    public boolean isFull() {
        return count == item.length;
    }
    
    
     /**
     * This method clears the queue
     */
    public void clear() {
        item = new Object[1];
        front = 0;
        rear  = -1;
        count = 0;
    }
     
    
     /**
     * This method inserts an object into the queue
     * 
     * @param o being inserted into the rear of the queue
     */
    public void insert(Object o) {
        if (isFull())
            resize(2 * item.length);
        rear = (rear+1) % item.length;
        item[rear] = o;
        ++count;
    }
    
    
     /**
     * This method removes an object from the queue
     * 
     * @return the object in the front of the queue and removes it from the front
     */
    public Object remove() {
        if (isEmpty()) {
            System.out.println("Queue Underflow");
            System.exit(1);
        }
        Object temp = item[front];
        item[front] = null;
        front = (front+1) % item.length;
        --count;
        if (item.length != 1 && count == item.length/4)
            resize(item.length/2);
        return temp;
    }
    
    
     /**
     * This method checks the front of the queue without any destruction to the queue
     * 
     * @returns the item infront of the queue without removing it
     */
    public Object query() {
        if (isEmpty()) {
            System.out.println("Queue Underflow");
            System.exit(1);
        }
        return item[front];
    }
    
    /**
     * This method accepts the original size of the queue if its full and resizes it
     * 
     * @param size of the original queue
     */
    private void resize(int size) {
        Object[] temp = new Object[size];
        for (int i = 0; i < count; ++i) {
            temp[i] = item[front];
            front = (front+1) % item.length;
        }
        front = 0;
        rear = count-1;
        item = temp;
    }
}
