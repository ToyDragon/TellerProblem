/**
 * ThreadScheduler will contain a queue of customers and move them to open counters
 */
public class ThreadScheduler extends Thread{
    Bank bank;

    public ThreadScheduler(Bank bank){
        this.bank = bank;
    }

    public void run(){
        while(bank.getRunning()){

        }
    }
}
