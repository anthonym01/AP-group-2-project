import org.apache.logging.log4j.Logger;

import views.statingpoint;
import views.tester;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    Logger loggner;// Log4j2

    public static void main(String[] args) {
        JFrame Appview = new statingpoint().getAppview();
        Appview.setSize(1280, 750);
        Appview.setVisible(true);
        Appview.repaint();
    }

    public App() {
        loggner = LogManager.getLogger(App.class);
        loggner.info("Startup");
    }

}
