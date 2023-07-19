package com.flowapp.view.dashboard.technician;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.flowapp.controller.Controller;
import com.flowapp.driver.Utils;
import com.flowapp.model.Employee;
import com.flowapp.model.Message;
import com.flowapp.view.dashboard.EmpDetailSidebarPanel;
import com.flowapp.view.dashboard.EmpMessageTablePanel;
import com.flowapp.view.listeners.DetailSidebarListener;
import com.flowapp.view.listeners.TableItemListener;


public class AssignedTaskPanel extends JPanel {
    private Controller controller;
    private Employee loggedInEmployee;
    private List<Message> messages;

    private JLabel basicInfoLabel, outstandingCountLabel, resolvedCountLabel;
    private JPanel northPanel, countLabelPanel;

    private EmpMessageTablePanel messageTablePanel;
    private EmpDetailSidebarPanel detailSidebarPanel;


    public AssignedTaskPanel(Controller controller) {
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
    public void setEmployee(Employee employee){
        loggedInEmployee = employee;
        detailSidebarPanel.setEmployee(employee);
        messageTablePanel.setEmployee(employee);

        updateAssignedTaskPanelInfo();
    }

    public void setTableItemListener(TableItemListener tableItemListener) {
        messageTablePanel.setTableItemListener(tableItemListener);
        detailSidebarPanel.setTableItemListener(tableItemListener);
    }

    public void setTechnicians(List<Employee> employees){
        messageTablePanel.setTechnicians(employees);
    }

    // Setup window properties
    private void setupWindowProperties(){
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);
    }
        
    // Initialize components
    private void initComponents(){
        // Labels
        basicInfoLabel = new JLabel("Assigned Tasks (0)", SwingConstants.CENTER);
        outstandingCountLabel = new JLabel("Oustanding (0)", SwingConstants.CENTER);
        resolvedCountLabel = new JLabel("Resolved (0)", SwingConstants.CENTER);

        // Update the font
        basicInfoLabel.setFont(Utils.getFont(Font.BOLD, 20));

        // Panels
        northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        countLabelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        countLabelPanel.setBackground(Color.WHITE);
        countLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        messageTablePanel = new EmpMessageTablePanel("technician");
        detailSidebarPanel = new EmpDetailSidebarPanel(controller);
    }

    // Add components to panel
    private void addComponentsToPanel(){
        countLabelPanel.add(resolvedCountLabel);
        countLabelPanel.add(outstandingCountLabel);

        // Adding components to north panel
        northPanel.add(basicInfoLabel, BorderLayout.CENTER);
        northPanel.add(countLabelPanel, BorderLayout.EAST);
        

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

            @Override
            public void clearSidebar() {
                detailSidebarPanel.clearSidebar();
            }
        };

        messageTablePanel.setDetailSidebarListener(detailSidebarListener);
        detailSidebarPanel.setDetailSidebarListener(detailSidebarListener);
    }
    
    private void updateAssignedTaskPanelInfo(){
        messages = new ArrayList<>(loggedInEmployee.getAssignedMessages());

        messageTablePanel.setMessages(messages);

        basicInfoLabel.setText("Assigned Tasks (" + messages.size() + ")");

        // Counting the number of outstanding and resolved queries separately to update the respective labels
        int outstandingCounter = 0, resolvedCounter = 0;
        for (Message message : messages) {
            if(message.isResolved()) resolvedCounter++;
            else outstandingCounter++;
        }

        resolvedCountLabel.setText("Resolved (" + resolvedCounter + ")");
        outstandingCountLabel.setText("Oustanding (" + outstandingCounter + ")");
    }

    // Reselect the current selected row after the highlight vanished after calling -> model.fireTableDataChanged()
    public void reHighlightLastSelectedRow(){
        messageTablePanel.reHighlightLastSelectedRow();
    }
}
