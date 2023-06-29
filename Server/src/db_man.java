import java.sql.*;

public class db_man {
    private final String server_address = "jdbc:mysql://34.66.158.127:3306/";
    private final String usernameString = "apProject";
    private final String passwordString = "appain";
    private Connection myConnection = null;

    public db_man(){

    }

    public void test() {// Test the database connection
        try {
            myConnection = DriverManager.getConnection(server_address, usernameString, passwordString);

            if (myConnection != null) {
                System.out.println("Remote server connection successful");
            }
        } catch (Exception e) {
            System.out.println("connection failure" + myConnection);
        }
    }
}
