package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
	private static final String url = "jdbc:mariadb://localhost/cubes4";
	private static final String user = "cesi";
	private static final String passwd = "cesi";
	
	private static Connection instance;
	public static Connection getInstance(){
		if(instance == null){
			try {
			      Class.forName("org.mariadb.jdbc.Driver");
			      instance = DriverManager.getConnection(url,user, passwd);
			} catch (SQLException | ClassNotFoundException e) {
				System.out.println("Connexion Impossible " + e.getMessage());
			}
		}       
		return instance; 
	}
}
