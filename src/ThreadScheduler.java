import java.util.LinkedList;
import java.util.Queue;

/**
 * ThreadScheduler will contain a queue of customers and move them to open counters
 */
public class ThreadScheduler extends WaitingThread{
    final String TAG = "ThreadScheduler";
    int customerLimit;

    Queue<Customer> customerQueue;
    Bank bank;

    public ThreadScheduler(Bank bank){
        this.bank = bank;

        customerLimit = 20;
        customerQueue = new LinkedList<Customer>();
    }

    public void run(){
        while(bank.getRunning()){
            int openCounter = bank.getOpenCounterIndex();

            //If a customer is waiting, and a counter is open send him through!
            if(customerQueue.size() > 0 && openCounter != -1){
                bank.log(TAG,"Sending customer to counter "+openCounter+"...");
                sendCustomer(customerQueue.poll(), openCounter);
            }
            //Otherwise wait for a notification (new customer or open counter
            else{
                bank.log(TAG,"Waiting for counter to open or new customer to come...");
                waitForNotification();
            }
        }
    }

    /**
     * Tells the specified counter to accept the specified customer, then notifies that counter that he has a
     *   new customer to process.
     * @param customer customer to be sent to counter
     * @param counterIndex index of counter in bank.threadCounters array
     */
    public void sendCustomer(Customer customer, int counterIndex){
        bank.threadCounters[counterIndex].acceptCustomer(customer);
        bank.notifyThread(bank.threadCounters[counterIndex]);
    }
}
