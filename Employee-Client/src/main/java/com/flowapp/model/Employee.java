package com.flowapp.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Employee extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String role;
    private Boolean availableForLiveChat;
    private Set<Message> assignedMessages;

    // Default Constructor
    public Employee() {
        super();

        role = "";
        assignedMessages = new HashSet<>();
        availableForLiveChat = false;
    }

    // Primary Constructor
    public Employee(String idNumber, String firstName, String lastName, String password, boolean isStaff, String role) {
        super(idNumber, firstName, lastName, password, isStaff);

        this.role = role;
        assignedMessages = new HashSet<>();
        availableForLiveChat = false;
    }

    // Copy Constructor
    public Employee(Employee employee) {
        super(employee);

        role = employee.role;
        assignedMessages = employee.assignedMessages;
        availableForLiveChat = employee.availableForLiveChat;
    }

    // Copy method
    public void update(Employee employee) {
        this.idNumber = employee.idNumber;
        this.firstName = employee.firstName;
        this.lastName = employee.lastName;
        this.password = employee.password;
        this.isStaff = employee.isStaff;

        this.role = employee.role;
        this.assignedMessages = employee.assignedMessages;
        this.availableForLiveChat = employee.availableForLiveChat;
    }

    // Getters & Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAvailableForLiveChat(boolean availableForLiveChat) {
        this.availableForLiveChat = availableForLiveChat;
    }

    public boolean isAvailableForLiveChat() {
        return availableForLiveChat;
    }

    public Set<Message> getAssignedMessages() {
        return assignedMessages;
    }

    public void setAssignedMessages(HashSet<Message> assignedMessages) {
        this.assignedMessages = assignedMessages;
    }

    // To string method
    @Override
    public String toString() {
        if(role.equalsIgnoreCase("technician")) return getFirstName() + " " + getLastName();

        return "Staff -> [ " + super.toString() + " | " + role + " | available for chat: " + availableForLiveChat + " | messages assigned: " + assignedMessages.size() + " ]";
    }

    /* Utility Methods */
    // Assign message to this staff member
    public void assignMessage(Message message){
        assignedMessages.add(message);
    }

    // Unassign message from this staff member
    public void unAssignMessage(Message message){
        assignedMessages.remove(message);
    }
}
