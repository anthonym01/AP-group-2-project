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
    JFrame testview;
    Logger loggner;// Log4j2
    JButton change_to_startuppoint_btn;

    public tester() {
        loggner = LogManager.getLogger(statingpoint.class);

        // create test view
        testview = new JFrame("testview", null);

        JLabel placeholder = new JLabel("test change");
        placeholder.setBounds(90, 15, 100, 30);
        testview.add(placeholder);

        change_to_startuppoint_btn = new JButton("Go to START view");
        change_to_startuppoint_btn.setBounds(80, 100, 100, 30);
        testview.add(change_to_startuppoint_btn);

        change_to_startuppoint_btn.addActionListener(new ActionListener() {// make button actionable
            @Override
            public void actionPerformed(ActionEvent e) {
                loggner.info("Test change to startup action");

                // Repint test view with startup
                //testview = 
                testview.getContentPane().removeAll();
                testview.repaint();

                
                statingpoint placeholStatingpoint = new statingpoint();
                Component[] startupview_components = placeholStatingpoint.getviewFrame();
                for (Component component : startupview_components) {
                    testview.add(component);
                }

                placeholStatingpoint.setAppview(testview);//Needed for app actions later treat as a 'frame' of reference
                
            }
        });
    }

    public Component[] getviewFrame() {
        return testview.getContentPane().getComponents();
    }
    public JFrame getAppview(){
        return testview;
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    public void setAppview(JFrame appview) {
        this.testview = appview;//Needed to repaint content later
    }

}
