package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Hero;
import beans.Incident;
import beans.IncidentType;
import utils.SqlConnection;

public class IncidentTypeRepository {
	private Connection connection;

	public IncidentTypeRepository()
	{
		connection = SqlConnection.getInstance();
	}
	
	public ArrayList<IncidentType> getAllIncidentTypes()
	{
		ArrayList<IncidentType> incidentTypes = new ArrayList<>(); 
		IncidentType incidentType;
		ResultSet result;
		try {
			Statement st = this.connection.createStatement();
			result = st.executeQuery("SELECT * FROM incidenttypes;");
			while (result.next()) {
				incidentType = new IncidentType(result.getInt("ID"), result.getString("Name"));
				incidentTypes.add(incidentType);
			}
			st.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return incidentTypes;
	}
	
	public String getHeroIncidentTypes(Hero hero)
	{
		ResultSet result;
		String incidentTypes = "";
		try {
			PreparedStatement prepare = this.connection.prepareStatement(
			      	"SELECT Name FROM incidenttypes " +
					" INNER JOIN heroes_incidenttypes i ON i.IncidentTypeID = incidenttypes.ID "+
			      		" WHERE i.HeroID = ? ORDER BY Name ASC;");
			prepare.setInt(1, hero.getId());
			result = prepare.executeQuery();
			while (result.next()) 
			{
				if (incidentTypes.length() > 0)
					incidentTypes += ", ";
				incidentTypes += result.getString("Name");
			}
			prepare.close();
			return incidentTypes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Erreur";
	}
	
	public Boolean canTheHeroHandleTheIncident(int heroId, int incidentTypeId)
	{
		ResultSet result;
		Boolean r;
		try {
			PreparedStatement prepare = this.connection.prepareStatement(
			      	"SELECT * FROM heroes_incidenttypes i " +
			      	" WHERE i.HeroID = ? AND i.IncidentTypeID = ?;");
			prepare.setInt(1, heroId);
			prepare.setInt(2, incidentTypeId);
			result = prepare.executeQuery();
			r = result.next();
			prepare.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
