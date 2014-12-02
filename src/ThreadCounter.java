/**
 * ThreadCounter will handle a single customer at a time, and signal the scheduler when done
 */
public class ThreadCounter extends Thread{
    final String TAG = "ThreadCounter";
    Bank bank;
    int tellerId;
    Customer customer;
    Boolean busy;

    public ThreadCounter(Bank bank){
        this.bank = bank;
        this.customer = null;
        this.busy = false;
    }

    public void run(){
        while(bank.getRunning()){
            //TODO: if there is a customer handle him, then clean up and wait/notify the scheduler
        }
    }

    public boolean isBusy(){
        return this.busy;
    }

    public void acceptCustomer(Customer customer){
        this.busy = true;
        this.customer = customer;
        this.customer.outOfLine();
        this.customer.setWaitTime();
        //TODO: mark this counter as inaccessible and store the customer for processing in run()
    }

    public void serviceCustomer(){
        if(this.customer != null){
            bank.log(TAG + this.tellerId, "Servicing customer " + this.customer.getId() + "!");
            this.customer = null;
        }

        else{
            bank.log(TAG + this.tellerId, "No customer to be serviced!");
        }
    }
}
