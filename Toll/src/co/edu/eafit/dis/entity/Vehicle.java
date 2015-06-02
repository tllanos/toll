package co.edu.eafit.dis.entity;

import java.util.ArrayList;
import java.util.List;

import javax.management.openmbean.TabularType;

import co.edu.eafit.dis.dijkstra.*;
import co.edu.eafit.dis.graph.Intersection;
import co.edu.eafit.dis.graph.Node;
import co.edu.eafit.dis.graph.Toll;
import co.edu.eafit.dis.tollbooths.Register;

public class Vehicle implements Runnable{
	private int userid;
	
	public int getUserid() {
		return userid;
	}

	private int type;
	private int finalDestination;
	private int initialPoint;
	private int location;
	private List<Vertex> path;
	private ArrayList<Toll> tolls;
	private ArrayList<Intersection> intersections;
	private Register register;
	
	public void run(){
		location = Integer.parseInt(path.get(0).name);
		path.remove(0);
		Node last = null;
		System.out.println(userid + ": " + location);
		for(Intersection i: intersections){
			if(location == i.getId()){
				i.addVehicle(this);
				last = i;
				break;
			}
		}
		int next;
		while(!path.isEmpty()){
			next = Integer.parseInt(path.get(0).name);
			if(next < tolls.size()+1){
				for(Toll t: tolls){
					if(t.getId() == next){
						System.out.println("Ingresando a: " + next);
						if(last instanceof Intersection){
							((Intersection) last).removeVehicle(this);
						}else{
							System.exit(1);
						}
						path.remove(0);
						last = t;
						synchronized (((Toll) last).b1) {
							((Toll) last).recieveVehicle(this);
						}
						synchronized(last){
						}
						System.out.println("Hice mi transaccion");
						break;
					}
				}
			}else{
				for(Intersection i: intersections){
					if(i.getId() == next){
						System.out.println("Ingresando a: " + next);
						if(!(last instanceof Toll)){
							System.exit(1);
						}
						path.remove(0);
						i.addVehicle(this);
						last = i;
						break;
					}
				}
			}
		}
	}
	
	public Vehicle(int userid, int type, List<Vertex> path, ArrayList<Toll> tolls, ArrayList<Intersection> intersections, Register register){
		this.userid = userid;
		this.type = type;
		this.path = path;
		this.tolls = tolls;
		this.intersections = intersections;
		this.register = register;
	}
	
//	public void setInitialPoint(int initialPoint){
//		this.initialPoint = initialPoint;
//		this.location = this.initialPoint;
//	}
//	
//	public void setDestination(int destination){
//		this.finalDestination = destination;
//	}
	
//	public int getInitialPoint(){
//		return initialPoint;
//	}
	
//	public void switchLocation(int dest){
//		double time = (Math.random() * 5);
//		time *= 60000;
//		try {
//			Thread.sleep((long) time);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		location = dest;
//	}
}
