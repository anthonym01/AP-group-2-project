package com.flowapp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Customer")
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User implements Serializable {
    @Transient
    private static final long serialVersionUID = 1L;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "telephone", length = 255)
    private String telephone;

    // One customer can submit many messages -> able to create and delete messages
    @OneToMany(fetch = FetchType.EAGER) //cascade = CascadeType.ALL,
    @JoinColumn(name = "customer_id")
    @OrderBy("dateCreated DESC") // order by date_created
    
    //Using a set to remove duplicates from the results set return form hibernate
    private Set<Message> messagesSubmitted;

    // Default Constructor
    public Customer() {
        super();

        email = "";
        telephone = "";

        messagesSubmitted = new HashSet<>(); // ini a new set
    }

    // Primary Constructor
    public Customer(String idNumber, String firstName, String lastName, String password, boolean isStaff, String email,
                   String telephone) {
        super(idNumber, firstName, lastName, password, isStaff);

        this.email = email;
        this.telephone = telephone;

        messagesSubmitted = new HashSet<>();
    }

    // Copy Constructor
    public Customer(Customer customer) {
        super(customer);

        email = customer.email;
        telephone = customer.telephone;
        messagesSubmitted = customer.messagesSubmitted;
    }

    // Copy method
    public void update(Customer customer) {
        this.idNumber = customer.idNumber;
        this.firstName = customer.firstName;
        this.lastName = customer.lastName;
        this.password = customer.password;
        this.isStaff = customer.isStaff;

        this.email = customer.email;
        this.telephone = customer.telephone;
        this.messagesSubmitted = customer.messagesSubmitted;
    }

    // Getters & Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<Message> getMessagesSubmitted() {
        return messagesSubmitted;
    }

    public void setMessagesSubmitted(HashSet<Message> messagesSubmitted) {
        this.messagesSubmitted = messagesSubmitted;
    }

    // To string method
    @Override
    public String toString() {
        return "customer -> [ " + super.toString() + " | " + email + " | " + telephone + " | messages submitted: " + messagesSubmitted.size() + " ]";
    }

    /* Utility Methods */
    // Add submitted message to this customer
    public void addMessage(Message message){
        messagesSubmitted.add(message);
    }

    // Delete message from this customer
    public void deleteMessage(Message message){
        messagesSubmitted.remove(message);
    }
}

