package co.edu.eafit.dis.tollbooths;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Toll;

public class TollPhoto extends TollBooth{
	
	public TollPhoto(Toll location){
		super(location);
		type = 1;
	}
	
	public void run(){
		Vehicle tmp;
		while(true){			
			while(!q.isEmpty()){
				tmp = q.poll();
				tmp.setVisited(true);
				System.out.println("TRANSACCION");
				System.out.println("Transacción completada");
			}
		}
	}
}
