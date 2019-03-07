package com.halogen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Method {
	public int id;
	public String name;
	public boolean active;
	
	public Method (int method_id) {
		this.id = method_id;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			if (DB.conn == null || DB.conn.isClosed()) {
				DB.connect();
			}
			
			stmt = DB.conn.createStatement();
			String query = "select * from methods where id = "+this.id;
			rs = stmt.executeQuery(query);
			
			if (rs.next()) {
				this.name = rs.getString("name");
				this.active = rs.getBoolean("active");
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
	}
}
