package co.edu.eafit.dis.tollbooths;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;

import co.edu.eafit.dis.Main;
import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Toll;

/**
 * Esta clase es la representacion de las casetas que solo reciben efectivo, y
 * por tanto es ligeramente mas sencilla que las otras, pero en su
 * funcionalidad, esencialemente, hace todo el manejo de los usuarios sin tener
 * que hacer varias consultas en la base de datos.
 * <p>
 * Esencialmente lo que hace la clase, al ser instanciada y puesta en marcha, es
 * constantemente revisar si tiene vehiculos por procesar y si es del caso los
 * procesa, primero creando la consulta en la base de datos y luego ya esperando
 * el tiempo que, segun lo estudiado, toma recorrer un peaje.
 *
 * @author tllanos, ccorre20, icardena
 * @see TollPhoto
 * @see TollScanner
 * @see java.lang.Runnable
 */
public class TollCash extends TollBooth { 

    /**
     * Simplemente construye al objeto, dandole el tipo y lugar, y continua con
     * la construccion en el constructor del padre.
     * 
     * @param location
     *            el peaje en que se encuentra.
     * @see TollBooth#TollBooth(Toll)
     */
    public TollCash(Toll location) {
        super(location);
        for (int i : location.getConnectionInt())
            q.put(i, new LinkedList<Vehicle>());
        type = 1;
    }

    /**
     * Ver {@link TollCash}
     */
    @Override
    public void run() {
        Vehicle tmp;
        boolean cars;
        while (Main.running) {
            cars = true;
            synchronized (q) {
                for (LinkedList<Vehicle> l : q.values()) {
                    if (!l.isEmpty()) {
                        cars = true;
                    }
                }
                while (cars) {
                    try {
                        for (int i = 0; i < location.getConnectionInt().length; i++) {
                            tmp = q.get(location.getConnectionInt()[i]).poll();
                            if (tmp != null) {
                                pstate = connection
                                        .prepareStatement("INSERT INTO tollcash VALUES ( 5, ?, ?, ?)"
                                                + "ON DUPLICATE KEY UPDATE date = date + INTERVAL 1 SECOND;");
                                pstate.setTimestamp(1, new Timestamp(Calendar
                                        .getInstance().getTimeInMillis()));
                                pstate.setInt(2, location.getId());
                                pstate.setInt(3, type);
                                pstate.execute();
                                tmp.setVisited(true);
                            }
                        }
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
