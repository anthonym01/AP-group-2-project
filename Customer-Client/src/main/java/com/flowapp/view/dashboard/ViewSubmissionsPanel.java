package com.flowapp.view.dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.flowapp.controller.Controller;
import com.flowapp.driver.Utils;
import com.flowapp.model.Customer;
import com.flowapp.model.Message;
import com.flowapp.view.listeners.DetailSidebarListener;
import com.flowapp.view.listeners.TableItemListener;


public class ViewSubmissionsPanel extends JPanel {
    private Controller controller;
    private Customer loggedInCustomer;

    private JLabel basicInfoLabel;
    private JPanel northPanel;

    private MessageTablePanel messageTablePanel;
    private DetailSidebarPanel detailSidebarPanel;


    public ViewSubmissionsPanel(Controller controller) {
        super();
        this.controller = controller;

        setupWindowProperties();

        initComponents();
        addComponentsToPanel();
        registerListeners();

        focusOnLoginPanel();
    }

    private void focusOnLoginPanel(){
        setFocusable(true);
        grabFocus();
    }

    // Setters
    public void setCustomer(Customer customer){
        loggedInCustomer = customer;
        detailSidebarPanel.setCustomer(customer);

        updatePanelInfo();
    }

    public void setTableItemListener(TableItemListener tableItemListener) {
        messageTablePanel.setTableItemListener(tableItemListener);
        detailSidebarPanel.setTableItemListener(tableItemListener);
    }

    // Setup window properties
    private void setupWindowProperties(){
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);
    }
        
    // Initialize components
    private void initComponents(){
        // Labels
        basicInfoLabel = new JLabel("Past Complaints (0)", SwingConstants.CENTER);

        // Update the font
        basicInfoLabel.setFont(Utils.getFont(Font.BOLD, 20));

        // Panels
        northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        messageTablePanel = new MessageTablePanel();
        detailSidebarPanel = new DetailSidebarPanel(controller);
    }

    // Add components to panel
    private void addComponentsToPanel(){
        // Adding components to north panel
        northPanel.add(basicInfoLabel, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(messageTablePanel, BorderLayout.CENTER);
        add(detailSidebarPanel, BorderLayout.WEST);
    }

    // Register listeners
    private void registerListeners(){
        DetailSidebarListener detailSidebarListener = new DetailSidebarListener() {
            @Override
            public void readOnly(Message message) {
                // VIEW ONLY
                detailSidebarPanel.updatePanel(message, "read-only");
            }

            @Override
            public void allowEdit(Message message) {
                // VIEW AND EDIT
                detailSidebarPanel.updatePanel(message, "edit");
            }

            @Override
            public void save(Message message) {
                messageTablePanel.updateMessage(message);
            }
        };

        messageTablePanel.setDetailSidebarListener(detailSidebarListener);
        detailSidebarPanel.setDetailSidebarListener(detailSidebarListener);
    }
    
    private void updatePanelInfo(){
        basicInfoLabel.setText("Past Complaints (" + loggedInCustomer.getMessagesSubmitted().size() + ")");
        messageTablePanel.setMessages(new ArrayList<>(loggedInCustomer.getMessagesSubmitted()));
    }

    // Reselect the current selected row after the highlight vanished after calling -> model.fireTableDataChanged()
    public void reHighlightLastSelectedRow(){
        messageTablePanel.reHighlightLastSelectedRow();
    }
}
