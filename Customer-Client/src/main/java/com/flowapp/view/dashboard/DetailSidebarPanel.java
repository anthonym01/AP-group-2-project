package com.flowapp.view.dashboard;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import com.flowapp.controller.Controller;
import com.flowapp.driver.Utils;
import com.flowapp.model.Customer;
import com.flowapp.model.Message;
import com.flowapp.model.Response;
import com.flowapp.view.customized.CTextArea;
import com.flowapp.view.customized.CTextField;
import com.flowapp.view.listeners.DetailSidebarListener;
import com.flowapp.view.listeners.TableItemListener;


public class DetailSidebarPanel extends JPanel{
    private Message message;
    private JLabel headerLabel;

    private Customer loggedInCustomer;

    // Form Fields & Labels
    private JLabel nameLabel, emailLabel, phoneLabel, detailsLabel, categoryLabel, issueTypeLabel, responseDetailsLabel;
    private CTextField nameField, emailField, phoneField, issueTypeField;
    private CTextArea detailsArea, responsesArea;
    private JComboBox<String> categoryComboBox;

    private JPanel subPanel, buttonsPanel;

    private JButton saveBtn, cancelBtn, respondBtn;

    // Listeners
    private TableItemListener tableItemListener;
    private DetailSidebarListener detailSidebarListener;

    private final String comboBoxDefaultString = "----------------------------------------------------------------";

    public DetailSidebarPanel(Controller controller) {
        super();
        //this.controller = controller;

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
    public void setTableItemListener(TableItemListener tableItemListener) {
        this.tableItemListener = tableItemListener;
    }

    public void setDetailSidebarListener(DetailSidebarListener detailSidebarListener) {
        this.detailSidebarListener = detailSidebarListener;
    }

    public void setCustomer(Customer loggedInCustomer) {
        this.loggedInCustomer = loggedInCustomer;
    }

    // Setup window properties
    private void setupWindowProperties(){
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);

        setPreferredSize(new Dimension(450, 250));
        setMaximumSize(new Dimension(250, 250));
    }
        
    // Initialize components
    private void initComponents(){
        // Labels
        headerLabel = new JLabel("Message Details", SwingConstants.CENTER);
        headerLabel.setFont(Utils.getFont(Font.BOLD, 15));

        nameLabel = new JLabel("Name: ");
        emailLabel = new JLabel("Email: ");
        phoneLabel = new JLabel("Phone: ");
        detailsLabel = new JLabel("Details: ");
        categoryLabel = new JLabel("Category: ");
        issueTypeLabel = new JLabel("Issue Type: "); 
        responseDetailsLabel = new JLabel("Responses: ");

        // Text fields
        nameField = new CTextField("", 25);
        emailField = new CTextField("", 25);
        phoneField = new CTextField("", 25);
        issueTypeField = new CTextField("", 25);

        // Text areas
        detailsArea = new CTextArea(10, 25);
        responsesArea = new CTextArea(10, 25);

        // Combo boxes
        categoryComboBox = new JComboBox<>(new String[]{comboBoxDefaultString});
        categoryComboBox.setPrototypeDisplayValue(comboBoxDefaultString);

        // Panels
        subPanel = new JPanel(new GridBagLayout());
        subPanel.setBackground(Color.WHITE);

        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBackground(Color.WHITE);

        // Buttons
        saveBtn = new JButton("Update");
        cancelBtn = new JButton("Cancel");
        respondBtn = new JButton("Respond");

        // Disable fields
        enableEditing(false);
    }

    // Add components to panel
    private void addComponentsToPanel(){
        add(headerLabel, BorderLayout.NORTH);

        buttonsPanel.add(saveBtn);
        buttonsPanel.add(respondBtn);
        buttonsPanel.add(cancelBtn);

        setupSubPanel();

        add(new JScrollPane(subPanel), BorderLayout.CENTER);
    }

    // Using this method to setup the sub-panel to have a grid bag layout manager
    private void setupSubPanel(){
        GridBagConstraints c = new GridBagConstraints();

        
        // First Row & First Column
        // First Row & First Column
        c.insets = new Insets(20, 5, 5, 20);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 0; // column
        c.gridy = 0; // row
        subPanel.add(nameLabel, c);

        // First Row & Second Column
        c.gridx = 1; 
        c.gridy = 0; 
        subPanel.add(nameField, c);


        // Second Row & First Column
        c.gridx = 0; 
        c.gridy = 1; 
        subPanel.add(emailLabel, c);

        // Second Row & Second Column
        c.gridx = 1; 
        c.gridy = 1; 
        subPanel.add(emailField, c);


        // Third Row & First Column
        c.gridx = 0; 
        c.gridy = 2; 
        subPanel.add(phoneLabel, c);

        // Third Row & Second Column
        c.gridx = 1;
        c.gridy = 2;
        subPanel.add(phoneField, c);


        // Fourth Row & First Column
        c.gridx = 0; 
        c.gridy = 3; 
        subPanel.add(issueTypeLabel, c);

        // Fifth Row & Second Column
        c.gridx = 1;
        c.gridy = 3;
        subPanel.add(issueTypeField, c);


        // Fifth Row & First Column
        c.gridx = 0; 
        c.gridy = 4; 
        subPanel.add(categoryLabel, c);

        // Fifth Row & Second Column
        c.gridx = 1;
        c.gridy = 4;
        subPanel.add(categoryComboBox, c);


        // Sixth Row & First Column
        c.gridx = 0; 
        c.gridy = 5; 
        subPanel.add(detailsLabel, c);

        // Sixth Row & Second Column
        c.gridx = 1;
        c.gridy = 5;
        subPanel.add(new JScrollPane(detailsArea), c);


        // Seventh Row & First Column
        c.gridx = 0; 
        c.gridy = 6; 
        subPanel.add(responseDetailsLabel, c);

        // Seventh Row & Second Column
        c.gridx = 1;
        c.gridy = 6;
        subPanel.add(new JScrollPane(responsesArea), c);


        // Eigth Row & First Column
        c.gridx = 1; 
        c.gridy = 7;
        c.anchor = GridBagConstraints.CENTER;
        subPanel.add(buttonsPanel, c);
    }


