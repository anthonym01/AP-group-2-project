package com.flowapp.view.listeners;

import com.flowapp.model.Message;

public interface DetailSidebarListener {
    void readOnly(Message message);
    void allowEdit(Message message);
    void save(Message message);
}
