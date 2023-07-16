package com.flowapp.view.dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import com.flowapp.controller.Controller;
import com.flowapp.driver.Utils;
import com.flowapp.logging.Log;
import com.flowapp.model.Customer;
import com.flowapp.model.Employee;
import com.flowapp.model.TemporaryChat;
import com.flowapp.view.customized.CTextArea;
import com.flowapp.view.listeners.LiveChatListener;


public class LiveChatPanel extends JPanel {
    private Controller controller;
    private Employee selectedTechnician;
    private Customer loggedInCustomer;
    private TemporaryChat chat, tempChat;

    private CTextArea chatArea;
    private JPanel settingsPanel, basePanel;
    private JButton availableToggleBtn;
    private JButton respondBtn, startChatBtn, endChatBtn;

    private JLabel initializeChatLabel;
    private JComboBox<Employee> technicianComboBox;

    private Timer fetchUpdatedChatTimer;
    private boolean currentlyInLiveChat = false;

    // Listeners
    private LiveChatListener liveChatListener;


    public LiveChatPanel(Controller controller) {
        super();
        this.controller = controller;

        setupWindowProperties();

        initComponents();
        addComponentsToPanel();
        registerListeners();

        focusOnLoginPanel();
    }

    // Setters
    public void setCustomer(Customer customer){
        this.loggedInCustomer = customer;
    }

    public void updateChat(TemporaryChat chat){
        this.chat = chat;

        // Update the chat area here...
        if(chat == null || (chat.getCustomerMessage() != null && chat.getCustomerMessage().length() == 0) && (chat.getEmployeeResponse() != null && chat.getEmployeeResponse().length() == 0)) return;
    }

    public void setTechnicians(List<Employee> technicians){
        technicianComboBox.removeAllItems();

        if(technicians != null){
            for (Employee employee : technicians) {
                technicianComboBox.addItem(employee);
            }
        }

        // Hide/show start button if there are technicians available or not
        startChatBtn.setVisible(technicians != null && technicians.size() > 0 && !currentlyInLiveChat);

        repaint();
        revalidate();
    }

    public void setLiveChatListener(LiveChatListener liveChatListener) {
        this.liveChatListener = liveChatListener;
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
        initializeChatLabel = new JLabel("Initialize live chat with: ");

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
        availableToggleBtn = new JButton("Turn on Availability");

        Utils.toggleSwitchIndicator(availableToggleBtn, false);


        // Combo Boxes
        technicianComboBox = new JComboBox<>();
        technicianComboBox.setPreferredSize(new Dimension(200, 28));
        technicianComboBox.setMaximumSize(technicianComboBox.getPreferredSize());
        technicianComboBox.setMinimumSize(technicianComboBox.getPreferredSize());

        // Set the visibility of some of the components
        startChatBtn.setVisible(false);
        endChatBtn.setVisible(false);
        respondBtn.setVisible(false);
    }

    // Add components to panel
    private void addComponentsToPanel(){
        basePanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        settingsPanel.add(initializeChatLabel);
        settingsPanel.add(technicianComboBox);
        settingsPanel.add(startChatBtn);
        settingsPanel.add(respondBtn);
        settingsPanel.add(endChatBtn);

        add(basePanel, BorderLayout.CENTER);
        add(settingsPanel, BorderLayout.SOUTH);
    }

    // Register listeners
    private void registerListeners(){
        // Start Chat Button
        startChatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTechnician = (Employee) technicianComboBox.getSelectedItem();
                
                if(selectedTechnician == null){
                    JOptionPane.showMessageDialog(null, "Something went wrong... Please try again later!", "CHat Cannot Start!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int choice  = JOptionPane.showConfirmDialog(null, "Are you sure you want to start a chat with " + selectedTechnician + "?", "", JOptionPane.YES_NO_OPTION);

                if(choice == JOptionPane.YES_OPTION){
                    currentlyInLiveChat = true;

                    // Hide and show the relevant components...
                    startChatBtn.setVisible(false);
                    technicianComboBox.setVisible(false);

                    endChatBtn.setVisible(true);
                    respondBtn.setVisible(true);

                    initializeChatLabel.setText("You are now chatting with " + selectedTechnician + "!");

                    // Signal to start the chat: creating a new chat in the database...
                    if(liveChatListener != null) liveChatListener.startChat(selectedTechnician, loggedInCustomer);

                    fetchUpdatedChat();
                }
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

                chat.setCustomerMessage(text);
                chatArea.append("You: " + text + "\n\n");

                // Update the chat from the cutomer side in the database...
                if(liveChatListener != null) liveChatListener.updateChat(chat);

                fetchUpdatedChat();
            }
        });

        // End Chat Button
        endChatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice  = JOptionPane.showConfirmDialog(null, "Are you sure you want to end the chat with " + selectedTechnician + "?", "", JOptionPane.YES_NO_OPTION);

                if(choice == JOptionPane.YES_OPTION){
                    currentlyInLiveChat = false;
                    fetchUpdatedChatTimer.stop();

                    // Hide and show the relevant components...
                    endChatBtn.setVisible(false);
                    respondBtn.setVisible(false);

                    startChatBtn.setVisible(true);
                    technicianComboBox.setVisible(true);

                    initializeChatLabel.setText("Initialize live chat with: ");

                    chatArea.populate("");
                    // Delete the temporary chat from the database
                    if(liveChatListener != null) liveChatListener.endChat(chat);
                }
            }
        });
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

                    if(oldChat == null) fetchUpdatedChatTimer.stop();

                    // if(oldChat != null) chat = oldChat;
                    chat = oldChat;

                    /* CODE BELOW IS TO PREVENT DUPLICATES SHOWING UP IN THE CHAT AREA */
                    // If the employee response is different then update the chat area for the technician
                    if((chat.getEmployeeResponse() != null && tempChat.getEmployeeResponse()!= null) && (!chat.getEmployeeResponse().equalsIgnoreCase(tempChat.getEmployeeResponse()))) {
                        chatArea.append((selectedTechnician.getFirstName() != null ? selectedTechnician.getFirstName() + ": " : "Technician: ") + chat.getEmployeeResponse() + "\n\n");
                        
                        // Update the temp chat to capture the last reponse from the employee
                        tempChat.setEmployeeResponse(chat.getEmployeeResponse());
                    }

                    Log.info("Chat area update-to-date...\n");
                } 
                catch (Exception ex) {}
            }
        });

        fetchUpdatedChatTimer.start();
    }
}
