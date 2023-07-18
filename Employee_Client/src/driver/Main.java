package driver;

import controllers.ComplaintController;
import controllers.ComplaintListController;
import models.Complaint;
import views.complaint.ComplaintList;
import views.dashboard.DashboardPanel;
import views.login.LoginPanel;
import window.Window;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Window window = new Window();
        Complaint complaint = new Complaint();
        ComplaintController controller = new ComplaintController(complaint);
        window.setActivePanel(controller.getView());

    }
}