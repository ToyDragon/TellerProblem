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

    public int getId(){
        return this.id;
    }

    public void inLine(){
        this.inTime = System.currentTimeMillis();
    }

    public void outOfLine(){
        this.outTime = System.currentTimeMillis();
    }

    public void setWaitTime(){
        this.waitTime = this.outTime - this.inTime;
    }
    public long getWaitTime(){
        return this.waitTime;
    }
}