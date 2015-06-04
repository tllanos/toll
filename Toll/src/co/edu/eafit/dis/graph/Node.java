package co.edu.eafit.dis.graph;

import java.util.ArrayList;

/**
 * Esta clase representa a un nodo en el grafo del sistema
 * vial, y por tanto podra ser o un peaje o una interseccion.
 * Esta clase define los metodos principales comunes a ambas.
 * @author tllanos, ccorre20, icardena
 * @see Intersection, Toll
 */
public abstract class Node {
	
	private int id;
	public boolean source;
	private ArrayList<Node> connection;
	private ArrayList<Integer> connectionInt;
	
	/**
	 * Constructor del nodo, comun  a todos las
	 * instancias de quienes hereden al nodo e
	 * inicializa las conexiones.
	 * @param id es el valor numero que representa al nodo en la simulacion
	 * @param source determina si el nodo es un punto de entrada al sistema.
	 */
	public Node(int id, boolean source){
		connection = new ArrayList<Node>();
		connectionInt =  new ArrayList<Integer>();
		this.id = id;
		this.source = source;
	}
	
	/**
	 * Conecta al Nodo a otro nodo
	 * @param node el nodo al que se conectara.
	 */
	public void connectTo(Node node){
		connection.add(node);
	}
	
	/**
	 * Conecta al Nodo a otro nodo
	 * @param node recibe es el identificador del destino y no el objeto.
	 */
	public void connectTo(int node){
		connectionInt.add(new Integer(node));
	}
	
	/**
	 * Retorna las conexiones a los demas nodos, es decir,
	 * todas las aristas, entrantes y salientes.
	 * @return devuelve la colleccion que representa las adyacencias.
	 */
	public int[] getConnectionInt() {
		int connectionInt[] = new int[connection.size()]; 
		for(int i = 0; i < connectionInt.length; i++) {
			connectionInt[i] = this.connectionInt.get(i);
		}
		return connectionInt;
	}
	
	/**
	 * Devuelve el identificador numerico del nodo.
	 * @return id del nodo
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Asigna el identificador al nodo.
	 * @param id el id a ser agregado
	 */
	public void setId(int id){
		this.id = id;
	}
	
	/**
	 * Asigna al nodo si es o no fuente.
	 * @param source verdadero si es fuente.
	 */
	public void setSource(boolean source){
		this.source = source;
	}
	
	/**
	 * Devuele todas las conexiones del Nodo
	 * @return las conexiones
	 */
	public ArrayList<Node> getConnection() {
		return connection;
	}
}