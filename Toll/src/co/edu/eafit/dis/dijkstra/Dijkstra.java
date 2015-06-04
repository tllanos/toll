//Implementacion de Dijkstra adaptada de
//http://www.algolist.com/code/java/Dijkstra's_algorithm
//y convertida para los requisitos del problema con
//la mayoria del codigo nuestro y original.
package co.edu.eafit.dis.dijkstra;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import co.edu.eafit.dis.graph.Intersection;
import co.edu.eafit.dis.graph.Toll;

/**
 * @author tllanos, ccorre20, icardena
 *
 * Clase encargada de proveer la información de cada vértice:
 * Su objeto identificador y peso respectivo.
 *
 */
class Edge {
    public final Vertex target;
    public final double weight;

    /**
     * Crea una arista con un peso especifico.
     *
     * @param argTarget nodo objetivo.
     * @param argWeight peso del nodo.
     */
    public Edge(Vertex argTarget, double argWeight){
        target = argTarget;
        weight = argWeight;
    }
}

/**
 * Esta clase simplemente se encarga de hacer el procesamiento
 * basado en el algoritmo de Dijkstra para el camino
 * con la menor congestion.
 * @author tllanos, ccorre20, icardena, algolist.com
 */
public class Dijkstra {

    public ArrayList<Vertex> v;

    /**
     * Aplica el algoritmo de Dijkstra al grafo proveido a partir de un
     * vertice fuente.
     * <p>
     * Nota: La implementación del algoritmo de Dijkstra se realiza utilizando
     * una cola de prioridad.
     * @param source el nodo fuente
     */
    public static void computePaths(Vertex source) {
        source.minDistance = 0;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);
        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();
        // Visit each edge exiting u
	        for (Edge e : u.adjacencies){
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

    /**
     * Devuelve una lista de tipo "list<Vertex>", la cual continene el camino
     * más corto de un punto inical A a un punto final B.
     * @param target el nodo objetivo.
     * @return una lista que representa al camino.
     */
    public static List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    /**
     * Se mueve por cada peaje hasta encontrar aquel cuyo número de
     * identificación concuerda con el proveido al llamar la función.
     *
     * @param id id del nodo.
     * @param toll referencia a todos los tolls.
     * @return Flujo almacenado en cada peaje.
     */
    public int getIntFlow(int id, ArrayList<Toll> toll) {
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
    /**
     * Llama al método Dijkstra y comienza el procesamiento del
     * "camino más corto".
     * @param toll referencia a todos los tolls.
     * @param inter referencia a todos las intersecciones.
     * @param init nodo inicial.
     * @param dest nodo destino.
     * @return Una lista de tipo "list<Vertex>", la cual continene el camino
     *             más corto de un punto inical A a un punto final B.
     */
    public List<Vertex> initDijkstra(ArrayList<Toll> toll,
        ArrayList<Intersection> inter,
        int init, int dest) {
		v = new ArrayList<Vertex>();

        for(int i = 0; i < toll.size(); i++) {
            v.add(new Vertex(toll.get(i).getId() + ""));
        }

        for(int i = 0; i < inter.size(); i++) {
            v.add(new Vertex(inter.get(i).getId() + ""));
        }

        for(int i = 0; i < toll.size(); i++) {
            Edge edges[] = new Edge[toll.get(i).getConnectionInt().length];
            int connection[] = toll.get(i).getConnectionInt();
            for(int j = 0; j<toll.get(i).getConnectionInt().length; j++){
            	edges[j] = new Edge(matchVertex(
                    Integer.toString(connection[j])),
                    toll.get(j).getFlow());
            }
            v.get(i).adjacencies = edges;
        }

        for(int i = 0; i < inter.size(); i++) {
            Edge edges[] = new Edge[inter.get(i).getConnectionInt().length];
            int connection[] = inter.get(i).getConnectionInt();
            for(int j = 0; j<inter.get(i).getConnectionInt().length; j++){
            	edges[j] = new Edge(matchVertex(
                    Integer.toString(connection[j])),
                    getIntFlow(connection[j], toll));
            }
            v.get(i+toll.size()).adjacencies = edges;
        }
        computePaths(v.get(init-1));
        return getShortestPathTo(v.get(dest-1));
    }
}
