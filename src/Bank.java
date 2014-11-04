/**
 * The Bank class will be the main class of the project and house instances of all classes used in the project
 */
public class Bank {

    boolean isRunning;

    ThreadCreateCustomers threadCreateCustomers;
    ThreadScheduler threadScheduler;

    ThreadCounter[] threadCounters;

    public static void main(String[] args){}

    public boolean getRunning(){
        return isRunning;
    }

}
