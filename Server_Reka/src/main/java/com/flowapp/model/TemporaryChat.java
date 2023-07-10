
package com.flowapp.model;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "TemporaryChat")
public class TemporaryChat implements Serializable {
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "chat_id", nullable = false)
    private String idNumber;

    @Column(name = "customer_message")
    private String customerMessage;

    @Column(name = "employee_response")
    private String employeeResponse;

    @Column(name = "employee_id")
    private String employeeID;

    @Column(name = "customer_id")
    private String customerID;

    // Default Constructor
    public TemporaryChat() {
        this.idNumber = "";
        this.customerMessage = "";
        this.employeeResponse = "";
        this.employeeID = "";
        this.customerID = "";
    }

    // Primary Constructor
    public TemporaryChat(String idNumber) {
        this.idNumber = idNumber;
    }

    // Copy Constructor
    public TemporaryChat(TemporaryChat chat) {
        this.idNumber = chat.idNumber;
        this.customerMessage = chat.customerMessage;
        this.employeeResponse = chat.employeeResponse;
        this.employeeID = chat.employeeID;
        this.customerID = chat.customerID;
    }

    // Copy method
    public void update(TemporaryChat chat) {
        this.idNumber = chat.idNumber;
        this.customerMessage = chat.customerMessage;
        this.employeeResponse = chat.employeeResponse;
        this.employeeID = chat.employeeID;
        this.customerID = chat.customerID;
    }

    // Getters & Setters
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }

    public String getEmployeeResponse() {
        return employeeResponse;
    }

    public void setEmployeeResponse(String employeeResponse) {
        this.employeeResponse = employeeResponse;
    }

    public String getEmployee() {
        return employeeID;
    }

    public void setEmployee(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getCustomer() {
        return customerID;
    }

    public void setCustomer(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "TemporaryChat{" +
                "idNumber='" + idNumber + '\'' +
                ", customerMessage='" + customerMessage + '\'' +
                ", employeeResponse='" + employeeResponse + '\'' +
                ", employeeID=" + employeeID +
                ", customerID=" + customerID +
                '}';
    }
}
