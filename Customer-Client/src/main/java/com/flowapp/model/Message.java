package com.flowapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int messageID;
    private String messageType;
    private String category;
    private String details;
    private Date dateCreated;
    private Boolean resolved;
    private Customer customer;
    private Employee employee;
    private Set<Response> responses;

    // Default Constructor
    public Message() {
        messageID = 0;
        messageType = "";
        category = "";
        details = "";
        dateCreated = new Date();
        resolved = false;
        customer = new Customer();
        employee = new Employee();

        responses = new HashSet<>();
    }

    // Primary Constructor
    public Message(int messageID, String messageType, String category, String details, Date dateCreated, boolean resolved, Customer customer, Employee employee) {
        this.messageID = messageID;
        this.messageType = messageType;
        this.category = category;
        this.details = details;
        this.dateCreated = dateCreated;
        this.resolved = resolved;
        this.customer = customer;
        this.employee = employee;

        responses = new HashSet<>();
    }

    // Copy Constructor
    public Message(Message message) {
        this.messageID = message.messageID;
        this.messageType = message.messageType;
        this.category = message.category;
        this.details = message.details;
        this.dateCreated = message.dateCreated;
        this.resolved = message.resolved;
        this.customer = message.customer;
        this.employee = message.employee;
        this.responses = message.responses;
    }

    // Copy method
    public void update(Message message) {
        this.messageID = message.messageID;
        this.messageType = message.messageType;
        this.category = message.category;
        this.details = message.details;
        this.dateCreated = message.dateCreated;
        this.resolved = message.resolved;
        this.customer = message.customer;
        this.employee = message.employee;
        // this.responses = message.responses;
    }

    // Getters & Setters
    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Response> getResponses() {
        return responses;
    }

    public void setResponses(HashSet<Response> responses) {
        this.responses = responses;
    }

    // Additional
    public void addResponse(Response response) {
        responses.add(response);
    }

    public void removeResponse(Response response) {
        responses.remove(response);
    }

    // To string method
    @Override
    public String toString() {
        return "Message{" +
                "messageID=" + messageID +
                ", messageType='" + messageType + '\'' +
                ", category='" + category + '\'' +
                ", details='" + details + '\'' +
                ", dateCreated=" + dateCreated +
                ", resolved=" + resolved +
                ", customer=" + customer +
                ", employee=" + employee +
                ", responseCount=" + (responses != null ? responses.size() : 0) +
                "}";
    }
}

