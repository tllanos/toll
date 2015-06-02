package co.edu.eafit.dis.tollbooths;

import java.util.ArrayList;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Toll;

public class Register {
	ArrayList<Toll> tolls;
	
	public Register(ArrayList<Toll> tolls){
		this.tolls = tolls;
	}
	
	public void addVehicle(int tollid, Vehicle vehicle){
		for(Toll toll: tolls){
			if(tollid == toll.getId()){
				System.out.println("Toll: " + tollid);
				toll.cashB.get(0).recieveVehicle(vehicle);
				break;
			}
		}
	}
}