package repositories;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

import beans.Hero;
import beans.Incident;
import utils.SqlConnection;

public class IncidentRepository {
	private Connection connection;
	public IncidentRepository() {
		connection = SqlConnection.getInstance();
	}
	
	public Boolean createIncident(Incident incident)
	{
		try {
			int insertedId;
			
			PreparedStatement prepare = this.connection.prepareStatement(
			      	"INSERT INTO incidents (IncidentTypeID, City, Latitude, Longitude, Code) "+
			      	"VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			prepare.setInt(1, incident.getIncidentTypeID());
			prepare.setString(2,  incident.getCity());
			prepare.setBigDecimal(3,  incident.getLatitude());
			prepare.setBigDecimal(4,  incident.getLongitude());
			String code = generateRandomBase64Token();
			prepare.setString(5, code);
			prepare.executeUpdate();
			ResultSet rs = prepare.getGeneratedKeys();
            if(rs.next())
            {
            	insertedId = rs.getInt(1);
            	incident.setId(insertedId);
            	incident.setCode(code);
            }
            else
            	return false;
			prepare.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String generateRandomBase64Token() {
	    SecureRandom secureRandom = new SecureRandom();
	    byte[] token = new byte[8];
	    secureRandom.nextBytes(token);
	    return Base64.getUrlEncoder().withoutPadding().encodeToString(token);
	}

	
	public Boolean updateIncidentHero(int incidentId, int heroId) {
		try {
			PreparedStatement prepare = this.connection.prepareStatement(
			      	"UPDATE  incidents SET HeroID = ? WHERE ID = ?;");
			prepare.setInt(1, heroId);
			prepare.setInt(2, incidentId);
			prepare.executeUpdate();
			prepare.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Incident getIncidentById(int id)
	{
		Incident incident = null;
		ResultSet result;
		try {
			PreparedStatement prepare = this.connection.prepareStatement(
			      	"SELECT * FROM incidents WHERE ID = ?;"
			      );
			prepare.setInt(1, id);
			result = prepare.executeQuery();
			if (!result.next())
			{
				prepare.close();
				return null;
			}
			incident = new Incident(result.getInt("ID"), result.getInt("IncidentTypeID"), result.getInt("HeroID"),
					result.getString("City"), result.getBigDecimal("Latitude"), result.getBigDecimal("Longitude"), result.getTimestamp("sysCreatedDate"));
			prepare.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return incident;
	}
	
	public Incident getIncidentByCode(String date, String city, String code)
	{
		Incident incident = null;
		ResultSet result;
		try {
			PreparedStatement prepare = this.connection.prepareStatement(
			      	"SELECT * FROM incidents WHERE Date(sysCreatedDate) = ? AND City = ? AND Code = ? AND HeroID IS NULL;"
			      );
			prepare.setString(1, date);
			prepare.setString(2, city);
			prepare.setString(3, code);
			result = prepare.executeQuery();
			if (!result.next())
			{
				prepare.close();
				return null;
			}
			incident = new Incident(result.getInt("ID"), result.getInt("IncidentTypeID"), result.getInt("HeroID"),
					result.getString("City"), result.getBigDecimal("Latitude"), result.getBigDecimal("Longitude"), result.getTimestamp("sysCreatedDate"));
			prepare.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return incident;
	}
}
