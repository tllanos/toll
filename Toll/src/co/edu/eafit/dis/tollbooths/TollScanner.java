package co.edu.eafit.dis.tollbooths;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import co.edu.eafit.dis.entity.Vehicle;
import co.edu.eafit.dis.graph.Toll;

public class TollScanner extends TollBooth{
	
	public TollScanner(Toll location){
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
					System.out.println("TRANSACCION");
					try {
						query = "SELECT funds FROM users where userid = "+tmp.getUserid()+";";
						rs = st.executeQuery(query);
						rs.next();
						int fund = rs.getInt(1);
						if(fund < 5){
							System.out.println("Esto sucedio");
							Thread.dumpStack();
							System.exit(1);
						}
						
						pstate = connection.prepareStatement("INSERT INTO tollsensor VALUES ( 5, ?, ?, ?, ?)");
						pstate.setTimestamp(1, new Timestamp(Calendar.getInstance().getTimeInMillis()));
						pstate.setInt(2, location.getId());
						pstate.setInt(3, tmp.getSensorId());
						pstate.setInt(4, type);
						pstate.execute();
						
						query = "UPDATE  users " +
								"SET funds = " + (fund-5.0d) + 
								"WHERE userid = " + tmp.getUserid()+";";
						st.execute(query);
						
						Thread.sleep(15000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (SQLException e){
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
