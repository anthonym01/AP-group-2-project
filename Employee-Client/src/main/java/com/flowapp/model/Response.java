package com.flowapp.model;

import java.io.Serializable;
import java.util.Date;


public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    private int responseID;
    private String content;
    private Date responseDate;
    private Message message;
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

