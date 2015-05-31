package co.edu.eafit.dis.graph;

public class Toll extends Node{
	
	private int flow;
	
	public Toll(int id, boolean source){
		super(id, source);
	}
	
	public void setFlow(int f){
		flow = f;
	}
	
	public int getFlow() {
		return flow;
	}
}
