package com.flowapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name = "Response")
public class Response implements Serializable {
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "response_id", nullable = false)
    private int responseID;

    @Column(name = "content", nullable = false, length = 2000)
    private String content;

    @Column(name = "response_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date responseDate;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "message_id")
    private Message message;

    @Column(name = "user_id")
    private String userID;


    // Default Constructor
    public Response() {
        this.responseID = 0;
        this.content = "";
        this.responseDate = new Date();
        this.message = null;
        this.userID = "";
    }

    // Primary Constructor
    public Response(int responseID, String content, Date responseDate, String userID) {
        this.responseID = responseID;
        this.content = content;
        this.responseDate = responseDate;
        this.userID = userID;
        this.message = null;
    }

    // Copy Constructor
    public Response(Response response) {
        this.responseID = response.responseID;
        this.content = response.content;
        this.responseDate = response.responseDate;
        this.message = response.message;
        this.userID = response.userID;
    }

    public void update(Response response) {
        this.responseID = response.responseID;
        this.content = response.content;
        this.responseDate = response.responseDate;
        this.message = response.message;
        this.userID = response.userID;
    }

    // Getters & Setters
    public int getResponseID() {
        return responseID;
    }

    public void setResponseID(int responseID) {
        this.responseID = responseID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    // To string method
    @Override
    public String toString() {
        return "Response [responseID=" + responseID + ", content=" + content + ", responseDate=" + responseDate + ", userID=" + userID + "]";
    }
}

