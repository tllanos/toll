package co.edu.eafit.dis.simulation;

import java.lang.Math;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Simulator {
	private int valueFunction;
	private int numTollCash;
	private int numTollPhoto;
	private int numTollSensor;
	private Statement st;
	private ResultSet rs;
	private Connection connection;
	private ArrayList<Integer[]> users = new ArrayList<Integer[]>();
	
	public Simulator() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/tollcontrol?"
		              + "user=root&password=root");
		} catch(Exception e) {
			System.out.println("Error connecting to the database");
		}
		
		try {
			st = connection.createStatement();
		} catch(SQLException e) { System.out.print(e); }
		
		simulate();
	}
	
	public void simulate() {
		for (double i = 0; i < Math.PI; i = i + (Math.PI / 128)) {
			generateVehicle(i);
		}
	}
	
	private void generateVehicle(double value) {
		valueFunction = (int)(Math.sin(value) * 100);
		
		String query = "SELECT userid, type FROM users LIMIT 0,"
				+ valueFunction;
		try {
			rs = st.executeQuery(query);
			while(rs.next()) {
				int userid = rs.getInt("userid");
				int type = rs.getInt("type");
				// System.out.println("User ID: " + userid + " Type: " + type);
				Integer values[] = {userid, type};
				users.add(values);
			}
		
			connection.close();
		} catch(SQLException e) { System.out.println(e); }
	}
}