package test;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) {
		String oracle_driver = "oracle.jdbc.driver.OracleDriver";
		String oracle_url = "jdbc:oracle:thin:@localhost:1521:XE";
		String oracle_user = "javase";
		String oracle_pass = "1234";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("성공 ");
			DriverManager.getConnection(oracle_url, oracle_user, oracle_pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
