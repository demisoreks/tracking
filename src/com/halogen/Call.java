package com.halogen;

import java.sql.SQLException;
import java.sql.Statement;

public class Call {
	public static void log(int user_id, int method_id, String parameters, String response) {
		Statement stmt = null;
		
		try {
			if (DB.conn == null || DB.conn.isClosed()) {
				DB.connect();
			}
			
			stmt = DB.conn.createStatement();
			String query = "insert into calls "
					+ "(user_id, method_id, parameters, response, call_date_time) "
					+ "values "
					+ "('"+user_id+"', '"+method_id+"', '"+parameters+"', '"+response+"', now())";
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				DB.conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
