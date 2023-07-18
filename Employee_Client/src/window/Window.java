package window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class Window extends JFrame {
    private JPanel activePanel;
    private JPanel previousPanel;

    public Window() {
        super("Flow Employee App");
        initWindow();
    }

    public void setActivePanel(JPanel panel) {
        if (this.activePanel != null) {
            this.remove(this.activePanel);
            this.previousPanel = this.activePanel;
        }

        this.activePanel = panel;
        activePanel.setBounds(0, 0, 800, 600);
        this.add(activePanel);
        this.repaint();
    }

    public void returnToPreviousPanel() {
        if (this.previousPanel != null) {
            this.setActivePanel(this.previousPanel);
        }
    }

    private void initWindow() {
        setSize(800, 600);
        setBackground(Color.RED);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
