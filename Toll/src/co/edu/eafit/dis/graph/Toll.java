package co.edu.eafit.dis.graph;

import java.util.ArrayList;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.tollbooths.TollBooth;
import co.edu.eafit.dis.tollbooths.TollCash;
import co.edu.eafit.dis.tollbooths.TollPhoto;
import co.edu.eafit.dis.tollbooths.TollScanner;

/**
 * Esta clase representa a un peaje en el sistema vial, el cual estara encargado
 * de "restringir" el movimiento del sistema, y es aqui en los peajes donde se
 * llavaran a cabo las transacciones, y donde se calculara el flujo del sistema.
 *
 * @author tllanos, ccorre20, icardena
 * @see Node
 * @see co.edu.eafit.dis.tollbooths.TollBooth
 * @see co.edu.eafit.dis.tollbooths.TollCash
 * @see co.edu.eafit.dis.tollbooths.TollScanner
 * @see co.edu.eafit.dis.tollbooths.TollPhoto
 */
public class Toll extends Node {

    public ArrayList<TollCash> cashB;
    public ArrayList<TollScanner> scannerB;
    public ArrayList<TollPhoto> photoB;
    private ArrayList<Thread> boothP;
    private int flow;

    /**
     * Constructor del Toll, este se encargara de crear el nuevo peaje, y
     * ademas, inicializar dentro de si a las casetas, que se encargaran de
     * hacer el procesamiento como tal de los vehiculos.
     * 
     * @param id
     * @param source
     */
    public Toll(int id, boolean source) {
        super(id, source);
    }

    /**
     * Este metodo "recibe a un vehiculo" y dependiendo de su comportamiento y
     * tipo lo mete a una de sus casetas.
     * 
     * @param vehicle
     *            el vehiculo a procesar
     */
    public void recieveVehicle(Vehicle vehicle, int source) {
        switch (vehicle.getType()) {
        case 1:
            cashB.get(0).q.get(source).add(vehicle);
            break;
        case 2:
            scannerB.get(0).q.get(source).add(vehicle);
            break;
        case 3:
            photoB.get(0).q.get(source).add(vehicle);
            break;
        }
    }

    public void initiateBooths() {
        cashB = new ArrayList<TollCash>();
        cashB.add(new TollCash(this));
        scannerB = new ArrayList<TollScanner>();
        scannerB.add(new TollScanner(this));
        photoB = new ArrayList<TollPhoto>();
        photoB.add(new TollPhoto(this));
        boothP = new ArrayList<Thread>();

        for (TollBooth c : cashB) {
            boothP.add(new Thread(c));
        }
        for (TollBooth c : scannerB) {
            boothP.add(new Thread(c));
        }
        for (TollBooth c : photoB) {
            boothP.add(new Thread(c));
        }
        for (Thread t : boothP) {
            t.start();
        }
    }

    /**
     * Este metodo asigna el "flujo" al objeto, que es calculado por la
     * simulacion y este se encarga de darle sentido al analisis de dijkstra.
     * 
     * @param f
     *            el flujo
     */
    public void setFlow(int f) {
        flow = f;
    }

    /**
     * Retorna el flujo.
     * 
     * @return el flujo.
     */
    public int getFlow() {
        return flow;
    }

}
