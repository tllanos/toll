package co.edu.eafit.dis.simulation;

import java.util.ArrayList;

import co.edu.eafit.dis.entity.Vehicle;

public class Register {
	private ArrayList<Vehicle> vehs;
	
	public Register(){
		vehs =  new ArrayList<Vehicle>();
	}
	
	public void add(Vehicle veh){
		vehs.add(veh);
	}
	
	public void destroy(Vehicle veh){
		vehs.remove(veh);
	}
	
	public boolean exists(int sensorid){
		for(Vehicle v: vehs){
			if(v.getSensorId() == sensorid){
				return true;
			}
		}
		return false;
	}
	
	public boolean exists(String plate){
		for(Vehicle v: vehs){
			if(v.getPlate().equals(plate)){
				return true;
			}
		}
		return false;
	}
	
}