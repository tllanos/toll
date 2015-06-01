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
import co.edu.eafit.dis.analysis.Calculation;
import co.edu.eafit.dis.dijkstra.Dijkstra;
import co.edu.eafit.dis.entity.Vehicle;

public class Simulator {
	private int valueFunction;
	private Statement st;
	private ResultSet rs;
	private Connection connection;
	private ArrayList<Vehicle> users = new ArrayList<Vehicle>();
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
		              + "user=root&password=root");
		}catch(Exception e){
			System.out.println("Error connecting to the database");
		}
		
		try{
			st = connection.createStatement();
		}catch(SQLException e) { 
			e.printStackTrace(); 
		}
		simulate();
	}
	
	public void simulate() {
		//Start
		generateGraph();
		for (double i = 0; i < Math.PI; i = i + (Math.PI / 128)) {
			generateVehicle(i);
		}
		assignVehicles();
		/*Vehicle vehicle = new Vehicle(1,3);
		vehicle.setInitialPoint(1);
		vehicle.setDestination(8);
		Calculation c = new Calculation(tolls, intersections, vehicle);
		ArrayList<Integer> a = c.dijkstra();
		for(Integer b : a) {
			System.out.println(" " + b);
		}*/
		Dijkstra d = new Dijkstra();
		d.initDijkstra(tolls, intersections);
		/*Worker worker = new Worker(connection, pstate, tolls, rs);
		Thread thread = new Thread(worker);
		thread.start();*/
	}
	
	private static class Worker implements Runnable{
		
		private ArrayList<Toll> tolls;
		private PreparedStatement pstate = null;
		private Connection connection;
		private ResultSet rs;
		
		public void run(){
			while(true){
				assignFlow();
				System.out.println("Update!");
				try{
				Thread.sleep(1000);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		public Worker(Connection connection, PreparedStatement pstate, ArrayList<Toll> tolls, ResultSet rs){
			this.connection = connection;
			this.pstate = pstate;
			this.rs = rs;
			this.tolls = tolls;
		}
		
		private void assignFlow(){
			try{
				int id;
				int f;
				for(Toll toll: tolls){
					f = 0;
					id = toll.getId();
					
					pstate = connection.prepareStatement(
							"SELECT COUNT(tollid) FROM tollcash WHERE date > date_sub(NOW(), INTERVAL 1 MINUTE) AND tollid = ?;");
					pstate.setInt(1, id);
					
					rs = pstate.executeQuery();
					if(rs.next()){
						f += rs.getInt(1);
					}
					
					pstate = connection.prepareStatement(
							"SELECT COUNT(tollid) FROM tollsensor WHERE date > date_sub(NOW(), INTERVAL 1 MINUTE) AND tollid = ?;");
					pstate.setInt(1, id);
					
					rs = pstate.executeQuery();
					if(rs.next()){
						f += rs.getInt(1);
					}
					
					pstate = connection.prepareStatement(
							"SELECT COUNT(tollid) FROM tollphoto WHERE date > date_sub(NOW(), INTERVAL 1 MINUTE) AND tollid = ?;");
					
					pstate.setInt(1, id);
					
					rs = pstate.executeQuery();
					if(rs.next()){
						f += rs.getInt(1);
					}
					toll.setFlow(f);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
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
							ints.connectTo(tollid.getId());
							System.out.println("Toll: " + tollid.getId() + "-> Intersection: " + ints.getId());
							tollid.connectTo(ints);
							tollid.connectTo(ints.getId());
						}
					}
				}
			}
			rs.close();
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
				users.add(new Vehicle(userid,type));
			}
			rs.close();
		} catch(SQLException e) { 
			System.out.println(e); 
		}
	}

	private void assignVehicles(){
		int random;
		for(Vehicle vehicle: users){
			random = (int)(Math.random()* 8);
			vehicle.setInitialPoint(random);
			random = (int)(Math.random()* 8);
			if(vehicle.getInitialPoint() == random){
				while(vehicle.getInitialPoint() == random){	
					random = (int)(Math.random()* 8);
				}
				vehicle.setDestination(random);
			}else{
				vehicle.setDestination(random);
			}
		}
		
		
	}

}