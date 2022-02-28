package beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Incident {
	private int id;
	private int incidentTypeID;
	private int heroID;
	private String city;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private Timestamp sysCreatedDate;
	
	public Incident(int incidentTypeID, String city, BigDecimal latitude, BigDecimal longitude) {
		this.city = city;
		this.incidentTypeID = incidentTypeID;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Incident(int incidentTypeID, int heroID, String city, BigDecimal latitude, BigDecimal longitude) {
		this.incidentTypeID = incidentTypeID;
		this.heroID = heroID;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Incident(int id, int incidentTypeID, int heroID, String city, BigDecimal latitude, BigDecimal longitude, Timestamp date) {
		this.id = id;
		this.incidentTypeID = incidentTypeID;
		this.heroID = heroID;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sysCreatedDate = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIncidentTypeID() {
		return incidentTypeID;
	}

	public void setIncidentTypeID(int incidentTypeID) {
		this.incidentTypeID = incidentTypeID;
	}

	public int getHeroID() {
		return heroID;
	}

	public void setHeroID(int heroID) {
		this.heroID = heroID;
	}

	public Timestamp getDate() {
		return sysCreatedDate;
	}

	public void setDate(Timestamp date) {
		this.sysCreatedDate = date;
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

	public Timestamp getSysCreatedDate() {
		return sysCreatedDate;
	}

	public void setSysCreatedDate(Timestamp sysCreatedDate) {
		this.sysCreatedDate = sysCreatedDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}	
	
	
}
