package co.edu.eafit.dis.simulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;

import co.edu.eafit.dis.dijkstra.Dijkstra;
import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Intersection;
import co.edu.eafit.dis.graph.Toll;

public class Generator implements Runnable{
	
	private CopyOnWriteArrayList<Thread> vSim;
	private int li, range, init, dest;
	private Vehicle veh;
	private Dijkstra d;
	private Thread t;
	private int id = 0;
	private ArrayList<Intersection> intersections;
	private ArrayList<Toll> tolls;
	private Register register;
	private Connection connection;
	private Statement statement;
	private ResultSet rs;
	
	public Generator(CopyOnWriteArrayList<Thread> vSim, 
			ArrayList<Intersection> intersections, 
			ArrayList<Toll> tolls, 
			Register register){
		d = new Dijkstra();
		this.vSim = vSim;
		this.register = register;
		int count = 0, last = 0;
		for (Intersection i: intersections){
			if(i.source == true){
				last = i.getId();
				count++;
			}
		}
		range = count;
		li = last - range + 1;
		this.intersections = intersections;
		this.tolls = tolls;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/tollcontrol?"
		              + "user=root&password=root");
			statement = connection.createStatement();
		}catch(Exception e){
			System.out.println("Error connecting to the database");
		}
	}
	
	public synchronized void run(){
		while(true){
			Calendar rightNow = Calendar.getInstance();
			int hour = rightNow.get(Calendar.HOUR_OF_DAY);
			double f = (hour / 24.0d);
			if(f == 0){
				f = 12;
			}
			int quantity = Math.abs((int)(((Math.sin((2 * Math.PI) * f)) * 5999.0d) + 0.5d)) + 1;
			long dt = (long)((3600.0d/quantity) * 1000.0d);
			init = (int)((Math.random() * (range-1)) + li);
			System.out.println("init : "+ init);
			dest = (int)((Math.random() * (range-1)) + li);
			while(dest == init){
				dest = (int)((Math.random() * (range-1)) + li);
			}
			System.out.println("dest : "+ dest);
			System.out.println("Estoy generando un carro");
			System.out.println("Son las: " + hour + "q: " + quantity + "dt: " + dt);
			//
			int nran = (int)((Math.random() * 5.0d) + 0.5d);
			String platetmp = null, query;
			int utmp = 0, max = 0, sensortmp = 0;
			//nran = 2;
			switch(nran){
				default:
				case 1:
					System.out.println("CARRO CASH");
					veh = new Vehicle(0, 0, null, 1, d.initDijkstra(tolls, intersections, init, dest), tolls, intersections);
					register.add(veh);
					break;
				case 2:
					System.out.println("CARRO SCANNER1");
					try {
						do{
							rs = statement.executeQuery("SELECT userid, sensorid FROM users where type = 1 ORDER BY RAND() LIMIT 1;");
							rs.next();
							utmp = rs.getInt(1);
							sensortmp = rs.getInt(2);
						}while(register.exists(sensortmp));
					} catch (SQLException e) {
						e.printStackTrace();
					}
					veh = new Vehicle(utmp, sensortmp, null, 2, d.initDijkstra(tolls, intersections, init, dest), tolls, intersections);
					register.add(veh);
					break;
				case 3:
					System.out.println("CARRO SCANNER2");
					try {
						query = "SELECT MAX(userid) FROM users";
						rs = statement.executeQuery(query);
						rs.next();
						max = rs.getInt(1);
						max++;
						query = "INSERT INTO users VALUES ("+max+" , "+max+" , 1 , "+max+" , NULL , 1000);";
						statement.execute(query);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					veh = new Vehicle(max, 0, platetmp, 2, d.initDijkstra(tolls, intersections, init, dest), tolls, intersections);
					register.add(veh);
					break;
				case 4:
					System.out.println("CARRO PHOTO1");
					try {
						do{
							rs = statement.executeQuery("SELECT userid, plate FROM users where type = 2 ORDER BY RAND() LIMIT 1;");
							if(!rs.next())
								break;
							utmp = rs.getInt(1);
							platetmp = rs.getString(2);
						}while(register.exists(platetmp));
					} catch (SQLException e) {
						e.printStackTrace();
					}
					veh = new Vehicle(utmp, 0, platetmp, 3, d.initDijkstra(tolls, intersections, init, dest), tolls, intersections);
					register.add(veh);
					break;
				case 5:
					System.out.println("CARRO PHOTO2");
					try {
						do{
							int r;
							String p = "";
							for(int k = 0; k < 3; k++){
								r = (int)((Math.random() * 25.0d) + 65.0d);
								p += ((char)r);
							}
							for(int k = 0; k < 3; k++){
								r = (int)(Math.random() * 9.0d);
								p += r;
							}
							platetmp = p;
							query = "SELECT * FROM users where plate = '"+platetmp+"';";
							rs = statement.executeQuery(query);
						}while(rs.isBeforeFirst());
						query = "SELECT MAX(userid) FROM users";
						rs = statement.executeQuery(query);
						rs.next();
						max = rs.getInt(1);
						max++;
						query = "INSERT INTO users VALUES ("+max+" , "+max+" , 2 , NULL, '"+platetmp+"' , 1000);";
						statement.execute(query);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					veh = new Vehicle(max, 0, platetmp, 3, d.initDijkstra(tolls, intersections, init, dest), tolls, intersections);
					register.add(veh);
					break;
			}
			//
			t = new Thread(veh);
			synchronized(vSim){
				vSim.add(t);
			}
			t.start();
			try {
				Thread.sleep(dt);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}