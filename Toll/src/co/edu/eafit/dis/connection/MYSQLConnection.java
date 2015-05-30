package co.edu.eafit.dis.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MYSQLConnection {

	private Connection con = null;
	private Statement state = null;
	private PreparedStatement pstate = null;
	private ResultSet resultSet = null;
	
	public void createCon(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/feedback?"
		              + "user=sqluser&password=sqluserpw");
		}catch(Exception e){
			System.out.println("Error connecting to the database");
			
		}
	}
	
	public static void main(String[] args){
		MYSQLConnection con =  new MYSQLConnection();
		con.createCon();
	}
	
}
