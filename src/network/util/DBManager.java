package network.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Oracle과 MySQL을 둘 다 처리하는 DB매니저 정의
// 둘 중 하나만 가지고 있는 사람들 있어서 둘 다 보여주는 것

public class DBManager {
	String oracle_driver = "oracle.jdbc.driver.OracleDriver";
	String oracle_url = "jdbc:oracle:thin:@localhost:1521:XE";
	String oracle_user = "javase";
	String oracle_pass = "1234";

	String mysql_driver = "com.mysql.jdbc.Driver";
	String mysql_url = "jdbc:mysql://localhost:3306/javase?characterEncoding=utf-8";
	String mysql_user = "javase";
	String mysql_pass = "1234";

	private static DBManager instance; // null
	private Connection connection;

	private DBManager() {
//		connectMysql();
		connectOracle();
	}

	public Connection getConnection() {
		return connection;
	}

//	오라클 접속
	public void connectOracle() {
		try {
//			드라이버 로드
			Class.forName(oracle_driver);
			connection = DriverManager.getConnection(oracle_url, oracle_user, oracle_pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
//	MySQL 접속
	public void connectMysql() {
		try {
			Class.forName(mysql_driver);
			connection = DriverManager.getConnection(mysql_url, mysql_user, mysql_driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	public void release(Connection connection) {
		if(connection !=null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void release(PreparedStatement pstmt) {
		if(pstmt !=null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if(rs !=null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(pstmt !=null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}