package com.flowapp.view.listeners;


public interface AuthListener {
    boolean login(String userID, String password);
    void logout();
}
