/**
 * The Bank class will be the main class of the project and house instances of all classes used in the project
 */
public class Bank {

    boolean isRunning;

    ThreadCreateCustomers threadCreateCustomers;
    ThreadScheduler threadScheduler;

    ThreadCounter[] threadCounters;

    public static void main(String[] args){ new Bank(); }

    public Bank(){
        threadCreateCustomers = new ThreadCreateCustomers(this);
        threadScheduler = new ThreadScheduler(this);

        int amtCounters = 3;

        threadCounters = new ThreadCounter[amtCounters];
        for(int i = 0; i < amtCounters; i++){
            threadCounters[i] = new ThreadCounter(this);
        }

        isRunning = true;

        //start counters first, so they wait for customers
        for(int i = 0; i < amtCounters; i++) {
            threadCounters[i].start();
        }

        //start scheduler second, so it is waiting for the queue to populate to send to counters
        threadScheduler.start();

        //start the create customers thread last to send customers into the now open for business bank
        threadCreateCustomers.start();

    }

    public boolean getRunning(){
        return isRunning;
    }

    /**
     * Notifies the specified thread in a synchronized block
     * @param thread thread to be notified
     */
    public void notifyThread(Thread thread){
        synchronized(thread) {
            thread.notifyAll();
        }
    }

    /**
     * Used to find the next open counter that a customer can be sent to.
     * @return index of open counter in threadCounters array, or -1 if none found
     */
    public int getOpenCounterIndex(){
        for(int i = 0; i < threadCounters.length; i++)
            if(!threadCounters[i].isBusy())
                return i;
        return -1;
    }

    /**
     * Used to have a standardized logging system with tags
     * @param tag Super-label of the message being logged, usually class name
     * @param message message to be logged
     */
    public void log(String tag, String message){
        System.out.println("["+tag+"]: "+message);
    }
}
