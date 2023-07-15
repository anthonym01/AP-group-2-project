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
						logger.warn("Attempting to receive data from client, Errors may occur");
						action = (String) InputStream.readObject();
						logger.info("Data Successfully received from client");
						switch (action) {
							case "Test":
								logger.info("Client test connection");
								String payload = (String) InputStream.readObject();
								logger.info("Received: "+payload);
								OutputStream.writeObject(true);
							break;
							case "Add Customer"://switch to model.example
								logger.warn("Attempting to receive data from client, Errors may occur");
								Customer obj = (Customer) InputStream.readObject();
								logger.info("Data Successfully received from client :" + obj.getId());
								// add customer
								// CustomerHibernate ch = new CustomerHibernate();
								// ch.insertCustomer(obj);
								logger.warn("Attempting to send data to client, Errors may occur");
								OutputStream.writeObject(true);
								logger.info("Data Successfully sent from client");
								break;
							case "Add Complaint":
								// add code here
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
