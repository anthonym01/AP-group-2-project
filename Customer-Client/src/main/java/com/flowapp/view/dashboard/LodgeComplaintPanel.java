package com.flowapp.view.dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.flowapp.controller.Controller;
import com.flowapp.driver.Utils;
import com.flowapp.model.Customer;
import com.flowapp.view.forms.SubmissionForm;



public class LodgeComplaintPanel extends JPanel {
    private SubmissionForm form;
    private Controller controller;

    private JLabel formNameLabel;


    public LodgeComplaintPanel(Controller controller) {
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
        // Auto populate the name, email and phone # fields
        form.populateFormFields(customer);
    }

    // Setup window properties
    private void setupWindowProperties(){
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);
    }
        
    // Initialize components
    private void initComponents(){
        form = new SubmissionForm("lodge a complaint", controller);

        // Labels
        formNameLabel = new JLabel("Lodge a Complaint Form", SwingConstants.CENTER);

        // Update the font
        formNameLabel.setFont(Utils.getFont(Font.BOLD, 20));
    }

    // Add components to panel
    private void addComponentsToPanel(){
        add(formNameLabel, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
    }

    // Register listeners
    private void registerListeners(){
        
    }
    
}
