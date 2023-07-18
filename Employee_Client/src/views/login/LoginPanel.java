package views.login;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JLabel mainLabel, idLabel, passwordLabel;
    private JTextField idField, passwordField;
    private JButton loginButton;

    public LoginPanel() {
        super(null);

        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {
        setBounds(0, 0, 800, 600);

        mainLabel = new JLabel("Welcome to the Flow App", SwingConstants.CENTER);
        mainLabel.setBounds(400-300/2, 20, 300, 30);

        idLabel = new JLabel("Staff ID:", SwingConstants.CENTER);
        idLabel.setBounds(400-300/2, 100, 300, 30);

        idField = new JTextField("", 50);
        idField.setBounds(400-300/2, 130, 300, 30);

        passwordLabel = new JLabel("Password: ", SwingConstants.CENTER);
        passwordLabel.setBounds(400-300/2, 160, 300, 30);

        passwordField = new JTextField("", 50);
        passwordField.setBounds(400-300/2, 190, 300, 30);

        loginButton = new JButton("Login");
        loginButton.setBounds(400-150/2, 400, 150, 30);
    }

    private void addComponents() {
        add(mainLabel);
        add(passwordField);
        add(passwordLabel);
        add(idField);
        add(idLabel);
        add(loginButton);
    }

    private void addListeners() {

    }
}
