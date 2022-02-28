package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
			      	"INSERT INTO incidents (IncidentTypeID, City, Latitude, Longitude) "+
			      	"VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			prepare.setInt(1, incident.getIncidentTypeID());
			prepare.setString(2,  incident.getCity());
			prepare.setBigDecimal(3,  incident.getLatitude());
			prepare.setBigDecimal(4,  incident.getLongitude());
			prepare.executeUpdate();
			ResultSet rs = prepare.getGeneratedKeys();
            if(rs.next())
            {
            	insertedId = rs.getInt(1);
            	incident.setId(insertedId);
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
}
