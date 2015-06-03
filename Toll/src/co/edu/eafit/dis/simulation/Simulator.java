package co.edu.eafit.dis.simulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import co.edu.eafit.dis.graph.Intersection;
import co.edu.eafit.dis.graph.Toll;
import co.edu.eafit.dis.graphics.GUI;
import co.edu.eafit.dis.dijkstra.Dijkstra;
import co.edu.eafit.dis.entity.Vehicle;

public class Simulator {
	private boolean running;
	private Statement st;
	private ResultSet rs;
	private Connection connection;
	private CopyOnWriteArrayList<Thread> vSim;
	private ArrayList<Toll> tolls;
	private ArrayList<Intersection> intersections;
	private String query = null;
	private PreparedStatement pstate = null;
	private Register register;
	private GUI g;
	
	public Simulator() {
		
		tolls = new ArrayList<Toll>();
		intersections = new ArrayList<Intersection>();
		vSim = new CopyOnWriteArrayList<Thread>();
		register = new Register();
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
//		g = new GUI();
//		while(!g.checkstate()){
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		Driver();
	}
	
	public void Driver(){
		simulate();
	}
		
	public void simulate() {
		//Start
		generateGraph();
		Worker worker = new Worker(connection, pstate, tolls, rs);
		Thread threadw = new Thread(worker);
		threadw.start();
		Generator generator =  new Generator(vSim, intersections, tolls, register);
		Thread threadg = new Thread(generator);
		threadg.start();
		while(/*g.checkstate()*/true){
			synchronized(vSim){
				for(Thread t: vSim){
					synchronized(t){
						if(!t.isAlive()){
							try {
								t.join();
								System.out.println(vSim.size());
								vSim.remove(t);		
								System.out.println(vSim.size());
								System.out.println("Killed: " + t.getId());
								t = null;
								if(vSim.size() == 0){
									break;
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
				Thread.sleep(60000);
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
		
		private synchronized void assignFlow(){
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
			query = "SELECT distinct tollid FROM toll";
			rs = st.executeQuery(query);
			while(rs.next()){
				tolls.add(new Toll(rs.getInt(1),false,register));
			}
			rs.close();
			query = "SELECT distinct intid FROM intersection";
			rs = st.executeQuery(query);
			while(rs.next()){
				intersections.add(new Intersection((rs.getInt(1)+tolls.size()),false));
			}
			rs.close();
			for(Toll tollid: tolls){
				pstate = connection.prepareStatement("SELECT intersection from connection where tollid = ?");
				pstate.setInt(1, tollid.getId());
				rs = pstate.executeQuery();
				while(rs.next()){
					for(Intersection ints: intersections){
						if(ints.getId() == (rs.getInt(1)+tolls.size())){
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
}