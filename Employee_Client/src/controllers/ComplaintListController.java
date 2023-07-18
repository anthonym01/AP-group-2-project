package controllers;

import models.Complaint;
import views.complaint.ComplaintList;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ComplaintListController {
    public static final String[] categories = {
            "Broadband",
            "Mobile"
    };
    private ComplaintList view;
    private HashMap<Complaint, JPanel> complaintList;

    public ComplaintListController() {
        this.view = new ComplaintList(this);
        this.complaintList = new HashMap<>();

        addCategoryOptions();
        testComplaintList();
    }

    public void addCategoryOptions() {
        for (String option : categories)
            view.getCategorySelector().addItem(option);
    }

    public void showCategoryPanels(String category) {
        // Remove all added panels
        complaintList.forEach((complaint, jPanel) -> {
            view.getListPanel().remove(jPanel);
        });

        // Then we add them back
        complaintList.forEach(((complaint, jPanel) -> {
            if (category.equalsIgnoreCase("All")) {
                view.getListPanel().add(jPanel);
            } else {
                if (complaint.getServiceType().equalsIgnoreCase(category)) {
                    view.getListPanel().add(jPanel);
                } else {
                    view.getListPanel().remove(jPanel);
                }
            }

            view.getListPanel().updateUI();
        }));
    }

    private void testComplaintList() {
        Complaint complaint = new Complaint();
        complaintList.put( complaint, view.addComplaint(complaint) );

        complaint = new Complaint();
        complaint.setServiceType("Mobile");
        complaint.setAssigned_to(-1);
        complaintList.put( complaint, view.addComplaint(complaint) );
    }

    public ComplaintList getView() {
        return view;
    }
}
