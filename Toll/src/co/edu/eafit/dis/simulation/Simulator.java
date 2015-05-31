package co.edu.eafit.dis.simulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import co.edu.eafit.dis.graph.Intersection;
import co.edu.eafit.dis.graph.Toll;

public class Simulator {
	private int valueFunction;
	private int numTollCash;
	private int numTollPhoto;
	private int numTollSensor;
	private Statement st;
	private ResultSet rs;
	private Connection connection;
	private ArrayList<Integer[]> users = new ArrayList<Integer[]>();
	private ArrayList<Toll> tolls;
	private ArrayList<Intersection> intersections;
	private String query = null;
	private PreparedStatement pstate = null;
	
	public Simulator() {
		
		tolls = new ArrayList<Toll>();
		intersections = new ArrayList<Intersection>();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/tollcontrol?"
		              + "user=root&password=ccr");
		}catch(Exception e){
			System.out.println("Error connecting to the database");
		}
		
		try{
			st = connection.createStatement();
		}catch(SQLException e) { 
			e.printStackTrace(); 
		}
		
		generateGraph();
	}
	
	public void simulate() {
		for (double i = 0; i < Math.PI; i = i + (Math.PI / 128)) {
			generateVehicle(i);
		}
	}
	
	private void generateGraph(){
		try{
			query = "SELECT tollid FROM toll";
			rs = st.executeQuery(query);
			while(rs.next()){
				tolls.add(new Toll(rs.getInt(1),false));
			}
			rs.close();
			query = "SELECT intid FROM intersection";
			rs = st.executeQuery(query);
			while(rs.next()){
				intersections.add(new Intersection(rs.getInt(1),false));
			}
			rs.close();
			for(Toll tollid: tolls){
				pstate = connection.prepareStatement("SELECT intersection from connection where tollid = ?");
				pstate.setString(1, Integer.toString(tollid.getId()));
				rs = pstate.executeQuery();
				while(rs.next()){
					for(Intersection ints: intersections){
						if(ints.getId() == rs.getInt(1)){
							ints.connectTo(tollid);
							System.out.println("Toll: " + tollid.getId() + "-> Intersection: " + ints.getId());
							tollid.connectTo(ints);
						}
					}
				}
			}
			rs.close();
			for(Toll toll: tolls){
				toll.setMaxFlow(20);
			}
			for(Intersection ints: intersections.subList(0, 8)){
				ints.setSource(true);
				System.out.println("Sources: " + ints.getId());
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private void generateVehicle(double value){
		valueFunction = (int)(Math.sin(value) * 100);
		
		String query = "SELECT userid, type FROM users LIMIT 0,"
				+ valueFunction;
		try {
			rs = st.executeQuery(query);
			int userid, type;
			while(rs.next()) {
				userid = rs.getInt("userid");
				type = rs.getInt("type");
				// System.out.println("User ID: " + userid + " Type: " + type);
				Integer values[] = {userid, type};
				users.add(values);
			}
		
			rs.close();
		} catch(SQLException e) { 
			System.out.println(e); 
		}
	}
}