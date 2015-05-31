package co.edu.eafit.dis.analysis;

import java.util.ArrayList;
import java.util.PriorityQueue;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Intersection;
import co.edu.eafit.dis.graph.Node;
import co.edu.eafit.dis.graph.Toll;

public class Calculation {
	private ArrayList<Toll> tolls = new ArrayList<Toll>();
	private ArrayList<Intersection> intersections = new ArrayList<Intersection>();
	private Vehicle vehicle;
	private int distance[];
	private int preview[];
	private boolean visited[];
	int graphSize;
	int n[];
	
	private PriorityQueue<Node> pq;
	
	public Calculation(ArrayList<Toll> tolls, 
			ArrayList<Intersection> intersections, Vehicle vehicle) {
		pq = new PriorityQueue<Node>();
		graphSize = tolls.size() + intersections.size();
		distance = new int[graphSize];
		preview  = new int[graphSize];
		visited  = new boolean[graphSize];		
	}
	
	public int[] dijkstra() {
		for(int i = 0; i < graphSize; i++) {
			distance[i] = Integer.MAX_VALUE;
			preview[i] = -1;
			visited[i] = false;
		}
		
		
		for(int i = 0; i < distance.length; i++) {
			int next = minVertex(distance, visited);
			visited[next] = true;
			boolean found = false;
			
			for(Toll t : tolls) {
				if(t.getId() == next) {
					n = t.getConnectionInt();
					found = true;
					break;
				}
			}
			
			for(int j = 0; j < intersections.size() && !found; j++) {
				if(intersections.get(j).getId() == next) {
					n = intersections.get(j).getConnectionInt();
					found = true;
					break;
				}
			}
			
			int distan;
			
			for(int j = 0; j < n.length; j++) {
				int vertex = n[j];
				if(returnFlow(vertex) != 0) {
					distan =  distance[next] + returnFlow(vertex);
				} else distan = distance[next] + returnFlow(next);
				
				if(distance[vertex] > distan) {
					distance[vertex] = distan;
					preview [vertex] = next;
				}
			}
		}
		return preview;
	}
	
	public int returnFlow(int vertex) {
		int flow = 0;
		
		for(Toll t : tolls) {		
			if(t.getId() == vertex) {
				flow = t.getFlow(); break;
			}
		}
		return flow;
	}
	
	public int minVertex(int distance[], boolean visited[]) {
		int minValue = Integer.MAX_VALUE;
		int node = -1;
		for(int i = 0; i < distance.length; i++) {
			if (!visited[i] && distance[i] < minValue) {
				node = i; minValue = distance[i];
			}
		}
		return node;
	}
}
