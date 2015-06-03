package co.edu.eafit.dis.tollbooths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import co.edu.eafit.dis.graph.*;
import co.edu.eafit.dis.employee.*;
import co.edu.eafit.dis.entity.Vehicle;

public abstract class TollBooth implements Runnable{

	Toll location;
	Employee Manager;
	Employee Operator;
	int bid;
	boolean status;
	int type;
	Connection connection;
	String query;
	Statement st;
	PreparedStatement pstate;
	ResultSet rs;
	
	public LinkedList<Vehicle> q;
	
	public TollBooth(Toll location){
		this.location = location;
		q = new LinkedList<Vehicle>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/tollcontrol?"
			          + "user=root&password=root");
			st = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void recieveVehicle(Vehicle vehicle){
		q.add(vehicle);
		System.out.println("Agregado y frenado: " + vehicle.getUserid());
		q.notify();
	}
	
	public int getType(){
		return type;
	}
}