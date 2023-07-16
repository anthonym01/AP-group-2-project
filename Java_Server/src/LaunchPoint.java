import controller.Server;
import models.Customer;

public class LaunchPoint {
    public static void main(String[] args) {
        test();

        new Server();
    }

    public static void test(){
        Customer testload = new Customer("1584", "test","tes update", "1", "test@update.com", "87855884");
        testload.update(testload);
    }
}
