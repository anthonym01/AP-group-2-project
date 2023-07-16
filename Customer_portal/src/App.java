
import views.statingpoint;
import javax.swing.JFrame;

import ComsStation.AppClient;
import models.Customer;

public class App {
    JFrame MainView;

    public static void main(String[] args) {
        new App();

        // test servcer connection

        /*
         * MyClient client =new MyClient();
         * client.sendAction("Add Customer");
         * client.sendCustomer(new Customer(1,"Oniel Charles"));
         * client.receiveResponse();
         * client.closeConnection();
         */
        /*
         * AppClient client = new AppClient();
         * Boolean sucess = client.createCustomer(new
         * Customer("158944","0000","fdfsf","gfggfg","sdfds@1.1","834845934"));
         * System.out.println("Sucess state? "+sucess);
         */

    }

    public App() {
        // App kickoff point
        MainView = new statingpoint().getMainView();
        MainView.setSize(1280, 750);
        MainView.setVisible(true);
        MainView.repaint();
    }

}
