package com.flowapp.view.dashboard.technician;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.flowapp.controller.Controller;
import com.flowapp.driver.Utils;
import com.flowapp.logging.Log;
import com.flowapp.model.Customer;
import com.flowapp.model.Employee;
import com.flowapp.model.TemporaryChat;
import com.flowapp.view.customized.CTextArea;
import com.flowapp.view.listeners.LiveChatListener;


public class EmpLiveChatPanel extends JPanel {
    private Controller controller;
    private Customer selectedCustomer;
    private Employee loggedInEmployee;
    private TemporaryChat chat, tempChat;
    private List<Customer> customers;

    private CTextArea chatArea;
    private JPanel settingsPanel, basePanel;
    private JButton availableToggleBtn;
    private JButton respondBtn, startChatBtn, endChatBtn;

    private JLabel initializeChatLabel;
    private JComboBox<Customer> customerComboBox;

    private Timer fetchUpdatedChatTimer;
    private boolean currentlyInLiveChat = false, chatIsNull;

    // Listeners
    private LiveChatListener liveChatListener;


    public EmpLiveChatPanel(Controller controller) {
        super();
        this.controller = controller;

        setupWindowProperties();

        initComponents();
        addComponentsToPanel();
        registerListeners();

        focusOnLoginPanel();
    }

    // Setters
    public void setEmployee(Employee employee){
        this.loggedInEmployee = employee;

        Utils.toggleSwitchIndicator(availableToggleBtn, employee.isAvailableForLiveChat());

        updateAvailability();
    }

    public void setLiveChatListener(LiveChatListener liveChatListener) {
        this.liveChatListener = liveChatListener;
    }

    public void updateChat(TemporaryChat chat){
        this.chat = chat;

        // Update the chat area here...
        if(chat == null || (chat.getCustomerMessage() != null && chat.getCustomerMessage().length() == 0) && (chat.getEmployeeResponse() != null && chat.getEmployeeResponse().length() == 0)) return;
    }

    public void setCustomers(List<Customer> customers){
        this.customers = customers;

        customerComboBox.removeAllItems();

        if(customers != null){
            for (Customer customer : customers) {
                customerComboBox.addItem(customer);
            }
        }
        
        updateAvailability();

        repaint();
        revalidate();
    }

    private void focusOnLoginPanel(){
        setFocusable(true);
        grabFocus();
    }

