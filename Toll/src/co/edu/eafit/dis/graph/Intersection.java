package co.edu.eafit.dis.graph;

import java.util.ArrayList;

import co.edu.eafit.dis.entity.Vehicle;

public class Intersection extends Node{
	
	ArrayList<Vehicle> vehicles;
	
	public Intersection(int id, boolean source){
		super(id, source);
		vehicles =  new ArrayList<Vehicle>();
	}
	
	public void addVehicle(Vehicle vehicle){
		vehicles.add(vehicle);
	}
	
	public void removeVehicle(Vehicle vehicle){
		vehicles.remove(vehicle);
	}
	
}
