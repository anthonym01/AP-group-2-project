package com.flowapp.view.customized;

import java.awt.Color;
import java.util.Set;

import javax.swing.JTextArea;

import com.flowapp.model.Customer;
import com.flowapp.model.Employee;
import com.flowapp.model.Message;
import com.flowapp.model.Response;
import com.flowapp.model.TemporaryChat;
import com.flowapp.model.User;



public class CTextArea extends JTextArea {
    private String[] customerLastResponse, technicianLastResponse;

    
    public CTextArea() {
        super();

        settings();
    }

    public CTextArea(String text) {
        super(text);

        settings();
    }
    
    public CTextArea(String text, int rows, int cols) {
        super(text, rows, cols);

        settings();
    }

    public CTextArea(int rows, int cols) {
        super(rows, cols);

        settings();
    }

    private void settings(){
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    // Calling these methods when populating the fields of a form...
    public void populate(String text){
        setText(text);
        setForeground(Color.BLACK);

        // To allow the text area to scroll back to the top of the component after populating the field
        setCaretPosition(0);
    }

    public boolean populate(Message message, User user){
        setText("");

        Set<Response> responses = message.getResponses();

        Customer customer = message.getCustomer();
        Employee employee = message.getEmployee();

        boolean empFound = false, custFound = false;

        String text = "";

        for (Response response : responses) {
            // If the customer made this response then display "you"
            if(customer != null && response.getUserID() != null && response.getUserID().equalsIgnoreCase(customer.getIDNumber())){
                if (user != null && user.getIDNumber().equalsIgnoreCase(customer.getIDNumber())) text = "You: " + response.getContent() + "\n\n"; 
                else text = customer.getFirstName() + ": " + response.getContent() + "\n\n";

                custFound = true;
            }
            // If the employee made this response then display the employee's firstname
            else if(employee != null && response.getUserID() != null && response.getUserID().equalsIgnoreCase(employee.getIDNumber())){
                if (user != null && user.getIDNumber().equalsIgnoreCase(employee.getIDNumber())) text = "You: " + response.getContent() + "\n\n"; 
                else text = employee.getFirstName() + ": " + response.getContent() + "\n\n";

                empFound = true;
            }

            append(text);
        }
        
        setForeground(Color.BLACK);

        // To allow the text area to scroll back to the top of the component after populating the field
        setCaretPosition(0);

        // If the employee response found then show the respond button for the customer to respond...
        return (custFound && empFound);
    }
}
