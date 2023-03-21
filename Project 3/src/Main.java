/* 
  Name: Noah Carrier
  Course: CNT 4714 Spring 2023 
  Assignment title: Project 3 – A Two-tier Client-Server Application 
  Date:  February 26th, 2023 
 
  Class:  App
*/

// importing libraries
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


public class Main implements ActionListener {
  // many variables to be used, globally in the class
  private JFrame frame;
  private JButton connectButton, clearSQLButton, executeButton, clearResultButton;
  private JLabel connectionDetailsLabel, enterCommandLabel, sqlExecutionLabel, propertiesLabel, usernameLabel, passwordLabel;
  private JPasswordField passwordField;
  private JTextArea enterCommandField;
  private JTextField usernameField, connectionField;
  private JTable resultTable;
  private JComboBox <String> propertiesBox ; 
  private Connection con, conOp;

    // main method
    public Main() {
        // creating frame
        frame = new JFrame("SQL Client App - (MJL - CNT 4714 - Spring 2023 - Project 3)");                 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,675);
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
        String [] cb = {"root.properties", "client.properties"};
        propertiesBox = new JComboBox<String>(cb);
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        enterCommandField = new JTextArea();
        enterCommandField.setCaretPosition(0);
        // set the caret to the beginning of the text for enterCommandField
        enterCommandField.setCaretPosition(0);
        connectionField = new JTextField("No Connection Now");
        connectionField.setEditable(false);
        resultTable = new JTable();
        
        

        // creating Buttons
        connectButton = new JButton("Connect to Database");
        clearSQLButton = new JButton("Clear SQL Command");
        executeButton = new JButton("Execute SQL Command");
        clearResultButton = new JButton("Clear Result Window");

        // adding action listeners to buttons
        connectButton.addActionListener(this);
        clearSQLButton.addActionListener(this);
        executeButton.addActionListener(this);
        clearResultButton.addActionListener(this);





        // setting bounds for labels
        connectionDetailsLabel.setBounds(10,5,200,30);
        enterCommandLabel.setBounds(375,5,200,30);
        sqlExecutionLabel.setBounds(40,315,250,30);
        propertiesLabel.setBounds(10,50,100,30);
        usernameLabel.setBounds(10,80,100,30);
        passwordLabel.setBounds(10,110,100,30);

        connectionDetailsLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        enterCommandLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        sqlExecutionLabel.setFont(new Font("Verdana", Font.BOLD, 12));


        // setting bounds for fields
        propertiesBox.setBounds(100,50,250,30);
        usernameField.setBounds(100,80,250,30);
        passwordField.setBounds(100,110,250,30);
        enterCommandField.setBounds(375,50,400,150);
        resultTable.setBounds(40,345,750,250);
        connectionField.setBounds(40,275,750,30);


        

        // setting bounds for buttons
        connectButton.setBounds(30,225,175,30);
        clearSQLButton.setBounds(380,225,175,30);
        executeButton.setBounds(575,225,190,30);
        clearResultButton.setBounds(30,600,175,30);


        // setting colors for buttons, fields and labels
        connectionField.setBackground(java.awt.Color.BLACK);
        connectionField.setForeground(java.awt.Color.RED);
        connectButton.setOpaque(true);
        connectButton.setBorderPainted(false);
        connectButton.setBackground(java.awt.Color.BLUE);
        connectButton.setForeground(java.awt.Color.YELLOW);
        clearSQLButton.setOpaque(true);
        clearSQLButton.setBorderPainted(false);
        clearSQLButton.setBackground(java.awt.Color.WHITE);
        clearSQLButton.setForeground(java.awt.Color.RED);
        executeButton.setOpaque(true);
        executeButton.setBorderPainted(false);
        executeButton.setBackground(java.awt.Color.GREEN);
        clearResultButton.setOpaque(true);
        clearResultButton.setBorderPainted(false);
        clearResultButton.setBackground(java.awt.Color.YELLOW);


        

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
        frame.add(resultTable);


