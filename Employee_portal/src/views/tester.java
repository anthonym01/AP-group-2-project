package views;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.apache.logging.log4j.LogManager;

public class tester extends view_template {

    public tester() {
        loggner = LogManager.getLogger(statingpoint.class);

        // create test view
        MainView = new JFrame("MainView", null);

        JLabel placeholder = new JLabel("test change");
        placeholder.setBounds(90, 15, 100, 30);
        MainView.add(placeholder);

        JButton change_to_startuppoint_btn = new JButton("Go to START view");
        change_to_startuppoint_btn.setBounds(80, 100, 100, 30);
        MainView.add(change_to_startuppoint_btn);

        change_to_startuppoint_btn.addActionListener(new ActionListener() {// make button actionable
            @Override
            public void actionPerformed(ActionEvent e) {
                goTostartupView();
            }
        });
    }

    public void goTostartupView() {
        loggner.info("Test change to startup action");

        // Repint test view with startup
        MainView.getContentPane().removeAll();
        MainView.repaint();

        statingpoint placeholderStatingpoint = new statingpoint();
        Component[] startupview_components = placeholderStatingpoint.getviewFrameComponents();
        for (Component component : startupview_components) {
            MainView.add(component);
        }
        placeholderStatingpoint.setMainView(MainView);// Needed for app actions later treat as a 'frame' of reference
        placeholderStatingpoint.dispose();// dispose of the hidden frame used to draw components
    }

}
