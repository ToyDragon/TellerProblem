/**
 * ThreadCreateCustomers will continuously create new customers to put into the queue in ThreadScheduler
 */
public class ThreadCreateCustomers extends WaitingThread{
    final String TAG = "ThreadCreateCustomers";

    Bank bank;

    public ThreadCreateCustomers(Bank bank){
        this.bank = bank;
    }

    public void run() {
        while (bank.getRunning()) {

            //create a new customer and attempt to add them to the queue
            Customer newCustomer = new Customer();
            if (!putInQueue(newCustomer)) {

                //If we couldn't put this customer in the queue, wait until the queue has space
                waitForNotification();
                if(putInQueue(newCustomer)) {

                    bank.log(TAG,"Customer put into queue!");
                }
            }
        }
    }

    /**
     * Attempt to place the customer in the queue of ThreadScheduler.
     * @param customer
     * @return true if successful
     */
    public boolean putInQueue(Customer customer){
        return false;
    }

}
