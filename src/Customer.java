/**
 * The Customer class is a data holding class that will be moved from creation in ThreadCreateCustomers, to queue in
 *   ThreadScheduler, and finally to processing at ThreadCounter.
 */
public class Customer{
    private int id;
    private long inTime, outTime, waitTime;

    public Customer(int id){
        this.id = id;
        this.inTime = this.outTime = this.waitTime = 0;
    }

    /**
     * Records the time at which the customer was put into the queue.
     */
    public void inLine(){
        this.inTime = System.currentTimeMillis();
    }

    /**
     * Records the time at which the customer was sent to a counter.
     */
    public void outOfLine(){
        this.outTime = System.currentTimeMillis();
    }

    /**
     * Records the time the customer spent waiting in the queue.
     */
    public void setWaitTime(){
        this.waitTime = this.outTime - this.inTime;
    }

    /**
     * Returns the customer's wait time.
     * @return waitTime
     */
    public long getWaitTime(){
        return this.waitTime;
    }

    /**
     * Returns the customer's id.
     * @return id
     */
    public String toString(){
        return "" + this.id;
    }
}