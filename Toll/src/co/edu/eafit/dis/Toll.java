package co.edu.eafit.dis;

public class Toll {
	
	private String nameToll;
	private int idToll;
	private StandCash[] standCash;
	private StandFast[] standFast;
	private StandSensor[] standSensor;
	
	public String getNameToll() {
		return nameToll;
	}
	
	public void setNameToll(String nameToll) {
		this.nameToll = nameToll;
	}
	
	public int getIdToll() {
		return idToll;
	}
	
	public void setIdToll(int idToll) {
		this.idToll = idToll;
	}
	
	public StandCash[] getStandCash() {
		return standCash;
	}
	
	public void setStandCash(StandCash[] standCash) {
		this.standCash = standCash;
	}
	
	public StandFast[] getStandFast() {
		return standFast;
	}
	
	public void setStandFast(StandFast[] standFast) {
		this.standFast = standFast;
	}
	
	public StandSensor[] getStandSensor() {
		return standSensor;
	}
	
	public void setStandSensor(StandSensor[] standSensor) {
		this.standSensor = standSensor;
	}
}