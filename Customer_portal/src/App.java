
import views.statingpoint;
import javax.swing.JFrame;

public class App {

    public static void main(String[] args) {
        // App kickoff point
        JFrame Appview = new statingpoint().getAppview();
        Appview.setSize(1280, 750);
        Appview.setVisible(true);
        Appview.repaint();
    }

    public App() {
    }

}
