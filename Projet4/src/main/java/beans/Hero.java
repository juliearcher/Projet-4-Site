package beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Hero {
	private int id;
	private String name;
	private String email;
	private String phoneNumber;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private Timestamp sysCreatedDate;
	private double distance;
	
	public Hero(String name, String email, String phoneNumber, BigDecimal latitude, BigDecimal longitude) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public Hero(int id, String name, String phoneNumber, BigDecimal latitude, BigDecimal longitude, Timestamp sysCreatedDate)	{
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sysCreatedDate = sysCreatedDate;
	}
	public Hero(int id, String name, String email, String phoneNumber, BigDecimal latitude, BigDecimal longitude, Timestamp sysCreatedDate)	{
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sysCreatedDate = sysCreatedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getSysCreatedDate() {
		return sysCreatedDate;
	}

	public void setSysCreatedDate(Timestamp sysCreatedDate) {
		this.sysCreatedDate = sysCreatedDate;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
}
