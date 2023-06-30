import java.sql.*;
import java.util.ArrayList;

import classes.Customer;

//import classes.Customer;

public class db_man {
    private final String server_address = "jdbc:mysql://34.66.158.127:3306/apProject";
    private final String usernameString = "apProject";
    private final String passwordString = "appain";
    String DRIVER = "com.mysql.jdbc.Driver";
    private Connection our_Connection = null;
    private ResultSet our_resultSet = null;
    private Statement our_statement = null;

    public db_man() {

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

        // `ID`, `password`, `first_name`, `last_name`, `Email`, `Contact_number` FROM
        // `customers`
        try {
            //Loads an instance
            Class.forName(DRIVER).newInstance();
            our_Connection = DriverManager.getConnection(server_address, usernameString, passwordString);

            if (our_Connection != null) {
                System.out.println("Remote server connection successful");
                // our_Connection.close();

                our_statement = our_Connection.createStatement(); // create statement
                our_resultSet = our_statement.executeQuery(sql_query); // execute query and retrieve results/cursor

                while (our_resultSet.next()) { // while there are more records
                    Customer customer_tmp = new Customer();

                    customer_tmp.setCusId(our_resultSet.getString("ID"));
                    costomerList.add(customer_tmp);
                }
                
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(costomerList);
    }
}
