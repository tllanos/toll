package co.edu.eafit.dis.simulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;

import co.edu.eafit.dis.dijkstra.Dijkstra;
import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Intersection;
import co.edu.eafit.dis.graph.Toll;

/**
 * Clase encargada de generar vehiculos para simular un ambiente controlado en
 * el cual se produce cierta cantidad de tráfico vehicular en función de la hora
 * del día.
 * 
 * @author tllanos, ccorre20, icardena
 */
public class Generator implements Runnable {

	private CopyOnWriteArrayList<Thread> vSim;
	private int li, range, init, dest;
	private Vehicle veh;
	private Dijkstra d;
	private Thread t;
	private ArrayList<Intersection> intersections;
	private ArrayList<Toll> tolls;
	private Register register;
	private Connection connection;
	private Statement statement;
	private ResultSet rs;

	/**
	 * Inicializa la conexión con la base de datos y asigna los valores
	 * requeridos para dar inicio a la generación de vehiculos.
	 * <p>
	 * El proceso de generación de vehiculos usa la función seno como una forma
	 * de controlar la cantidad de autos que serán creados. Para cumplir con
	 * dicho objetivo, se calcula el porcentaje de tiempo transcurrido, basado
	 * en el sistema 24h, el cual es multiplica por 2π. Luego, la función
	 * |sin(Ө)| es aplicada al resultado de la operación anterior y finalmente
	 * multiplicado por la cantidad máxima de vehiculos que se requiere
	 * producir.
	 *
	 * @param vSim
	 *            los "threads" para los carros.
	 * @param intersections
	 *            todas las intersecciones.
	 * @param tolls
	 *            todos los "tolls".
	 * @param register
	 *            el registro de los carros generados.
	 */
	public Generator(CopyOnWriteArrayList<Thread> vSim,
			ArrayList<Intersection> intersections, ArrayList<Toll> tolls,
			Register register) {
		d = new Dijkstra();
		this.vSim = vSim;
		this.register = register;
		int count = 0, last = 0;
		for (Intersection i : intersections) {
			if (i.source == true) {
				last = i.getId();
				count++;
			}
		}
		range = count;
		li = last - range + 1;
		this.intersections = intersections;
		this.tolls = tolls;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql:"
					+ "//toll.cqkduygrcpmt.us-east-1.rds.amazonaws.com:"
					+ "3306/tollcontrol?" + "user=root&password=rootroot");
			statement = connection.createStatement();
		} catch (Exception e) {
			System.out.println("Error connecting to the database");
			System.exit(1);
		}
	}

	public synchronized void run() {
		while (true) {
			Calendar rightNow = Calendar.getInstance();
			int hour = rightNow.get(Calendar.HOUR_OF_DAY);
			double f = (hour / 24.0d);
			int quantity = Math
					.abs((int) (((Math.sin((2 * Math.PI) * f)) * 5940.0d) + 0.5d)) + 60;
			long dt = (long) ((3600.0d / quantity) * 1000.0d);
			init = (int) ((Math.random() * (range - 1)) + li);
			dest = (int) ((Math.random() * (range - 1)) + li);
			while (dest == init) {
				dest = (int) ((Math.random() * (range - 1)) + li);
			}
			int nran = (int) ((Math.random() * 100.0d) + 0.5d);
			String platetmp = null, query;
			int utmp = 0, max = 0, sensortmp = 0;
			// Evaluacion ternaria.
			if (nran < 70) {
				nran = 1;
			} else if (nran < 85) {
				nran = (int) ((Math.random() * 100.0d) + 0.5d);
				nran = (nran < 50) ? 2 : 3;
			} else {
				nran = (int) ((Math.random() * 100.0d) + 0.5d);
				nran = (nran < 50) ? 4 : 5;
			}
			switch (nran) {
			default:
			case 1:
				veh = new Vehicle(0, 0, null, 1, d.initDijkstra(tolls,
						intersections, init, dest), tolls, intersections, connection);
				register.add(veh);
				break;
			case 2:
				try {
					do {
						rs = statement.executeQuery("SELECT userid, "
								+ "sensorid FROM users where type = 1 "
								+ "ORDER BY RAND() LIMIT 1;");
						rs.next();
						utmp = rs.getInt(1);
						sensortmp = rs.getInt(2);
					} while (register.exists(sensortmp));
				} catch (SQLException e) {
					System.out.println("Hubo un error "
							+ "irrecuperable en la simulacion");
					System.exit(1);
				}
				veh = new Vehicle(utmp, sensortmp, null, 2, d.initDijkstra(
						tolls, intersections, init, dest), tolls, intersections, connection);
				register.add(veh);
				break;
			case 3:
				try {
					query = "SELECT MAX(userid) FROM users";
					rs = statement.executeQuery(query);
					rs.next();
					max = rs.getInt(1);
					max++;
					query = "INSERT INTO users VALUES (" + max + " , " + max
							+ " " + ", 1 , " + max + " , NULL , 1000);";
					statement.execute(query);
				} catch (SQLException e) {
					System.out.println("Hubo un error "
							+ "irrecuperable en la simulacion");
					System.exit(1);
				}
				veh = new Vehicle(max, max, null, 2,
						d.initDijkstra(tolls, intersections, init, dest),
						tolls, intersections, connection);
				register.add(veh);
				break;
			case 4:
				try {
					do {
						rs = statement.executeQuery("SELECT userid, "
								+ "plate FROM users " + "WHERE type = 2 "
								+ "ORDER BY RAND() " + "LIMIT 1;");
						if (!rs.next())
							break;
						utmp = rs.getInt(1);
						platetmp = rs.getString(2);
					} while (register.exists(platetmp));
				} catch (SQLException e) {
					System.out.println("Hubo un error "
							+ "irrecuperable en la simulacion");
					System.exit(1);
				}
				veh = new Vehicle(utmp, 0, platetmp, 3, d.initDijkstra(tolls,
						intersections, init, dest), tolls, intersections, connection);
				register.add(veh);
				break;
			case 5:
				try {
					do {
						int r;
						String p = "";
						for (int k = 0; k < 3; k++) {
							r = (int) ((Math.random() * 25.0d) + 65.0d);
							p += ((char) r);
						}
						for (int k = 0; k < 3; k++) {
							r = (int) (Math.random() * 9.0d);
							p += r;
						}
						platetmp = p;
						query = "SELECT * FROM users " + "where plate = '"
								+ platetmp + "';";
						rs = statement.executeQuery(query);
					} while (rs.isBeforeFirst());
					query = "SELECT MAX(userid) FROM users";
					rs = statement.executeQuery(query);
					rs.next();
					max = rs.getInt(1);
					max++;
					query = "INSERT INTO users VALUES (" + max + " " + ", "
							+ max + " , 2 , NULL, '" + platetmp + "' , 1000);";
					statement.execute(query);
				} catch (SQLException e) {
					System.out.println("Hubo un error "
							+ "irrecuperable en la simulacion");
					System.exit(1);
				}
				veh = new Vehicle(max, 0, platetmp, 3, d.initDijkstra(tolls,
						intersections, init, dest), tolls, intersections, connection);
				register.add(veh);
				break;
			}
			t = new Thread(veh);
			synchronized (vSim) {
				vSim.add(t);
			}
			t.start();
			System.out.println("Generando un vehiculo en: " + init
					+ " con destino: " + dest);
			System.out
					.println("El id del thread del vehiculo es: " + t.getId());
			try {
				Thread.sleep(dt);
			} catch (InterruptedException e) {
				System.out.println("Hubo un error "
						+ "irrecuperable en la simulacion");
				System.exit(1);
			}
		}
	}

}
