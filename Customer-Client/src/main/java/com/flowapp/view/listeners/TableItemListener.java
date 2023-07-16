package com.flowapp.view.listeners;

import com.flowapp.model.Message;
import com.flowapp.model.Response;

public interface TableItemListener {
    void update(Message message);
    void delete(Message message);
    void addResponse(Message message, Response response);
}
