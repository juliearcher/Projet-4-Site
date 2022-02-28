package beans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GPSCoordinates {
	private BigDecimal latitude;
	private BigDecimal longitude;
	public GPSCoordinates(BigDecimal latitude, BigDecimal longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public double calculateDistance(GPSCoordinates coordGps) {
		double distance = gps2m(latitude, longitude, coordGps.getLatitude(), coordGps.getLongitude());
		System.out.println("Distance=" + distance);
		return distance;
	}
	public static double calculateDistance(BigDecimal lat1, BigDecimal lng1, BigDecimal lat2, BigDecimal lng2) {
		double distance = gps2m(lat1, lng1, lat2, lng2);
		System.out.println("Distance=" + distance);
		return distance;
	}
	private static double gps2m(BigDecimal lat_a, BigDecimal lng_a, BigDecimal lat_b, BigDecimal lng_b) {	
		BigDecimal pk = BigDecimal.valueOf(180/3.1419);

		double a1 = lat_a.divide(pk, 7, RoundingMode.HALF_EVEN).doubleValue();
		double a2 = lng_a.divide(pk, 7, RoundingMode.HALF_EVEN).doubleValue();
		double b1 = lat_b.divide(pk, 7, RoundingMode.HALF_EVEN).doubleValue();
	    double b2 = lng_b.divide(pk, 7, RoundingMode.HALF_EVEN).doubleValue();

	    double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
	    double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
	    double t3 = Math.sin(a1)*Math.sin(b1);
	    double tt = Math.acos(t1 + t2 + t3);
	   
	    return 6366000*tt;
	}
	
	
	public static GPSCoordinates getGpsCoordinatesByAddress(String address, String city, String zipcode) {
		GPSCoordinates GPSCoordinates = null;
		String fullAddress = address + " " + zipcode + " " + city;
		fullAddress = fullAddress.replace('è', 'e');
		fullAddress = fullAddress.replace('é', 'e');
		fullAddress = fullAddress.replace('î', 'i');
		fullAddress = fullAddress.replace('ê', 'e');
		try {
			URL url = new URL("https://nominatim.openstreetmap.org/search?q=" + fullAddress +  "&format=json");
			System.out.println("url=" + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", "application/json");
	        if (conn.getResponseCode() != 200) {
	            return null;
	        }
	        InputStreamReader in = new InputStreamReader(conn.getInputStream());
	        BufferedReader reader = new BufferedReader(in);
	        StringBuilder sb = new StringBuilder();
	        String readLine;
	        while ((readLine = reader.readLine()) != null) {
	             sb.append(readLine);
	             System.out.println("readLine=" + readLine);
	        }
	        JSONParser parser = new JSONParser(sb.toString());
	       // JSONArray infoAdresse = (JSONArray) parser.parseArray();
	        ArrayList<Object> infoAddress = parser.parseArray();
	        if(infoAddress.isEmpty()) {
	        	System.out.println("Erreur adresse");
	        	return null;
	        }
	        else {
	        	@SuppressWarnings("unchecked")
				HashMap<String, String> map = (HashMap<String, String>) infoAddress.get(0);
	        	System.out.println(map.get("lat"));
	        	System.out.println(map.get("lon"));
		        GPSCoordinates = new GPSCoordinates(new BigDecimal(map.get("lat")), new BigDecimal(map.get("lon")));
	        }
	        conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GPSCoordinates;
	}
	
	@Override
	public String toString() {
		return "CoordGps [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
}
