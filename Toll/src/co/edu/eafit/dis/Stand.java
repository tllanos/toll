package co.edu.eafit.dis;

public abstract class Stand {
	private int idStand;
	private String employee;

	public int getIdStand() {
		return idStand;
	}
	
	public void setIdStand(int idStand) {
		this.idStand = idStand;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}
}