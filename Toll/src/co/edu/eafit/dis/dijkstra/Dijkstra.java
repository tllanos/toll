
// http://www.algolist.com/code/java/Dijkstra's_algorithm.

package co.edu.eafit.dis.dijkstra;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import co.edu.eafit.dis.graph.Intersection;
import co.edu.eafit.dis.graph.Toll;

class Edge {
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}

public class Dijkstra {
	
    public ArrayList<Vertex> v;
    
    public static void computePaths(Vertex source) {
        source.minDistance = 0;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);

		while (!vertexQueue.isEmpty()) {
		    Vertex u = vertexQueue.poll();
	        // Visit each edge exiting u
	        for (Edge e : u.adjacencies)
	        {
	            Vertex v = e.target;
	            double weight = e.weight;
	            double distanceThroughU = u.minDistance + weight;
	            if (distanceThroughU < v.minDistance) {
				    vertexQueue.remove(v);
				    v.minDistance = distanceThroughU ;
				    v.previous = u;
				    vertexQueue.add(v);
	            }
	        }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        for(Vertex v: path){
        	System.out.println(v.name);
        }
        return path;
    }
    
    public int getIntFlow(int id, ArrayList<Toll> toll) {
//    	System.out.println("Flow to " + id);
    		int flow = 0;
    	for(Toll t : toll) {
    		if(t.getId() == id) {
    			flow = t.getFlow(); 
    			break;
    		}
    	}
    	return flow;
    }
    
    public Vertex matchVertex(String id){
    	for (Vertex vert: v){
    		if(vert.name.equals(id)){
    			return vert;
    		}
    	}
    	return null;
    }

    public List<Vertex> initDijkstra(ArrayList<Toll> toll, ArrayList<Intersection> inter, int init, int dest) {
    	
    	
    	v = new ArrayList<Vertex>();
    	
    	for(int i = 0; i < toll.size(); i++) {
    		v.add(new Vertex(toll.get(i).getId() + ""));
    	}
    	
    	for(int i = 0; i < inter.size(); i++) {
    		v.add(new Vertex(inter.get(i).getId() + ""));
    	}
    	
    	for(int i = 0; i < toll.size(); i++) {
//    		1System.out.println(toll.get(i).getId());
    		Edge edges[] = new Edge[toll.get(i).getConnectionInt().length];
    		int connection[] = toll.get(i).getConnectionInt();
    		for(int j = 0; j < toll.get(i).getConnectionInt().length; j++) {
    			edges[j] = new Edge(matchVertex(Integer.toString(connection[j])), 
    					toll.get(j).getFlow());
//    			System.out.println("" + toll.get(i).getId() + " -> " + (connection[j]));
    		}
    		v.get(i).adjacencies = edges;
    	}

//    	System.out.println("PARTIR");
    	
    	for(int i = 0; i < inter.size(); i++) {
//    		System.out.println(inter.get(i).getId());
    		Edge edges[] = new Edge[inter.get(i).getConnectionInt().length];
    		int connection[] = inter.get(i).getConnectionInt();
    		for(int j = 0; j < inter.get(i).getConnectionInt().length; j++) {
    			edges[j] = new Edge(matchVertex(Integer.toString(connection[j])), 
    					getIntFlow(connection[j], toll));
//    			System.out.println(inter.get(i).getId() + " -> " + connection[j]);
    		}
    		v.get(i+toll.size()).adjacencies = edges;
    	}
    	
    	
        /*Vertex v0 = new Vertex("Redvile");
		Vertex v1 = new Vertex("Blueville");
		Vertex v2 = new Vertex("Greenville");
		Vertex v3 = new Vertex("Orangeville");
		Vertex v4 = new Vertex("Purpleville");
	
		v0.adjacencies = new Edge[]{ new Edge(v1, 5),
		                             new Edge(v2, 10),
	                                 new Edge(v3, 8) };
		v1.adjacencies = new Edge[]{ new Edge(v0, 5),
		                             new Edge(v2, 3),
		                             new Edge(v4, 7) };
		v2.adjacencies = new Edge[]{ new Edge(v0, 10),
	                               new Edge(v1, 3) };
		v3.adjacencies = new Edge[]{ new Edge(v0, 8),
		                             new Edge(v4, 2) };
		v4.adjacencies = new Edge[]{ new Edge(v1, 7),
	                               new Edge(v3, 2) };
		Vertex[] vertices = { v0, v1, v2, v3, v4 };*/
    	
	    computePaths(v.get(init-1));
	    
	    return getShortestPathTo(v.get(dest-1));
	    
    }
}
