package co.edu.eafit.dis.graph;

import java.util.ArrayList;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.tollbooths.Register;
import co.edu.eafit.dis.tollbooths.TollBooth;
import co.edu.eafit.dis.tollbooths.TollCash;

public class Toll extends Node implements Runnable{
	
	public ArrayList<TollBooth> b1;
	private int flow;
	private Register register;
	
	public Toll(int id, boolean source, Register register){
		super(id, source);
		b1 = new ArrayList<TollBooth>();
		b1.add(new TollCash(this));
		this.register = register;
	}
	
	public void run(){
		while(true){
			synchronized(b1){
				while(b1.get(0).q.isEmpty())
					try {
						b1.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				b1.get(0).q.poll();
				System.out.println("TRANSACCION");
				b1.notify();
			}
		}
	}
	
	public synchronized void recieveVehicle(Vehicle vehicle){
		b1.get(0).q.add(vehicle);
		b1.notify();	
	}
	
	public void setFlow(int f){
		flow = f;
	}
	
	public int getFlow() {
		return flow;
	}
	
}
