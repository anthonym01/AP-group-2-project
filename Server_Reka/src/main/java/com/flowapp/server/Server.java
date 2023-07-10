package com.flowapp.server;


import com.flowapp.controller.Controller;
import com.flowapp.logging.Log;
import com.flowapp.populator.GenerateData;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


public class Server {
    // Socket
    private Socket connectionSocket;
    private ServerSocket serverSocket;
    private int clientCount = 0;

    private Controller controller;


    // Default Constructor
    public Server(){
        initializeDB();
        initializeServerSocket();
    }

    // Initializing the controller object
    private void initializeDB(){
        controller = new Controller();
       
        try {
            controller.connect();

            // Uncomment below to generate data for the application...
            //populateData();
        }
        catch (SQLException e) {
            Log.error("Error inside the initializeDB() -> " + e.getMessage());
        }
    }

    private void initializeServerSocket(){
        // Creating new instance of the ServerSocket, listening on port 8888
        try {
            serverSocket = new ServerSocket(8888);
            Log.info("Server started on port 8888...");

            // Server waiting for requests
            while(true){
                connectionSocket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(connectionSocket, controller);
                clientHandler.start();

                Log.info("New server thread started -> Client [" + (clientCount + 1) + "]\n\n");
                clientCount++;
            }
        }
        catch (EOFException e) {
            Log.error("Error inside the initializeServerSocket() -> Client has terminated connections with the server.");
            closeConnection();
        }
        catch (IOException e) {
            Log.error("Error inside the initializeServerSocket() -> " + e.getMessage());
        }
    }

    // Close off all the connections
    private void closeConnection() {
        try {
            connectionSocket.close();
        }
        catch (IOException e) {
            Log.error("Error inside the closeConnection() -> [connection close] " + e.getMessage());
        }

        try {
            controller.disconnect();
        }
        catch (SQLException ex) {
            Log.error("Error inside the handler() -> [controller] " + ex.getMessage());
        }
    }

    // Using to populate data for the applications
    private void populateData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new GenerateData();
            }
        });

        thread.start();
    }
}
