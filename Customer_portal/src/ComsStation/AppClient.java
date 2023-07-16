package ComsStation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import models.Customer;

public class AppClient {
	private ObjectOutputStream os;
	private ObjectInputStream is;
	private Socket connection;
	private static final Logger logger = LogManager.getLogger(AppClient.class);

	public AppClient() {
		this.createconnection();
		this.getStreams();
	}

	//a test connection
	public void test(String postload) {
		try {

			this.sendRequest("Test");
			os.writeObject(postload);
		} catch (Exception e) {
			logger.error(e.getMessage());

		}
	}

	private void createconnection() {
		try {
			logger.warn("Attempting to setup Client Socket, Errors may occur");
			connection = new Socket(InetAddress.getLocalHost(), 8888);
			logger.info("Socket Successfully Configured");
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}

	public final void getStreams() {
		try {
			logger.warn("Attempting to setup Socket, Errors may occur");
			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());
			logger.info("Client Streams Successfully Configured to Server");
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}

	public void closeConnection() {
		try {
			logger.warn("Attempting to close Client Streams to Server, Errors may occur");
			os.close();
			is.close();
			connection.close();
			logger.info("Client Streams Successfully Closed to Server");
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}

	public void sendRequest(String action) {
		try {
			os.writeObject(action);
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}


	
	public boolean createCustomer(Customer payload) {
		try {
			logger.info("request create customer: " + payload);
			os.writeObject("create Customer");
			os.writeObject(payload);
			Boolean sucess = (Boolean) is.readObject();
			closeConnection();
			return sucess;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	

}
