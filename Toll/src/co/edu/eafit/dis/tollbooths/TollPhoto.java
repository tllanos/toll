package co.edu.eafit.dis.tollbooths;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;

import co.edu.eafit.dis.Main;
import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Toll;

/**
 * Esta clase es la representacion de las casetas que utilizan un analisis de
 * una foto de la placa de los carros, y por tanto es ligeramente mas compleja
 * que las otras, pero en su funcionalidad, esencialemente, hace todo el manejo
 * de los usuarios haciendo algunas consultas a la base de datos, pero, no es
 * tan significativo como para comprometer la velocidad y eficacia de la
 * simulacion.
 * <p>
 * Esencialmente lo que hace la clase, al ser instanciada y puesta en marcha, es
 * constantemente revisar si tiene vehiculos por procesar y si es del caso los
 * procesa, primero creando la consulta en la base de datos y luego ya esperando
 * el tiempo que, segun lo estudiado, toma recorrer un peaje.
 * 
 * @author tllanos, ccorre20, icardena
 * @see TollCash
 * @see TollScanner
 * @see java.lang.Runnable
 */
public class TollPhoto extends TollBooth {

    /**
     * Simplemente construye al objeto, dandole el tipo y lugar, y continua con
     * la construccion en el constructor del padre.
     * 
     * @param location
     *            el peaje en que se encuentra.
     * @see TollBooth#TollBooth(Toll)
     */
    public TollPhoto(Toll location) {
        super(location);
        for (int i : location.getConnectionInt())
            q.put(i, new LinkedList<Vehicle>());
        type = 3;
    }

    /**
     * Ver {@link TollPhoto}
     */
    @Override
    public void run() {
        Vehicle tmp;
        boolean cars;
        while (Main.running) {
            cars = false;
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
                                query = "SELECT funds FROM users where userid = "
                                        + tmp.getUserid() + ";";
                                rs = st.executeQuery(query);
                                rs.next();
                                int fund = rs.getInt(1);
                                if (fund < 5) {
                                    System.out
                                            .println("Un usuario debe dinero");
                                }
                                pstate = connection
                                        .prepareStatement("INSERT INTO tollphoto "
                                                + "VALUES ( 5, ?, ?, ?, ?)"
                                                + "ON DUPLICATE KEY UPDATE date = date + INTERVAL 1 SECOND;");
                                pstate.setTimestamp(1, new Timestamp(Calendar
                                        .getInstance().getTimeInMillis()));
                                pstate.setInt(2, location.getId());
                                pstate.setString(3, tmp.getPlate());
                                pstate.setInt(4, type);
                                pstate.execute();

                                query = "UPDATE  users " + "SET funds = "
                                        + (fund - 5.0d) + "WHERE userid = "
                                        + tmp.getUserid() + ";";
                                st.execute(query);
                                tmp.setVisited(true);
                            }
                        }
                        Thread.sleep(100);
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
