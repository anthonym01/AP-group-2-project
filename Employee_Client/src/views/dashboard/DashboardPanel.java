package views.dashboard;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private JLabel mainLabel, complaintLabel, queryLabel, requestLabel;
    private JLabel resolvedComplaintLbl, resolvedQueryLbl, resolvedReqLbl;
    private JLabel openComplaintLbl, openQueryLbl, openReqLbl;
    private JPanel issuePanel, complaintPanel, queryPanel, requestPanel;
    private JPanel complaintInfoPanel, queryInfoPanel, requestInfoPanel;
    private JButton complaintButton, queryButton, requestButton;

    public DashboardPanel() {
        super(null);
        initComponents();
        addComponents();
    }

    public void initComponents() {
        mainLabel = new JLabel("Employee Dashboard", SwingConstants.CENTER);
        mainLabel.setBounds(20, 20, 740, 40);

        issuePanel = new JPanel(new GridLayout(1, 3, 20, 0));
        issuePanel.setBounds(400-600/2, 100, 600, 300);

        complaintPanel = new JPanel(new BorderLayout());
        queryPanel = new JPanel(new BorderLayout());
        requestPanel = new JPanel(new BorderLayout());

        complaintLabel = new JLabel("Complaints", SwingConstants.CENTER);
        queryLabel = new JLabel("Queries", SwingConstants.CENTER);
        requestLabel = new JLabel("Requests", SwingConstants.CENTER);

        complaintInfoPanel = new JPanel();
        complaintInfoPanel.setLayout(new BoxLayout(complaintInfoPanel, BoxLayout.Y_AXIS));
        openComplaintLbl = new JLabel("Outstanding: ", SwingConstants.CENTER);
        resolvedComplaintLbl = new JLabel("Resolved: ", SwingConstants.CENTER);

        queryInfoPanel = new JPanel();
        queryInfoPanel.setLayout(new BoxLayout(queryInfoPanel, BoxLayout.Y_AXIS));
        openQueryLbl = new JLabel("Outstanding: ", SwingConstants.CENTER);
        resolvedQueryLbl = new JLabel("Resolved: ", SwingConstants.CENTER);

        requestInfoPanel = new JPanel();
        requestInfoPanel.setLayout(new BoxLayout(requestInfoPanel, BoxLayout.Y_AXIS));
        openReqLbl = new JLabel("Outstanding: ", SwingConstants.CENTER);
        resolvedReqLbl = new JLabel("Resolved: ", SwingConstants.CENTER);

        complaintButton = new JButton("View");
        queryButton = new JButton("View");
        requestButton = new JButton("View");
    }

    public void addComponents() {
        add(mainLabel);
        add(issuePanel);

        issuePanel.add(complaintPanel);
        issuePanel.add(queryPanel);
        issuePanel.add(requestPanel);

        complaintPanel.add(complaintLabel, BorderLayout.NORTH);
        complaintPanel.add(complaintInfoPanel, BorderLayout.CENTER);
        complaintPanel.add(complaintButton, BorderLayout.SOUTH);

        complaintInfoPanel.add(openComplaintLbl);
        complaintInfoPanel.add(resolvedComplaintLbl);

        queryPanel.add(queryLabel, BorderLayout.NORTH);
        queryPanel.add(queryInfoPanel, BorderLayout.CENTER);
        queryPanel.add(queryButton, BorderLayout.SOUTH);

        queryInfoPanel.add(openQueryLbl);
        queryInfoPanel.add(resolvedQueryLbl);

        requestPanel.add(requestLabel, BorderLayout.NORTH);
        requestPanel.add(requestInfoPanel, BorderLayout.CENTER);
        requestPanel.add(requestButton, BorderLayout.SOUTH);

        requestInfoPanel.add(openReqLbl);
        requestInfoPanel.add(resolvedReqLbl);

        issuePanel.updateUI();
    }

}
