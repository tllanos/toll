package co.edu.eafit.dis.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MYSQLConnection {

	private Connection connection = null;
	private Statement state = null;
	private PreparedStatement pstate = null;
	private ResultSet resultSet = null;
	
	public MYSQLConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/tollcontrol?"
		              + "user=root&password=root");
		} catch(Exception e){
			System.out.println("Error connecting to the database");
		}
	}

	
	public Connection getConnection() {
		return connection;
	}
	
}
