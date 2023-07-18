package views.complaint;

import controllers.ComplaintController;
import models.Complaint;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

public class ComplaintView extends JPanel {
    private ComplaintController controller;
    private Complaint complaint;
    private JLabel mainLabel, titleLabel, assignToLabel;
    private JButton backButton;
    private JTextArea descriptionArea;
    private JScrollPane scrollPane;
    private JTextField assignToField;
    private JPanel detailPanel;
    private JLabel typeLabel, dateCreatedLabel, createdByLabel;

    public ComplaintView(ComplaintController controller, Complaint complaint) {
        super(null);

        this.controller = controller;
        this.complaint = complaint;

        initComponents();
        addComponents();
        setUpListeners();
    }

    public void initComponents() {
        mainLabel = new JLabel("Complaint ID: ", SwingConstants.CENTER);
        mainLabel.setBounds(20, 20, 740, 40);

        backButton = new JButton("< Go Back");
        backButton.setBounds(20, 20, 150, 30);

        titleLabel = new JLabel(complaint.getTitle(), SwingConstants.CENTER);
        titleLabel.setBounds(20, 60, 530, 30);

        descriptionArea = new JTextArea("", 40, 20);
        descriptionArea.setBounds(20, 110, 530, 60);
        descriptionArea.setText(complaint.getDescription());
        descriptionArea.setEditable(false);
        scrollPane = new JScrollPane(descriptionArea);

        assignToLabel = new JLabel("Assign to: ");
        assignToLabel.setBounds(20, 190, 100, 30);

        assignToField = new JTextField(String.valueOf(complaint.getAssigned_to()), 50);
        assignToField.setBounds(140, 190, 410, 30);

        detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBorder(new BorderUIResource.LineBorderUIResource(Color.GRAY));
        detailPanel.setBounds(800-200-20, 60, 200, 150);

        typeLabel = new JLabel("Type: " + complaint.getServiceType());
        dateCreatedLabel = new JLabel("Date created: " + complaint.getDate_created().toString());
        createdByLabel = new JLabel("Created by: " + complaint.getCreatedBy());
    }

    public void addComponents() {
        add(mainLabel);
        add(backButton);
        add(titleLabel);
        add(descriptionArea);
        add(assignToLabel);
        add(assignToField);
        add(detailPanel);

        descriptionArea.add(scrollPane);
        detailPanel.add(typeLabel);
        detailPanel.add(dateCreatedLabel);
        detailPanel.add(createdByLabel);

        detailPanel.updateUI();
    }

    public void setUpListeners() {

    }


}
