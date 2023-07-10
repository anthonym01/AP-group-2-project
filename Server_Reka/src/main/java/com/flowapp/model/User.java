package com.flowapp.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "User")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id", nullable = false)
    protected String idNumber;

    @Column(name = "firstname", length = 255) // nullable = false
    protected String firstName;

    @Column(name = "lastname", length = 255)
    protected String lastName;

    @Column(name = "password", length = 255)
    protected String password;

    @Column(name = "is_staff")
    protected Boolean isStaff;

    // Default Constructor
    public User() {
        idNumber = "";
        firstName = "";
        lastName = "";
        password = "";
        isStaff = false;
    }

    // Primary Constructor
    public User(String idNumber, String firstName, String lastName, String password, boolean isStaff) {
        this.idNumber = idNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isStaff = isStaff;
    }

    // Copy Constructor
    public User(User user) {
        idNumber = user.idNumber;
        firstName = user.firstName;
        lastName = user.lastName;
        password = user.password;
        isStaff = user.isStaff;
    }

    // Getters & Setters
    public String getIDNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }

    // To string method
    @Override
    public String toString() {
        return idNumber + " | " + firstName + " | " + lastName + " | " + password + " | " + isStaff;
    }
}
