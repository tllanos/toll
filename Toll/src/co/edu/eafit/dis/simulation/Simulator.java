package co.edu.eafit.dis.simulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import co.edu.eafit.dis.graph.Intersection;
import co.edu.eafit.dis.graph.Toll;

/**
 * Clase encargada de Iniciar la simulación por medio de la integración
 * de los recursos del proyecto.
 * @author tllanos, ccorre20, icardena
 */
public class Simulator {
    private Statement st;
    private ResultSet rs;
    private Connection connection;
    private CopyOnWriteArrayList<Thread> vSim;
    private ArrayList<Toll> tolls;
    private ArrayList<Intersection> intersections;
    private String query = null;
    private PreparedStatement pstate = null;
    private Register register;

    /**
     * Inicializa la conección a la base de datos y asigna los valores
     * requeridos para dar inicio a la simulación.
     * <p>
     * Para efectos semánticos de la simulación, se hace uso de hilos
     * o "Threads" (Los cuales representan los diferentes procesos que se
     * ejecutan simultaneamente para crear un entorno controlado que
     * simula sucesos de la vida real) lo cuales son almacenados en un objeto
     * de tipo "CopyOnWriteArrayList<Thread>".
     * <p>
     * Nota: Es más seguro utilizar objetos de tipo "CopyOnWriteArrayList" para
     * almacenar hilos o "Threads".
     *
     */

