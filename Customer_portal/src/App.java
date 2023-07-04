
import views.statingpoint;
import javax.swing.JFrame;

public class App {
    JFrame MainView;

    public static void main(String[] args) {
        new App();
    }

    public App() {
        // App kickoff point
        MainView = new statingpoint().getMainView();
        MainView.setSize(1280, 750);
        MainView.setVisible(true);
        MainView.repaint();
    }

}
