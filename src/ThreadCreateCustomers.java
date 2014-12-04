/**
 * Authors: Matt Bates and Gerard Briones
 *
 * ThreadCreateCustomers will continuously create new customers to put into the queue in ThreadScheduler
 */
public class ThreadCreateCustomers extends WaitingThread{
    private final String TAG = "ThreadCreateCustomers";
    private final int customerGenerationTime = 1000, customerGenerationVariance = 500;

    private Bank bank;

    public ThreadCreateCustomers(Bank bank){
        this.bank = bank;
    }

    public void run() {
        int id = 0;
        while (bank.getRunning()) {

            //create a new customer and attempt to add them to the queue
            Customer newCustomer = new Customer(id++);
            if (!putInQueue(newCustomer)) {

                //If we couldn't put this customer in the queue, wait until the queue has space
                bank.log(TAG,"Waiting for queue to empty...");
                waitForNotification();
                if(putInQueue(newCustomer)) {
                    //notify the scheduler that it has a new customer
                    newCustomer.inLine();
                    bank.log(TAG,"Customer " + newCustomer + " put into queue!");
                    bank.notifyThread(bank.threadScheduler);
                }else{
                    //we were notified at an incorrect time and the queue is full, this should never happen
                    //but we'll keep this here to find errors if they appear
                    bank.log(TAG,"Cannot put customer " + newCustomer + " in full queue!");
                }
            }else{
                newCustomer.inLine();
                bank.log(TAG,"Customer " + newCustomer + " put into queue!");
                bank.notifyThread(bank.threadScheduler);
            }

            //Sleep for a random amount of time before next customer
            try{
                sleep(customerGenerationTime + (int)(Math.random()*customerGenerationVariance));
            }catch(InterruptedException ignored){}//We will never interrupt this sleep call
        }
    }

    /**
     * Attempt to place the customer in the queue of ThreadScheduler.
     * @param customer customer to be placed into queue
     * @return true if successful
     */
    public boolean putInQueue(Customer customer){
        if(bank.threadScheduler.getQueueSize() >= bank.threadScheduler.getCustomerLimit()) return false;

        bank.threadScheduler.addCustomer(customer);

        return true;
    }

}
