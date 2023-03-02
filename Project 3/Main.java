import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/* 
  Name: Noah Carrier
  Course: CNT 4714 Spring 2023 
  Assignment title: Project 3 – A Two-tier Client-Server Application 
  Date:  February 26th, 2023 
 
  Class:  Main
*/

public class Main {
    // many variables to be used, globally in the class
    private JFrame frame;
    private JButton connectButton, clearSQLButton, executeButton, clearResultButton;
    private JLabel connectionDetailsLabel, enterCommandLabel, sqlExecutionLabel, propertiesLabel, usernameLabel, passwordLabel;
    private JTextField usernameField, passwordField, enterCommandField, connectionField, resultField;
    private JComboBox propertiesBox; 

    
    public Main() {
      frame = new JFrame("SQL Client App - (MJL - CNT 4714 - Spring 2023 - Project 3)");                 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(800,650);
		  frame.setLayout(null);
      frame.setResizable(false);




      // creating Labels
      connectionDetailsLabel = new JLabel("Connection Details");
      enterCommandLabel = new JLabel("Enter An SQL Command");
      sqlExecutionLabel = new JLabel("SQL Execution Result Window");
      propertiesLabel = new JLabel("Properties File");
      usernameLabel = new JLabel("Username");
      passwordLabel = new JLabel("Password");

      
      
      // creating Fields
      propertiesBox = new JComboBox();
      usernameField = new JTextField();
      passwordField = new JTextField();
      enterCommandField = new JTextField();
      connectionField = new JTextField("No Connection Now");
      resultField = new JTextField();
      
      

      // creating Buttons
      connectButton = new JButton("Connect to Database");
      clearSQLButton = new JButton("Clear SQL Command");
      executeButton = new JButton("Execute SQL Command");
      clearResultButton = new JButton("Clear Result Window");




		

      // setting bounds for labels
      connectionDetailsLabel.setBounds(10,5,150,30);
      enterCommandLabel.setBounds(375,5,150,30);
      sqlExecutionLabel.setBounds(40,290,200,30);
      propertiesLabel.setBounds(10,50,100,30);
      usernameLabel.setBounds(10,80,100,30);
      passwordLabel.setBounds(10,110,100,30);



      // setting bounds for fields
      propertiesBox.setBounds(100,50,250,30);
      usernameField.setBounds(100,80,250,30);
      passwordField.setBounds(100,110,250,30);
      enterCommandField.setBounds(375,50,400,150);
      resultField.setBounds(40,320,750,250);
      connectionField.setBounds(40,250,750,30);


      // setting bounds for buttons
      connectButton.setBounds(30,200,175,30);
      clearSQLButton.setBounds(380,200,175,30);
      executeButton.setBounds(575,200,175,30);
      clearResultButton.setBounds(30,575,175,30);



      

      // adding labels to frame
      frame.add(connectionDetailsLabel);
      frame.add(enterCommandLabel);
      frame.add(sqlExecutionLabel);
      frame.add(propertiesLabel); 
      frame.add(usernameLabel);
      frame.add(passwordLabel);
      
      // adding fields to frame
      frame.add(propertiesBox);
      frame.add(usernameField);
      frame.add(passwordField);
      frame.add(enterCommandField);
      frame.add(connectionField);
      frame.add(resultField);


      // adding buttons to frame
      frame.add(connectButton);
      frame.add(clearSQLButton);
      frame.add(executeButton);
      frame.add(clearResultButton);

        

        

       

              
      frame.setVisible(true);
    }


    public static void main(String[] args) {
        new Main();     // creating new instance of Main class
    }
}