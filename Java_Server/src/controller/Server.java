package controller;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import models.Customer;

/**
 *
 * @author Oniel
 */
public class Server {
	private ObjectOutputStream OutputStream;
	private ObjectInputStream InputStream;
	private Socket connection;
	private ServerSocket Socket;
	private static final Logger logger = LogManager.getLogger(Server.class);

	public Server() {
		this.createConnection();
		this.waitForRequests();
	}

	private void createConnection() {
		try {
			logger.warn("Attempting to setup Server Socket, Errors may occur");
			Socket = new ServerSocket(8888, 1);
			logger.info("Server Socket Successfully Configured");
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}

	public void getStream() {
		try {
			logger.warn("Attempting to setup Server Streams to client, Errors may occur");
			OutputStream = new ObjectOutputStream(connection.getOutputStream());
			InputStream = new ObjectInputStream(connection.getInputStream());
			logger.info("Server Streams Successfully Configured to Client");
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}

	public void waitForRequests() {
		String action = "";
		try {
			while (true) {
				logger.info("Server waiting for connections");
				connection = Socket.accept();
				logger.info("C1ient requests accepted");
				this.getStream();
				do {
					try {
						action = (String) InputStream.readObject();
						logger.info("Data Successfully received from client");
						switch (action) {
							case "Test":
								logger.info("Client test connection");
								String payload = (String) InputStream.readObject();
								logger.info("Received: " + payload);
								break;
							case "create Customer":// //Request customer creation action
								try {

									Customer tempCustomer = (Customer) InputStream.readObject();// data beamed from client
									logger.info("Create customer with ID :" + tempCustomer.getId());
									tempCustomer.create(tempCustomer);
									OutputStream.writeObject(true);// send reply to client
								} catch (Exception e) {
									// TODO: handle exception
									logger.warn(e.getMessage());
									OutputStream.writeObject(false);// send reply to client AFTER FAILIURE
								}

								break;
						}
					} catch (Exception ex) {
						logger.error(ex.getMessage());
						try {
							OutputStream.writeObject(false);
						} catch (IOException ioex) {
							logger.error(ioex.getMessage());
							break;
						}
					}
				} while (!action.equals("Exit"));
				this.closeConnection();
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	public void closeConnection() {
		try {
			logger.info("Close Server");
			OutputStream.close();
			InputStream.close();
			connection.close();
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}
}
