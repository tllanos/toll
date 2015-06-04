package co.edu.eafit.dis.tollbooths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import co.edu.eafit.dis.graph.*;
import co.edu.eafit.dis.entity.Vehicle;

/**
 * Esta clase es la abstraccion de la caseta de un peaje,
 * que mas precisamente puede ser vista como una caseta de ida y de venida.
 * la cual, como es usual en colombia y diferentes partes del
 * mundo, se satura si hay mucho flujo en
 * cualquiera de los sentidos.
 * 
 * Una casita ademas siempre debe existir ligada a un peaje,
 * de lo contrario no tendria sentido.
 * 
 * Ahora, has de decirse, esta clase es una implementacion de
 * la interfaz Runnable, pues, para poder concurrentemente simular
 * a todas las casetas habria que hacerse un procesamiento secuencial
 * de cada una si no se implementasen concurrentemente.
 * @author tllanos, ccorre20, icardena
 * @see java.lang.Runnable 
 * @see co.edu.eafit.dis.graph.Toll
 */
public abstract class TollBooth implements Runnable{

	protected Toll location;
	protected int bid;
	protected boolean status;
	protected int type;
	protected Connection connection;
	protected String query;
	protected Statement st;
	protected PreparedStatement pstate;
	protected ResultSet rs;
	public LinkedList<Vehicle> q;
	
	/**
	 * Este es el constructor de las casetas,
	 * basicamente se encarga de prepararse para hacer
	 * las transacciones y para hacer el manejo adecuado de los vehiculos.
	 * @param location el peaje al que pertenece la caseta.
	 */
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
	
	/**
	 * Este metodo, similar al metodo en el peaje,
	 * hace el manejo de la recepcion de los vehiculos.
	 * @param vehicle el vehiculo que entra al peaje.
	 * @see co.edu.eafit.dis.graph.Toll#recieveVehicle(Vehicle)
	 */
	public synchronized void recieveVehicle(Vehicle vehicle){
		q.add(vehicle);
		System.out.println("Agregado y frenado: " + vehicle.getUserid());
		q.notify();
	}
	
	/**
	 * Este metodo simplemente retorna el tipo de la caseta.
	 * @return el tipo
	 */
	public int getType(){
		return type;
	}
}