/*  
    Name:  Noah Carrier
    Course: CNT 4714 – Spring 2023 
    Assignment title: Project 1 – Event-driven Enterprise Simulation 
    Date: Thursday January 12, 2023 
*/

// ======== Library Imports ========
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//==================================
    

// ========== Main Class ==========
public class NileDotCom implements ActionListener {

        // many variables to be used, globally in the class
    private int itemCount = 0;
    private JFrame frame;
    private JButton findItemButton, purchaseItemButton, viewOrderButton, completeOrderButton, startNewButton, exitButton;
    private JLabel enterItemLabel, enterQuantityLabel, itemDetailsLabel, orderTotalLabel;
    private JTextField enterItemField, enterQuantityField, itemDetailsField, orderTotalField;
    private ArrayList<String[]> shoppingCart;
    private String [] currentItemInfo;
    private double subtotal = 0.0;
    private int itemIDIndex = 0, itemDetailsIndex = 1, inStockIndex = 2, priceIndex = 3, totalPriceIndex = 4, quantityIndex = 5, discountIndex = 6;
    File transactionFile;
    DecimalFormat moneyFormat = new DecimalFormat("#.##");
    
    public NileDotCom() {                                              // constructor method to handle GUI
        frame = new JFrame("Nile Dot Com - Spring 2023");                                   // creating window  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(750,500);
		frame.setLayout(null);


        // creating shopping cart
        shoppingCart = new ArrayList<String[]>();
            // array list of an array that will store in each entry an item ID, description, quantity, and price



        // creating Labels
        enterItemLabel = new JLabel("Enter item ID for Item #1:");
        enterQuantityLabel = new JLabel("Enter quantity for Item #1:");
        itemDetailsLabel = new JLabel("Details for Item #1:");
        orderTotalLabel = new JLabel("Order subtotal for 0 item(s):");
        //===================

        // creating Fields
        enterItemField = new JTextField();
        enterQuantityField = new JTextField();
        itemDetailsField = new JTextField();
        itemDetailsField.setEditable(false);        //Setting output text field to be uneditable
        orderTotalField = new JTextField();
        orderTotalField.setEditable(false);        //Setting output text field to be uneditable
        //===================


        // creating Buttons
        findItemButton = new JButton("Find Item #1");
        purchaseItemButton = new JButton("Purchase Item #1");
        viewOrderButton = new JButton("View Current Order");
        completeOrderButton = new JButton("Complete Order - Check Out");
        startNewButton = new JButton("Start New Order");
        exitButton = new JButton("Exit (Close App)");

            // adding action listeners for buttons
        findItemButton.addActionListener(this);
        purchaseItemButton.addActionListener(this);
        viewOrderButton.addActionListener(this);
        completeOrderButton.addActionListener(this);
        startNewButton.addActionListener(this);
        exitButton.addActionListener(this);
        //===================



		

        // setting bounds for labels
        enterItemLabel.setBounds(188,40 ,188,30);
        enterQuantityLabel.setBounds(188,103 ,188,30);
        itemDetailsLabel.setBounds(188,166 ,188,30);
        orderTotalLabel.setBounds(188,229 ,188,30);
        //===================
        
        // setting bounds for fields
        enterItemField.setBounds(376,42 ,250,30);
        enterQuantityField.setBounds(376,105 ,250,30);
        itemDetailsField.setBounds(376,168 ,250,30);
        orderTotalField.setBounds(376,231 ,250,30);
        //===================

        // setting bounds for buttons
        findItemButton.setBounds(78,290,250,30);
        purchaseItemButton.setBounds(426,290,250,30);
        viewOrderButton.setBounds(78,340,250,30);
        completeOrderButton.setBounds(426,340,250,30);
        startNewButton.setBounds(78,390,250,30);
        exitButton.setBounds(426,390,250,30);
        //===================



        // adding labels to frame
        frame.add(enterItemLabel);
        frame.add(enterQuantityLabel);
        frame.add(itemDetailsLabel);
        frame.add(orderTotalLabel);
        //===================


        // adding fields to frame
        frame.add(enterItemField);
        frame.add(enterQuantityField);
        frame.add(itemDetailsField);
        frame.add(orderTotalField);
        //===================

        // adding buttons to frame
        frame.add(findItemButton);
        frame.add(purchaseItemButton);
        frame.add(viewOrderButton);
        frame.add(completeOrderButton);
        frame.add(startNewButton);
        frame.add(exitButton);

        viewOrderButton.setEnabled(false);
        purchaseItemButton.setEnabled(false);
        completeOrderButton.setEnabled(false);
        //===================     

        // creating colors
        Color fieldColor =  new Color(175,202,234);
        Color bgColor = new Color(104,160,230);
        Color frameColor = new Color(46,61,77);
        //===================

        

        // setting colors
        frame.setBackground(frameColor);
        frame.getContentPane().setBackground(bgColor);
        enterItemField.setBackground(fieldColor);
        enterQuantityField.setBackground(fieldColor);
        itemDetailsField.setBackground(fieldColor);
        orderTotalField.setBackground(fieldColor);
        //===================

       

              
        frame.setVisible(true);     // making frame visible, after everything has been added
    }



