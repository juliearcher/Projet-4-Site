package repositories;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import beans.GPSCoordinates;
import beans.Hero;
import beans.Incident;
import utils.SqlConnection;


public class HeroRepository {
	private Connection connection;
	public HeroRepository() {
		connection = SqlConnection.getInstance();
	}
	
	public Boolean createHero(Hero hero, String password, String[] types)
	{
		try {
			int insertedId;
			
			PreparedStatement prepare = this.connection.prepareStatement(
			      	"INSERT INTO heroes (Name, Email, PhoneNumber, Password, Latitude, Longitude, Salt) "+
			      	"VALUES (?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			prepare.setString(1, hero.getName());
			prepare.setString(2, hero.getEmail());
			prepare.setString(3, hero.getPhoneNumber());
			
			String salt = getNewSalt();
			String encryptedPassword = getEncryptedPassword(password, salt);
			prepare.setString(4, encryptedPassword);
			prepare.setBigDecimal(5, hero.getLatitude());
			prepare.setBigDecimal(6, hero.getLongitude());
			prepare.setString(7, salt);
			prepare.executeUpdate();
			ResultSet rs = prepare.getGeneratedKeys();
            if(rs.next())
            {
            	insertedId = rs.getInt(1);
            	hero.setId(insertedId);
            }
            else
            	return false;
			prepare.close();
			return insertHeroIncidentTypes(types, insertedId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private Boolean insertHeroIncidentTypes(String[] types, int heroId)
	{
		for (String type : types)
		{
			try {			
				PreparedStatement prepare = this.connection.prepareStatement(
				      	"INSERT INTO heroes_incidenttypes (HeroID, IncidentTypeID) "+
				      	"VALUES (?, ?);");
				prepare.setInt(1, heroId);
				prepare.setInt(2, Integer.valueOf(type));
				prepare.executeUpdate();
				prepare.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public String getEncryptedPassword(String password, String salt) throws Exception{
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160;
        int iterations = 20000;
 
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
 
        byte[] encBytes = f.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(encBytes);
    }
	
	private String getNewSalt() throws NoSuchAlgorithmException
	{
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
	}

	public ArrayList<Hero> getAllHeroes()
	{
		ArrayList<Hero> heroes = new ArrayList<>(); 
		Hero hero;
		ResultSet result;
		try {
			Statement st = this.connection.createStatement();
			result = st.executeQuery("SELECT ID, Name, PhoneNumber, Latitude, Longitude, sysCreatedDate FROM heroes;");
			while (result.next()) {
				hero = new Hero(result.getInt("ID"), result.getString("Name"), result.getString("PhoneNumber"),
						result.getBigDecimal("Latitude"), result.getBigDecimal("Longitude"), result.getTimestamp("sysCreatedDate"));
				heroes.add(hero);
			}
			st.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return heroes;
	}
	
	public ArrayList<Hero> getAllHeroesForIncident(Incident incident)
	{
		ArrayList<Hero> heroes = new ArrayList<>(); 
		double distance;
		Hero hero;
		ResultSet result;
		try {
			PreparedStatement prepare = this.connection.prepareStatement(
			"SELECT ID, Name, PhoneNumber, Latitude, Longitude, sysCreatedDate " +
			"FROM heroes INNER JOIN heroes_incidenttypes i ON i.HeroID = heroes.ID WHERE IncidentTypeID = ?;");
			prepare.setInt(1, incident.getIncidentTypeID());
			result = prepare.executeQuery();
			while (result.next()) {
				hero = new Hero(result.getInt("ID"), result.getString("Name"), result.getString("PhoneNumber"),
						result.getBigDecimal("Latitude"), result.getBigDecimal("Longitude"), result.getTimestamp("sysCreatedDate"));
				if ((distance = GPSCoordinates.calculateDistance(incident.getLatitude(), incident.getLongitude(),
						hero.getLatitude(), hero.getLongitude())) < 50000)
				{
					hero.setDistance(distance);
					heroes.add(hero);
				}
			}
			prepare.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return heroes;
	}
	
	
	public Hero getHero(String username, String password)
	{
		Hero hero = null;
		ResultSet result;
		try {
			PreparedStatement prepare = this.connection.prepareStatement(
			      	"SELECT * FROM heroes WHERE name = ? OR email = ?;"
			      );
			prepare.setString(1, username);
			prepare.setString(2, username);
			result = prepare.executeQuery();
			if (!result.next() || !isPasswordValid(password, result.getString("Password"), result.getString("Salt")))
			{
				prepare.close();
				return null;
			}
			hero = new Hero(result.getInt("ID"), result.getString("Name"), result.getString("Email"), result.getString("PhoneNumber"),
					result.getBigDecimal("Latitude"), result.getBigDecimal("Longitude"), result.getTimestamp("sysCreatedDate"));
			prepare.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hero;
	}
	
	public Boolean isNewHero(String username, String email)
	{
		try {
			PreparedStatement prepare = this.connection.prepareStatement(
			      	"SELECT * FROM heroes WHERE name = ? OR email = ?;"
			      );
			prepare.setString(1, username);
			prepare.setString(2, email);
			ResultSet result = prepare.executeQuery();
			if (!result.next())
			{
				prepare.close();
				return true;
			}
			prepare.close();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private Boolean isPasswordValid(String password, String encryptedPassword, String salt) throws Exception
	{
        return getEncryptedPassword(password, salt).equals(encryptedPassword);
	}
	
}
