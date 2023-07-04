
import views.statingpoint;
import javax.swing.JFrame;

public class App {

    public static void main(String[] args) {
        // App kickoff point
        JFrame MainView = new statingpoint().getMainView();
        MainView.setSize(1280, 750);
        MainView.setVisible(true);
        MainView.repaint();
    }

    public App() {
    }

}
