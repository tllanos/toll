package co.edu.eafit.dis.tollbooths;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Toll;

public class TollPhoto extends TollBooth {
	
	public TollPhoto(Toll location){
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
					tmp.setVisited(true);
					System.out.println("TRANSACCION");
					try {
						query = "SELECT funds FROM users where userid = "+tmp.getUserid()+";";
						rs = st.executeQuery(query);
						rs.next();
						if(rs.getInt(1) < 5){
							System.out.println("Esto sucedio");
							Thread.dumpStack();
							System.exit(1);
						}
						pstate = connection.prepareStatement("INSERT INTO tollphoto VALUES ( 5, ?, ?, ?, ?)");
						pstate.setTimestamp(1, new Timestamp(new Date().getTime()));
						pstate.setInt(2, location.getId());
						pstate.setString(3, tmp.getPlate());
						pstate.setInt(4, type);
						pstate.execute();
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (SQLException e){
						e.printStackTrace();
					}
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
