package com.flowapp.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.flowapp.controller.Controller;
import com.flowapp.driver.Utils;
import com.flowapp.model.Customer;
import com.flowapp.model.Employee;
import com.flowapp.model.Message;
import com.flowapp.model.Response;
import com.flowapp.model.TemporaryChat;
import com.flowapp.view.dashboard.representative.RepresentativeDashboardPanel;
import com.flowapp.view.dashboard.technician.TechnicianDashboardPanel;
import com.flowapp.view.listeners.AuthListener;
import com.flowapp.view.listeners.LiveChatListener;
import com.flowapp.view.listeners.TableItemListener;
import com.flowapp.view.login.LoginPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class EmpMainFrame extends JFrame {
    private Controller controller;
    private Employee loggedInEmployee;

    // Components responsible for changing views
    private CardLayout cards;
    private JPanel views;

    // JMenu components
    private JMenuBar jMenuBar;
    private JMenu optionsMenu;
    private JMenuItem logoutMenuItem, viewSubmissionsMenuItem, searchMenuItem, refreshMenuItem, liveChatMenuItem;

    // Custom Components
    private LoginPanel loginPanel;
    private TechnicianDashboardPanel technicianDashboardPanel;
    private RepresentativeDashboardPanel representativeDashboardPanel;

    // Listeners
    private AuthListener authListener;

    private String lastAction, dashboardPage, categoryLookupValue;
    private boolean showingSearchResults = false;


    public EmpMainFrame(Controller controller) { 
        super("Flow App [Employee Client]");
        this.controller = controller;

        setupWindowProperties();

        initComponents();
        addComponentsToWindow();
        registerListeners();
    }

    // Setup window properties
    private void setupWindowProperties(){
        setLayout(new BorderLayout());

        changeScreenSize(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Change to small screen whenever the application is at the login screen; else, the application will be extended
    private void changeScreenSize(boolean smallscreen) {
        // If the program is at the login screen
        if(smallscreen){
            setPreferredSize(new Dimension(800, 600));
            setMinimumSize(new Dimension(800, 600));

            setResizable(false);

            pack();
            setLocationRelativeTo(null);

            return;
        }

        // If the program is not at the login screen
        setResizable(true);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    
    // Initialize components
    private void initComponents(){
        cards = new CardLayout();
        views = new JPanel(cards);

        // Panels
        loginPanel = new LoginPanel();
        representativeDashboardPanel = new RepresentativeDashboardPanel(controller);
        technicianDashboardPanel = new TechnicianDashboardPanel(controller);

        // JMenuBar
        jMenuBar = new JMenuBar();

        // JMenus
        optionsMenu = new JMenu("Options");

        // JMenuItems
        logoutMenuItem = new JMenuItem("Logout");
        viewSubmissionsMenuItem = new JMenuItem("View Submissions");
        searchMenuItem = new JMenuItem("Search by Category");
        refreshMenuItem = new JMenuItem("Refresh Table");
        liveChatMenuItem = new JMenuItem("Live Chat");

        // Adding the menu items to the menus
        optionsMenu.add(viewSubmissionsMenuItem);
        optionsMenu.add(refreshMenuItem);
        optionsMenu.add(searchMenuItem);
        optionsMenu.add(liveChatMenuItem);
        optionsMenu.add(logoutMenuItem);

        // Adding the menus to the menu bar
        jMenuBar.add(optionsMenu);


        // Setup tooltips
        optionsMenu.setToolTipText("Select to show menu options."); 
        refreshMenuItem.setToolTipText("Select to refresh table."); 
        viewSubmissionsMenuItem.setToolTipText("Select to view the table of messages."); 
        searchMenuItem.setToolTipText("Select to show search by category form."); 
        liveChatMenuItem.setToolTipText("Select to show the page for live chat."); 
        logoutMenuItem.setToolTipText("Select to logout of the system.");  

        // Setup mnemonics
        optionsMenu.setMnemonic(KeyEvent.VK_O); // ALT + O
        refreshMenuItem.setMnemonic(KeyEvent.VK_R); // ALT + R
        viewSubmissionsMenuItem.setMnemonic(KeyEvent.VK_V); // ALT + V
        searchMenuItem.setMnemonic(KeyEvent.VK_S); // ALT + S
        liveChatMenuItem.setMnemonic(KeyEvent.VK_A); // ALT + A
        logoutMenuItem.setMnemonic(KeyEvent.VK_L); // ALT + L

        // Setup accelerators
        refreshMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK)); // CTRL + R
        viewSubmissionsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK)); // CTRL + V
        searchMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK)); // CTRL + S
        liveChatMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK)); // CTRL + A
        logoutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK)); // CTRL + L
        

        // Adding the menubar as the frame's menubar
        setJMenuBar(jMenuBar);

        // Hiding the following menu and menu items before user login
        jMenuBar.setVisible(false);
        refreshMenuItem.setVisible(false);
        searchMenuItem.setVisible(false);
        liveChatMenuItem.setVisible(false);

        // Set default selected background and foreground for the view all submissions menu
        Utils.highlightMenuItem(viewSubmissionsMenuItem);
    }

    // Add components to window/frame
    private void addComponentsToWindow(){
        // Adding the components for the different views
        views.add("login", loginPanel); // -> Temp Comment...
        views.add("representative-dashboard", representativeDashboardPanel);
        views.add("technician-dashboard", technicianDashboardPanel);

        // Adding the view component to the main window
        add(views, BorderLayout.CENTER);
    }

    // Register listeners
    private void registerListeners(){
        // Setup the authentication listener that will be passed to the login and dashboard
        authListener = new AuthListener() {
            @Override
            public boolean login(String userID, String password) {
                // Validate the user id and password here... if the user exists then call the showDashboardPage()
                controller.sendAction("find employee");
                controller.sendID(userID);

                loggedInEmployee = (Employee) controller.receiveResponse();

                // If the user exist and the password is correct
                if(loggedInEmployee != null && loggedInEmployee.getPassword().equalsIgnoreCase(password)){
                    jMenuBar.setVisible(true);

                    // Redirect to the relevant dashboard page based on the employee's role...
                    if(loggedInEmployee.getRole().equalsIgnoreCase("representative")){
                        JOptionPane.showMessageDialog(EmpMainFrame.this, "Redirecting you to the representative dashboard...", "Note", JOptionPane.INFORMATION_MESSAGE);

                        dashboardPage = "representative-dashboard";
                        showRepresentativeDashboardPage();
                    }
                    else if(loggedInEmployee.getRole().equalsIgnoreCase("technician")){
                        JOptionPane.showMessageDialog(EmpMainFrame.this, "Redirecting you to the technician dashboard...", "Note", JOptionPane.INFORMATION_MESSAGE);

                        dashboardPage = "technician-dashboard";
                        showTechnicianDashboardPage();
                    }

                    return true;
                }
                
                return false;
            }

            @Override
            public void logout() {
                // Reset all menu colors when user logs out...
                deHighlightAllMenuItems();

                // Redirect to the login page
                showLoginPage();

                jMenuBar.setVisible(false);

                refreshMenuItem.setVisible(false);
                searchMenuItem.setVisible(false);
                liveChatMenuItem.setVisible(false);
            }
        };

        loginPanel.setAuthListener(authListener);

        
        // Setup the menu item listeners...
        // Logged out
        logoutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(EmpMainFrame.this, "Are you sure you want to logout?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                // If the user's choice is yes
                if(choice == JOptionPane.YES_OPTION){
                    authListener.logout();

                    loggedInEmployee = null;
                }
            }
        });

        // View All Submissions
        viewSubmissionsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ensure that every other menu item is not highlighted
                deHighlightAllMenuItems();

                // Highlight the selected menu item
                Utils.highlightMenuItem(viewSubmissionsMenuItem);

                // Update the respective dashboards
                if(dashboardPage.equalsIgnoreCase("representative-dashboard")){
                    representativeDashboardPanel.showMessages();
                }
                else if(dashboardPage.equalsIgnoreCase("technician-dashboard")){
                    technicianDashboardPanel.showMessages();
                }

                // Updating the panel info for the past complaints using another thread...
                refreshData();
            }
        });

        // Search By Category
        searchMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryLookupValue = JOptionPane.showInputDialog(null, "Enter desired category to search by: ", "Search By Category", JOptionPane.DEFAULT_OPTION);

                if(categoryLookupValue == null) return;

                fetchAllMessagesByCategory();
            }
        });

        // Refresh Table
        refreshMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to refresh the table?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                // Refresh table if the answer selected was yes...
                if(choice == JOptionPane.YES_OPTION) {
                    showingSearchResults = false;
                    refreshData(); 
                }
            }
        });

        // Set the technician availability for the live chat...
        liveChatMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ensure that every other menu item is not highlighted
                deHighlightAllMenuItems();

                // Highlight the selected menu item
                Utils.highlightMenuItem(liveChatMenuItem);

                technicianDashboardPanel.showLiveChat();

                // Refreshing the customer combo box while talking to the current customer... just keeping the list up-to-date
                fetchLiveChats();
            }
        });

        // Setup past complaints table listeners [update a message] && [delete a message]
        TableItemListener tableItemListener = new TableItemListener() {
            @Override
            public void update(Message message) {
                controller.sendAction("update message");
                controller.sendMessage(message);
                controller.receiveResponse();

                lastAction = "update message";
                refreshData();
            }

            @Override
            public void delete(Message message) {
                // Delete the message from the cutomer
                Customer customer = message.getCustomer();

                // Retrieve the customer from the database to before updating
                controller.sendAction("find customer");
                controller.sendID(customer.getIDNumber());
                customer = (Customer) controller.receiveResponse();

                Set<Message> messages = customer.getMessagesSubmitted();

                if(messages != null && messages.size() > 0) messages.remove(message);

                controller.sendAction("update customer");
                controller.sendCustomer(customer);
                controller.receiveResponse();

                // Delete the message from the database completely
                controller.sendAction("delete message by representative");
                controller.sendMessage(message);
                controller.receiveResponse();

                lastAction = "delete message";
                refreshData();
            }

            @Override
            public void addResponse(Message message, Response response) {
                controller.sendAction("add response");
                controller.sendResponse(response);
                controller.receiveResponse();

                controller.sendAction("update message");
                controller.sendMessage(message);
                controller.receiveResponse();

                lastAction = "update message";
                refreshData();
            }

            @Override
            public void assignMessageTo(Message message, Employee employee) {
                // Add the message to the technician
                // Retrieve the employee from the database to before updating
                controller.sendAction("find employee");
                controller.sendID(employee.getIDNumber());
                employee = (Employee) controller.receiveResponse();

                Set<Message> messages = employee.getAssignedMessages();
                messages.add(message);

                controller.sendAction("update employee");
                controller.sendEmployee(employee);
                controller.receiveResponse();

                // Update the message in the database...
                controller.sendAction("update message");
                controller.sendMessage(message);
                controller.receiveResponse();

                if(showingSearchResults){
                    fetchAllMessagesByCategory();
                }
                else{
                    lastAction = "update message";
                    refreshData();
                }
            }
        };

        representativeDashboardPanel.setTableItemListener(tableItemListener);
        technicianDashboardPanel.setTableItemListener(tableItemListener);


        // Setup the live chat listener
        technicianDashboardPanel.setLiveChatListener(new LiveChatListener() {
            @Override
            public void updateAvailability(Employee employee) {
                // Send the updated employee to the database...
                controller.sendAction("update employee");
                controller.sendEmployee(employee);

                boolean updated = (Boolean) controller.receiveResponse();

                if(updated) {
                    JOptionPane.showMessageDialog(EmpMainFrame.this, "Your live chat availability was updated successfully!", "Update Status", JOptionPane.INFORMATION_MESSAGE);
                
                    // Get the updated employee
                    controller.sendAction("find employee");
                    controller.sendID(loggedInEmployee.getIDNumber());
                    loggedInEmployee = (Employee) controller.receiveResponse();

                    technicianDashboardPanel.setEmployee(loggedInEmployee);

                    // Refreshing the customer combo box while talking to the current customer... just keeping the list up-to-date
                    fetchLiveChats();
                }
            }

            @Override
            public void updateChat(TemporaryChat chat) {
                // Send the updated chat to the server to get save in the database...
                controller.sendAction("update chat");
                controller.sendChat(chat);
                controller.receiveResponse();

                // Retrieve the saved chat...
                controller.sendAction("find chat");
                controller.sendID(chat.getIdNumber());
                chat = (TemporaryChat) controller.receiveResponse();

                technicianDashboardPanel.updateChat(chat);

                // Refreshing the customer combo box while talking to the current customer... just keeping the list up-to-date
                fetchLiveChats();
            }

            @Override
            public void startChat(Employee employee, Customer customer) {
                TemporaryChat chat = new TemporaryChat();
                chat.setCustomer(customer.getIDNumber());
                chat.setEmployee(employee.getIDNumber());

                // Retrieve the chat that will be used by the technician to speak to a particular customer...
                controller.sendAction("find chat by technician");
                controller.sendChat(chat);
                chat = (TemporaryChat) controller.receiveResponse();

                technicianDashboardPanel.updateChat(chat);
            }

            @Override
            public void endChat() {
                // liveChatMenuItem.doClick();
                // Refreshing the customer combo box while talking to the current customer... just keeping the list up-to-date
                fetchLiveChats();
            }
        });
    }

    // Refresh table when there are changes...
    private void refreshData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(loggedInEmployee != null){
                    // Get the updated user and his/her updated list of assigned messages
                    // Employee
                    controller.sendAction("find employee");
                    controller.sendID(loggedInEmployee.getIDNumber());

                    Employee employee = (Employee) controller.receiveResponse();

                    if(employee != null) loggedInEmployee = employee;
                    
                    // Update the respective dashboards
                    if(dashboardPage.equalsIgnoreCase("representative-dashboard")){
                        fetchAllMessages();
                    }
                    else if(dashboardPage.equalsIgnoreCase("technician-dashboard")){
                        technicianDashboardPanel.setEmployee(loggedInEmployee);

                        // Re-highlighting the previous selected row after refreshing the data table...
                        if(lastAction != null && lastAction.equalsIgnoreCase("update message")) technicianDashboardPanel.reHighlightLastSelectedRow();
                    }
                }
            }
        });

        thread.start();
    }

    // Show the login panel...
    public void showLoginPage(){
        changeScreenSize(true);

        cards.show(views, "login");
    }

    // Show the representative dashboard panel...
    public void showRepresentativeDashboardPage(){
        changeScreenSize(false);

        // Showing the menu items after user login -> Only representatives will see these options
        refreshMenuItem.setVisible(true);
        searchMenuItem.setVisible(true);

        representativeDashboardPanel.setEmployee(loggedInEmployee);
        cards.show(views, "representative-dashboard");
        
        fetchAllMessages();
        fetchAllTechnicians();
    }

    // Show the technician dashboard panel...
    public void showTechnicianDashboardPage(){
        changeScreenSize(false);

        // Showing the menu items after user login -> Only technicians will see these options
        refreshMenuItem.setVisible(true);
        liveChatMenuItem.setVisible(true);

        technicianDashboardPanel.setEmployee(loggedInEmployee);
        cards.show(views, "technician-dashboard");

        fetchLiveChats();
    }

    // Using this method to avoid code repitition/duplication...
    private void deHighlightAllMenuItems(){
        // Ensure that every other menu item is not highlighted
        Utils.removeMenuItemHighlight(viewSubmissionsMenuItem);
        Utils.removeMenuItemHighlight(liveChatMenuItem);
        Utils.removeMenuItemHighlight(logoutMenuItem);
    }

    // Utilizing this method to eliminate code repetition...
    @SuppressWarnings("unchecked")
    private void fetchAllMessages(){
        // Using another here to ensure the application doesn't glitch or stuck upon retrieving a large number of data from the database...
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Retrieve the list of all existing messages in the database...
                controller.sendAction("get messages");
                List<Message> messages = (List<Message>) controller.receiveResponse();

                // To remove any possible duplicates returned in the list of messages...
                if(messages != null){
                    Set<Message> messageSet = new HashSet<>(messages); // convert the list to a set to remove duplicates
                    messages = new ArrayList<>(messageSet); // convert the set back a list after removing all duplicates
                }

                // Update the respective dashboards
                if(dashboardPage.equalsIgnoreCase("representative-dashboard")){
                    representativeDashboardPanel.setEmployee(loggedInEmployee);
                    representativeDashboardPanel.setMessages(messages, null);

                    // Re-highlighting the previous selected row after refreshing the data table...
                    // if(lastAction != null && lastAction.equalsIgnoreCase("update message")) representativeDashboardPanel.reHighlightLastSelectedRow();
                }
                else if(dashboardPage.equalsIgnoreCase("technician-dashboard")){
                    // technicianDashboardPanel.setEmployee(loggedInEmployee);

                    // Re-highlighting the previous selected row after refreshing the data table...
                    // if(lastAction != null && lastAction.equalsIgnoreCase("update message")) technicianDashboardPanel.reHighlightLastSelectedRow();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {}
    }

    @SuppressWarnings("unchecked")
    private void fetchAllTechnicians(){
        // Using another here to ensure the application doesn't glitch or stuck upon retrieving a large number of data from the database...
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Retrieve the list of all existing technicians in the database...
                controller.sendAction("get technicians");
                List<Employee> technicians = (List<Employee>) controller.receiveResponse();

                // To remove any possible duplicates returned in the list of technicians...
                if(technicians != null){
                    Set<Employee> empSet = new HashSet<>(technicians); // convert the list to a set to remove duplicates
                    technicians = new ArrayList<>(empSet); // convert the set back a list after removing all duplicates
                }

                // Update the respective dashboards
                if(dashboardPage.equalsIgnoreCase("representative-dashboard")){
                    representativeDashboardPanel.setTechnicians(technicians);

                    // Re-highlighting the previous selected row after refreshing the data table...
                    //if(lastAction != null && lastAction.equalsIgnoreCase("update message")) representativeDashboardPanel.reHighlightLastSelectedRow();
                }
                else if(dashboardPage.equalsIgnoreCase("technician-dashboard")){
                    // technicianDashboardPanel.setEmployee(loggedInEmployee);

                    // Re-highlighting the previous selected row after refreshing the data table...
                    // if(lastAction != null && lastAction.equalsIgnoreCase("update message")) technicianDashboardPanel.reHighlightLastSelectedRow();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {}
    }

    @SuppressWarnings("unchecked")
    private void fetchAllMessagesByCategory(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Retrieve the list of all existing messages by the desired category...
                controller.sendAction("get messages by category");
                controller.sendCategory(categoryLookupValue);
                List<Message> messages = (List<Message>) controller.receiveResponse();

                // To remove any possible duplicates returned in the list of messages...
                if(messages != null){
                    Set<Message> messageSet = new HashSet<>(messages); // convert the list to a set to remove duplicates
                    messages = new ArrayList<>(messageSet); // convert the set back a list after removing all duplicates
                }

                // Updating the table with the results
                representativeDashboardPanel.setMessages(messages, categoryLookupValue);

                showingSearchResults = true;
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {}
    }

    @SuppressWarnings("unchecked")
    private void fetchLiveChats(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Retrieve the list of all existing chats that the employee has been the person a customer want to talk to...
                controller.sendAction("get chats");
                controller.sendID(loggedInEmployee.getIDNumber());
                List<TemporaryChat> chats = (List<TemporaryChat>) controller.receiveResponse();

                // To remove any possible duplicates returned in the list of chats...
                if(chats != null){
                    Set<TemporaryChat> chatSet = new HashSet<>(chats); // convert the list to a set to remove duplicates
                    chats = new ArrayList<>(chatSet); // convert the set back a list after removing all duplicates
                }

                Set<Customer> customerSet = new HashSet<>();

                if(chats != null){
                    // Retrieving all customers that wants to talk to the current logged in employee
                    for (TemporaryChat chat : chats) {
                        controller.sendAction("find customer");
                        controller.sendID(chat.getCustomer());
                        Customer customer = (Customer) controller.receiveResponse();

                        if(customer != null) customerSet.add(customer);
                    }

                    // Updating the customer combo box with the results
                    //if(customerSet != null && customerSet.size() > 0) technicianDashboardPanel.setCustomers(new ArrayList<>(customerSet));
                    if(customerSet != null) technicianDashboardPanel.setCustomers(new ArrayList<>(customerSet));
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {}
    }
}
