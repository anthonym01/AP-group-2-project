import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class startingpoint {
    void startingpoint() {

    }

    public static void main(String[] args) {
        final Logger loggerite = LogManager.getLogger(startingpoint.class);
        loggerite.info("Startup");
    }
}
