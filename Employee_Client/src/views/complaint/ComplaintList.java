package views.complaint;

import controllers.ComplaintListController;
import models.Complaint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComplaintList extends JPanel {
    private final ComplaintListController controller;
    private JLabel mainLabel, categoryLbl;
    private JButton backButton;
    private JComboBox<String> categorySelector;
    private JPanel listPanel;


    public ComplaintList(ComplaintListController controller) {
        super(null);
        this.controller = controller;
        initComponents();
        addComponents();
        setUpListeners();
    }

    public JPanel addComplaint(Complaint complaint) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(complaint.getTitle(), SwingConstants.CENTER);
        JLabel creator = new JLabel(complaint.getCreatedBy(), SwingConstants.CENTER);
        JLabel category = new JLabel(complaint.getServiceType(), SwingConstants.CENTER);
        JLabel dateCreated = new JLabel(complaint.getDate_created().toString(), SwingConstants.CENTER);

        String assigned = complaint.getAssigned_to() != -1 ? String.valueOf(complaint.getAssigned_to()) : "Nobody";
        JLabel assignedToLbl = new JLabel("Assigned to: " + assigned, SwingConstants.CENTER);
        JButton viewButton = new JButton("View");

        listPanel.add(mainPanel);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        infoPanel.add(title);
        infoPanel.add(creator);
        infoPanel.add(category);
        infoPanel.add(dateCreated);

        actionPanel.add(assignedToLbl);
        actionPanel.add(viewButton);

        listPanel.updateUI();
        return mainPanel;
    }

    private void initComponents() {
        mainLabel = new JLabel("Complaints", SwingConstants.CENTER);
        mainLabel.setBounds(20, 20, 740, 40);

        backButton = new JButton("< Go Back");
        backButton.setBounds(20, 20, 150, 30);

        categoryLbl = new JLabel("Category: ");
        categoryLbl.setBounds(20, 80, 100, 30);

        categorySelector = new JComboBox<>(new String[]{"All"});
        categorySelector.setBounds(140, 80, 150, 30);
        categorySelector.setSelectedItem("All");

        listPanel = new JPanel(new GridLayout(0, 5, 20, 20));
        listPanel.setBounds(20, 130, 740, 350);
    }

    private void addComponents() {
        add(mainLabel);
        add(backButton);
        add(categoryLbl);
        add(categorySelector);
        add(listPanel);
    }

    private void setUpListeners() {
        categorySelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showCategoryPanels((String) categorySelector.getSelectedItem());
            }
        });
    }

    public JPanel getListPanel() {
        return listPanel;
    }

    public JComboBox<String> getCategorySelector() {
        return categorySelector;
    }
}
