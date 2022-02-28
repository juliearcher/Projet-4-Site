package repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
}
