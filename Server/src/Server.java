//https://www.javatpoint.com/steps-to-connect-to-the-database-in-java

public class Server {
    public static void main(String[] args) throws Exception {
        db_man database = new db_man();

        //database.test();// test database connection
        database.test_customer_table();
    }

}
