package com.halogen;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
	public static Connection conn = null;
	private static String server = "localhost";
	private static String db = "tracking";
	private static String user = "root";
	private static String pass = "Halo@2018";
	private static String timezone = "Africa/Lagos";
	
	public static void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String db_conn_string = "jdbc:mysql://"+server+"/"+db+"?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone="+timezone;
			conn = DriverManager.getConnection(db_conn_string, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
