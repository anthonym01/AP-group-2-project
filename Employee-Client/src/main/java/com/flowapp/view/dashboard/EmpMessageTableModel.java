package com.flowapp.view.dashboard;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.flowapp.driver.Utils;
import com.flowapp.model.Customer;
import com.flowapp.model.Employee;
import com.flowapp.model.Message;
import com.flowapp.model.Response;
import com.flowapp.model.User;


public class EmpMessageTableModel extends AbstractTableModel {
    private List<Message> messages;
    private final String[] columnNames = {"Type", "Category", "Created On", "Details", "Responded by", "Response Date", "Response", "Resolved"};


    public EmpMessageTableModel(List<Message> messages){
        this.messages = new ArrayList<>(messages);
    }

    public void setMessages(List<Message> messages){
        this.messages = messages;
    }


    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return messages.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        Message message = messages.get(row);

        Customer customer = message.getCustomer();
        Employee employee = message.getEmployee();

        boolean empFound = false, custFound = false;

        // Get the last response on a message to display in the list/table...
        List<Response> responses = (message != null && message.getResponses() != null ? new ArrayList<>(message.getResponses()) : null);
        Response lastResponse = (responses != null && responses.size() > 0 ? responses.get(0) : null);

        int tracker = 0;
        User user = null;

        for (Response response : responses) {
            // If the customer made this response then display "you"
            if(customer != null && response.getUserID() != null && response.getUserID().equalsIgnoreCase(customer.getIDNumber())){
                custFound = true;
                empFound = false;
            }
            // If the employee made this response then display the employee's firstname
            else if(employee != null && response.getUserID() != null && response.getUserID().equalsIgnoreCase(employee.getIDNumber())){
                empFound = true;
                custFound = false;
            }

            // Capture the first user in the list "which is the last response"
            if(tracker == 0) user = (custFound ? customer : empFound ? employee : null);
            tracker++;
        }

        // The person who last responded...
        String lastResponseMadeBy = (user != null ? user.getFirstName() + " " + user.getLastName() : null);
        
        switch (column) {
            case 0:
                return message.getMessageType();
        
            case 1:
                return message.getCategory();

            case 2:
                return (message.getDateCreated() != null ? Utils.convertDate(message.getDateCreated()) : "-");

            case 3:
                return message.getDetails();
        
            case 4:
                return (lastResponseMadeBy != null && lastResponse != null ? lastResponseMadeBy : "-");
            
            case 5:
                return (lastResponse != null && lastResponse.getResponseDate() != null ? Utils.convertDate(lastResponse.getResponseDate()) : "-");
            
            case 6:
                return (lastResponse != null && lastResponse.getContent() != null ? lastResponse.getContent() : "-");

            case 7:
                return (message.isResolved() ? "Yes" : "No");
        }

        return null;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return Object.class;
    }
    
    // Get message at row index
    public Message getMessageAt(int row){
        return messages.get(row);
    }

    // Add a message to the table
    public void addMessage(Message message){
        messages.add(message);
    }

    // Update a message at row index
    public void updateMessage(Message message){
        int index = messages.indexOf(message);

        messages.set(index, message);
    }

    // Delete a message at row index
    public void deleteMessage(Message message){
        messages.remove(message);
    }

    // Get the message count of the table
    public int getSize(){
        return messages.size();
    }

    // Clear the table
    public void clear(){
        messages.clear();
    }
}
