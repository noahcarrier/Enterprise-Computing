import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Name: Noah Carrier
    Course: CNT 4714 Spring 2023 
    Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking 
    Due Date: February 1, 2023 
*/ 


public class Main {

    public static int balance = 0;
    public static int transaction = 0;
    public static void main(String [] args) {
        

        // printing header
        System.out.println("Depositor Agents\t\t\t\tWithdrawal Agents\t\t\t\t\t        Balance\t\t\t\t\t\t\t\t             Transaction Number");
        System.out.println("================\t\t\t\t=================\t\t\t\t\t========================\t\t\t\t\t\t\t===========================");

        
        ExecutorService executorService = Executors.newFixedThreadPool(16);

        for (int i = 0; i < 10; i++) {
            executorService.execute(new Withdrawal(("WT"+i)));
        }
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Depositor(("DT"+i)));
        }
        executorService.execute(new Auditor());

        executorService.shutdown();
        
    }

}





