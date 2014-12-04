/**
 * ThreadCounter will handle a single customer at a time, and signal the scheduler when done
 */
public class ThreadCounter extends Thread{
    private final String TAG = "ThreadCounter";
    private final int serviceTimeAverage = 5000, serviceTimeVariance = 10000;
    private Bank bank;
    private int tellerId;
    private Customer customer;

    public ThreadCounter(Bank bank, int tellerId){
        this.bank = bank;
        this.customer = null;
        this.tellerId = tellerId;
    }

    public void run(){
        while(bank.getRunning()){
            try {
                if (hasCustomer())
                    serviceCustomer();
                else
                    wait();
            } catch(InterruptedException e) {
            } catch(IllegalMonitorStateException e){
            }
        }
    }

    /**
     * Returns current status of the counter/teller.
     * @return true if counter/teller has a customer
     */
    public boolean hasCustomer(){
        if(this.customer != null)
            return true;
        return false;
    }

    /**
     * Attempts to accept a customer at this counter/teller.
     * @param customer customer to be accepted at this counter/teller
     */
    public void acceptCustomer(Customer customer){
        if(this.customer == null) {
            this.customer = customer;
            this.customer.outOfLine();
            this.customer.setWaitTime();
            bank.log(TAG + this, "Customer " + customer + " has been accepted by counter " + this + "! cust" +
                    customer + " wait time: " + customer.getWaitTime());
        }else{
            bank.log(TAG + this, "Counter " + this + " currently has a customer!");
        }
    }

    /**
     * Services the current customer for [5, 15] seconds at this counter/teller then ends transaction.
     */
    public void serviceCustomer(){
        if (this.customer != null) {
            bank.log(TAG + this, "Servicing customer " + this.customer + " at counter " + this + "!");
            try {
                long serviceTime = serviceTimeAverage + (int) (Math.random() * serviceTimeVariance);
                sleep(serviceTime);
                this.customer.setServiceTime(serviceTime);
            }catch(InterruptedException e){}
            bank.log(TAG + this, "Customer " + this.customer + " has been serviced at counter " + this +"! cust" +
                    customer + " service time: " + customer.getServiceTime());
            endTransaction();
        } else {
            bank.log(TAG + this, "No customer to be serviced at counter " + this + "!");
        }
    }

    /**
     * Discharges the current customer after a transaction has been completed.
     */
    public void endTransaction(){
        this.customer = null;
        bank.notifyThread(bank.threadScheduler);
    }

    /**
     * Returns this counter/teller id.
     * @return tellerId
     */
    public String toString(){
        return "" + this.tellerId;
    }
}
