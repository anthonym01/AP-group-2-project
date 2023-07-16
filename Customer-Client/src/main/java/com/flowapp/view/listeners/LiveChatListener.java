package com.flowapp.view.listeners;

import com.flowapp.model.Customer;
import com.flowapp.model.Employee;
import com.flowapp.model.TemporaryChat;


public interface LiveChatListener {
    void startChat(Employee employee, Customer customer);
    void updateChat(TemporaryChat chat);
    void endChat(TemporaryChat chat);
}
