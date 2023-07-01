import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Server {
    public static void main(String[] args) throws Exception {

        final Logger loggerite = LogManager.getLogger(Server.class);
        loggerite.info("Server launch");

        db_man database = new db_man();

        //database.test();// test database connection
        database.test_customer_table();
        database.test_change_Customer_password("test", "remote");

    }

}
