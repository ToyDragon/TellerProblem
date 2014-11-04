/**
 * All this class does is provide the helper method waitForNotification()
 */
public class WaitingThread extends Thread {

    /**
     * Calls wait, dumps the interrupted exception. It isn't relevant for this project, and this makes the code much
     *   prettier to look at.
     */
    public void waitForNotification(){
        try{
            wait();
        }catch(InterruptedException e){}
    }
}
