package com.flowapp.view.dashboard;

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
import com.flowapp.model.Customer;
import com.flowapp.model.Employee;
import com.flowapp.model.TemporaryChat;
import com.flowapp.view.listeners.LiveChatListener;
import com.flowapp.view.listeners.TableItemListener;



public class DashboardPanel extends JPanel {
    private Controller controller;

    private JPanel userDetailsPanel;
    private JLabel userDetailsLabel;

    // Components responsible for changing views
    private CardLayout cards;
    private JPanel views;

    // Custom components
    private MakeQueryPanel makeQueryPanel;
    private RequestServicePanel requestServicePanel;
    private LodgeComplaintPanel lodgeComplaintPanel;
    private ViewSubmissionsPanel viewSubmissionsPanel;
    private LiveChatPanel liveChatPanel;

    // Listeners


    public DashboardPanel(Controller controller) {
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
        viewSubmissionsPanel.setCustomer(customer);
        makeQueryPanel.setCustomer(customer);
        requestServicePanel.setCustomer(customer);
        lodgeComplaintPanel.setCustomer(customer);
        liveChatPanel.setCustomer(customer);

        // Whenever a user logs in, then update the user details label to sdisplay the user anem at the bottom of the screen...
        userDetailsLabel.setText("Customer: " + customer.getFirstName() + " " + customer.getLastName());
    }

    public void setTechnicians(List<Employee> employees){
        liveChatPanel.setTechnicians(employees);
    }

    public void setTableItemListener(TableItemListener tableItemListener) {
        viewSubmissionsPanel.setTableItemListener(tableItemListener);
    }

    public void setLiveChatListener(LiveChatListener liveChatListener){
        liveChatPanel.setLiveChatListener(liveChatListener);
    }

    public void updateChat(TemporaryChat chat){
        liveChatPanel.updateChat(chat);
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
        makeQueryPanel = new MakeQueryPanel(controller);
        requestServicePanel = new RequestServicePanel(controller);
        lodgeComplaintPanel = new LodgeComplaintPanel(controller);
        viewSubmissionsPanel = new ViewSubmissionsPanel(controller);
        liveChatPanel = new LiveChatPanel(controller);

        userDetailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userDetailsPanel.setBackground(Color.WHITE);

        // Labels
        userDetailsLabel = new JLabel("Customer: Developer", SwingConstants.RIGHT);
        userDetailsLabel.setFont(Utils.getFont(Font.BOLD, 15));
    }

    // Add components to panel
    private void addComponentsToPanel(){
        // Adding the user details to the bottom of the screen
        userDetailsPanel.add(userDetailsLabel);

        // Adding the components for the different views
        views.add("data-list", viewSubmissionsPanel); // Default View
        views.add("make-query", makeQueryPanel);
        views.add("request-service", requestServicePanel);
        views.add("lodge-complaint", lodgeComplaintPanel);
        views.add("live-chat", liveChatPanel);

        // Adding the view component to the main window
        add(views, BorderLayout.CENTER);
        add(userDetailsPanel, BorderLayout.SOUTH);
    }

    // Register listeners
    private void registerListeners(){
       
    }

    // These methods will be responsible for the switching of views depending on which menu item selected.
    // View Submissions
    public void showPastComplaints(){
        cards.show(views, "data-list");
    }

    // Make a Query
    public void showQueryForm(){
        cards.show(views, "make-query");
    }

    // Request a Service 
    public void showRequestForm(){
        cards.show(views, "request-service");
    }

    // Lodge a Complaint
    public void showComplaintForm(){
        cards.show(views, "lodge-complaint");
    }

    // Live Chat
    public void showLiveChat(){
        cards.show(views, "live-chat");
    }

    // Reselect the current selected row after the highlight vanished after calling -> model.fireTableDataChanged()
    public void reHighlightLastSelectedRow(){
        viewSubmissionsPanel.reHighlightLastSelectedRow();
    }
}
