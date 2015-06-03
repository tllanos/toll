package co.edu.eafit.dis.tollbooths;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
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