    public static void main(String [] args) {
        new NileDotCom();  
    }



    public void actionPerformed(ActionEvent e) {
		
        if (e.getSource() == findItemButton) {
            File inventoryFile = new File("inventory.txt");


            try {
                Scanner input = new Scanner(inventoryFile);
                
                String readItemInfo;


                while (input.hasNextLine()) {       // iterating through inventory for item searching by ID
                    readItemInfo = input.nextLine();
                    currentItemInfo = readItemInfo.split(",");

                    if(currentItemInfo[itemIDIndex].equals(enterItemField.getText())) {      // checking if the line contains the ID for the item being searched
                        
                            // checking if item is out of stock
                        if (currentItemInfo[inStockIndex].equals(" false")) {            
                            JOptionPane.showMessageDialog(frame, "Sorry... that item is out of stock, please try another item", "Item Out Of Stock", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                      
                                // filling out other fields on GUI and changing button status
                        itemDetailsField.setText(currentItemInfo[itemIDIndex] + " " + currentItemInfo[itemDetailsIndex] + " $" + currentItemInfo[priceIndex] + " 1 0% " + currentItemInfo[priceIndex]);
                        enterQuantityField.setText("1");
                        findItemButton.setEnabled(false);
                        purchaseItemButton.setEnabled(true);
                        break;
                    }
                        // if this is the last item in the file and it is not what we searched, the item does not exist
                    if (!input.hasNextLine()) {
                        JOptionPane.showMessageDialog(frame, "Item ID " + enterItemField.getText() + " not in file", "Invalid Item", JOptionPane.ERROR_MESSAGE);
                    }
                }

                input.close();
            }
            
            
            catch (Exception exception) {
                exception.printStackTrace();
                System.exit(-1);
            }
        }


        if(e.getSource() == purchaseItemButton) {
            
            boolean validQuantity = true;
            double discount = 0.0;
            // checking if quantity field contains an invalid entry
            try {
                Integer.parseInt(enterQuantityField.getText());
            }
            catch (Exception exception) {
                validQuantity = false;                
            }

            if (validQuantity) {
                int getQuantity = Integer.parseInt(enterQuantityField.getText());
                
                if (getQuantity == 0) validQuantity = false;        // also checking if the entry is 0
                else if (getQuantity < 5)
                    discount = 0.0;
                else if (getQuantity >= 5 && getQuantity <= 9)       // adding discount ammount
                    discount = 0.10;
                else if (getQuantity >= 10 && getQuantity <= 14)
                    discount = 0.15;
                else
                    discount = 0.20;
                
                Double itemQuantityTotal = getQuantity * Double.parseDouble(currentItemInfo[priceIndex]);
                
                itemQuantityTotal = itemQuantityTotal - (itemQuantityTotal*discount);       // discounting purchase
                itemQuantityTotal = Math.floor(itemQuantityTotal * 100) / 100;

                shoppingCart.add(new String [] {currentItemInfo[itemIDIndex], currentItemInfo[itemDetailsIndex], String.valueOf(true), currentItemInfo[priceIndex], String.valueOf(itemQuantityTotal), enterQuantityField.getText(), String.valueOf((int)(discount * 100))});

                // changing fields, buttons and labels to correspond with the new item being purchased
                itemCount++;
                findItemButton.setText("Find Item #"+(itemCount+1));
                findItemButton.setEnabled(true);
                viewOrderButton.setEnabled(true);
                completeOrderButton.setEnabled(true);
                purchaseItemButton.setEnabled(false);
                purchaseItemButton.setText("Purchase Item #"+(itemCount+1));
                enterItemLabel.setText("Enter item ID for Item #"+(itemCount+1)+":");
                enterItemField.setText("");
                enterQuantityLabel.setText("Enter quantity for Item #"+(itemCount+1)+":");
                enterQuantityField.setText("");
                itemDetailsLabel.setText("Details for Item #"+(itemCount+1)+":");
                itemDetailsField.setText(currentItemInfo[itemIDIndex] + " " + currentItemInfo[itemDetailsIndex] + " $" + currentItemInfo[priceIndex] + " " + getQuantity + " %" + (int)(discount*100) + " $" + moneyFormat.format(itemQuantityTotal));
                orderTotalLabel.setText("Order subtotal for "+itemCount+" item(s):");
                orderTotalField.setText("$" + moneyFormat.format(itemQuantityTotal));

                JOptionPane.showMessageDialog(frame, "Item #" + itemCount + " accepted. Added to your cart.", "Added to Cart", JOptionPane.PLAIN_MESSAGE);

                try {                                                                       // writing to the transaction
                    transactionFile = new File("transactions.txt");
                    transactionFile.createNewFile();
                    FileWriter output = new FileWriter(transactionFile, true);

                    LocalDateTime dateTime = LocalDateTime.now();

                    String transactionID = (String.valueOf(dateTime.getDayOfMonth()) + String.valueOf(dateTime.getMonthValue()) + String.valueOf(dateTime.getYear()) + String.valueOf(dateTime.getHour()) + String.valueOf(dateTime.getMinute()));
                    
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss a");  

                    output.write(transactionID + ", " + currentItemInfo[itemIDIndex] + ", " + currentItemInfo[itemDetailsIndex] + ", " + currentItemInfo[priceIndex] + ", " + enterQuantityField.getText() + ", " + String.valueOf(discount) + ", "  + "$" + itemQuantityTotal + ", " + dateTime.format(dateFormat) + " EST\n");        
                    output.close();
                }
                catch(Exception exception) {
                    exception.printStackTrace();
                }

            }
            else    // if the quantity entry is invalid
            {
                JOptionPane.showMessageDialog(frame, "Invalid quantity entry, please enter a quantity using integer greater than zero", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        

        if(e.getSource() == viewOrderButton) {      // displays the current items in the cart
            String viewOrderMessage = "";
            for (int i = 0; i < itemCount; i++)
            {
                viewOrderMessage += ((i+1) + ". " + shoppingCart.get(i)[itemIDIndex] + " " + shoppingCart.get(i)[itemDetailsIndex] + " $" + shoppingCart.get(i)[priceIndex] + " " + shoppingCart.get(i)[quantityIndex] + " %" + shoppingCart.get(i)[discountIndex] + " " + shoppingCart.get(i)[totalPriceIndex]+"\n");
            }
            JOptionPane.showMessageDialog(frame, viewOrderMessage, "Nile Dot Com - Current Shopping Cart Status", JOptionPane.INFORMATION_MESSAGE);
		}


        if (e.getSource() == completeOrderButton) {
            
            enterItemField.setEnabled(false);       // disabling fields for new items
            enterQuantityField.setEnabled(false);
            
            String completeOrderMessage = "";       // creating string for invoice message

            LocalDateTime dateTime = LocalDateTime.now();                   // creating a datetime variable to use in transaction invoice
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yy, h:mm:ss a");

            completeOrderMessage += "Date: " + dateTime.format(dateTimeFormat) + " EST\n\n";

            completeOrderMessage += "Number of line items: " + itemCount + "\n\n";

            completeOrderMessage += "Item# / ID / Title / Price / Qty / Disc % / Subtotal:\n\n";

            for (int i = 0 ; i < itemCount ; i ++) {        // printing purchases and summing up the subtotal

                completeOrderMessage += (i+1) + ". "+ shoppingCart.get(i)[itemIDIndex] + " " + shoppingCart.get(i)[itemDetailsIndex] + " $" + shoppingCart.get(i)[priceIndex] + " " + shoppingCart.get(i)[quantityIndex] + " " + shoppingCart.get(i)[discountIndex] + "% $" + shoppingCart.get(i)[totalPriceIndex] + "\n";
                subtotal += Double.parseDouble(shoppingCart.get(i)[totalPriceIndex]);
            }
            
            completeOrderMessage += "\n\nOrder subotal: $" + moneyFormat.format(subtotal) + "\n\n";

            completeOrderMessage += "Tax rate:\t\t\t6%\n\nTax amount:\t\t\t$" + moneyFormat.format(subtotal * .06) + "\n\n";

            completeOrderMessage += "ORDER TOTAL:\t\t\t" + moneyFormat.format(subtotal * 1.06) + "\n\nThanks for shopping at Nile Dot Com!";


            JOptionPane.showMessageDialog(frame, completeOrderMessage, "Nile Dot Com - FINAL INVOICE", JOptionPane.INFORMATION_MESSAGE);        // invoice message
        }


        if(e.getSource() == startNewButton) {       // resetting all components, including deleting the shopping cart
            for (int i = 0; i < itemCount; i++) {
                shoppingCart.remove(i);
            }
            itemCount = 0;
            enterItemField.setText("");
            itemDetailsField.setText("");
            enterQuantityField.setText("");
            orderTotalField.setText("");
            findItemButton.setText("Find Item #1");
            purchaseItemButton.setText("Purchase Item #1");
            enterItemLabel.setText("Enter item ID for Item #1:");
            enterQuantityLabel.setText("Enter quantity for Item #1:");
            itemDetailsLabel.setText("Details for Item #1:");
            orderTotalLabel.setText("Order subtotal for 0 item(s):");
            viewOrderButton.setEnabled(false);
            purchaseItemButton.setEnabled(false);
            completeOrderButton.setEnabled(false);
            enterItemField.setEnabled(true);
            enterQuantityField.setEnabled(true);
            transactionFile.delete();
        }


        if(e.getSource() == exitButton) {       // closing the program
            System.out.println("Thank you for shopping with us...\nNow exiting Nile Dot Com...\n");
			System.exit(0);
		}
        
	}
    
}
//==================================