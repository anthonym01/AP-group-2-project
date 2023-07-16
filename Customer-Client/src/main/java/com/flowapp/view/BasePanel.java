package com.flowapp.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import com.flowapp.controller.Controller;



public class BasePanel extends JPanel {
    //private Controller controller;


    public BasePanel(Controller controller) {
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

    // Setup window properties
    private void setupWindowProperties(){
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);
    }
        
    // Initialize components
    private void initComponents(){

    }

    // Add components to panel
    private void addComponentsToPanel(){
        
    }

    // Register listeners
    private void registerListeners(){
        
    }
}
