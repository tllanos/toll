package co.edu.eafit.dis.tollbooths;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Toll;

public class TollScanner extends TollBooth{
	
	public TollScanner(Toll location){
		super(location);
		type = 3;
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
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					tmp.setVisited(true);
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
