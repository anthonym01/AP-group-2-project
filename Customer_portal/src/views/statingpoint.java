package views;

import javax.swing.JFrame;
import views.tester;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class statingpoint extends JFrame implements ActionListener {
    Logger loggner;// Log4j2
    JFrame Appview;

    JButton change_to_test_btn;

    public statingpoint() {
        loggner = LogManager.getLogger(statingpoint.class);
        loggner.info("Startup");

        Create_startup_screen();
    }

    public void Create_startup_screen() {// The true starting point
        // Create App view Frame
        Appview = new JFrame("Flow Customer", null);
         Appview.setSize(1300, 700);

        
        Appview.setLayout(null);
        Appview.setLocationRelativeTo(null);
        Appview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

                // Repint Startup view with test view
                Appview.getContentPane().removeAll();
                Appview.repaint();

                tester testview = new tester();
                Component[] testview_components = testview.getviewFrame();
                for (Component component : testview_components) {
                    Appview.add(component);
                }
                testview.setAppview(Appview);//Needed for app actions later treat as a 'frame' of reference
            }
        });
    }

    public Component[] getviewFrame() {
        return Appview.getContentPane().getComponents();
    }

    public JFrame getAppview() {
        return Appview;
    }

    public void setAppview(JFrame appview) {
        this.Appview = appview;// Needed to repaint content later
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
