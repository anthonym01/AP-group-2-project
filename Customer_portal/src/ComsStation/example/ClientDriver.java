package ComsStation.example;

import models.Customer;

public class ClientDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyClient client =new MyClient();
		client.sendAction("Add Customer");
		client.sendCustomer(new Customer(1,"Oniel Charles"));
        client.receiveResponse();
        client.closeConnection();
	}

}
