package com.flowapp.view.dashboard.representative;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.flowapp.controller.Controller;
import com.flowapp.driver.Utils;
import com.flowapp.model.Employee;
import com.flowapp.model.Message;
import com.flowapp.view.listeners.TableItemListener;


public class RepresentativeDashboardPanel extends JPanel {
    private Controller controller;

    private JPanel userDetailsPanel;
    private JLabel userDetailsLabel;

    // Components responsible for changing views
    private CardLayout cards;
    private JPanel views;

    // Custom components
    private EmpViewSubmissionsPanel viewSubmissionsPanel;

    // Listeners


    public RepresentativeDashboardPanel(Controller controller) {
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
        viewSubmissionsPanel.setEmployee(employee);

        // Whenever a user logs in, then update the user details label to sdisplay the user anem at the bottom of the screen...
        userDetailsLabel.setText("Representative: " + employee.getFirstName() + " " + employee.getLastName());
    }

    public void setMessages(List<Message> messages, String category){
        viewSubmissionsPanel.setMessages(messages, category);
    }

    public void setTableItemListener(TableItemListener tableItemListener) {
        viewSubmissionsPanel.setTableItemListener(tableItemListener);
    }

    public void setTechnicians(List<Employee> employees){
        viewSubmissionsPanel.setTechnicians(employees);
    }

    // Setup window properties
    private void setupWindowProperties(){
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);
    }
        
    // Initialize components
    private void initComponents(){
        cards = new CardLayout();
        views = new JPanel(cards);

        // Panels
        // Passing the controller object to each panel... this will facilitate the saving of data when necessary
        viewSubmissionsPanel = new EmpViewSubmissionsPanel(controller);

        userDetailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userDetailsPanel.setBackground(Color.WHITE);

        // Labels
        userDetailsLabel = new JLabel("Representative: Developer", SwingConstants.RIGHT);
        userDetailsLabel.setFont(Utils.getFont(Font.BOLD, 15));
    }

    // Add components to panel
    private void addComponentsToPanel(){
        // Adding the user details to the bottom of the screen
        userDetailsPanel.add(userDetailsLabel);

        // Adding the components for the different views
        views.add("data-list", viewSubmissionsPanel); // Default View

        // Adding the view component to the main window
        add(views, BorderLayout.CENTER);
        add(userDetailsPanel, BorderLayout.SOUTH);
    }

    // Register listeners
    private void registerListeners(){
       
    }

    // These methods will be responsible for the switching of views depending on which menu item selected.
    // View Submissions
    public void showMessages(){
        cards.show(views, "data-list");
    }

    // Reselect the current selected row after the highlight vanished after calling -> model.fireTableDataChanged()
    public void reHighlightLastSelectedRow(){
        viewSubmissionsPanel.reHighlightLastSelectedRow();
    }
    
}
