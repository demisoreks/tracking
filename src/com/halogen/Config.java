package com.halogen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Config {
	private static String getDetail(String column_name) {
		String result = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			if (DB.conn == null || DB.conn.isClosed()) {
				DB.connect();
			}
			
			stmt = DB.conn.createStatement();
			String query = "select * from config where id = 1";
			rs = stmt.executeQuery(query);
			
			if (rs.next()) {
				result = rs.getString(column_name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				DB.conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static String getAPIURL() {
		return getDetail("api_url");
	}
	
	public static String getServerKey() {
		return getDetail("server_key");
	}
	
	public static String getGeocodingAPIURL() {
		return getDetail("geocoding_api_url");
	}
	
	public static String getGeocodingAPIKey() {
		return getDetail("geocoding_api_key");
	}
}
