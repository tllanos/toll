package co.edu.eafit.dis.tollbooths;

import java.util.LinkedList;

import co.edu.eafit.dis.graph.*;
import co.edu.eafit.dis.employee.*;
import co.edu.eafit.dis.entity.Vehicle;

public abstract class TollBooth {

	Toll location;
	Employee Manager;
	Employee Operator;
	int bid;
	boolean status;
	
	public LinkedList<Vehicle> q;
	
	public TollBooth(Toll location){
		this.location = location;
		q = new LinkedList<Vehicle>();
	}
	
	public void recieveVehicle(Vehicle vehicle){
		q.add(vehicle);
		System.out.println("Agregado y frenado: " + vehicle.getUserid());
		System.out.println(this);
	}
}