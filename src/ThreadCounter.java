/**
 * ThreadCounter will handle a single customer at a time, and signal the scheduler when done
 */
public class ThreadCounter extends Thread{
    Bank bank;

    public ThreadCounter(Bank bank){
        this.bank = bank;
    }

    public void run(){
        while(bank.getRunning()){

        }
    }

    public void acceptCustomer(Customer customer){
        //TODO: mark this counter as inaccessible and store the customer for processing in run()
    }
}
