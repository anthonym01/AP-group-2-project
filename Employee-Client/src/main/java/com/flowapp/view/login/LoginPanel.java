package com.flowapp.view.login;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.flowapp.driver.Utils;
import com.flowapp.view.customized.CPasswordField;
import com.flowapp.view.customized.CTextField;
import com.flowapp.view.listeners.AuthListener;



public class LoginPanel extends JPanel {
    // Panels
    private JPanel mediaPanel, buttonPanel, northPanel, middlePanel, fieldPanel;
    
    // Textfields
    private CTextField usernameField;

    // Password fields
    private CPasswordField passwordField;

    // Buttons
    private JButton loginBtn, clearBtn;


    // Listeners
    private AuthListener authListener;


    public LoginPanel() { 
        super();

        setupWindowProperties();

        initComponents();
        addComponentsToPanel();
        registerListeners();

        focusOnLoginPanel();
        // autoLogin();
    }

    // Setters
    public void setAuthListener(AuthListener authListener) {
        this.authListener = authListener;
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
        // Panels
        northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        middlePanel = new JPanel(new BorderLayout());
        fieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mediaPanel = new JPanel(new BorderLayout());

        // Set the background color on the panels
        northPanel.setBackground(Color.WHITE);
        middlePanel.setBackground(Color.WHITE);
        buttonPanel.setBackground(Color.WHITE);
        mediaPanel.setBackground(Color.WHITE);
        fieldPanel.setBackground(Color.WHITE);

        // Textfields
        usernameField = new CTextField("ID", 20);
        passwordField = new CPasswordField("Password", 20);

        // Buttons
        loginBtn = new JButton("Login");
        clearBtn = new JButton("Clear");
    }

    // Add components to panel
    private void addComponentsToPanel(){
        JLabel label = new JLabel("Welcome to the Flow App!", SwingConstants.CENTER);
        label.setFont(Utils.getFont(Font.BOLD, 15));

        // JLabel iconLabel = new JLabel(Utils.getIcon("black-flow.png"));
        JLabel iconLabel = new JLabel(Utils.getIcon("logo.png"));

        
        // Add sub-panels to main panel
        northPanel.add(label);

        buttonPanel.add(loginBtn);
        buttonPanel.add(clearBtn);

        fieldPanel.add(usernameField);
        fieldPanel.add(passwordField);
        fieldPanel.add(buttonPanel);

        mediaPanel.add(iconLabel, BorderLayout.CENTER);
        mediaPanel.add(fieldPanel, BorderLayout.SOUTH);

        middlePanel.add(mediaPanel, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
    }

    // Register listeners
    private void registerListeners(){
        // Login Button
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                focusOnLoginPanel();

                // If the information cannot be submitted, return without executing logging in the user...
                if(!canSubmit()) return;


                // Using another thread so the program doesn't glitch or stuch...
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String username = usernameField.getText();
                        String password = new String(passwordField.getPassword());

                        if(authListener != null){
                            boolean authenticated = authListener.login(username, password);

                            if(authenticated){
                                // Clear the form fields after logging in successfully
                                clearBtn.doClick();
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Your credentials must be incorrect... Please try again!", "Cannot Login", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });

                thread.start();
            }
        });

        // Clear Button
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                focusOnLoginPanel();
                
                usernameField.reset();
                ((CPasswordField) passwordField).reset();
            }
        });
    }

    // Check if the form can be submitted
    private boolean canSubmit() {
        // If the login form is empty when the login button is clicked
        if(!usernameField.isTextDifferent() && !passwordField.isTextDifferent()){
            JOptionPane.showMessageDialog(LoginPanel.this, "Please enter your ID and password and try again...", "Empty fields!", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        // If the ID field is empty when the login button is clicked
        if(!usernameField.isTextDifferent()){
            JOptionPane.showMessageDialog(LoginPanel.this, "ID field cannot be empty... Please enter your ID #.", "Empty field!", JOptionPane.ERROR_MESSAGE);
            
            return false;
        }

        // If the passord field is empty when the login button is clicked
        if(!passwordField.isTextDifferent()){
            JOptionPane.showMessageDialog(LoginPanel.this, "Password field cannot be empty... Please enter your password.", "Empty field!", JOptionPane.ERROR_MESSAGE);
            
            return false;
        }

        // If the above options are not true, then the form can be submitted
        return true;
    }

    // AUTO LOGIN FOR DEVELOPMENT...
    private void autoLogin(){
        // Rep: 000-22-9034 - zK]3MfWBg)
        // Technician: 013-36-4063 - qS)8AH$!@8

        // Rep
        usernameField.populate("000-22-9034");
        passwordField.populate("zK]3MfWBg)");

        // Technician
        //usernameField.populate("013-36-4063");
        //passwordField.populate("qS)8AH$!@8");
    }
}
