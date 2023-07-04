import org.apache.logging.log4j.Logger;

import views.tester;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener {
    Logger loggner;// Log4j2
    JFrame Appview;

    JButton change_to_test_btn;

    public static void main(String[] args) {
        new App();
    }

    public App() {
        loggner = LogManager.getLogger(App.class);
        loggner.info("Startup");

        fillContents();
    }

    public void fillContents() {// The true starting point
        // Create App view Frame
        Appview = new JFrame("Flow Customer", null);
        Appview.setSize(1300, 700);

        Appview.setVisible(true);
        Appview.setLayout(null);
        Appview.setLocationRelativeTo(null);
        Appview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Appview.setSize(1280, 750);

        JLabel placeholder = new JLabel("App placeholder");
        placeholder.setBounds(15, 15, 100, 30);
        Appview.add(placeholder);

        change_to_test_btn = new JButton("Go to test view");
        change_to_test_btn.setBounds(15, 100, 100, 30);
        Appview.add(change_to_test_btn);

        change_to_test_btn.addActionListener(new ActionListener() {// make button actionable
            @Override
            public void actionPerformed(ActionEvent e) {
                loggner.info("Test change action");
                tester test_chage_placeholder = new tester();
                Appview = test_chage_placeholder.getviewFrame();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if (arg0.getSource() == Appview) {
            // Window event
            loggner.info("User closed main wundow");
        } else if (arg0.getSource() == change_to_test_btn) {

            // tester test_chage_placeholder = new tester();
        }
    }
}
