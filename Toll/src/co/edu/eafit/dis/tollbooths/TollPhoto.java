package co.edu.eafit.dis.tollbooths;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Toll;

public class TollPhoto extends TollBooth {
	
	public TollPhoto(Toll location){
		super(location);
		type = 2;
	}
	
	@Override
	public void run(){
		Vehicle tmp;
		while(true){	
			synchronized(q){
				while(!q.isEmpty()){
					tmp = q.poll();
					tmp.setVisited(true);
					System.out.println("TRANSACCION");
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
