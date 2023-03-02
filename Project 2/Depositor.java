import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Depositor extends Thread {
    private String threadNum;
    private static Lock lock = new ReentrantLock();
    File output = new File("transactions.txt");
    public Depositor (String threadNum) {
        // numbering thread
        this.threadNum = threadNum;
    }
    @Override
    public void run() {
        try {
                
            while (true) {
                FileWriter write = new FileWriter(output, true);
                // sleeping thread
                Thread.sleep((long)(Math.random()*15000));
                if(lock.tryLock()) {        // checking if available
                    try {
                        // locking object
                        lock.lock();

                        // iterating transaction
                        Main.transaction++;
                        Auditor.lastAudit++;              
                            
                        
                        // choosing amount
                        Random nextDeposit = new Random();
                        int deposit = nextDeposit.nextInt(1,500);
                        Main.balance += deposit;
                        // printing agent
                        System.out.println("Agent "+threadNum+" deposits $"+deposit+"\t\t\t\t\t\t\t\t\t\t\t\t(+) Balance is $"+Main.balance+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+Main.transaction);
                        
                        // flagging transaction
                        if (deposit > 350) {
                            System.out.println("\n* * * Flagged Transaction - Depositor Agent "+threadNum+" Made A Transaction In Excess Of $350.00 USD - See Flagged Transaction log.\n");
                            Thread.sleep((long)(Math.random()*500));
                            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
                            write.append("Depositor Agent "+threadNum+" issued deposit of $"+deposit+" at: "+ dateFormat.format(LocalDateTime.now()) + " EST\tTransaction Number : "+Main.transaction+"\n");
                        }   
                    }
                    finally {
                        // unlocking object
                        lock.unlock();
                        Thread.sleep((long)(Math.random()*3000));
                    }                    
                }
                
                write.close();   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}