        // adding buttons to frame
        frame.add(connectButton);
        frame.add(clearSQLButton);
        frame.add(executeButton);
        frame.add(clearResultButton);

        

        

        

                
        frame.setVisible(true);
    }

  public void actionPerformed(ActionEvent e) {
    // clear result button
    if (e.getSource() == clearResultButton) {
        resultTable.setModel(new DefaultTableModel());
    }
    // clear SQL button
    else if (e.getSource() == clearSQLButton) {
      enterCommandField.setText("");
    }
    // connect button
    else if (e.getSource() == connectButton) {
        
        Properties prop = new Properties(); // creating properties object
        try {                        // loading properties file                 
            InputStream input = new FileInputStream((propertiesBox.getSelectedItem()).toString());
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (usernameField.getText().equals("") || passwordField.getText().equals("")) { // checking if username and password fields are empty
            JOptionPane.showMessageDialog(frame, "Please enter username and password", "Invalid Credentials", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {  // if username and password fields are not empty
            connectToDatabase(usernameField.getText(), passwordField.getText(), prop);
        }
        if (propertiesBox.getSelectedItem().equals("root.properties")) {    // checking if root.properties is selected
            if (usernameField.getText().equals("root") && passwordField.getText().equals("root")) {
                connectToDatabase(usernameField.getText(), passwordField.getText(), prop);
            }
            else {  // if username and password fields are not empty
                JOptionPane.showMessageDialog(frame, "Please enter correct username and password", "Invalid Credentials", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (propertiesBox.getSelectedItem().equals("client.properties")) { // checking if client.properties is selected
            if (usernameField.getText().equals("client") && passwordField.getText().equals("client")) {
                connectToDatabase(usernameField.getText(), passwordField.getText(), prop);
            }
            else {  // if username and password fields are not empty
                JOptionPane.showMessageDialog(frame, "Please enter correct username and password", "Invalid Credentials", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    // execute button
    else if (e.getSource() == executeButton) {
        if (con == null) {  // checking if connection is null
            JOptionPane.showMessageDialog(frame, "Please connect to database", "Connection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {  // if connection is not null
            try {   
                (conOp.prepareStatement("UPDATE operationscount SET num_queries = 0;")).execute();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                String sql = enterCommandField.getText();   //  get sql command from text field
                try {
                    PreparedStatement st = con.prepareStatement(sql);   //  create statement
                    
                    ResultSet rs = st.executeQuery();   // execute query and get result set

                    ResultSetMetaData rsmd = rs.getMetaData();      // get result set meta data

                    int columnCount = rsmd.getColumnCount();       // get number of columns

                    String[] columnNames = new String[columnCount]; // array of strings to store column names

                    for (int i = 1; i <= columnCount; i++) {    // get column names
                        columnNames[i-1] = rsmd.getColumnName(i);
                    }

                    DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);    // create a new table model
                    
                    Object[] row = new Object[columnCount]; // array of objects to store each row

                    while (rs.next()) { // while there are rows in the result set
                        for (int i = 1; i <= columnCount; i++) {
                            row[i-1] = rs.getObject(i);
                        }     
                        tableModel.addRow(row);
                    }
                    
                    resultTable.setModel(tableModel);
                    try {
                        int updateCount = st.getUpdateCount();
                        
                        (conOp.prepareStatement("UPDATE operationscount SET num_updates = num_updates + " + updateCount + ";")).execute();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    
                }
                catch (Exception ex) {  // if sql query is not valid
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
        
        
  }

  public void connectToDatabase(String username, String password, Properties prop) {
    // connect to database
    try { 
        Class.forName("com.mysql.jdbc.Driver"); // load driver

        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project3?useTimezone=true&serverTimezone=UTC",username,password);        // connection for main database
        conOp = DriverManager.getConnection("jdbc:mysql://localhost:3306/operationslog?useTimezone=true&serverTimezone=UTC","root", "root");   // connection for operations log
        connectionField.setForeground(java.awt.Color.YELLOW);
        connectionField.setText("Connected to jdbc:mysql://localhost:3306/project3?useTimezone=true&serverTimezone=UTC");
    }
    catch (Exception e) {
        e.printStackTrace();
    }
  }
  public static void main(String[] args) {
       new Main();     // creating new instance of Main class
  }
}