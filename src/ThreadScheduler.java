import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ThreadScheduler will contain a queue of customers and move them to open counters
 */
public class ThreadScheduler extends WaitingThread{
    private final String TAG = "ThreadScheduler";
    private int customerLimit;

    private Queue<Customer> customerQueue;
    private Bank bank;

    public ThreadScheduler(Bank bank){
        this.bank = bank;

        customerLimit = 20;
        customerQueue = new ConcurrentLinkedQueue<Customer>();
    }

    public void run(){
        while(bank.getRunning()){
            int openCounter = bank.getOpenCounterIndex();

            //If a customer is waiting, and a counter is open send him through!
            if(!customerQueue.isEmpty() && openCounter != -1){
                Customer newCustomer = customerQueue.poll();
                bank.log(TAG, "Sending customer " + newCustomer + " to counter " + openCounter + "...");
                sendCustomer(newCustomer, openCounter);
            }
            //Otherwise wait for a notification (new customer or open counter
            else{
                bank.log(TAG, "Waiting for counter to open or new customer to come...");
                waitForNotification();
            }
        }
    }

    /**
     * Adds a customer to the queue.
     * @param customer customer to be added into queue
     */
    public void addCustomer(Customer customer){
        customerQueue.offer(customer);
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
        bank.notifyThread(bank.threadCreateCustomers);
    }

    /**
     * Returns the current queue size.
     * @return current size of queue
     */
    public int getQueueSize(){
        return this.customerQueue.size();
    }

    /**
     * Returns the limit of the amount of customers in the queue.
     * @return customer limit
     */
    public int getCustomerLimit(){
        return this.customerLimit;
    }
}
