package co.edu.eafit.dis.graph;

import java.util.ArrayList;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.simulation.Register;
import co.edu.eafit.dis.tollbooths.TollBooth;
import co.edu.eafit.dis.tollbooths.TollCash;
import co.edu.eafit.dis.tollbooths.TollPhoto;
import co.edu.eafit.dis.tollbooths.TollScanner;

public class Toll extends Node {
	
	public ArrayList<TollCash> cashB;
	public ArrayList<TollScanner> scannerB;
	public ArrayList<TollPhoto> photoB;
	private ArrayList<Thread> boothP;
	private int flow;
	private Register register;
	
	public Toll(int id, boolean source, Register register){
		super(id, source);
		cashB = new ArrayList<TollCash>();
		cashB.add(new TollCash(this));
		scannerB = new ArrayList<TollScanner>();
		scannerB.add(new TollScanner(this));
		photoB = new ArrayList<TollPhoto>();
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
	
	public void recieveVehicle(Vehicle vehicle){
		System.out.println("Recibido");
		switch(vehicle.getType()){
		case 1:
			cashB.get(0).q.add(vehicle);
			break;
		case 2:
			scannerB.get(0).q.add(vehicle);
			break;
		case 3:
			photoB.get(0).q.add(vehicle);
			break;
		}
	}
	
	public void setFlow(int f){
		flow = f;
	}
	
	public int getFlow() {
		return flow;
	}
	
}
