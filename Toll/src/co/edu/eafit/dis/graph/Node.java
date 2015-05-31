package co.edu.eafit.dis.graph;

import java.util.ArrayList;

public abstract class Node {
	
	private int id;
	public boolean source;
	ArrayList<Node> connection;
	
	
	public Node(int id, boolean source){
		connection = new ArrayList<Node>();
		this.id = id;
		this.source = source;
	}
	
	public void connectTo(Node node){
		connection.add(node);
	}
	
	public int getId(){
		return id;
	}
	
	public void setSource(boolean source){
		this.source = source;
	}
	
}