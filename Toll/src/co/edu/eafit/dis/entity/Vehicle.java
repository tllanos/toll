package co.edu.eafit.dis.entity;

public class Vehicle {
	int userid;
	int type;
	int finalDestination;
	int initialPoint;
	
	public Vehicle(int userid, int type){
		this.userid = userid;
		this.type = type;
	}
	
	public void setInitialPoint(int initialPoint){
		this.initialPoint = initialPoint;
	}
	
	public void setDestination(int destination){
		this.finalDestination = destination;
	}
	
	public int getInitialPoint(){
		return initialPoint;
	}
}
