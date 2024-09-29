package com.pk.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
	public static String url = "jdbc:postgresql://localhost:5432/db_mvc";
	public static String userName = System.getenv("dbUsername");
	public static String password = System.getenv("dbPassword");

	public static Connection getConnection() {
		
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");

			conn = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conn;
	}

}
