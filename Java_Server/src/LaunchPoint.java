import controller.Server;
import models.Customer;
import models.Employee;

public class LaunchPoint {
    public static void main(String[] args) {
        test();

        //new Server();
    }

    public static void test() {

        Customer testload = new Customer("3", "test", "tmake", "1", "test@make.com", "87855884");
        testload.readAll();
        //Employee testemp = new Employee(12, 1, "test", "tester", "foop", "Awoooo","32524354");
        //testemp.create(testemp);
        
        // testemp.readAll();
    }
}
