package co.edu.eafit.dis.graph;

import java.util.ArrayList;

public abstract class Node {
	
	private int id;
	public boolean source;
	ArrayList<Node> connection;
	ArrayList<Integer> connectionInt;
	
	public Node(int id, boolean source){
		connection = new ArrayList<Node>();
		connectionInt =  new ArrayList<Integer>();
		this.id = id;
		this.source = source;
	}
	
	public void connectTo(Node node){
		connection.add(node);
	}
	
	public void connectTo(int node){
		connectionInt.add(new Integer(node));
	}
	
	public int[] getConnectionInt() {
		int connectionInt[] = new int[connection.size()]; 
		for(int i = 0; i < connectionInt.length; i++) {
			connectionInt[i] = this.connectionInt.get(i);
		}
		return connectionInt;
	}
	
	public int getId(){
		return id;
	}
	
	public void setSource(boolean source){
		this.source = source;
	}
	
	public ArrayList<Node> getConnection() {
		return connection;
	}
}