    // Register listeners
    private void registerListeners(){
        // Save Button 
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate the submmitted values before saving...\
                // If the form cannot submit then show error message and return
                if(!canSubmit()) return;

                // Collect the data from each fields on the form: Uncomment the following when it's saving to the database
                String category = (String) categoryComboBox.getSelectedItem();
                String details = detailsArea.getText();

        
                message.setCategory(category);
                message.setDetails(details);

                // message.setResponses(null);

                // Send the message to the server to be saved in the db
                if(detailSidebarListener != null) detailSidebarListener.save(message);
                if(tableItemListener != null) tableItemListener.update(message);
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

                // Add the response to the message's list of responses...
                if(message != null && message.getResponses() != null){
                    Response response = new Response();

                    response.setContent(text);
                    response.setMessage(message);
                    response.setResponseDate(new Date());

                    String userID = (message.getCustomer() != null ? message.getCustomer().getIDNumber() : null);
                    response.setUserID(userID);

                    message.addResponse(response);

                    // Update the message in the database...
                    if(tableItemListener != null) tableItemListener.addResponse(message, response);
                }
            }
        });

        // Clear Button
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableEditing(false);
            }
        });
    }

    // Updating the sidebar panel with the message details
    public void updatePanel(Message message, String action){
        this.message = message;

        if(action.equalsIgnoreCase("read-only")){
            enableEditing(false);
            updateInfo();

            return;
        }

        if(action.equalsIgnoreCase("edit")){
            enableEditing(true);
            updateInfo();

            nameField.setEnabled(false);
            emailField.setEnabled(false);
            phoneField.setEnabled(false);
            issueTypeField.setEnabled(false);

            responsesArea.setEnabled(false);
            responsesArea.setEditable(false);

            return;
        }
    }

    // updatePanel() helpers
    private void enableEditing(boolean value){
        nameField.setEnabled(value);
        emailField.setEnabled(value);
        phoneField.setEnabled(value);
        issueTypeField.setEnabled(value);
        categoryComboBox.setEnabled(value);

        detailsArea.setEnabled(value);
        detailsArea.setEditable(value);
        responsesArea.setEnabled(value);
        responsesArea.setEditable(value);

        buttonsPanel.setVisible(value);
    }

    // Updating the message details on the sidebar...
    private void updateInfo(){
        Customer customer = message.getCustomer();

        // Text fields
        nameField.populate(customer.getFirstName() + " " + customer.getLastName());
        emailField.populate(customer.getEmail());
        phoneField.populate(customer.getTelephone());
        issueTypeField.populate(message.getMessageType());


        // Combo boxes
        categoryComboBox.removeAllItems(); // remove all to repopulate...

        for (String category : getCategories(message.getMessageType())) {
            categoryComboBox.addItem(category);
        }

        categoryComboBox.setSelectedItem(message.getCategory()); // selected category from the list of new options


        // Text areas
        detailsArea.populate(message.getDetails());
        boolean showRespondBtn = responsesArea.populate(message, loggedInCustomer);

        respondBtn.setVisible(showRespondBtn);
    }

    private String[] getCategories(String issueType){
        // If the form belongs to the make query page, return the necessary categories...
        if(issueType.equalsIgnoreCase("make a query")){
            return new String[]{
                "Career Path", "Language Support", "Job Opportunities", "Cultural Adjustment",
                "Internship Opportunities", "Scholarship Applications", "Immigration Requirements"
            };
        }

        // If the form belongs to the request service page, return the necessary categories...
        if(issueType.equalsIgnoreCase("request a service")){
            return new String[]{
                "Service Delivery", "Campus Security", "Facilities Related"
            };
        }

        // If the form belongs to the lodge complaint page, return the necessary categories...
        if(issueType.equalsIgnoreCase("lodge a complaint")){
            return new String[]{
                "Harassment", "Discrimination", "Disciplinary Matters", "Financial Complaints"
            };
        }

        return new String[]{comboBoxDefaultString};
    }


    private boolean canSubmit() {
        // Checking for invalid data, if any found, return false
        String details = detailsArea.getText();

        // If the details field is empty
        if(details.length() < 1 || details.trim().length() < 1){
            JOptionPane.showMessageDialog(null, "Details of the submission is required! Please enter a details here...", "Cannot Submit!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Form is valid and ready for submission...
        return true;
    }
}
