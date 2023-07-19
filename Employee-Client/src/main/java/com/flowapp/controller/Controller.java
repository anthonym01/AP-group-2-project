package com.flowapp.controller;

import com.flowapp.client.Client;
import com.flowapp.model.*;
import com.flowapp.view.EmpMainFrame;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;


public class Controller {
    private Client client;
    private EmpMainFrame frame;

    public Controller(){
        client = new Client();

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                setupNimbusLAF();

                frame = new EmpMainFrame(Controller.this);
                frame.setVisible(true);
            }
        });
    }

    // Set the application laf to nimbus
    private void setupNimbusLAF(){
        // Source: https://stackoverflow.com/a/18816972
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
        catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
    }

    // Closing the connection between the client and the server
    public void closeConnection(){
        if(client == null) return;

        client.closeConnection();
    }

    // Send action
    public void sendAction(String action) {
        client.sendAction(action);
    }

    // Send ID#
    public void sendID(String id) {
        client.sendID(id);
    }

    public void sendID(int id) {
        client.sendID(id);
    }

    public void sendCategory(String category){
        client.sendCategory(category);
    }

    // Send customer
    public void sendCustomer(Customer customer) {
        client.sendCustomer(customer);
    }

    // Send employee
    public void sendEmployee(Employee employee) {
        client.sendEmployee(employee);
    }

    // Send message
    public void sendMessage(Message message) {
        client.sendMessage(message);
    }

    // Send response
    public void sendResponse(Response response) {
        client.sendResponse(response);
    }

    // Send chat
    public void sendChat(TemporaryChat chat) {
        client.sendChat(chat);
    }

    // Retrieve response from the server
    public Object receiveResponse() {
        return client.receiveResponse();
    }
}

