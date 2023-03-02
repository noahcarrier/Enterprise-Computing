import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Withdrawal extends Thread {
    private String threadNum;
    private static Lock lock = new ReentrantLock();
    File output = new File("transactions.txt");
    public Withdrawal (String threadNum) {
        this.threadNum = threadNum;
    }

    @Override
    public void run() {
        try {
            while(true) {
                FileWriter write = new FileWriter(output, true);   
                    // sleeping thread
                    Thread.sleep((long)(Math.random()*1500));
                    if (lock.tryLock()) {       // checking if available                 
                        try{     
                            // locking object
                            lock.lock();             
                                
                            Random nextWithdraw = new Random();
                            int withdraw = nextWithdraw.nextInt(1,99);
                            // printing agent
                            System.out.print("\t\t\t\t\t\t\t\tAgent "+threadNum+" withdraws $"+withdraw);
                            if (Main.balance - withdraw >= 0)
                            {
                                // iterating transaction
                                Main.transaction++;
                                Auditor.lastAudit++;
                                // withdrawing
                                Main.balance -= withdraw;
                                // continue print agent
                                System.out.println("\t\t\t\t(-) Balance is $"+Main.balance+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+Main.transaction);
                                if (withdraw > 75) {
                                    // flagging transaction
                                    System.out.println("\n* * * Flagged Transaction - Withdrawal Agent "+threadNum+" Made A Transaction In Excess Of $75.00 USD - See Flagged Transaction log.\n");
                                    Thread.sleep((long)(Math.random()*500));
                                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
                                    write.append("\tWithdrawal Agent "+threadNum+" issued deposit of $"+withdraw+" at: "+ dateFormat.format(LocalDateTime.now()) + " EST\tTransaction Number : "+Main.transaction+"\n");
                                }
                            }
                            else         // insufficient funds, does not count transaction
                            {
                                System.out.println("\t\t\t\t(*******) WITHDRAWAL BLOCKED - INSUFFICIENT FUNDS!!!");
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
            }
            catch(Exception e) {
                e.printStackTrace();
            }
    }
}

