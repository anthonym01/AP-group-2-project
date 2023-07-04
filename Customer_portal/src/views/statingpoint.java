package views;

import javax.swing.JFrame;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class statingpoint extends view_template {
    Logger loggner;// Log4j2
    JButton change_to_test_btn;

    public statingpoint() {
        loggner = LogManager.getLogger(statingpoint.class);
        loggner.info("Startup point");

        // Create starting point view Frame
        MainView = new JFrame("Flow Customer", null);
        MainView.setSize(1300, 700);

        MainView.setLayout(null);
        MainView.setLocationRelativeTo(null);
        MainView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel placeholder = new JLabel("App placeholder");
        placeholder.setBounds(15, 15, 100, 30);
        MainView.add(placeholder);

        change_to_test_btn = new JButton("Go to test view");
        change_to_test_btn.setBounds(15, 100, 100, 30);
        MainView.add(change_to_test_btn);

        change_to_test_btn.addActionListener(new ActionListener() {// make button actionable
            @Override
            public void actionPerformed(ActionEvent e) {
                goTotestView();
            }
        });
    }

    public void goTotestView() {// change reference point to testview
        loggner.info("Test change action");

        // Re-paint Startup view with test view
        MainView.getContentPane().removeAll();
        MainView.repaint();

        tester testview = new tester();
        Component[] testview_components = testview.getviewFrameComponents();
        for (Component component : testview_components) {
            MainView.add(component);
        }
        testview.setMainView(MainView);// Needed for app actions later treat as a 'frame' of reference
        testview.dispose();// dispose of the hidden frame used to draw components

    }

}
