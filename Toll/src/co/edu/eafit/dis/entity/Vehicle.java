package co.edu.eafit.dis.entity;

import java.util.List;
import co.edu.eafit.dis.dijkstra.*;

public class Vehicle implements Runnable{
	int userid;
	int type;
	int finalDestination;
	int initialPoint;
	int location;
	List<Vertex> path;
	
	public synchronized void run(){
		
	}
	
	public Vehicle(int userid, int type, List<Vertex> path){
		this.userid = userid;
		this.type = type;
		this.path = path;
	}
	
	public void setInitialPoint(int initialPoint){
		this.initialPoint = initialPoint;
		this.location = this.initialPoint;
	}
	
	public void setDestination(int destination){
		this.finalDestination = destination;
	}
	
	public int getInitialPoint(){
		return initialPoint;
	}
	
	public void switchLocation(int dest){
		double time = (Math.random() * 5);
		time *= 60000;
		try {
			Thread.sleep((long) time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		location = dest;
	}
}
