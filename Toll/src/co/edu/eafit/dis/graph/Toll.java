package co.edu.eafit.dis.graph;

import java.util.ArrayList;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.tollbooths.Register;
import co.edu.eafit.dis.tollbooths.TollBooth;
import co.edu.eafit.dis.tollbooths.TollCash;
import co.edu.eafit.dis.tollbooths.TollPhoto;
import co.edu.eafit.dis.tollbooths.TollScanner;

public class Toll extends Node {
	
	public ArrayList<TollBooth> cashB;
	public ArrayList<TollBooth> scannerB;
	public ArrayList<TollBooth> photoB;
	private ArrayList<Thread> boothP;
	private int flow;
	private Register register;
	
	public Toll(int id, boolean source, Register register){
		super(id, source);
		cashB = new ArrayList<TollBooth>();
		cashB.add(new TollCash(this));
		scannerB = new ArrayList<TollBooth>();
		scannerB.add(new TollScanner(this));
		photoB = new ArrayList<TollBooth>();
		photoB.add(new TollPhoto(this));
		boothP = new ArrayList<Thread>();
		
		for(TollBooth c : cashB){
			boothP.add(new Thread(c));			
		}
		for(TollBooth c : scannerB){
			boothP.add(new Thread(c));
		}
		for(TollBooth c : photoB){
			boothP.add(new Thread(c));
		}
		for(Thread t : boothP){
			t.start();
		}
		
		this.register = register;
	}
	
	public void run(){
//		while(true){
//			synchronized(cashB){
//				while(cashB.get(0).q.isEmpty())
//					try {
//						cashB.wait();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				cashB.get(0).q.poll();
//				System.out.println("TRANSACCION");
//				cashB.notify();
//			}
//			synchronized(photoB){
//				while(photoB.get(0).q.isEmpty())
//					try {
//						photoB.wait();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//			}
//		}
	}
	
	public synchronized void recieveVehicle(Vehicle vehicle){
		switch(vehicle.getType()){
		case 0:
			cashB.get(0).q.add(vehicle);
		case 1:
			photoB.get(0).q.add(vehicle);
		case 2:
			scannerB.get(0).q.add(vehicle);
		}
//		cashB.get(0).q.add(vehicle);
//		cashB.notify();	
	}
	
	public void setFlow(int f){
		flow = f;
	}
	
	public int getFlow() {
		return flow;
	}
	
}
