package controllers;

import views.complaint.ComplaintView;

public class ComplaintController {
    private ComplaintView view;

    public ComplaintController(models.Complaint complaint) {
        this.view = new ComplaintView(this, complaint);
    }

    public ComplaintView getView() {
        return view;
    }
}
