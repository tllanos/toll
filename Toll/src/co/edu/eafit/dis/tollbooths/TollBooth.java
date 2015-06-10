package co.edu.eafit.dis.tollbooths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import co.edu.eafit.dis.graph.*;
import co.edu.eafit.dis.entity.Vehicle;

/**
 * Esta clase es la abstraccion de la caseta de un peaje, que mas precisamente
 * puede ser vista como una caseta de ida y de venida. la cual, como es usual en
 * colombia y diferentes partes del mundo, se satura si hay mucho flujo en
 * cualquiera de los sentidos.
 * <p>
 * Una caseta ademas siempre debe existir ligada a un peaje, de lo contrario no
 * tendria sentido.
 * <p>
 * Ahora, has de decirse, esta clase es una implementacion de la interfaz
 * Runnable, pues, para poder concurrentemente simular a todas las casetas
 * habria que hacerse un procesamiento secuencial de cada una si no se
 * implementasen concurrentemente.
 *
 * @author tllanos, ccorre20, icardena
 * @see java.lang.Runnable
 * @see co.edu.eafit.dis.graph.Toll
 */
public abstract class TollBooth implements Runnable {

    protected Toll location;
    protected int bid;
    protected boolean status;
    protected int type;
    protected Connection connection;
    protected String query;
    protected Statement st;
    protected PreparedStatement pstate;
    protected ResultSet rs;
    public Map<Integer, LinkedList<Vehicle>> q;

    /**
     * Este es el constructor de las casetas, basicamente se encarga de
     * prepararse para hacer las transacciones y para hacer el manejo adecuado
     * de los vehiculos.
     * 
     * @param location
     *            el peaje al que pertenece la caseta.
     */
    public TollBooth(Toll location) {
        this.location = location;
        q = new HashMap<Integer, LinkedList<Vehicle>>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:"
                    + "//toll.cqkduygrcpmt.us-east-1.rds.amazonaws.com:"
                    + "3306/tollcontrol?" + "user=root&password=rootroot");
            st = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Error connecting to the database");
            System.exit(1);
        }
    }

    /**
     * Este metodo simplemente retorna el tipo de la caseta.
     * 
     * @return el tipo
     */
    public int getType() {
        return type;
    }
}
