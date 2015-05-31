package co.edu.eafit.dis.graph;

public class Toll extends Node{
	
	private int maxflow;
	
	public Toll(int id, boolean source){
		super(id, source);
	}
	
	public void setMaxFlow(int mf){
		maxflow = mf;
	}
	
}
