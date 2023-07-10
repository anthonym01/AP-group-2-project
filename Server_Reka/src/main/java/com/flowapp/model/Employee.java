package com.flowapp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Employee")
@PrimaryKeyJoinColumn(name = "user_id")
public class Employee extends User implements Serializable {
    @Transient
    private static final long serialVersionUID = 1L;

    @Column(name = "role", length = 255)
    private String role;

    @Column(name = "available_for_chat")
    private Boolean availableForLiveChat;

    // One employee can be assigned to many messages -> able to create and delete
    // messages
    @OneToMany(fetch = FetchType.EAGER) // cascade = CascadeType.ALL,
    @JoinColumn(name = "employee_id")
    @OrderBy("dateCreated DESC") // order by date_created
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