    public Simulator() {

        tolls = new ArrayList<Toll>();
        intersections = new ArrayList<Intersection>();
        vSim = new CopyOnWriteArrayList<Thread>();
        register = new Register();
        System.out.println("Preparando conexion a la base de datos");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:"
                                                     + "//toll.cqkduygrcpmt.us-east-1.rds.amazonaws.com:"
                                                     + "3306/tollcontrol?"
                                                     + "user=root&password=rootroot");
        }catch(Exception e){
            System.out.println("Error connecting to the database");
            System.exit(1);
        }

        try{
            st = connection.createStatement();
        }catch(SQLException e) {
            System.out.println("Hubo un error irrecuperable en la simulacion");
            System.exit(1);
        }
    }

    /**
     * Inicia la simulación al agrupar todos los métodos de la clase y
     * hacerlos trabajar conjuntamente. Adicionalmente, Mantiene un riguroso
     * control sobre todos los hilos o "Threads" y destruye los mismos cuando
     * dejan de ser requeridos.
     */

    public void simulate() {
        //Start
        generateGraph();
        Worker worker = new Worker(connection, pstate, tolls, rs);
        Thread threadw = new Thread(worker);
        threadw.start();
        Generator generator =  new Generator(vSim, intersections,
                                             tolls, register);
        Thread threadg = new Thread(generator);
        threadg.start();
        while(true){
            synchronized(vSim){
                for(Thread t: vSim){
                    synchronized(t){
                        //Evalua si el hilo es o no requerido.
                        if(!t.isAlive()){
                            try {
                                //Destruye el objeto de tipo "Thread".
                                t.join();
                                vSim.remove(t);
                                System.out.println("El thread: " + t.getId()
                                                   + " ha llegado a su destino");
                                t = null;
                                if(vSim.size() == 0){
                                    break;
                                }
                            } catch (InterruptedException e) {
                                System.out.println("Hubo un error "
                                                   + "irrecuperable en la simulacion");
                                System.exit(1);
                            }
                        }
                    }
                }
            }
            //Se requiere especificar una pausa entre los cilos.
            //Si dicha pausa no es especificada, el programa no correra
            //de forma apropiada.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Hubo un error "
                                   + "irrecuperable en la simulacion");
                System.exit(1);
            }
        }
    }

    /**
     * Clase encargada de asignar el flujo a cada caseta de cada peaje.
     * @author tllanos, ccorre20, icardena
     */

    private static class Worker implements Runnable{

        private ArrayList<Toll> tolls;
        private PreparedStatement pstate = null;
        private Connection connection;
        private ResultSet rs;

        public void run(){
            while(true){
                assignFlow();
                System.out.println("Se han recalculado los flujos!");
                try{
                    Thread.sleep(60000);
                }catch(Exception e){
                    System.out.println("Hubo un error "
                                       + "irrecuperable en la simulacion");
                    System.exit(1);
                }
            }
        }

        /**
         * Asigna los valores requeridos para iniciar el/los procesos
         * en segundo plano (Worker).
         *
         * @param connection
         * @param pstate
         * @param tolls
         * @param rs
         */

        public Worker(Connection connection, PreparedStatement pstate,
                      ArrayList<Toll> tolls, ResultSet rs){
            this.connection = connection;
            this.pstate = pstate;
            this.rs = rs;
            this.tolls = tolls;
        }

        /**
         * Recopila la información de todas las casetas ("tollbooths"),
         * referente al campo "date", de la base de datos y calcula el
         * flujo de cada una basado en la cantidad de vehiculos que las
         * cruzan por minuto.
         */

        private synchronized void assignFlow(){
            try{
                int id;
                int f;
                for(Toll toll: tolls){
                    f = 0;
                    id = toll.getId();

                    pstate = connection.prepareStatement(
                                        "SELECT COUNT(tollid) "
                                         + "FROM tollcash "
                                         + "WHERE date > date_sub(NOW(), "
                                         + "INTERVAL 1 MINUTE) "
                                         + "AND tollid = ?;");
                    pstate.setInt(1, id);

                    rs = pstate.executeQuery();
                    if(rs.next()){
                        f += rs.getInt(1);
                    }

                    pstate = connection.prepareStatement(
                                         "SELECT COUNT(tollid) "
                                         + "FROM tollsensor "
                                         + "WHERE date > date_sub(NOW(), "
                                         + "INTERVAL 1 MINUTE) "
                                         + "AND tollid = ?;");
                    pstate.setInt(1, id);

                    rs = pstate.executeQuery();
                    if(rs.next()){
                        f += rs.getInt(1);
                    }

                    pstate = connection.prepareStatement(
                                         "SELECT COUNT(tollid) "
                                         + "FROM tollphoto "
                                         + "WHERE date > date_sub(NOW(), "
                                         + "INTERVAL 1 MINUTE) "
                                         + "AND tollid = ?;");

                    pstate.setInt(1, id);

                    rs = pstate.executeQuery();
                    if(rs.next()){
                        f += rs.getInt(1);
                    }
                    toll.setFlow(f);
                }
            }catch(SQLException e){
                System.out.println("Hubo un error "
                                   + "irrecuperable en la simulacion");
                System.exit(1);
            }
        }
    }

    /**
     * Recopila la información de todas las casetas ("tollbooths"),
     * referente al campo "date", de la base de datos y calcula el
     * flujo de cada una basado en la cantidad de vehiculos que las
     * cruzan por minuto.
     */

    private void generateGraph(){
        try{
            // *** Inicio: Proceso de almacenaje ***
            // ** Inicio: Almacenamiento de "toll" **
            query = "SELECT distinct tollid FROM toll";
            rs = st.executeQuery(query);
            while(rs.next()){
                tolls.add(new Toll(rs.getInt(1),false));
            }
            rs.close();
            //  ** Fin: Almacenamiento de "toll" **
            //  ** Inicio: Almacenamiento de "intersection" **
            query = "SELECT distinct intid "
                + "FROM intersection";
            rs = st.executeQuery(query);
            while(rs.next()){
                intersections.add(
                                  new Intersection((rs.getInt(1)+tolls.size()),
                                                   false));
            }
            rs.close();
            // ** Fin: Almacenamiento de "intersection" **
            // *** Fin: Proceso de almacenaje ***
            // *** Inicio: Proceso de enlace entre "toll" e "interction" ***
            for(Toll tollid: tolls){
                pstate = connection.prepareStatement("SELECT intersection "
                                                     + "FROM connection "
                                                     + "WHERE tollid = ?");
                pstate.setInt(1, tollid.getId());
                rs = pstate.executeQuery();
                while(rs.next()){
                    for(Intersection ints: intersections){
                        if(ints.getId() == (rs.getInt(1)+tolls.size())){
                            ints.connectTo(tollid);
                            ints.connectTo(tollid.getId());
                            tollid.connectTo(ints);
                            tollid.connectTo(ints.getId());
                        }
                    }
                }
            }
            rs.close();
            // *** Fin: Proceso de enlace entre "toll" e "interction" ***
            // *** Inicio: Definición de intersecciones fuente ***
            for(Intersection ints: intersections.subList(0, 8)){
                ints.setSource(true);
            }
            // *** Fin: Definición de intersecciones fuente ***
        }catch(SQLException e){
            System.out.println("Hubo un error "
                               + "irrecuperable en la simulacion");
            System.exit(1);
        }
    }
}
