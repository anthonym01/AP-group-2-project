package com.flowapp.controller;

import com.flowapp.logging.Log;
import com.flowapp.model.*;
import org.hibernate.MappingException;
import org.hibernate.PersistentObjectException;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private volatile MySQLDatabase mysqlDB;

    public Controller() {
        mysqlDB = new MySQLDatabase();
    }

    // To open the database connection when the application starts
    public void connect() throws SQLException {
        HibernateDatabase.connect();
        mysqlDB.connect();
    }

    // To close the connection when the application has been terminated
    public void disconnect() throws SQLException {
        HibernateDatabase.disconnect();
        mysqlDB.disconnect();
    }

    /* Customer C.R.U.D Operation */
    // Adding a new customer
    public void create(Customer customer) {
        try {
            HibernateDatabase.create(customer);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the create() [customer] -> " + e.getMessage());
        }
    }

    // Retrieve a list of all customers
    public List<Customer> getCustomers() {
        try {
            return HibernateDatabase.getCustomers();
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the getCustomers() -> " + e.getMessage());
        }

        return new ArrayList<>();
    }

    // Find a customer
    public Customer find(Customer customer) {
        try {
            return HibernateDatabase.find(customer);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the find() [customer] -> " + e.getMessage());
        }

        return null;
    }

    // Update a customer
    public void update(Customer customer) {
        try {
            HibernateDatabase.update(customer);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the update() [customer] -> " + e.getMessage());
        }
    }

    // Delete a customer
    public void delete(Customer customer) {
        try {
            HibernateDatabase.delete(customer);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the delete() [customer] -> " + e.getMessage());
        }
    }

    /* Employee C.R.U.D Operation */
    // Adding a new employee
    public void create(Employee employee) {
        try {
            HibernateDatabase.create(employee);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the create() [employee] -> " + e.getMessage());
        }
    }

    // Retrieve a list of all employee
    public List<Employee> getEmployees() {
        try {
            return HibernateDatabase.getEmployees();
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the getEmployees() -> " + e.getMessage());
        }

        return new ArrayList<>();
    }

    // Retrieve a list of all employee [technichians]
    public List<Employee> getTechnichians() {
        try {
            return HibernateDatabase.getTechnichians();
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the getTechnichians() -> " + e.getMessage());
        }

        return new ArrayList<>();
    }

    public List<Employee> getAvailableTechnichians() {
        try {
            return HibernateDatabase.getAvailableTechnichians();
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the getAvailableTechnichians() -> " + e.getMessage());
        }

        return new ArrayList<>();
    }

    // Find a employee
    public Employee find(Employee employee) {
        try {
            return HibernateDatabase.find(employee);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the find() [employee] -> " + e.getMessage());
        }

        return null;
    }

    // Update a employee
    public void update(Employee employee) {
        try {
            HibernateDatabase.update(employee);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the update() [employee] -> " + e.getMessage());
        }
    }

    // Delete a employee
    public void delete(Employee employee) {
        try {
            HibernateDatabase.delete(employee);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the delete() [employee] -> " + e.getMessage());
        }
    }

    /* Message C.R.U.D Operation */
    // Adding a new message
    public void create(Message message) {
        try {
            HibernateDatabase.create(message);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the create() [message] -> " + e.getMessage());
        }
    }

    // Retrieve a list of all messages
    public List<Message> getMessages() {
        try {
            return HibernateDatabase.getMessages();
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the getMessages() -> " + e.getMessage());
        }

        return new ArrayList<>();
    }

    public List<Message> getMessagesByCategory(String category) {
        try {
            return HibernateDatabase.getMessagesByCategory(category);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the getMessagesByCategory() -> " + e.getMessage());
        }

        return new ArrayList<>();
    }

    // Find a message
    public Message find(Message message) {
        try {
            return HibernateDatabase.find(message);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the find() [message] -> " + e.getMessage());
        }

        return null;
    }

    // Update a message
    public void update(Message message) {
        try {
            HibernateDatabase.update(message);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the update() [message] -> " + e.getMessage());
        }
    }

    // Delete a message by customer
    public void deleteByCustomer(Message message) {
        // HibernateDatabase.delete(message);

        try {
            mysqlDB.deleteMessageByCustomer(message);
        } catch (SQLException e) {
            Log.error("Error inside the deleteByCustomer() [messages] -> " + e.getMessage());
        }
    }

    // Delete a message by employee [representative]
    public void deleteByRepresentative(Message message) {
        try {
            HibernateDatabase.delete(message);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the deleteByRepresentative() [message] -> " + e.getMessage());
        }
    }

    /* Response C.R.U.D Operation */
    // Adding a new response
    public void create(Response response) {
        try {
            HibernateDatabase.create(response);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the create() [response] -> " + e.getMessage());
        }
    }

    // Retrieve a list of all responses
    public List<Response> getResponses() {
        try {
            return HibernateDatabase.getResponses();
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the getResponses() -> " + e.getMessage());
        }

        return new ArrayList<>();
    }

    // Find a response
    public Response find(Response response) {
        try {
            return HibernateDatabase.find(response);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the find() [response] -> " + e.getMessage());
        }

        return null;
    }

    // Update a response
    public void update(Response response) {
        try {
            HibernateDatabase.update(response);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the update() [response] -> " + e.getMessage());
        }
    }

    // Delete a response
    public void delete(Response response) {
        try {
            HibernateDatabase.delete(response);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the delete() [response] -> " + e.getMessage());
        }
    }

    /* Temporary Chat C.R.U.D Operation */
    // Adding a new temporary chat
    public void create(TemporaryChat chat) {
        try {
            HibernateDatabase.create(chat);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the create() [chat] -> " + e.getMessage());
        }
    }

    // Retrieve a list of all chats
    public List<TemporaryChat> getChats(Employee employee) {
        try {
            return HibernateDatabase.getChats(employee);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the getChats() -> " + e.getMessage());
        }

        return new ArrayList<>();
    }

    // Find a temporary chat
    public TemporaryChat find(TemporaryChat chat) {
        try {
            return HibernateDatabase.find(chat);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the find() [chat] -> " + e.getMessage());
        }

        return null;
    }

    public TemporaryChat findByRepresentative(TemporaryChat chat) {
        try {
            return HibernateDatabase.findByRepresentative(chat);
        } catch (IllegalArgumentException | PersistenceException e) {
            Log.error("Error inside the findByRepresentative() [chat] -> " + e.getMessage());
        }

        return null;
    }

    // Update a temporary chat
    public void update(TemporaryChat chat) {
        try {
            HibernateDatabase.update(chat);
        } catch (IllegalArgumentException | MappingException | RollbackException | PersistentObjectException e) {
            Log.error("Error inside the update() [chat] -> " + e.getMessage());
        }
    }

    // Delete a temporary chat
    public void delete(TemporaryChat chat) {
        try {
            HibernateDatabase.delete(chat);
        } catch (IllegalArgumentException | MappingException | RollbackException | PersistentObjectException e) {
            Log.error("Error inside the delete() [chat] -> " + e.getMessage());
        }
    }
}
