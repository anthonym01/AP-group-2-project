package com.flowapp.view.listeners;

import com.flowapp.model.Customer;
import com.flowapp.model.Employee;
import com.flowapp.model.TemporaryChat;


public interface LiveChatListener {
    void updateAvailability(Employee employee);
    void updateChat(TemporaryChat chat);
    void startChat(Employee employee, Customer customer);
    void endChat();
}
