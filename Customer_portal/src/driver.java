import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class driver {
    
    public driver() {

    }

    public static void main(String[] args) {
        final Logger loggerite = LogManager.getLogger(driver.class);
        loggerite.info("Launch");
    }
}
