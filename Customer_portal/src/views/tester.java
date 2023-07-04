package views;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class tester extends JFrame implements ActionListener {
    JFrame MainView;
    Logger loggner;// Log4j2
    JButton change_to_startuppoint_btn;

    public tester() {
        loggner = LogManager.getLogger(statingpoint.class);

        // create test view
        MainView = new JFrame("MainView", null);

        JLabel placeholder = new JLabel("test change");
        placeholder.setBounds(90, 15, 100, 30);
        MainView.add(placeholder);

        change_to_startuppoint_btn = new JButton("Go to START view");
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

        statingpoint placeholStatingpoint = new statingpoint();
        Component[] startupview_components = placeholStatingpoint.getviewFrameComponents();
        for (Component component : startupview_components) {
            MainView.add(component);
        }
        placeholStatingpoint.setMainView(MainView);// Needed for app actions later treat as a 'frame' of reference
        placeholStatingpoint.dispose();// dispose of the hidden frame used to draw components
    }

    public Component[] getviewFrameComponents() {
        return MainView.getContentPane().getComponents();
    }

    public JFrame getMainView() {
        return MainView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    public void setMainView(JFrame MainView) {
        this.MainView = MainView;// Needed to repaint content later
    }

}
