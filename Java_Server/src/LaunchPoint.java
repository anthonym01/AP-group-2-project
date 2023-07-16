import controller.Server;
import models.Customer;

public class LaunchPoint {
    public static void main(String[] args) {
        test();

        new Server();
    }

    public static void test(){
        Customer testload = new Customer("1999", "test","testcusto", "mer", "test@test.com", "87855884");
        testload.delete("1999");
    }
}
