import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Auditor extends Thread {

    public static int lastAudit = 0;
    Lock lock = new ReentrantLock();
    @Override
    public void run () {
       while (true) {
            try {
                Thread.sleep((long)(Math.random()*30000));
                if (lock.tryLock()) {
                    try{
                        lock.lock();
                        System.out.println("*******************************************************************************************************************************\n");
                        System.out.println("\t\t\t\t\tAUDITOR FINDS CURRENT ACCOUNT BALANCE TO BE: $"+Main.balance+"\t\tNumber of transactions since last audit is: "+lastAudit+"\n");
                        System.out.println("*******************************************************************************************************************************\n\n");
                        lastAudit = 0;
                    }
                    finally {
                        lock.unlock();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
       }
       
    }
    
}
