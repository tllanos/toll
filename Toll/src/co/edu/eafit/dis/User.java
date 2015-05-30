package co.edu.eafit.dis;

public class User {
	
	private String name;
	private String lastName;
	private int idUser;
	private double balance;
	private Vehicle[] vehicles;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getIdUser() {
		return idUser;
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public Vehicle[] getVehicles() {
		return vehicles;
	}
	
	public void setVehicles(Vehicle[] vehicles) {
		this.vehicles = vehicles;
	}
}
