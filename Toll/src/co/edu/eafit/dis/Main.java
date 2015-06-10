package co.edu.eafit.dis;

import co.edu.eafit.dis.simulation.Simulator;

public class Main {

    public static boolean running = true;

    public static void main(String ... args) {
        Runtime.getRuntime().addShutdownHook(new Thread(){
        	public void run(){
        		running = false;
        		System.out.println("La simulación ha finalizado.");
        	}
        });
        System.out.println("Bienvenido al simulador de flujo v1.0");
        System.out.println("La simulacion comenzara ya, para frenar");
        System.out.println("su ejecucion haga el comando Ctrl-C");
        Simulator s = new Simulator();
        s.simulate();
    }
}
