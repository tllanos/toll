package co.edu.eafit.dis.tollbooths;

import java.util.LinkedList;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Toll;

public class TollCash extends TollBooth {
	public TollCash(Toll location){
		super(location);
		type = 1;
	}
	
	@Override
	public void run(){
		Vehicle tmp;
		while(true){
			synchronized(q){
				while(!q.isEmpty()){
					tmp = q.poll();
					System.out.println("TRANSACCION");
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
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
