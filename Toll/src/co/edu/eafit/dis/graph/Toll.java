package co.edu.eafit.dis.graph;

import java.util.ArrayList;

import co.edu.eafit.dis.tollbooths.TollBooth;

public class Toll extends Node{
	
	private ArrayList<TollBooth> b1, b2;
	private int flow;
	
	public Toll(int id, boolean source){
		super(id, source);
		b1 = new ArrayList<TollBooth>();
		b2 = new ArrayList<TollBooth>();
	}
	
	public void setFlow(int f){
		flow = f;
	}
	
	public int getFlow() {
		return flow;
	}
}
