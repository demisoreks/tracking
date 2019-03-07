package com.halogen;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	public int id;
	public String email;
	public String[] allowed_methods;
	public int tracker_daily_limit;
	public int global_daily_limit;
	public boolean exceed_limit;
	public boolean active;
	public String pass;
	public boolean authenticated;
	public boolean exists;
	
	public User (String email_address, String passcode) {
		this.email = email_address;
		this.pass = passcode;
	}
	
	private String md5Hash(String raw_string) {
		MessageDigest md5;
		String final_string = null;
		
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(StandardCharsets.UTF_8.encode(raw_string));
			final_string = String.format("%032x", new BigInteger(1, md5.digest()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return final_string;
	}
	
	private String multipleHash(String raw_string, int cycles) {
		String md5_string = md5Hash(raw_string);
		
		if (cycles == 1) {
			return md5_string;
		} else {
			return multipleHash(md5_string, cycles-1);
		}
	}
	
	private JSONObject getUser(String email) {
		try {
			if (DB.conn == null || DB.conn.isClosed()) {
				DB.connect();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Statement stmt = null;
		ResultSet rs = null;
		JSONObject user_details = new JSONObject();
		
		try {
			stmt = DB.conn.createStatement();
			String query = "select * from users where email = '"+email+"'";
			rs = stmt.executeQuery(query);
			
			if (rs.next()) {
				user_details.put("email", email);
				user_details.put("id", rs.getInt("id"));
				user_details.put("allowed_methods", rs.getString("allowed_methods"));
				user_details.put("tracker_daily_limit", rs.getInt("tracker_daily_limit"));
				user_details.put("global_daily_limit", rs.getInt("global_daily_limit"));
				user_details.put("exceed_limit", rs.getBoolean("exceed_limit"));
				user_details.put("active", rs.getBoolean("active"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
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
		
		return user_details;
	}
	
	public void authenticate() {
		JSONObject user_details = this.getUser(this.email);
		if (user_details.isNull("id")) {
			this.exists = false;
		} else {
			if (this.pass.equals(this.multipleHash(this.email, 5))) {
				try {
					this.id = user_details.getInt("id");
					this.allowed_methods = user_details.getString("allowed_methods").split(",");
					this.tracker_daily_limit = user_details.getInt("tracker_daily_limit");
					this.global_daily_limit = user_details.getInt("global_daily_limit");
					this.exceed_limit = user_details.getBoolean("exceed_limit");
					this.active = user_details.getBoolean("active");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.authenticated = true;
			} else {
				this.authenticated = false;
			}
			
			this.exists = true;
		}
	}
	
	public boolean authorize(int method_id) {
		for (int i=0; i<this.allowed_methods.length; i++) {
			if (method_id == Integer.parseInt(this.allowed_methods[i])) {
				return true;
			}
		}
		
		return false;
	}
}
