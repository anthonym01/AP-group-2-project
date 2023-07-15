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
	private ObjectOutputStream os;
	private ObjectInputStream is;
	private Socket connection;
	private ServerSocket servSock;
	private static final Logger logger = LogManager.getLogger(Server.class);

	public Server() {
		this.createConnection();
		this.waitForRequests();
	}

	private void createConnection() {
		try {
			logger.warn("Attempting to setup Server Socket, Errors may occur");
			servSock = new ServerSocket(8888, 1);
			logger.info("Server Socket Successfully Configured");
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}

	public void getStream() {
		try {
			logger.warn("Attempting to setup Server Streams to client, Errors may occur");
			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());
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
				connection = servSock.accept();
				logger.info("C1ient requests accepted");
				this.getStream();
				do {
					try {
						logger.warn("Attempting to receive data from client, Errors may occur");
						action = (String) is.readObject();
						logger.info("Data Successfully received from client");
						switch (action) {
							case "Add Customer"://switch to model.example
								logger.warn("Attempting to receive data from client, Errors may occur");
								Customer obj = (Customer) is.readObject();
								logger.info("Data Successfully received from client :" + obj.getCusId());
								// add customer
								// CustomerHibernate ch = new CustomerHibernate();
								// ch.insertCustomer(obj);
								logger.warn("Attempting to send data to client, Errors may occur");
								os.writeObject(true);
								logger.info("Data Successfully sent from client");
								break;
							case "Add Complaint":
								// add code here
								break;
						}
					} catch (Exception ex) {
						logger.error(ex.getMessage());
						try {
							os.writeObject(false);
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
			logger.warn("Attempting to close Server Streams to client, Errors may occur");
			os.close();
			is.close();
			connection.close();
			logger.info("$erver Streams Successfully Closed to Client");
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}

}
