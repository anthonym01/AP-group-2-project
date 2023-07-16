package com.flowapp.view.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.flowapp.controller.Controller;
import com.flowapp.model.Customer;
import com.flowapp.model.Message;
import com.flowapp.view.customized.CTextArea;
import com.flowapp.view.customized.CTextField;



public class SubmissionForm extends JPanel {
    private String formIdentifier;
    private Controller controller;
    private Customer loggedInCustomer;

    // Form Fields & Labels
    private JLabel nameLabel, emailLabel, phoneLabel, detailsLabel, categoryLabel;
    private CTextField nameField, emailField, phoneField;
    private CTextArea detailsArea;
    private JComboBox<String> categoryComboBox;

    private JPanel subPanel, buttonsPanel;

    private JButton saveBtn, clearBtn;

    private final String comboBoxDefaultString = "----------------------------------------------------------------";


    public SubmissionForm(String formIdentifier, Controller controller) {
        super();
        this.controller = controller;

        // The form identifier will be used to help set the necessary category information for each form displayed
        this.formIdentifier = formIdentifier; 
        

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

    // Setup window properties
    private void setupWindowProperties(){
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);
    }
        
    // Initialize components
    private void initComponents(){
        // Labels
        nameLabel = new JLabel("Name: ");
        emailLabel = new JLabel("Email: ");
        phoneLabel = new JLabel("Phone: ");
        detailsLabel = new JLabel("Details: ");
        categoryLabel = new JLabel("Category: ");

        // Text fields
        nameField = new CTextField("", 25);
        emailField = new CTextField("", 25);
        phoneField = new CTextField("", 25);

        // Text areas
        detailsArea = new CTextArea(10, 25);

        // Combo boxes
        categoryComboBox = new JComboBox<>(getCategories());
        categoryComboBox.setPrototypeDisplayValue(comboBoxDefaultString);

        // Panels
        subPanel = new JPanel(new GridBagLayout());
        subPanel.setBackground(Color.WHITE);

        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBackground(Color.WHITE);

        // Buttons
        saveBtn = new JButton("Save");
        clearBtn = new JButton("Clear");

        // Disable fields
        nameField.setEnabled(false);
        emailField.setEnabled(false);
        phoneField.setEnabled(false);
    }

    // Add components to panel
    private void addComponentsToPanel(){
        buttonsPanel.add(saveBtn);
        buttonsPanel.add(clearBtn);

        setupSubPanel();

        add(subPanel, BorderLayout.CENTER);
    }

    // Using this method to setup the sub-panel to have a grid bag layout manager
    private void setupSubPanel(){
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(5, 5, 5, 20);


        // First Row & First Column
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
        subPanel.add(categoryLabel, c);

        // Fourth Row & Second Column
        c.gridx = 1;
        c.gridy = 3;
        subPanel.add(categoryComboBox, c);


        // Fifth Row & First Column
        c.gridx = 0; 
        c.gridy = 4; 
        subPanel.add(detailsLabel, c);

        // Fifth Row & Second Column
        c.gridx = 1;
        c.gridy = 4;
        subPanel.add(new JScrollPane(detailsArea), c);


        // Sixth Row & First Column
        c.gridx = 1; 
        c.gridy = 5;
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
                String type = formIdentifier.equalsIgnoreCase("make a query") ? "Make a Query" : formIdentifier.equalsIgnoreCase("lodge a complaint") ? "Lodge a Complaint" : formIdentifier.equalsIgnoreCase("request a service") ? "Request a Service" : "Other";

                Message message = new Message();
                message.setMessageType(type);
                message.setCategory(category);
                message.setDetails(details);
                message.setCustomer(loggedInCustomer);
                message.setEmployee(null);
                message.setResponses(null);

                // Send the message to the server to be saved in the db
                controller.sendAction("add message");
                controller.sendMessage(message);

                controller.receiveResponse();
            }
        });

        // Clear Button
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //nameField.setText("");
                //emailField.setText("");
                //phoneField.setText("");
                detailsArea.setText("");
                categoryComboBox.setSelectedIndex(0);
            }
        });
    }

    // Validating the form identifier to determine what string array to return for the category combo box
    private String[] getCategories(){
        // If the form belongs to the make query page, return the necessary categories...
        if(formIdentifier.equalsIgnoreCase("make a query")){
            return new String[]{
                comboBoxDefaultString, "Career Path", "Language Support", "Job Opportunities", "Cultural Adjustment",
                "Internship Opportunities", "Scholarship Applications", "Immigration Requirements"
            };
        }

        // If the form belongs to the request service page, return the necessary categories...
        if(formIdentifier.equalsIgnoreCase("request a service")){
            return new String[]{
                comboBoxDefaultString, "Service Delivery", "Campus Security", "Facilities Related"
            };
        }

        // If the form belongs to the lodge complaint page, return the necessary categories...
        return new String[]{
            comboBoxDefaultString, "Harassment", "Discrimination", "Disciplinary Matters", "Financial Complaints"
        };
    }

    // Populate the form fields upon the availability of the user: name, email & phone number...
    public void populateFormFields(Customer customer){
        if(customer == null) return;

        loggedInCustomer = customer;
        nameField.populate(customer.getFirstName() + " " + customer.getLastName());
        emailField.populate(customer.getEmail());
        phoneField.populate(customer.getTelephone());
    }

    private boolean canSubmit() {
        // Checking for invalid data, if any found, return false
        String name = nameField.getText();
        String email = emailField.getText();
        String category = (String) categoryComboBox.getSelectedItem();
        String details = detailsArea.getText();

        // If the name field is empty
        if(name.length() < 1 || name.trim().length() < 1){
            JOptionPane.showMessageDialog(null, "Name is required! Please enter a name here...", "Cannot Submit!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // If the email field is empty
        if(email.length() < 1 || email.trim().length() < 1){
            JOptionPane.showMessageDialog(null, "Email is required! Please enter a email here...", "Cannot Submit!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // If the category field is empty
        if(category.length() < 1 || category.trim().length() < 1 || category.equalsIgnoreCase(comboBoxDefaultString)){
            JOptionPane.showMessageDialog(null, "Category is required! Please enter a category here...", "Cannot Submit!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // If the details field is empty
        if(details.length() < 1 || details.trim().length() < 1){
            JOptionPane.showMessageDialog(null, "Details of the submission is required! Please enter a details here...", "Cannot Submit!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Form is valid and ready for submission...
        return true;
    }
}
