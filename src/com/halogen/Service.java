package com.halogen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/service")
public class Service {
	private static String api_url = Config.getAPIURL();
	private static String server_key = Config.getServerKey();
	
	private static String getUserAPIKey(String email) {
		String output = "";
		
		try {
			URL url = new URL(api_url+"?api=server&ver=1.0&key="+server_key+"&cmd=GET_USER_API_KEY,"+email);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "text/plain");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : "+conn.getResponseCode());
			}
			
			InputStream is;
			
			try {
				is = conn.getInputStream();
			} catch (Exception ex) {
				is = conn.getErrorStream();
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = br.readLine()) != null) {
				output += line;
			}
			
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
	
	private static JSONArray getUserObjects(String email) {
		String output = "";
		JSONArray objects = null;
		
		try {
			URL url = new URL(api_url+"?api=user&ver=1.0&key="+getUserAPIKey(email)+"&cmd=USER_GET_OBJECTS");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : "+conn.getResponseCode());
			}
			
			InputStream is;
			
			try {
				is = conn.getInputStream();
			} catch (Exception ex) {
				is = conn.getErrorStream();
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = br.readLine()) != null) {
				output += line;
			}
			
			objects = new JSONArray(output);
			
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objects;
	}
	
	private static JSONObject objectGetLocations(String email, String imei) {
		String output = "";
		JSONObject location = null;
		
		try {
			URL url = new URL(api_url+"?api=user&ver=1.0&key="+getUserAPIKey(email)+"&cmd=OBJECT_GET_LOCATIONS,"+imei);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : "+conn.getResponseCode());
			}
			
			InputStream is;
			
			try {
				is = conn.getInputStream();
			} catch (Exception ex) {
				is = conn.getErrorStream();
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = br.readLine()) != null) {
				output += line;
			}
			
			location = new JSONObject(output);
			
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return location;
	}
	
	private static JSONObject getGoogleAddress(String latlng) {
		String output = "";
		JSONObject address = null;
		
		try {
			URL url = new URL(Config.getGeocodingAPIURL()+"?latlng="+latlng+"&key="+Config.getGeocodingAPIKey());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : "+conn.getResponseCode());
			}
			
			InputStream is;
			
			try {
				is = conn.getInputStream();
			} catch (Exception ex) {
				is = conn.getErrorStream();
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = br.readLine()) != null) {
				output += line;
			}
			
			address = new JSONObject(output);
			
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return address;
	}
	
	private static JSONArray objectGetMessages(String email, String imei, String start_date_time, String end_date_time) {
		String output = "";
		JSONArray messages = null;
		
		try {
			URL url = new URL(api_url+"?api=user&ver=1.0&key="+getUserAPIKey(email)+"&cmd=OBJECT_GET_MESSAGES,"+imei+","+URLEncoder.encode(start_date_time, "UTF-8")+","+URLEncoder.encode(end_date_time, "UTF-8"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : "+conn.getResponseCode());
			}
			
			InputStream is;
			
			try {
				is = conn.getInputStream();
			} catch (Exception ex) {
				is = conn.getErrorStream();
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = br.readLine()) != null) {
				output += line;
			}
			
			messages = new JSONArray(output.trim());
			
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return messages;
	}
	
	@GET
	@Path("/getObjects")
	@Produces(MediaType.APPLICATION_JSON)
	public String getObjects(@QueryParam("email") String email, @QueryParam("passcode") String passcode) {
		User user = new User(email, passcode);
		user.authenticate();
		Method method = new Method(1);
		
		JSONObject response = new JSONObject();
		JSONObject parameters = new JSONObject();

		try {
			if (!method.active) {
				response.put("response_code", "7701");
				response.put("response_message", "METHOD IS INACTIVE");
			} else if (!user.authorize(method.id)) {
				response.put("response_code", "7706");
				response.put("response_message", "USER IS NOT AUTHORIZED TO USE THIS METHOD");
			} else if (!user.exists) {
				response.put("response_code", "7702");
				response.put("response_message", "USER EMAIL DOES NOT EXIST");
			} else if (!user.authenticated) {
				response.put("response_code", "7703");
				response.put("response_message", "PASSCODE IS INVALID");
			} else if (!user.active) {
				response.put("response_code", "7704");
				response.put("response_message", "USER IS INACTIVE");
			} else if (getUserAPIKey(email).equals("")) {
				response.put("response_code", "7705");
				response.put("response_message", "CANNOT FETCH USER API KEY");
			} else {
				response.put("response_code", "00");
				response.put("response_message", "OPERATION SUCCESSFUL");
				response.put("data", getUserObjects(email));
			}
			
			parameters.put("email", email);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Call.log(user.id, method.id, parameters.toString(), response.toString());
		
		return response.toString();
	}
	
	@GET
	@Path("/getLastStatus")
	@Produces(MediaType.APPLICATION_JSON)
	public String getLastStatus(@QueryParam("email") String email, @QueryParam("passcode") String passcode, @QueryParam("imei") String imei) {
		User user = new User(email, passcode);
		user.authenticate();
		Method method = new Method(2);
		
		JSONObject response = new JSONObject();
		JSONObject parameters = new JSONObject();
		
		try {
			if (!method.active) {
				response.put("response_code", "7701");
				response.put("response_message", "METHOD IS INACTIVE");
			} else if (!user.authorize(method.id)) {
				response.put("response_code", "7706");
				response.put("response_message", "USER IS NOT AUTHORIZED TO USE THIS METHOD");
			} else if (!user.exists) {
				response.put("response_code", "7702");
				response.put("response_message", "USER EMAIL DOES NOT EXIST");
			} else if (!user.authenticated) {
				response.put("response_code", "7703");
				response.put("response_message", "PASSCODE IS INVALID");
			} else if (!user.active) {
				response.put("response_code", "7704");
				response.put("response_message", "USER IS INACTIVE");
			} else if (getUserAPIKey(email).equals("")) {
				response.put("response_code", "7705");
				response.put("response_message", "CANNOT FETCH USER API KEY");
			} else {
				response.put("response_code", "00");
				response.put("response_message", "OPERATION SUCCESSFUL");
				response.put("data", objectGetLocations(email, imei));
			}
			
			parameters.put("email", email);
			parameters.put("imei", imei);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Call.log(user.id, method.id, parameters.toString(), response.toString());
		
		return response.toString();
	}
	
	@GET
	@Path("/getStatusHistory")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStatusHistory(@QueryParam("email") String email, @QueryParam("passcode") String passcode, @QueryParam("imei") String imei, @QueryParam("start_date_time") String start_date_time, @QueryParam("end_date_time") String end_date_time) {
		User user = new User(email, passcode);
		user.authenticate();
		Method method = new Method(3);
		
		JSONObject response = new JSONObject();
		JSONObject parameters = new JSONObject();
		
		try {
			if (!method.active) {
				response.put("response_code", "7701");
				response.put("response_message", "METHOD IS INACTIVE");
			} else if (!user.authorize(method.id)) {
				response.put("response_code", "7706");
				response.put("response_message", "USER IS NOT AUTHORIZED TO USE THIS METHOD");
			} else if (!user.exists) {
				response.put("response_code", "7702");
				response.put("response_message", "USER EMAIL DOES NOT EXIST");
			} else if (!user.authenticated) {
				response.put("response_code", "7703");
				response.put("response_message", "PASSCODE IS INVALID");
			} else if (!user.active) {
				response.put("response_code", "7704");
				response.put("response_message", "USER IS INACTIVE");
			} else if (getUserAPIKey(email).equals("")) {
				response.put("response_code", "7705");
				response.put("response_message", "CANNOT FETCH USER API KEY");
			} else {
				response.put("response_code", "00");
				response.put("response_message", "OPERATION SUCCESSFUL");
				
				String data = objectGetMessages(email, imei, start_date_time, end_date_time).toString().trim();
				data = data.substring(2, data.length()-2);
				String[] split_data = data.split("\\],\\[");
				
				JSONArray history = new JSONArray();
				
				for (int i=0; i<split_data.length; i++) {
					String[] split_data2 = split_data[i].split(",\\{");
					JSONObject params = new JSONObject("{"+split_data2[1]);
					JSONObject single = new JSONObject();
					
					String[] split_data3 = split_data2[0].split(",");
					
					single.put("params", params);
					single.put("status_date_time", split_data3[0].substring(1, split_data3[0].length()-1));
					single.put("latitude", split_data3[1].substring(1, split_data3[1].length()-1));
					single.put("longitude", split_data3[2].substring(1, split_data3[2].length()-1));
					single.put("altitude", split_data3[3].substring(1, split_data3[3].length()-1));
					single.put("angle", split_data3[4].substring(1, split_data3[4].length()-1));
					single.put("speed", split_data3[5].substring(1, split_data3[5].length()-1));
					
					history.put(single);
				}
				
				response.put("data", history);
			}
			
			parameters.put("email", email);
			parameters.put("imei", imei);
			parameters.put("start_date_time", start_date_time);
			parameters.put("end_date_time", end_date_time);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Call.log(user.id, method.id, parameters.toString(), response.toString());
		
		return response.toString();
	}
	
	@GET
	@Path("/getAddress")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAddress(@QueryParam("email") String email, @QueryParam("passcode") String passcode, @QueryParam("latlng") String latlng) {
		User user = new User(email, passcode);
		user.authenticate();
		Method method = new Method(4);
		
		JSONObject response = new JSONObject();
		JSONObject parameters = new JSONObject();
		
		try {
			if (!method.active) {
				response.put("response_code", "7701");
				response.put("response_message", "METHOD IS INACTIVE");
			} else if (!user.authorize(method.id)) {
				response.put("response_code", "7706");
				response.put("response_message", "USER IS NOT AUTHORIZED TO USE THIS METHOD");
			} else if (!user.exists) {
				response.put("response_code", "7702");
				response.put("response_message", "USER EMAIL DOES NOT EXIST");
			} else if (!user.authenticated) {
				response.put("response_code", "7703");
				response.put("response_message", "PASSCODE IS INVALID");
			} else if (!user.active) {
				response.put("response_code", "7704");
				response.put("response_message", "USER IS INACTIVE");
			} else if (getUserAPIKey(email).equals("")) {
				response.put("response_code", "7705");
				response.put("response_message", "CANNOT FETCH USER API KEY");
			} else {
				response.put("response_code", "00");
				response.put("response_message", "OPERATION SUCCESSFUL");
				response.put("data", getGoogleAddress(latlng));
			}
			
			parameters.put("latlng", latlng);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Call.log(user.id, method.id, parameters.toString(), response.toString());
		
		return response.toString();
	}
	
	/*public static void main(String[] args) {
		Service service = new Service();
		System.out.println(service.getObjects("paul.oguche@halogensecurity.com", "c83793fb85cea4b393a7e4fe8be33a6c"));
		System.out.println(service.getLastStatus("paul.oguche@halogensecurity.com", "c83793fb85cea4b393a7e4fe8be33a6c", "864495030192691"));
		System.out.println(service.getStatusHistory("paul.oguche@halogensecurity.com", "c83793fb85cea4b393a7e4fe8be33a6c", "864495030192691", "2018-11-07 14:20:00", "2018-11-07 14:30:00"));
		System.out.println(service.getAddress("paul.oguche@halogensecurity.com", "c83793fb85cea4b393a7e4fe8be33a6c", "6.574753,3.362671"));
		//System.out.println(objectGetMessages("paul.oguche@halogensecurity.com", "864495030192691", "2018-11-07 14:20:00", "2018-11-07 14:40:59"));
	}*/
}
