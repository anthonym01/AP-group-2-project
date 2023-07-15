package ComsStation.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import models.Customer;

public class MyClient {
		private ObjectOutputStream os;
		private ObjectInputStream is;
		private Socket connection;
		private static final Logger logger = LogManager.getLogger(MyClient.class);
		public MyClient(){
		   this.createconnection();
		   this.getStreams();
		}
	
		private void createconnection() {
			try{
				logger.warn("Attempting to setup Client Socket, Errors may occur");				
				connection = new Socket(InetAddress.getLocalHost(),8888);
				logger.info("Socket Successfully Configured");
			}catch(IOException ex){
			    logger.error(ex.getMessage());
			}
		}
			
		public final void getStreams(){
			try{
				logger.warn("Attempting to setup Socket, Errors may occur");
				os = new ObjectOutputStream(connection.getOutputStream());
				is = new ObjectInputStream(connection.getInputStream());
				logger.info("Client Streams Successfully Configured to Server");
			}catch(IOException ex){
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
			}catch(IOException ex){
			   logger.error(ex.getMessage());
			}
		}
		public void sendAction(String action){
			try {
				logger.warn("Attempting to send information to Server, Errors may occur");
				os.writeObject(action);
			    logger.info("CIient Streams Successfully Closed to Server");
			}
			catch(IOException ex){
			   logger.error("Data Hot Sent to Server\n" + ex.getMessage());
			}
		}
		
		public void sendCustomer(Customer obj){
			try{
			  logger.warn("Attempting to send information to Server, Errors may occur");
			  os.writeObject(obj);
			  logger.info("Data Successfully Sent to Server");
			}
			catch(IOException ex){
			  logger.error("Data Not Sent to Server\n" + ex.getMessage());
			}
		  }
		public void receiveResponse(){
			try {
				logger.warn("Attempting to receive information from Server, Errors may occur");
				Boolean flag = (Boolean)is.readObject();
				logger.info("Data Successfully Received from Server");
				logger.info("Received: '" + flag + "' from Server");
			}catch(ClassCastException | ClassNotFoundException | IOException ex){
			    logger.error(ex.getMessage());
			}
		}


}
