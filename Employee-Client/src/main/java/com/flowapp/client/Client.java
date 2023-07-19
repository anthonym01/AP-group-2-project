package com.flowapp.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

import com.flowapp.logging.Log;
import com.flowapp.model.Customer;
import com.flowapp.model.Employee;
import com.flowapp.model.Message;
import com.flowapp.model.Response;
import com.flowapp.model.TemporaryChat;


public class Client {
    // Server Socket Objects
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private Socket connectionSocket;

    private String action;
    private Customer customer = null;
    private Employee employee = null;
    private Message message = null;
    private Response response = null;
    private TemporaryChat temporaryChat = null;

    private List<Employee> listOfEmployees;
    private List<Message> listOfMessages;
    private List<Customer> listOfCustomers;
    private List<TemporaryChat> listOfTemporaryChats;


    public Client(){
        initializeConnection();
        configureStreams();
    }


    // Initializing the connection with the server
    private void initializeConnection(){
        try {
            connectionSocket = new Socket("127.0.0.1", 8888);
        }
        catch (IOException e) {
            Log.error("Error inside the initializeConnection() -> " + e.getMessage());
        }
    }

    // Configure the streams
    private void configureStreams() {
        try {
            // Create an output stream to send data to the server
            os = new ObjectOutputStream(connectionSocket.getOutputStream());

            // Create an input stream to receive data from the server
            is = new ObjectInputStream(connectionSocket.getInputStream());
        }
        catch (NullPointerException e) {
            Log.error("Error inside the configureStreams() -> " + e.getMessage());

            // Delay the server error message when the app starts -> Delay about 2-3 seconds
            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);

                        JOptionPane.showMessageDialog(null, "The system server is currently down. Please try again later!", "Server Error", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (InterruptedException ex) {
                    Log.error("Error inside the configureStreams() -> [extra thread] " + ex.getMessage());
                    }
                }
            });

            thread.start();
        }
        catch (IOException e) {
            Log.error("Error inside the configureStreams() -> " + e.getMessage());
        }
    }

    // Close off all the connections
    public void closeConnection() {
        try {
            os.close();
            is.close();
            connectionSocket.close();
        }
        catch (NullPointerException e) {
            Log.error("Error inside the closeConnection() -> " + e.getMessage());
        }
        catch (IOException e) {
            Log.error("Error inside the closeConnection() -> " + e.getMessage());
        }
    }

    /* UTILITY METHODS */
    // Send action
    public void sendAction(String action) {
        this.action = action;

        try {
            os.writeObject(action);
        }
        catch (IOException | NullPointerException e) {
            Log.error("Error inside the sendAction() -> " + e.getMessage());
        }
    }

    // Send ID#
    public void sendID(String id) {
        try {
            os.writeObject(id);
        }
        catch (IOException | NullPointerException e) {
            Log.error("Error inside the sendID() -> " + e.getMessage());
        }
    }

    public void sendID(int id) {
        try {
            os.writeObject(id);
        }
        catch (IOException | NullPointerException e) {
            Log.error("Error inside the sendID() -> " + e.getMessage());
        }
    }

    // Send Category
    public void sendCategory(String category){
        try {
            os.writeObject(category);
        }
        catch (IOException | NullPointerException e) {
            Log.error("Error inside the sendCategory() -> " + e.getMessage());
        }
    }

    // Send customer
    public void sendCustomer(Customer customer) {
        try {
            os.writeObject(customer);
        }
        catch (IOException | NullPointerException e) {
            Log.error("Error inside the sendCustomer() -> " + e.getMessage());
        }
    }

    // Send employee
    public void sendEmployee(Employee employee) {
        try {
            os.writeObject(employee);
        }
        catch (IOException | NullPointerException e) {
            Log.error("Error inside the sendEmployee() -> " + e.getMessage());
        }
    }

    // Send message
    public void sendMessage(Message message) {
        try {
            os.writeObject(message);
        }
        catch (IOException | NullPointerException e) {
            Log.error("Error inside the sendMessage() -> " + e.getMessage());
        }
    }

    // Send response
    public void sendResponse(Response response) {
        try {
            os.writeObject(response);
        }
        catch (IOException | NullPointerException e) {
            Log.error("Error inside the sendResponse() -> " + e.getMessage());
        }
    }

    // Send session chat
    public void sendChat(TemporaryChat chat) {
        try {
            os.writeObject(chat);
        }
        catch (IOException | NullPointerException e) {
            Log.error("Error inside the sendChat() -> " + e.getMessage());
        }
    }

    // Retrieve response from the server
    @SuppressWarnings("unchecked")
    public Object receiveResponse() {
        Boolean flag = false;

        try {
            /* Creation */
            // Add a customer
            if(action.equalsIgnoreCase("add customer")){
                flag = (Boolean) is.readObject();

                if (flag) {
                    JOptionPane.showMessageDialog(null, "Customer record added successfully!", "Add Record Status", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }

                JOptionPane.showMessageDialog(null, "Customer record could not be added!\n", "Add Record Failure", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Add a message
            if(action.equalsIgnoreCase("add message")){
                flag = (Boolean) is.readObject();

                if (flag) {
                    JOptionPane.showMessageDialog(null, "Message record added successfully!", "Add Record Status", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }

                JOptionPane.showMessageDialog(null, "Message record could not be added!\n", "Add Record Failure", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Add a response
            if(action.equalsIgnoreCase("add response")){
                flag = (Boolean) is.readObject();

                return null;
            }

            // Add a session chat
            if(action.equalsIgnoreCase("add chat")){
                flag = (Boolean) is.readObject();

                return null;
            }


            /* Retrieval */
            // Find a customer
            if(action.equalsIgnoreCase("find customer")){
                customer = (Customer) is.readObject();

                return customer;
            }

            if(action.equalsIgnoreCase("get all customers")){
                listOfCustomers = (List<Customer>) is.readObject();

                return listOfCustomers;
            }

            // Find a employee
            if(action.equalsIgnoreCase("find employee")){
                employee = (Employee) is.readObject();

                return employee;
            }

            // Find a message
            if(action.equalsIgnoreCase("find message")){
                message = (Message) is.readObject();

                return message;
            }

            // Get a list of messages
            if(action.equalsIgnoreCase("get messages")){
                listOfMessages = (List<Message>) is.readObject();

                return listOfMessages;
            }

            // Get a list of messages by category
            if(action.equalsIgnoreCase("get messages by category")){
                listOfMessages = (List<Message>) is.readObject();

                return listOfMessages;
            }

            // Find a response
            if(action.equalsIgnoreCase("find response")){
                response = (Response) is.readObject();

                return response;
            }

            // Find a session chat
            if(action.equalsIgnoreCase("find chat")){
                temporaryChat = (TemporaryChat) is.readObject();

                return temporaryChat;
            }

            if(action.equalsIgnoreCase("find chat by technician")){
                temporaryChat = (TemporaryChat) is.readObject();

                return temporaryChat;
            }

            // Retrieving all technicians
            if(action.equalsIgnoreCase("get technicians")){
                listOfEmployees = (List<Employee>) is.readObject();

                return listOfEmployees;
            }

            // Retrieving all temporary chats
            if(action.equalsIgnoreCase("get chats")){
                listOfTemporaryChats = (List<TemporaryChat>) is.readObject();

                return listOfTemporaryChats;
            }

 
            /* Updating */
            // Update a customer
            if(action.equalsIgnoreCase("update customer")){
                flag = (Boolean) is.readObject();

                if (flag) {
                    //JOptionPane.showMessageDialog(null, "Customer updated successfully!", "Update Status", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }

                //JOptionPane.showMessageDialog(null, "Customer record could not be updated!\n", "Update Failure", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Update a employee
            if(action.equalsIgnoreCase("update employee")){
                flag = (Boolean) is.readObject();

                return flag;
            }

            // Update a message
            if(action.equalsIgnoreCase("update message")){
                flag = (Boolean) is.readObject();

                if (flag) {
                    JOptionPane.showMessageDialog(null, "Message updated successfully!", "Update Status", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }

                JOptionPane.showMessageDialog(null, "Message record could not be updated!\n", "Update Failure", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Update a response
            if(action.equalsIgnoreCase("update response")){
                flag = (Boolean) is.readObject();

                if (flag) {
                    JOptionPane.showMessageDialog(null, "Response updated successfully!", "Update Status", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }

                JOptionPane.showMessageDialog(null, "Response could not be updated!\n", "Update Failure", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Update a session chat
            if(action.equalsIgnoreCase("update chat")){
                flag = (Boolean) is.readObject();

                if (flag) {
                    //JOptionPane.showMessageDialog(null, "Response updated successfully!", "Update Status", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }

                //JOptionPane.showMessageDialog(null, "Response could not be updated!\n", "Update Failure", JOptionPane.ERROR_MESSAGE);
                return null;
            }


            /* Deletion */
            // Delete a message or response
            if(action.equalsIgnoreCase("delete message by representative") || action.equalsIgnoreCase("delete response")){
                flag = (Boolean) is.readObject();

                if (flag) {
                    JOptionPane.showMessageDialog(null, "Message record delete successfully!", "Deletion Status", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }

                JOptionPane.showMessageDialog(null, "Message record could not be delete!\n", "Deletion Failure", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Delete a session chat
            if(action.equalsIgnoreCase("delete chat")){
                flag = (Boolean) is.readObject();

                if (flag) {
                    //JOptionPane.showMessageDialog(null, "Message record delete successfully!", "Deletion Status", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }

                //JOptionPane.showMessageDialog(null, "Message record could not be delete!\n", "Deletion Failure", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        catch (ClassCastException e) {
            Log.error("Error inside the receiveResponse() -> [casting error] " + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            Log.error("Error inside the receiveResponse() -> [unknown class] " + e.getMessage());
        }
        catch (IOException | NullPointerException e) {
            Log.error("Error inside the receiveResponse() -> [?] " + e.getMessage());
        }

        return null;
    }
}
