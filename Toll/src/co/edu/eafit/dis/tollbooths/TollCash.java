package co.edu.eafit.dis.tollbooths;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Toll;

/**
 * Esta clase es la representacion de las casetas que solo reciben
 * efectivo, y por tanto es ligeramente mas sencilla que las otras,
 * pero en su funcionalidad, esencialemente, hace todo el manejo de
 * los usuarios sin tener que hacer varias consultas en la base de datos.
 * 
 * Esencialmente lo que hace la clase, al ser instanciada y puesta en
 * marcha, es constantemente revisar si tiene vehiculos por procesar
 * y si es del caso los procesa, primero creando la consulta en la base de datos
 * y luego ya esperando el tiempo que, segun lo estudiado,
 * toma recorrer un peaje.
 * @author tllanos, ccorre20, icardena
 * @see TollPhoto
 * @see TollScanner
 * @see java.lang.Runnable
 */
public class TollCash extends TollBooth {
	
	/**
	 * Simplemente construye al objeto, dandole el tipo y lugar,
	 * y continua con la construccion en el constructor del
	 * padre.
	 * @param location el peaje en que se encuentra.
	 * @see TollBooth#TollBooth(Toll)
	 */
	public TollCash(Toll location){
		super(location);
		type = 1;
	}
	
	/**
	 * Ver {@link TollCash}
	 */
	public void run(){
		Vehicle tmp;
		while(true){
			synchronized(q){
				while(!q.isEmpty()){
					tmp = q.poll();
					System.out.println("TRANSACCION");
					try {
						pstate = connection.prepareStatement("INSERT INTO tollcash VALUES ( 5, ?, ?, ?)");
						pstate.setTimestamp(1, new Timestamp(Calendar.getInstance().getTimeInMillis()));
						pstate.setInt(2, location.getId());
						pstate.setInt(3, type);
						pstate.execute();
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (SQLException e){
						e.printStackTrace();
					}
					tmp.setVisited(true);
					System.out.println(tmp);
					System.out.println("Transacción completada");
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
