package co.edu.eafit.dis.graph;

import java.util.ArrayList;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.tollbooths.TollBooth;
import co.edu.eafit.dis.tollbooths.TollCash;

public class Toll extends Node implements Runnable{
	
	public ArrayList<TollBooth> b1;
	private int flow;
	
	public Toll(int id, boolean source){
		super(id, source);
		b1 = new ArrayList<TollBooth>();
		b1.add(new TollCash(this));
	}
	
	public void run(){
		Vehicle temp;
		while(true){
			while(!(b1.get(0).q.isEmpty())){
				temp = b1.get(0).q.poll();
				System.out.println("TRANSACCION");
				this.notify();
			}
		}
	}
	
	public void setFlow(int f){
		flow = f;
	}
	
	public int getFlow() {
		return flow;
	}
	
}
