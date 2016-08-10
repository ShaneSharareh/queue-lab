
/**
 * This is an interface for the ObjectQueue class
 * 
 * @Shane Sharareh
 * @version 3.1 - March 25, 2014
 */
public interface ObjectQueueInterface
{
    /**
     * Checks to see if queue is empty
     * 
     * @return true if its empty, false if it's not empty
     */
    public boolean isEmpty();
    
    /**
     * Checks to see if queue is full
     * 
     * @return true if full, false if empty
     */
    public boolean isFull();
    
    /**
     * Clears queue
     */
    public void clear();
    
    /**
     * inserts an object into the queue
     * 
     * @param o is the object being inserted in the que
     */
    public void insert(Object o);
    
    /**
     * removes an object from a que
     * 
     * @return the object being removed from the queue
     */
    public Object remove();
    
    /**
     * checks the front of the queue without removing it
     * 
     * @return the object in the front of the queue
     */
    public Object query();
}
