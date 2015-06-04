package co.edu.eafit.dis.tollbooths;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Toll;

/**
 * Esta clase es la representacion de las casetas que 
 * utilizan un analisis de un sensor con un identificador unico,
 * y por tanto es ligeramente compleja,
 * pero en su funcionalidad, esencialemente, hace todo el manejo de
 * los usuarios haciendo algunas consultas a la base de datos,
 * pero, no es tan significativo como para comprometer
 * la velocidad y eficacia de la simulacion. 
 * <p>
 * Esencialmente lo que hace la clase, al ser instanciada y puesta en
 * marcha, es constantemente revisar si tiene vehiculos por procesar
 * y si es del caso los procesa, primero creando la consulta en 
 * la base de datos y luego ya esperando el tiempo que, segun lo estudiado,
 * toma recorrer un peaje.
 * @author tllanos, ccorre20, icardena
 * @see TollCash
 * @see TollPhoto
 * @see java.lang.Runnable
 */
public class TollScanner extends TollBooth{
	
	/**
	 * Simplemente construye al objeto, dandole el tipo y lugar,
	 * y continua con la construccion en el constructor del
	 * padre.
	 * @param location el peaje en que se encuentra.
	 * @see TollBooth#TollBooth(Toll)
	 */
	public TollScanner(Toll location){
		super(location);
		type = 2;
	}
	
	/**
	 * Ver {@link TollPhoto}
	 */
	public void run(){
		Vehicle tmp;
		while(true){
			synchronized(q){
				while(!q.isEmpty()){
					tmp = q.poll();
					try {
						query = "SELECT funds FROM users where userid = "
								+tmp.getUserid()+";";
						rs = st.executeQuery(query);
						rs.next();
						int fund = rs.getInt(1);
						if(fund < 5){
							System.out.println("Un usario debe dinero");
						}
						
						pstate = connection.prepareStatement(
								"INSERT INTO tollsensor "
								+ "VALUES ( 5, ?, ?, ?, ?)");
						pstate.setTimestamp(1, 
								new Timestamp(Calendar.getInstance()
										.getTimeInMillis()));
						pstate.setInt(2, location.getId());
						pstate.setInt(3, tmp.getSensorId());
						pstate.setInt(4, type);
						pstate.execute();
						
						query = "UPDATE  users " +
								"SET funds = " + (fund-5.0d) + 
								"WHERE userid = " + tmp.getUserid()+";";
						st.execute(query);
						
						Thread.sleep(15000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (SQLException e){
						e.printStackTrace();
					}
					tmp.setVisited(true);
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
