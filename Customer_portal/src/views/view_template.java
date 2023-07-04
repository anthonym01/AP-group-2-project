package views;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class view_template extends JFrame implements ActionListener {
    JFrame MainView;// Mainview must be filled with components by view children

    public view_template() {

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
        // TODO Auto-generated method stub
        if (e.getSource() == MainView) {
            // Window event
            
        }
    }

}
