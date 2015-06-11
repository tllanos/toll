package co.edu.eafit.dis.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.edu.eafit.dis.dijkstra.*;
import co.edu.eafit.dis.graph.Intersection;
import co.edu.eafit.dis.graph.Node;
import co.edu.eafit.dis.graph.Toll;
import co.edu.eafit.dis.simulation.Register;

/**
 * Esta clase es el vehiculo como tal que se mueve a traves de la simulacion y
 * el que, en la gran mayoria de los casos, instiga las acciones de los demas
 * objetos.
 *
 * Esta implementado de tal manera que implemente la interfaz Runnable pues es
 * necesario, para efectos de la sincronizacion de la simulacion, que todos los
 * vehiculos trabajen tan concurrentemente como sea posible.
 *
 * @author tllanos, ccorre20, icardena
 *
 */
public class Vehicle implements Runnable {
	private Register register;
    private int userid;
    private int sensorid;
    private String plate;
    private int type;
    private int location;
    private List<Vertex> path;
    private ArrayList<Toll> tolls;
    private ArrayList<Intersection> intersections;
    private boolean visited;
    private double velocity;
    private Connection connection;

    @Override
    public void run() {

        int source;
        int time;
        location = Integer.parseInt(path.get(0).name);
        path.remove(0); 
        Node last = null;
        for (Intersection i : intersections) {
            if (location == i.getId()) {
                i.addVehicle(this);
                last = i;
                break;
            }
        }
        int next;
        while (!path.isEmpty()) {
            visited = false;
            next = Integer.parseInt(path.get(0).name);
            if (next < tolls.size() + 1) {
                for (Toll t : tolls) {
                    if (t.getId() == next) {
                        if (last instanceof Intersection) {
                            ((Intersection) last).removeVehicle(this);
                        } else {
                            System.out.println("Hubo un comportamiento"
                                    + " anomalo en la simulacion");
                            System.out.println("Frenando ejecucion");
                            System.exit(2);
                        }
                        source = location;
                        path.remove(0);
                        last = t;
                        time = getTime(next, source); // ms
                        ((Toll) last).recieveVehicle(this, source);
                        while (!this.visited) {
                            try {
                                Thread.sleep(time);
                            } catch (InterruptedException e) {
                                System.out
                                        .println("Hubo un error "
                                                + "irrecuperable "
                                                + "en la simulacion");
                                System.exit(1);
                            }
                        }
                        break;
                    }
                }
            } else {
                for (Intersection i : intersections) {
                    if (i.getId() == next) {
                        if (!(last instanceof Toll)) {
                            System.out.println("Hubo un comportamiento"
                                    + " anomalo en la simulacion");
                            System.out.println("Frenando ejecucion");
                            System.exit(2);
                        }
                        path.remove(0);
                        i.addVehicle(this);
                        time = getTime(location, next);
                        try {
                            Thread.sleep(time);
                        } catch (InterruptedException e) {
                            System.out.println("Hubo un error "
                                    + "irrecuperable " + "en la simulacion");
                            System.exit(1);
                        }
                        last = i;
                        break;
                    }
                }
            }
            location = next;
        }
        Thread.yield();
        register.destroy(this);
    }

    private int getTime(int tollid, int intersection) {

        int distance = 0;
        Statement st = null;
        ResultSet rs;

        try {
            st = connection.createStatement();
            String query = "SELECT distance FROM connection WHERE "
                    + "tollid = " + tollid + " AND " + "intersection = "
                    + intersection + " ;";

            rs = st.executeQuery(query);
            while (rs.next()) {
                distance = rs.getInt(0);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error connecting to the database");
            System.exit(1);
        }

        return (distance / (int) velocity) * 3600000;
    }

    /**
     * Este metodo construye al vehiculo, dandole todo los necesario para poder
     * hacer su recorrido.
     * 
     * @param userid
     *            el identificador del usuario, 0 si no hay
     * @param sensorid
     *            el identificador del sensor, 0 si no hay
     * @param plate
     *            la placa del carro, si esta registrada, null si no
     * @param type
     *            el tipo del carro, 1 para efecitvo, 2 para sensor, y 3 para
     *            pago con photos
     * @param path
     *            una lista que implementa el camino a tomar por el carro
     * @param tolls
     *            referencias a todos los peajes
     * @param intersections
     *            referencias a todas las intersecciones
     */
    public Vehicle(int userid, int sensorid, String plate, int type,
            List<Vertex> path, ArrayList<Toll> tolls,
            ArrayList<Intersection> intersections, Connection connection,
            Register register) {
    	this.register = register;
        this.userid = userid;
        this.sensorid = sensorid;
        this.plate = plate;
        this.type = type;
        this.path = path;
        this.tolls = tolls;
        this.intersections = intersections;
        this.connection = connection;
        this.velocity = (Math.random() * (100 - 40 + 1)) + 40; // Km/h
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Hubo un error "
                    + "irrecuperable en la simulacion");
            System.exit(1);
        }
    }

    /**
     * Determina si el carro ha completado su visita del peaje
     * 
     * @param visited
     *            true si completo.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Devuelve el tipo del carro.
     * 
     * @return el tipo del carro.
     */
    public int getType() {
        return type;
    }

    /**
     * Devuelve el identificador del usuario.
     * 
     * @return el id.
     */
    public int getUserid() {
        return userid;
    }

    /**
     * Devuelve el identificador del sensor.
     * 
     * @return sensorid.
     */
    public int getSensorId() {
        return sensorid;
    }

    /**
     * Devuelve la placa del carro.
     * 
     * @return la placa.
     */
    public String getPlate() {
        return plate;
    }

}
