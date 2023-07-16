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
import com.flowapp.view.dashboard.DashboardPanel;
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



public class MainFrame extends JFrame {
    private Controller controller;
    private Customer loggedInCustomer;

    // Components responsible for changing views
    private CardLayout cards;
    private JPanel views;

    // JMenu components
    private JMenuBar jMenuBar;
    private JMenu optionsMenu, servicesMenu;
    private JMenuItem logoutMenuItem, viewSubmissionsMenuItem, liveChatMenuItem;
    private JMenuItem logdeComplaintMenuItem, makeQueryMenuItem, requestServiceMenuItem;

    // Custom Components
    private LoginPanel loginPanel;
    private DashboardPanel dashboardPanel;

    // Listeners
    private AuthListener authListener;

    private String lastAction;


    public MainFrame(Controller controller) { 
        super("Flow App [Customer Client]");
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
        dashboardPanel = new DashboardPanel(controller);


        // JMenuBar
        jMenuBar = new JMenuBar();

        // JMenus
        optionsMenu = new JMenu("Options");
        servicesMenu = new JMenu("Services");

        // JMenuItems
        logoutMenuItem = new JMenuItem("Logout");
        viewSubmissionsMenuItem = new JMenuItem("View Submissions");
        liveChatMenuItem = new JMenuItem("Live Chat");

        logdeComplaintMenuItem = new JMenuItem("Lodge a Complaint");
        makeQueryMenuItem = new JMenuItem("Make a Query");
        requestServiceMenuItem = new JMenuItem("Request a Service");

        // Adding the menu items to the menus
        optionsMenu.add(viewSubmissionsMenuItem);
        optionsMenu.add(liveChatMenuItem);
        optionsMenu.add(logoutMenuItem);

        servicesMenu.add(makeQueryMenuItem);
        servicesMenu.add(requestServiceMenuItem);
        servicesMenu.add(logdeComplaintMenuItem);

        // Adding the menus to the menu bar
        jMenuBar.add(optionsMenu);
        jMenuBar.add(servicesMenu);
        

        // Setup tooltips
        optionsMenu.setToolTipText("Select to show menu options."); 
        servicesMenu.setToolTipText("Select to show services."); 
        makeQueryMenuItem.setToolTipText("Select to make a query form."); 
        requestServiceMenuItem.setToolTipText("Select to show request service form."); 
        logdeComplaintMenuItem.setToolTipText("Select to show lodge complaint form."); 
        viewSubmissionsMenuItem.setToolTipText("Select to view the table of messages.");
        liveChatMenuItem.setToolTipText("Select to show the page for live chat."); 
        logoutMenuItem.setToolTipText("Select to logout of the system.");  

        // Setup mnemonics
        optionsMenu.setMnemonic(KeyEvent.VK_O); // ALT + O
        servicesMenu.setMnemonic(KeyEvent.VK_S); // ALT + S
        makeQueryMenuItem.setMnemonic(KeyEvent.VK_Q); // ALT + Q
        requestServiceMenuItem.setMnemonic(KeyEvent.VK_R); // ALT + R
        logdeComplaintMenuItem.setMnemonic(KeyEvent.VK_C); // ALT + C
        viewSubmissionsMenuItem.setMnemonic(KeyEvent.VK_V); // ALT + V
        liveChatMenuItem.setMnemonic(KeyEvent.VK_A); // ALT + A
        logoutMenuItem.setMnemonic(KeyEvent.VK_L); // ALT + L

        // Setup accelerators
        makeQueryMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK)); // CTRL + Q
        requestServiceMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK)); // CTRL + R
        logdeComplaintMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK)); // CTRL + C
        viewSubmissionsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK)); // CTRL + V
        liveChatMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK)); // CTRL + A
        logoutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK)); // CTRL + L

        
        // Adding the menubar as the frame's menubar
        setJMenuBar(jMenuBar);
        jMenuBar.setVisible(false);

        // Set default selected background and foreground for the view all submissions menu
        Utils.highlightMenuItem(viewSubmissionsMenuItem);
    }

    // Add components to window/frame
    private void addComponentsToWindow(){
        // Adding the components for the different views
        views.add("login", loginPanel); // -> Temp Comment...
        views.add("dashboard", dashboardPanel);

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
                controller.sendAction("find customer");
                controller.sendID(userID);

                loggedInCustomer = (Customer) controller.receiveResponse();

                // If the user exist and the password is correct
                if(loggedInCustomer != null && loggedInCustomer.getPassword().equalsIgnoreCase(password)){
                    jMenuBar.setVisible(true);

                    dashboardPanel.setCustomer(loggedInCustomer);

                    // Redirect to the dashboard page
                    showDashboardPage();

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
            }
        };

        loginPanel.setAuthListener(authListener);

        
        // Setup the menu item listeners...
        // Logged out
        logoutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure you want to logout?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                // If the user's choice is yes
                if(choice == JOptionPane.YES_OPTION){
                    authListener.logout();

                    loggedInCustomer = null;
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
                dashboardPanel.showPastComplaints();

                // Updating the panel info for the past complaints using another thread...
                refreshData();
            }
        });

        // Make a Query
        makeQueryMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ensure that every other menu item is not highlighted
                deHighlightAllMenuItems();

                // Highlight the selected menu item
                Utils.highlightMenuItem(makeQueryMenuItem);
                dashboardPanel.showQueryForm();
            }
        });

        // Request a Service
        requestServiceMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ensure that every other menu item is not highlighted
                deHighlightAllMenuItems();

                // Highlight the selected menu item
                Utils.highlightMenuItem(requestServiceMenuItem);
                dashboardPanel.showRequestForm();
            }
        });

        // Lodge a Complaint
        logdeComplaintMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ensure that every other menu item is not highlighted
                deHighlightAllMenuItems();

                // Highlight the selected menu item
                Utils.highlightMenuItem(logdeComplaintMenuItem);
                dashboardPanel.showComplaintForm();
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

                dashboardPanel.showLiveChat();

                // Refreshing the technician combo box while talking to the technician... just keeping the list up-to-date
                fetchAllTechnicians();
            }
        });

        // Setup the live chat listener
        dashboardPanel.setLiveChatListener(new LiveChatListener() {
            @Override
            public void startChat(Employee technician, Customer customer) {
                // System.out.println("Initializing chat here...");

                // Creating a new chat
                TemporaryChat chat = new TemporaryChat(Utils.generateChatID(15));
                chat.setCustomer(customer.getIDNumber());
                chat.setEmployee(technician.getIDNumber());

                // Send the new chat to the server to get save in the database...
                controller.sendAction("add chat");
                controller.sendChat(chat);
                controller.receiveResponse();

                // Retrieve the saved chat...
                controller.sendAction("find chat");
                controller.sendID(chat.getIdNumber());
                chat = (TemporaryChat) controller.receiveResponse();

                dashboardPanel.updateChat(chat);

                // Refreshing the technician combo box while talking to the technician... just keeping the list up-to-date
                fetchAllTechnicians();
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

                dashboardPanel.updateChat(chat);

                // Refreshing the technician combo box while talking to the technician... just keeping the list up-to-date
                fetchAllTechnicians();
            }

            @Override
            public void endChat(TemporaryChat chat) {
               // Send the chat to the server to be remove from the database...
                controller.sendAction("delete chat");
                controller.sendChat(chat);
                controller.receiveResponse();

                dashboardPanel.updateChat(null);

                // liveChatMenuItem.doClick();
            }
        });

        // Setup past complaints table listeners [update a message] && [delete a message]
        dashboardPanel.setTableItemListener(new TableItemListener() {
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
                Set<Message> messages = loggedInCustomer.getMessagesSubmitted();

                if(messages != null && messages.size() > 0) messages.remove(message);

                controller.sendAction("update customer");
                controller.sendCustomer(loggedInCustomer);
                controller.receiveResponse();

                // Delete the message from the database completely
                controller.sendAction("delete message");
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
        });
    }

    // Refresh table when there are changes...
    private void refreshData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(loggedInCustomer != null){
                    System.out.println("FLag 2.1");

                    controller.sendAction("find customer");
                    controller.sendID(loggedInCustomer.getIDNumber());

                    System.out.println("FLag 2.2");
                    Customer customer = (Customer) controller.receiveResponse();

                    System.out.println("FLag 2.3");

                    if(customer != null ) loggedInCustomer = customer;

                    System.out.println("FLag 2.4");


                    dashboardPanel.setCustomer(loggedInCustomer);

                    System.out.println("FLag 2.5");

                    // Re-highlighting the previous selected row after refreshing the data table...
                    if(lastAction != null && lastAction.equalsIgnoreCase("update message")) dashboardPanel.reHighlightLastSelectedRow(); System.out.println("FLag 2.6");

                    System.out.println("FLag 2.7");
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {}
    }

    // Show the login panel...
    public void showLoginPage(){
        changeScreenSize(true);

        cards.show(views, "login");
    }

    // Show the dashboard panel...
    public void showDashboardPage(){
        changeScreenSize(false);

        cards.show(views, "dashboard");

        fetchAllTechnicians();
    }

    // Using this method to avoid code repitition/duplication...
    private void deHighlightAllMenuItems(){
        // Ensure that every other menu item is not highlighted
        Utils.removeMenuItemHighlight(viewSubmissionsMenuItem);
        Utils.removeMenuItemHighlight(logoutMenuItem);
        Utils.removeMenuItemHighlight(makeQueryMenuItem);
        Utils.removeMenuItemHighlight(requestServiceMenuItem);
        Utils.removeMenuItemHighlight(logdeComplaintMenuItem);
        Utils.removeMenuItemHighlight(liveChatMenuItem);
    }

    @SuppressWarnings("unchecked")
    private void fetchAllTechnicians(){
        // Using another here to ensure the application doesn't glitch or stuck upon retrieving a large number of data from the database...
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Retrieve the list of all available technicians in the database...
                controller.sendAction("get available technicians");
                List<Employee> technicians = (List<Employee>) controller.receiveResponse();

                // To remove any possible duplicates returned in the list of technicians...
                if(technicians != null){
                    Set<Employee> empSet = new HashSet<>(technicians); // convert the list to a set to remove duplicates
                    technicians = new ArrayList<>(empSet); // convert the set back a list after removing all duplicates

                    dashboardPanel.setTechnicians(technicians);
                }

                // dashboardPanel.setTechnicians(technicians);
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {}
    }
}
