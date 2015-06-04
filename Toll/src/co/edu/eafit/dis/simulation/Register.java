package co.edu.eafit.dis.simulation;

import java.util.ArrayList;

import co.edu.eafit.dis.entity.Vehicle;

/**
 * Clase encargada de almacenar las entidades (vehiculos) que sean producidas
 * para efectos de la simulación. Además, implementa los métodos que se
 * encargan de operar sobre dichas entidades, de forma unificada.
 * @author tllanos, ccorre20, icardena
 */
public class Register {
	private ArrayList<Vehicle> vehs;
	
	/**
	 * Inicializa la variable "vehs" de tipo "ArrayList", la cual contiene
	 * todos aquellos vehiculos que existen en la simulación.
	 */
	public Register(){
		vehs =  new ArrayList<Vehicle>();
	}
	
	/**
	 * Adiciona un nuevo objeto de tipo "Vehicle" a la lista "vehs"
	 * (i.e., adiciona un nuevo vehiculo a la simulación).
	 * 
	 * @param veh el vehiculo a adicionarse
	 */
	public void add(Vehicle veh){
		vehs.add(veh);
	}
	
	/**
	 * Elimina un objeto especifico de tipo Vehiculo de la lista "vehs"
	 * (i.e., elimina un vehiculo de la simulación, lo que 
	 * significa que el mismo ha alcanzado su destino).
	 * 
	 * @param veh el vehiculo a eliminar
	 */
	public void destroy(Vehicle veh){
		vehs.remove(veh);
	}
	
	/**
	 * Verifica si un vehiculo especifico existe en la lista "vehs"
	 * (i.e., verifica si un vehiculo ha alcanzado su destino).
	 * <p>
	 * Nota: La verifiación es realizada basandose en el atributo "sensorid"
	 * de cada vehiculo.
	 * 
	 * @param sensorid el id del sensor a buscar
	 * @return Verdadero si un objeto de tipo vehiculo existe en 
	 * la lista "vehs" y falso en caso contrario.
	 */
	public boolean exists(int sensorid){
		for(Vehicle v: vehs){
			if(v.getSensorId() == sensorid){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verifica si un vehiculo especifico existe en la lista "vehs"
	 * (i.e., verifica si un vehiculo ha alcanzado su destino).
	 * <p>
	 * Nota: La verifiación es realizada basandose en el atributo "plate"
	 * de cada vehiculo.
	 *  
	 * @param plate la placa a cotejar.
	 * @return Verdadero si un objeto de tipo vehiculo existe en 
	 * la lista "vehs" y falso en caso contrario.
	 */
	public boolean exists(String plate){
		for(Vehicle v: vehs){
			if(v.getPlate() != null && v.getPlate().equals(plate)){
				return true;
			}
		}
		return false;
	}
	
}