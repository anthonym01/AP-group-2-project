package views;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class view_template extends JFrame implements ActionListener {
    JFrame MainView;// Mainview must be filled with components by view children
    Logger loggner;// Log4j2

    public view_template() {
        loggner = LogManager.getLogger(view_template.class);
        loggner.info("Startup point");
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
    public void actionPerformed(ActionEvent e) {
        loggner.warn("Unhandled action: " + e.toString());
        // TODO Auto-generated method stub
        if (e.getSource() == MainView) {
            // Window event

        }
    }

}