    // Setup window properties
    private void setupWindowProperties(){
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);
    }
        
    // Initialize components
    private void initComponents(){
        // Labels
        initializeChatLabel = new JLabel("Start live chat with: ");

        // Text Area
        chatArea = new CTextArea();

        // Disable editing...
        chatArea.setEditable(false);

        // Panels
        settingsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        basePanel = new JPanel(new BorderLayout());

        basePanel.setBackground(Color.WHITE);
        settingsPanel.setBackground(Color.WHITE);

        // Buttons
        respondBtn = new JButton("Respond");
        startChatBtn = new JButton("Start Chat Now");
        endChatBtn = new JButton("End Chat Now");
        availableToggleBtn = new JButton("Turn on Live Chat Availability");

        Utils.toggleSwitchIndicator(availableToggleBtn, false);

        // Combo Boxes
        customerComboBox = new JComboBox<>();
        customerComboBox.setPreferredSize(new Dimension(200, 28));
        customerComboBox.setMaximumSize(customerComboBox.getPreferredSize());
        customerComboBox.setMinimumSize(customerComboBox.getPreferredSize());

        // Set the visibility of some of the components
        initializeChatLabel.setVisible(false);
        respondBtn.setVisible(false);
        startChatBtn.setVisible(false);
        endChatBtn.setVisible(false);
        customerComboBox.setVisible(false);
    }

    // Add components to panel
    private void addComponentsToPanel(){
        basePanel.add(chatArea, BorderLayout.CENTER);

        settingsPanel.add(initializeChatLabel);
        settingsPanel.add(respondBtn);
        settingsPanel.add(customerComboBox);
        settingsPanel.add(startChatBtn);
        settingsPanel.add(endChatBtn);
        settingsPanel.add(availableToggleBtn);

        add(basePanel, BorderLayout.CENTER);
        add(settingsPanel, BorderLayout.SOUTH);
    }

    // Register listeners
    private void registerListeners(){
        // Toggle Button
        availableToggleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isAvailable = availableToggleBtn.isSelected();

                Utils.toggleSwitchIndicator(availableToggleBtn, isAvailable);

                // Set the new availability...
                loggedInEmployee.setAvailableForLiveChat(isAvailable);

                // Send the updated availability for the employee to the database...
                if(liveChatListener != null) liveChatListener.updateAvailability(loggedInEmployee);

                // updateAvailability();
            }
        });

        // Respond Button
        respondBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String text = (String)JOptionPane.showInputDialog(null,"Enter your response here: ", "Response Dialog", JOptionPane.PLAIN_MESSAGE);

                // If the text returned from the dialog is empty 
                if(text == null) return;

                if(text.length() < 1 || text.equalsIgnoreCase("") || text.isBlank() || text.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Field cannot be empty for submission... Please enter a response!", "Cannot Submit", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try{
                    if(chatIsNull){
                        JOptionPane.showMessageDialog(null, "It seems the customer deleted the chat session. Please exit this chat!", "Cannot Submit!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    chat.setEmployeeResponse(text);
                    chatArea.append("You: " + text + "\n\n");

                    // Update the chat from the technician side in the database...
                    if(liveChatListener != null) liveChatListener.updateChat(chat);
                }
                catch(NullPointerException ex){
                    JOptionPane.showMessageDialog(null, "It seems the customer deleted the chat session. Please exit this chat!", "Cannot Submit!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Start Chat Button
        startChatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCustomer = (Customer) customerComboBox.getSelectedItem();
                
                if(selectedCustomer == null){
                    JOptionPane.showMessageDialog(null, "Something went wrong... Please try again later!", "CHat Cannot Start!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int choice  = JOptionPane.showConfirmDialog(null, "Are you sure you want to start a chat with " + selectedCustomer + "?", "", JOptionPane.YES_NO_OPTION);

                if(choice == JOptionPane.YES_OPTION){
                    currentlyInLiveChat = true;

                    // Hide and show the relevant components...
                    startChatBtn.setVisible(false);
                    customerComboBox.setVisible(false);
                    availableToggleBtn.setVisible(false);

                    endChatBtn.setVisible(true);
                    respondBtn.setVisible(true);

                    initializeChatLabel.setText("You are now chatting with " + selectedCustomer + "!");

                    // Signal to start the chat: creating a new chat in the database...
                    if(liveChatListener != null) liveChatListener.startChat(loggedInEmployee, selectedCustomer);

                    fetchUpdatedChat();
                }
            }
        });

        // End Chat Button
        endChatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice  = JOptionPane.showConfirmDialog(null, "Are you sure you want to end the chat with " + selectedCustomer + "?", "", JOptionPane.YES_NO_OPTION);

                if(choice == JOptionPane.YES_OPTION){
                    currentlyInLiveChat = false;
                    fetchUpdatedChatTimer.stop();

                    // Hide and show the relevant components...
                    endChatBtn.setVisible(false);
                    respondBtn.setVisible(false);

                    startChatBtn.setVisible(true);
                    customerComboBox.setVisible(true);
                    availableToggleBtn.setVisible(true);

                    initializeChatLabel.setText("Initialize live chat with: ");

                    chatArea.populate("");

                    if(liveChatListener != null) liveChatListener.endChat();

                    updateAvailability();
                }
            }
        });
    }

    private void updateAvailability(){
        if(customers == null) customers = new ArrayList<>();

        // Hide/show start button if there are customers wanting to chat or not
        boolean showing = (customers.size() > 0 && !currentlyInLiveChat);

        // ONly show when user is available...
        if(loggedInEmployee.isAvailableForLiveChat()){
            customerComboBox.setVisible(showing);
            startChatBtn.setVisible(showing);
            initializeChatLabel.setVisible(true);

            if(currentlyInLiveChat){
                initializeChatLabel.setText("You are now chatting with " + selectedCustomer + "!");
            }
            else if(showing){
                initializeChatLabel.setText("Start live chat with: ");
            }
            else{
                initializeChatLabel.setText("No one wants talk at the moment...");
            }
        }
        else {
            customerComboBox.setVisible(false);
            startChatBtn.setVisible(false);
            initializeChatLabel.setVisible(false);
        }
    }

    // Updating the chat area using java swimg timer [multi-threading]...
    private void fetchUpdatedChat(){
        if(fetchUpdatedChatTimer != null && fetchUpdatedChatTimer.isRunning()) return;

        // Repeat every 5 seconds...
        fetchUpdatedChatTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(tempChat == null) tempChat = new TemporaryChat();
                    
                    // Pulling the chat from the db to see if the technician has responded...
                    controller.sendAction("find chat");
                    controller.sendID(chat.getIdNumber());
                    TemporaryChat oldChat = (TemporaryChat) controller.receiveResponse();

                    if(oldChat == null){
                        chatIsNull = true;
                        fetchUpdatedChatTimer.stop();
                    }

                    // if(oldChat != null) chat = oldChat;
                    chat = oldChat;

                    /* CODE BELOW IS TO PREVENT DUPLICATES SHOWING UP IN THE CHAT AREA */
                    // If the customer message is different then update the chat area for the customer
                    if((chat.getCustomerMessage() != null && tempChat.getCustomerMessage()!= null) && (!chat.getCustomerMessage().equalsIgnoreCase(tempChat.getCustomerMessage()))) {
                        chatArea.append((selectedCustomer.getFirstName() != null ? selectedCustomer.getFirstName() + ": " : "Technician: ") + chat.getCustomerMessage() + "\n\n");

                        // Update the temp chat to capture the last reponse from the employee
                        tempChat.setCustomerMessage(chat.getCustomerMessage());
                    }
                    
                    Log.info("Chat area update-to-date...\n");
                } 
                catch (NullPointerException ex) {}
            }
        });

        fetchUpdatedChatTimer.start();
    }
}
