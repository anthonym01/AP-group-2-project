import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class App {
    public static void main(String[] args) throws Exception {
        final Logger loggerite = LogManager.getLogger(App.class);
        loggerite.info("Launch");
    }
}
