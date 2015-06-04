package co.edu.eafit.dis.graph;

import java.util.ArrayList;

import co.edu.eafit.dis.entity.Vehicle;

/**
 * Esta clase es, en terminos de la simulacion,
 * una interseccion, la cual puede ser vista como una bifurcacion
 * en la via, una entrada al sistema vial,
 * o una poblacion dependiendo del lugar.
 * Esta interpreatcion es transparente para la simulacion,
 * pues nada mas se hace transito por estos lugares.
 * @author tllanos, ccorre20, icardena
 * @see Node
 */
public class Intersection extends Node{

    private ArrayList<Vehicle> vehicles;

    /**
     * Constructor de una Interseccion,
     * inicializa a vehicles, la coleccion
     * carros que contiene el nodo.
     * @param id
     * @param source
     */
    public Intersection(int id, boolean source){
        super(id, source);
        vehicles =  new ArrayList<Vehicle>();
    }

    /**
     * Anade un vehiculo a la coleccion de vehiculos.
     * @param vehicle el vehiculo a añadir
     */
    public void addVehicle(Vehicle vehicle){
        vehicles.add(vehicle);
    }

    /**
     * Elemina a un vehiculo de la coleccion.
     * @param vehicle el vehiculo a remover
     */
    public void removeVehicle(Vehicle vehicle){
        vehicles.remove(vehicle);
    }

}
