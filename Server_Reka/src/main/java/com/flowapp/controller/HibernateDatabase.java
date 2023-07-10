package com.flowapp.controller;


import com.flowapp.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.flowapp.factories.SessionFactoryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;


public class HibernateDatabase {
    private static SessionFactory factory;

    // Connect to the database using hibernate
    public static void connect() {
        if (factory != null) return;

        factory = SessionFactoryBuilder.getSessionFactory();
    }

    // Disconnecting to the database using hibernate
    public static void disconnect() {
        if (factory == null)
            return;

        factory.close();
        SessionFactoryBuilder.closeSessionFactory();

        factory = null;
    }

    /* Hibernate CRUD Operations */
    /* Customer */
    // Add a customer
    public static void create(Customer customer) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.save(customer);
        transaction.commit();

        session.close();
    }

    // Retrieve a list of all customers
    @SuppressWarnings("unchecked")
    public static List<Customer> getCustomers() {
        List<Customer> customerList = new ArrayList<>();

        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        customerList = (List<Customer>) session.createQuery("FROM Customer").getResultList();
        transaction.commit();

        session.close();

        return customerList;
    }

    // Find a customer
    public static Customer find(Customer cus) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = (Customer) session.get(Customer.class, cus.getIDNumber());
        transaction.commit();

        session.close();

        return customer;
    }

    // Update a customer
    public static void update(Customer cus) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = (Customer) session.get(Customer.class, cus.getIDNumber());

        // Copying the cus data to the retrieved customer
        customer.update(cus);

        session.update(customer);
        transaction.commit();

        session.close();
    }

    // Delete a customer
    public static void delete(Customer cus) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = (Customer) session.get(Customer.class, cus.getIDNumber());

        session.delete(customer);
        transaction.commit();

        session.close();
    }


    /* Employee */
    // Add a employee
    public static void create(Employee employee) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.save(employee);
        transaction.commit();

        session.close();
    }

    // Retrieve a list of all employee
    @SuppressWarnings("unchecked")
    public static List<Employee> getEmployees() {
        List<Employee> employeeList = new ArrayList<>();

        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        employeeList = (List<Employee>) session.createQuery("FROM Employee").getResultList();
        transaction.commit();

        session.close();

        return employeeList;
    }

    @SuppressWarnings("unchecked")
    public static List<Employee> getAvailableTechnichians() {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        List<Employee> employeeList = (List<Employee>) session.createQuery("FROM Employee WHERE role='Technichian' AND available_for_chat is TRUE").getResultList();
        transaction.commit();

        session.close();

        return employeeList;
    }

    @SuppressWarnings("unchecked")
    public static List<Employee> getTechnichians() {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        List<Employee> employeeList = (List<Employee>) session.createQuery("FROM Employee WHERE role='Advisor'").getResultList();
        transaction.commit();

        session.close();

        return employeeList;
    }

    // Find a employee
    public static Employee find(Employee emp) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Employee employee = (Employee) session.get(Employee.class, emp.getIDNumber());
        transaction.commit();

        session.close();

        return employee;
    }

    // Update a employee
    public static void update(Employee emp) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Employee employee = (Employee) session.get(Employee.class, emp.getIDNumber());

        // Copying the emp data to the retrieved employee
        employee.update(emp);

        session.update(employee);
        transaction.commit();

        session.close();
    }

    // Delete a employee
    public static void delete(Employee emp) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Employee employee = (Employee) session.get(Employee.class, emp.getIDNumber());

        session.delete(employee);
        transaction.commit();

        session.close();
    }


    /* Message */
    // Add a message
    public static void create(Message message) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.save(message);
        transaction.commit();

        session.close();
    }

    // Retrieve a list of all messages
    @SuppressWarnings("unchecked")
    public static List<Message> getMessages() {
        List<Message> messageList = new ArrayList<>();

        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        messageList = (List<Message>) session.createQuery("FROM Message").getResultList();
        transaction.commit();

        session.close();

        return messageList;
    }

    @SuppressWarnings("unchecked")
    public static List<Message> getMessagesByCategory(String category) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // HQL example - Get SessionChats by category
        Query query = session.createQuery("FROM Message WHERE category= :category");
        query.setParameter("category", category);

        List<Message> messageList = (List<Message>) query.getResultList();
        transaction.commit();

        session.close();

        return messageList;
    }

    // Find a message
    public static Message find(Message mes) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Message message = (Message) session.get(Message.class, mes.getMessageID());
        transaction.commit();

        session.close();

        return message;
    }

    // Update a message
    public static void update(Message mes) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Message message = (Message) session.get(Message.class, mes.getMessageID());

        // Copying the mes data to the retrieved message
        message.update(mes);

        session.update(message);
        transaction.commit();

        session.close();
    }

    // Delete a message
    public static void delete(Message mes) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Message message = (Message) session.get(Message.class, mes.getMessageID());

        session.delete(message);
        transaction.commit();

        session.close();
    }


    /* Response */
    // Add a response
    public static void create(Response response) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.save(response);
        transaction.commit();

        session.close();
    }

    // Retrieve a list of all responses
    @SuppressWarnings("unchecked")
    public static List<Response> getResponses() {
        List<Response> responseList = new ArrayList<>();

        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        responseList = (List<Response>) session.createQuery("FROM Response").getResultList();
        transaction.commit();

        session.close();

        return responseList;
    }

    // Find a response
    public static Response find(Response mes) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Response response = (Response) session.get(Response.class, mes.getResponseID());
        transaction.commit();

        session.close();

        return response;
    }

    // Update a response
    public static void update(Response mes) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Response response = (Response) session.get(Response.class, mes.getResponseID());

        // Copying the mes data to the retrieved response
        response.update(mes);

        session.update(response);
        transaction.commit();

        session.close();
    }

    // Delete a response
    public static void delete(Response res) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Response response = (Response) session.get(Response.class, res.getResponseID());

        session.delete(response);
        transaction.commit();

        session.close();
    }


    /* Temporary Chat */
    // Add a temporary chat
    public static void create(TemporaryChat chat) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.save(chat);
        transaction.commit();

        session.close();
    }

    // Retrieve a list of all temporary chats
    @SuppressWarnings("unchecked")
    public static List<TemporaryChat> getChats(Employee employee) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // HQL example - Get TemporaryChat by employee_id
        Query query = session.createQuery("FROM SessionChat WHERE employee_id= :employee_id");
        query.setParameter("employee_id", employee.getIDNumber());

        List<TemporaryChat> chatList = (List<TemporaryChat>) query.getResultList();
        transaction.commit();

        session.close();

        return chatList;
    }

    // Find a chat
    public static TemporaryChat find(TemporaryChat chat) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        TemporaryChat temporaryChat = (TemporaryChat) session.get(TemporaryChat.class, chat.getIdNumber());
        transaction.commit();

        session.close();

        return temporaryChat;
    }

    public static TemporaryChat findByRepresentative(TemporaryChat chat) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // HQL example - Get TemporaryChat by employee_id & customer_id
        Query query = session.createQuery("FROM TemporaryChat WHERE employee_id= :employee_id AND customer_id= :customer_id ");
        query.setParameter("employee_id", chat.getEmployee());
        query.setParameter("customer_id", chat.getCustomer());

        TemporaryChat temporaryChat = (TemporaryChat) query.uniqueResult();
        transaction.commit();

        session.close();

        return temporaryChat;
    }

    // Update a session chat
    public static void update(TemporaryChat chat) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        TemporaryChat temporaryChat = (TemporaryChat) session.get(TemporaryChat.class, chat.getIdNumber());

        // Copying the chat data to the retrieved session chat
        temporaryChat.update(chat);

        session.update(temporaryChat);
        transaction.commit();

        session.close();
    }

    // Delete a chat
    public static void delete(TemporaryChat chat) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        TemporaryChat temporaryChat = (TemporaryChat) session.get(TemporaryChat.class, chat.getIdNumber());

        session.delete(temporaryChat);
        transaction.commit();

        session.close();
    }
}


