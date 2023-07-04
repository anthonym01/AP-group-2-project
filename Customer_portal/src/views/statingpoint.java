package views;

import javax.swing.JFrame;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class statingpoint extends JFrame implements ActionListener {
    Logger loggner;// Log4j2
    JFrame MainView;// Mainview
    JButton change_to_test_btn;

    public statingpoint() {
        loggner = LogManager.getLogger(statingpoint.class);
        loggner.info("Startup point");

        Create_startup_screen();
    }

    public void Create_startup_screen() {// The true starting point
        // Create App view Frame
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

    public Component[] getviewFrameComponents() {
        return MainView.getContentPane().getComponents();
    }

    public JFrame getMainView() {
        return MainView;
    }

    public void setMainView(JFrame MainView) {
        this.MainView = MainView;// Needed to repaint content later
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if (arg0.getSource() == MainView) {
            // Window event
            loggner.info("User closed main wundow");
        } else if (arg0.getSource() == change_to_test_btn) {

            // tester test_chage_placeholder = new tester();
        }
    }
}
