package co.edu.eafit.dis.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.eafit.dis.dijkstra.*;
import co.edu.eafit.dis.graph.Intersection;
import co.edu.eafit.dis.graph.Node;
import co.edu.eafit.dis.graph.Toll;

/**
 * Esta clase es el vehiculo como tal que se mueve a traves de
 * la simulacion y el que, en la gran mayoria de los casos,
 * instiga las acciones de los demas objetos.
 * 
 * Esta implementado de tal manera que implemente la interfaz Runnable
 * pues es necesario, para efectos de la sincronizacion de la simulacion,
 * que todos los vehiculos trabajen tan concurrentemente como sea posible.
 * 
 * @author tllanos, ccorre20, icardena
 *
 */
public class Vehicle implements Runnable{
	private int userid;
	private int sensorid;
	private String plate;
	private int type;
	private int location;
	private List<Vertex> path;
	private ArrayList<Toll> tolls;
	private ArrayList<Intersection> intersections;
	private boolean visited;
	
	public void run(){		
		location = Integer.parseInt(path.get(0).name);
		path.remove(0);
		Node last = null;
		System.out.println(userid + ": " + location);
		for(Intersection i: intersections){
			if(location == i.getId()){
				i.addVehicle(this);
				last = i;
				break;
			}
		}
		
		int next;
		while(!path.isEmpty()){
			visited = false;
			next = Integer.parseInt(path.get(0).name);
			if(next < tolls.size()+1){
				for(Toll t: tolls){
					if(t.getId() == next){
						System.out.println("Ingresando a: " + next);
						if(last instanceof Intersection){
							((Intersection) last).removeVehicle(this);
						}else{
							System.out.println("Esto sucedio");
							Thread.dumpStack();
							System.exit(1);
						}
						path.remove(0);
						last = t;
						((Toll)last).recieveVehicle(this);	
						System.out.println("Estoy esperando");
						while(!this.visited){
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println("Pase");
						System.out.println("Hice mi transaccion");
						break;
					}
				}
			}else{
				for(Intersection i: intersections){
					if(i.getId() == next){
						System.out.println("Ingresando a: " + next);
						if(!(last instanceof Toll)){
							System.out.println("Esto sucedio");
							Thread.dumpStack();
							System.exit(1);
						}
						path.remove(0);
						i.addVehicle(this);
						try {
							System.out.println("transitando");
							Thread.sleep(30000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						last = i;
						break;
					}
				}
			}
		}
		Thread.yield();
		System.out.println("Llegue");
	}
	
	/**
	 * Este metodo construye al vehiculo,
	 * dandole todo los necesario para poder hacer su recorrido.
	 * @param userid el identificador del usuario, 0 si no hay
	 * @param sensorid el identificador del sensor, 0 si no hay
	 * @param plate la placa del carro, si esta registrada, null si no
	 * @param type el tipo del carro, 1 para efecitvo, 2 para sensor, y 3 para pago con photos
	 * @param path una lista que implementa el camino a tomar por el carro
	 * @param tolls referencias a todos los peajes
	 * @param intersections referencias a todas las intersecciones
	 */
	public Vehicle(int userid, 
			int sensorid,
			String plate,
			int type,
			List<Vertex> path, 
			ArrayList<Toll> tolls, 
			ArrayList<Intersection> intersections){
		this.userid = userid;
		this.sensorid = sensorid;
		this.plate = plate;
		this.type = type;
		this.path = path;
		this.tolls = tolls;
		this.intersections = intersections;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Determina si el carro ha completado su visita del peaje
	 * @param visited true si completo.
	 */
	public void setVisited(boolean visited){
		this.visited = visited;
		System.out.println("VISITE");
	}
	
	/**
	 * Devuelve el tipo del carro.
	 * @return el tipo del carro.
	 */
	public int getType(){
		return type;
	}
	
	/**
	 * Devuelve el identificador del usuario.
	 * @return el id.
	 */
	public int getUserid() {
		return userid;
	}
	
	/**
	 * Devuelve el identificador del sensor.
	 * @return sensorid.
	 */
	public int getSensorId(){
		return sensorid;
	}
	
	/**
	 * Devuelve la placa del carro.
	 * @return la placa.
	 */
	public String getPlate(){
		return plate;
	}
	
}
