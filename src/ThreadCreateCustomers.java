/**
 * ThreadCreateCustomers will continuously create new customers to put into the queue in ThreadScheduler
 */
public class ThreadCreateCustomers extends WaitingThread{
    final String TAG = "ThreadCreateCustomers";
    final int customerGenerationTime=3000, customerGenerationVariance=1500;

    Bank bank;

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
                    bank.log(TAG,"Customer put into queue!");
                    bank.notifyThread(bank.threadScheduler);
                }else{
                    //we were notified at an incorrect time and the queue is full, this should never happen
                    //but we'll keep this here to find errors if they appear
                    bank.log(TAG,"Cannot put customer in full queue!");
                }
            }else{
                newCustomer.inLine();
                bank.log(TAG,"Customer put into queue!");
                bank.notifyThread(bank.threadScheduler);
            }

            //Sleep for a random amount of time before next customer
            try{
                sleep(customerGenerationTime + (int)(Math.random()*customerGenerationVariance));
            }catch (InterruptedException e){}//We will never interrupt this sleep call
        }
    }

    /**
     * Attempt to place the customer in the queue of ThreadScheduler.
     * @param customer
     * @return true if successful
     */
    public boolean putInQueue(Customer customer){
        if(bank.threadScheduler.customerQueue.size() >= bank.threadScheduler.customerLimit) return false;

        bank.threadScheduler.customerQueue.add(customer);

        return true;
    }

}
