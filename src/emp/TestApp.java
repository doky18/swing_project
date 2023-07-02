package emp;

import java.sql.Connection;
import java.sql.SQLException;

import network.util.DBManager;

public class TestApp {
	public static void main(String[] args) {
		// 두개의 dao가 보유한 커넥션이 같은지 알아보기
		DeptDAO deptDAO = new DeptDAO();
		EmpDAO2 empDAO2 = new EmpDAO2();

		// JDBC는 개발자가 명시하지 않아도, DML에 대해서 자동 commit 처리되어 있다
		// 현재 사용중인 Connection에서 트랜잭션을 처리...
		DBManager dbManager = DBManager.getInstance();
		Connection con = dbManager.getConnection();

		try {
			// 자동 commit 막기
			con.setAutoCommit(false);

			// 부서지우기
			deptDAO.delete(20);// 10번 부서 삭제

			// 사원지우기
			empDAO2.delete(20);// 부서가 10번인 사원들 삭제
			
			con.commit();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("바깥쪽 도달");
			try {
				con.rollback();
			} catch (SQLException e1) {
			}
		}

	}
}
