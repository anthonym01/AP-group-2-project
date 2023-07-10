package com.flowapp.server;

import com.flowapp.controller.Controller;
import com.flowapp.logging.Log;
import com.flowapp.model.*;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.List;


public class ClientHandler extends Thread {
    private Socket clientSocket;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private Controller controller;

    public ClientHandler(Socket clientSocket, Controller controller){
        this.clientSocket = clientSocket;
        this.controller = controller;
    }

    @Override
    public void run() {
        initializeStreams();
        handler();
    }

    // Initializing the streams
    private void initializeStreams(){
        try {
            os = new ObjectOutputStream(clientSocket.getOutputStream());
            is = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (IOException e) {
            Log.error("Error inside the initializeStreams() -> " + e.getMessage());
        }
    }

    // Close off all the connections
    private void closeConnection() {
        try {
            os.close();
            is.close();
            clientSocket.close();
        }
        catch (IOException e) {
            Log.error("Error inside the closeConnection() -> [connection close] " + e.getMessage());
        }
    }

    // Handler method
    private void handler() {
        // Using a while loop here to avoid a connection with peer error when using a GUI
        while(true){
            // Accepting values to be calculated
            try {
                checkEvent();
            }
            catch (ClassNotFoundException e) {
                Log.error("Error inside the handler() -> [unknown class] " + e.getMessage());
            }
            catch (SocketException e) {
                // If there was a disconnection from the client side, disconnect from the database as well
                Log.fatal("Error inside the handler() -> Server on port 8888 has been terminated... [socket]\n" + e.getMessage());
                break;
            }
            catch (IOException e) {
                // If there was a disconnection from the client side, disconnect from the database as well
                Log.fatal("Error inside the handler() -> [connection disconnected] " + e.getMessage());
                break;
            }
        }

        closeConnection();
    }

    // This check event method will write and read events to and from the client side
    private void checkEvent() throws ClassNotFoundException, SocketException, IOException {
        String action = "";
        String userID = "";
        String chatID = "";
        int messageID = 0;
        int responseID = 0;
        Employee employee = null;
        Customer customer = null;
        Message message = null;
        Response response = null;
        TemporaryChat chat = null;

        action = (String) is.readObject();

        // Adding Objects
        if(action.equalsIgnoreCase("add customer")){
            customer = (Customer) is.readObject();
            controller.create(customer);

            Log.info("Customer added successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("add employee")){
            employee = (Employee) is.readObject();
            controller.create(employee);

            Log.info("Employee added successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("add message")){
            message = (Message) is.readObject();
            controller.create(message);

            Log.info("Message added successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("add response")){
            response = (Response) is.readObject();
            controller.create(response);

            Log.info("Response added successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("add chat")){
            chat = (TemporaryChat) is.readObject();
            controller.create(chat);

            Log.info("Chat added successfully!");
            os.writeObject(true);
        }

        else if(action.equalsIgnoreCase("assign to employee")){
            message = (Message) is.readObject();

            controller.update(message);

            String name = (message.getEmployee() != null ? message.getEmployee().getFirstName() + " " + message.getEmployee().getLastName() : "-");
            Log.info("Message assigned to " + name + " successfully!");

            os.writeObject(true);
        }

        // Finding Objects
        else if(action.equalsIgnoreCase("find customer")){
            userID = (String) is.readObject();

            // Creating a dummy customer with the id# sent from the client
            Customer cus = new Customer();
            cus.setIdNumber(userID);

            customer = controller.find(cus);

            Log.info("Customer found! [" + customer + "]");
            os.writeObject(customer);
        }
        else if(action.equalsIgnoreCase("find employee")){
            userID = (String) is.readObject();

            // Creating a dummy employee with the id# sent from the client
            Employee emp = new Employee();
            emp.setIdNumber(userID);

            employee = controller.find(emp);

            Log.info("Employee found! [" + employee + "]");
            os.writeObject(employee);
        }
        else if(action.equalsIgnoreCase("find message")){
            messageID = (Integer) is.readObject();

            // Creating a dummy message with the id# sent from the client
            Message mes = new Message();
            mes.setMessageID(messageID);

            message = controller.find(mes);

            Log.info("Message found! [" + message + "]");
            os.writeObject(message);
        }
        else if(action.equalsIgnoreCase("find response")){
            responseID = (Integer) is.readObject();

            // Creating a dummy response with the id# sent from the client
            Response res = new Response();
            res.setResponseID(responseID);

            response = controller.find(res);

            Log.info("Response found! [" + response + "]");
            os.writeObject(response);
        }
        else if(action.equalsIgnoreCase("find chat")){
            chatID = (String) is.readObject();

            // Creating a dummy chat with the id# sent from the client
            TemporaryChat sc = new TemporaryChat();
            sc.setIdNumber(chatID);

            chat = controller.find(sc);

            Log.info("Chat found! [" + chat + "]");
            os.writeObject(chat);
        }
        else if(action.equalsIgnoreCase("find chat by technichian")){
            TemporaryChat sc = (TemporaryChat) is.readObject();

            chat = controller.findByRepresentative(sc);

            Log.info("Chat found by Technichian! [" + chat + "]");
            os.writeObject(chat);
        }

        // Retrieving List of Objects
        else if(action.equalsIgnoreCase("get staff members")){
            List<Employee> staffList = controller.getEmployees();

            Log.info("Retrieved staff members [" + staffList.size() + "]");
            os.writeObject(staffList);
        }
        else if(action.equalsIgnoreCase("get technichians")){
            List<Employee> technichiansList = controller.getTechnichians();

            Log.info("Retrieved technichians [" + technichiansList.size() + "]");
            os.writeObject(technichiansList);
        }
        else if(action.equalsIgnoreCase("get available technichians")){
            List<Employee> technichiansList = controller.getAvailableTechnichians();

            Log.info("Retrieved available technichians [" + technichiansList.size() + "]");
            os.writeObject(technichiansList);
        }
        else if(action.equalsIgnoreCase("get messages")){
            List<Message> messageList = controller.getMessages();

            Log.info("Retrieved messages [" + messageList.size() + "]");
            os.writeObject(messageList);
        }
        else if(action.equalsIgnoreCase("get messages by category")){
            String category = (String) is.readObject();
            List<Message> messageList = controller.getMessagesByCategory(category);

            Log.info("Retrieved messages for category [" + messageList.size() + "]");
            os.writeObject(messageList);
        }
        else if(action.equalsIgnoreCase("get chats")){
            userID = (String) is.readObject();

            // Creating a dummy staff with the id# sent from the client
            employee = new Employee();
            employee.setIdNumber(userID);

            List<TemporaryChat> chatList = controller.getChats(employee);

            Log.info("Retrieved chats [" + chatList.size() + "]");
            os.writeObject(chatList);
        }

        // Updating Objects
        else if(action.equalsIgnoreCase("update customer")){
            customer = (Customer) is.readObject();
            controller.update(customer);

            Log.info("Customer updated successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("update employee")){
            employee = (Employee) is.readObject();
            controller.update(employee);

            Log.info("Employee updated successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("update message")){
            message = (Message) is.readObject();
            controller.update(message);

            Log.info("Message updated successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("update response")){
            response = (Response) is.readObject();
            controller.update(response);

            Log.info("Response updated successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("update chat")){
            chat = (TemporaryChat) is.readObject();
            controller.update(chat);

            Log.info("Chat updated successfully!");
            os.writeObject(true);
        }

        // Deleting Objects
        else if(action.equalsIgnoreCase("delete customer")){
            customer = (Customer) is.readObject();
            controller.delete(customer);

            Log.info("Customer deleted successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("delete employee")){
            employee = (Employee) is.readObject();
            controller.delete(employee);

            Log.info("Employee deleted successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("delete message")){
            message = (Message) is.readObject();
            controller.deleteByCustomer(message);

            Log.info("Message deleted successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("delete message by representative")){
            message = (Message) is.readObject();
            controller.deleteByRepresentative(message);

            Log.info("Message deleted by representative successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("delete response")){
            response = (Response) is.readObject();
            controller.delete(response);

            Log.info("Response deleted successfully!");
            os.writeObject(true);
        }
        else if(action.equalsIgnoreCase("delete chat")){
            chat = (TemporaryChat) is.readObject();
            controller.delete(chat);

            Log.info("Chat deleted successfully!");
            os.writeObject(true);
        }
    }
}
