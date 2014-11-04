/**
 * ThreadCreateCustomers will continuously create new customers to put into the queue in ThreadScheduler
 */
public class ThreadCreateCustomers extends Thread{
    Bank bank;

    public ThreadCreateCustomers(Bank bank){
        this.bank = bank;
    }

    public void run(){
        while(bank.getRunning()){

        }
    }
}
