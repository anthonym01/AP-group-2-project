package com.flowapp.controller;


import com.flowapp.logging.Log;
import com.flowapp.model.Message;

import java.sql.*;


public class MySQLDatabase {
    private Connection connection;
    private PreparedStatement preparedStatement;


    // To open the database connection when the application starts
    public void connect() throws SQLException {
        if(connection != null) return;

        String url = "jdbc:mysql://localhost:3306/flowappdb";
        connection = DriverManager.getConnection(url, "root", "");

        Log.info("Connected to database successfully! [MySQL]");
    }

    // To close the connection when the application has been terminated
    public void disconnect() throws SQLException {
        if(connection == null) return;

        connection.close();

        Log.info("Database disconnected successfully! [MySQL]");
    }


    /* Message C.R.U.D Operation */
    // Create
    public void addMessageByCustomer(Message message) throws SQLException {
        // Creating the user object first
        String insertSQL = "INSERT INTO message (message_type, category, details, customer_id) values (?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(insertSQL);

        // Updating the unknown values where the question marks are...
        preparedStatement.setString(1, message.getMessageType());
        preparedStatement.setString(2, message.getCategory());
        preparedStatement.setString(3, message.getDetails());
        preparedStatement.setString(4, message.getCustomer().getIDNumber());

        // Execute the statement
        preparedStatement.executeUpdate();

        // Close off the statement when finish executing the command
        preparedStatement.close();

        Log.info("Message added from customer successfully!");
    }

    public void addMessageByCustomerWithEmployee(Message message) throws SQLException {
        // Creating the user object first
        String insertSQL = "INSERT INTO message (message_type, category, details, customer_id, employee_id) values (?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(insertSQL);

        // Updating the unknown values where the question marks are...
        preparedStatement.setString(1, message.getMessageType());
        preparedStatement.setString(2, message.getCategory());
        preparedStatement.setString(3, message.getDetails());
        preparedStatement.setString(4, message.getEmployee().getIDNumber());
        preparedStatement.setString(5, message.getCustomer().getIDNumber());

        // Execute the statement
        preparedStatement.executeUpdate();

        // Close off the statement when finish executing the command
        preparedStatement.close();

        Log.info("Message added from customer successfully!");
    }

    // Update
    public void updateMessageByCustomer(Message message) throws SQLException {
        // Updating the user object first
        String updateSQL = "UPDATE message SET message_type=?, category=?, details=? WHERE message_id=? AND customer_id=?";
        preparedStatement = connection.prepareStatement(updateSQL);

        // Updating the unknown values where the question marks are...
        preparedStatement.setString(1, message.getMessageType());
        preparedStatement.setString(2, message.getCategory());
        preparedStatement.setString(3, message.getDetails());
        preparedStatement.setInt(4, message.getMessageID());
        preparedStatement.setString(5, message.getCustomer().getIDNumber());

        // Execute the statement
        preparedStatement.executeUpdate();

        // Close off the statement when finish executing the command
        preparedStatement.close();

        Log.info("Message updated by customer successfully!");
    }

    // Delete
    public void deleteMessageByCustomer(Message message) throws SQLException {
        String deleteSQL = "DELETE FROM message WHERE message_id=? AND customer_id=?";
        preparedStatement = connection.prepareStatement(deleteSQL);

        // Deleting the unknown values where the question marks are...
        preparedStatement.setInt(1, message.getMessageID());
        preparedStatement.setString(2, message.getCustomer().getIDNumber());

        // Execute the statement
        preparedStatement.executeUpdate();

        // Close off the statement when finish executing the command
        preparedStatement.close();

        Log.info("Message deleted by customer successfully!");
    }

    public void deleteMessageByEmployee(Message message) throws SQLException {
        String deleteSQL = "DELETE FROM message WHERE message_id=?";
        preparedStatement = connection.prepareStatement(deleteSQL);

        // Deleting the unknown values where the question marks are...
        preparedStatement.setInt(1, message.getMessageID());

        // Execute the statement
        preparedStatement.executeUpdate();

        // Close off the statement when finish executing the command
        preparedStatement.close();

        Log.info("Message deleted by employee successfully!");
    }
}
