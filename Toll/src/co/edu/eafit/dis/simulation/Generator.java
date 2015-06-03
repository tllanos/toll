package co.edu.eafit.dis.simulation;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;

import co.edu.eafit.dis.dijkstra.Dijkstra;
import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Intersection;
import co.edu.eafit.dis.graph.Toll;

public class Generator implements Runnable{
	
	private ArrayList<Thread> vSim;
	private int li, range, init, dest;
	private Vehicle veh;
	private Dijkstra d;
	private Thread t;
	private int id = 0;
	private ArrayList<Intersection> intersections;
	private ArrayList<Toll> tolls;
	private Register register;
	private Connection connection;
	
	public Generator(ArrayList<Thread> vSim, 
			ArrayList<Intersection> intersections, 
			ArrayList<Toll> tolls, 
			Register register, 
			Connection connection){
		d = new Dijkstra();
		this.connection = connection;
		this.vSim = vSim;
		this.register = register;
		int count = 0, last = 0;
		for (Intersection i: intersections){
			if(i.source == true){
				last = i.getId();
				count++;
			}
		}
		range = count;
		li = last - range + 1;
		this.intersections = intersections;
		this.tolls = tolls;
	}
	
	public synchronized void run(){
		while(true){
			Calendar rightNow = Calendar.getInstance();
			int hour = rightNow.get(Calendar.HOUR_OF_DAY);
			double f = (hour / 24.0d);
			int quantity = Math.abs((int)(((Math.sin((2 * Math.PI) * f)) * 30.0d) + 0.5d));
			long dt = (long)((3600.0d/quantity) * 1000.0d);
			init = (int)((Math.random() * range) + li + 0.5d);
			dest = (int)((Math.random() * range) + li + 0.5d);
			while(dest == init){
				dest = (int)((Math.random() * range) + li + 0.5d);
			}
			System.out.println("Estoy generando un carro");
			System.out.println("Son las: " + hour + "q: " + quantity + "dt: " + dt);
			//
			int nran = (int)(Math.random() * 5.0d + 0.5d);
			switch(nran){
				case 1:
					veh = new Vehicle(0, 0, null, 1, d.initDijkstra(tolls, intersections, init, dest), tolls, intersections);
					register.add(veh);
					break;
				case 2:
					
					break;
				case 3:
					
					break;
				case 4:
					
					break;
				case 5:
					
					break;
			}
			//
			t = new Thread(veh);
			vSim.add(t);
			t.start();
			try {
				Thread.sleep(dt);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}