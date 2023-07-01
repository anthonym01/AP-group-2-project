import java.sql.*;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import classes.Customer;

//import classes.Customer;

public class db_man {
    private final String server_address = "jdbc:mysql://34.66.158.127:3306/apProject";
    private final String usernameString = "apProject";
    private final String passwordString = "appain";
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private Connection our_Connection = null;
    private ResultSet our_resultSet = null;
    private Statement our_statement = null;
    Logger loggerite;

    public db_man() {
        loggerite = LogManager.getLogger(db_man.class);
    }

    public void test() {// Test the database connection
        try {
            our_Connection = DriverManager.getConnection(server_address, usernameString, passwordString);

            if (our_Connection != null) {
                System.out.println("Remote server connection successful");
                // our_Connection.close();
            }
        } catch (Exception e) {
            System.out.println("connection failure" + our_Connection);
        }
    }

    public void test_customer_table() {// SELECT * FROM `customers`
        ArrayList<Customer> costomerList = new ArrayList<Customer>();

        String sql_query = "select * FROM customers";

        try {
            our_Connection = DriverManager.getConnection(server_address, usernameString, passwordString);

            if (our_Connection != null) {
                System.out.println("Remote server connection successful");

                our_statement = our_Connection.createStatement(); // create statement
                our_resultSet = our_statement.executeQuery(sql_query); // execute query and retrieve results/cursor

                while (our_resultSet.next()) { // while there are more records

                    Customer customer_tmp = new Customer();

                    customer_tmp.setCusId(our_resultSet.getString(1));
                    customer_tmp.setPassword(our_resultSet.getString(2));
                    customer_tmp.setFirstName(our_resultSet.getString(3));
                    customer_tmp.setLastName(our_resultSet.getString(4));
                    customer_tmp.setEmail(our_resultSet.getString(5));
                    customer_tmp.setTelNum(our_resultSet.getString(6));

                    costomerList.add(customer_tmp);
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(costomerList);
    }

    public boolean test_change_Customer_password(String customerID, String Newpassword) {// test update
        String sql = "UPDATE customers SET password = ? WHERE ID = ?";
        try {
            our_Connection = DriverManager.getConnection(server_address, usernameString, passwordString);
            // create prepared statement
            PreparedStatement ps = our_Connection.prepareStatement(sql);
            ps.setString(1, Newpassword); // Set the parameters at the indexes
            ps.setString(2, customerID);

            int numberOfAffectedRecords = ps.executeUpdate();
            return numberOfAffectedRecords == 1;// comparate to bolean

        } catch (SQLException e) {
            // handle accordingly
            loggerite.catching(e);
        }
        return false;
    }

}
