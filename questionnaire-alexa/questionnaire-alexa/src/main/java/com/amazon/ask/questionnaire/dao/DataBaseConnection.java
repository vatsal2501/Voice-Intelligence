package com.amazon.ask.questionnaire.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
	
	private static DataBaseConnection instance;

	private static Connection connection;

	/*
	 * String url =
	 * "jdbc:mysql://demo1.crynddqstuuq.us-east-1.rds.amazonaws.com/questionnaire_db";
	 * String username = "admin"; String password = "Vatsal2501";
	 */
	
	String url = "jdbc:mysql://alexa-1.cwon78mswd1e.us-east-1.rds.amazonaws.com/questionnaire_db";
	String username = "user";
	String password = "Zeelleo21";

	private DataBaseConnection() {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			this.connection = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
        return connection;
    }
	
	 public static DataBaseConnection getInstance() throws SQLException {
	        if (instance == null) {
	            instance = new DataBaseConnection();
	        } else if (instance.getConnection().isClosed()) {
	            instance = new DataBaseConnection();
	        }

	        return instance;
	    }

}
