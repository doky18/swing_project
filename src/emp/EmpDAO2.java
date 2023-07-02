package emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import network.util.DBManager;

public class EmpDAO2 {
	DBManager dbManager = DBManager.getInstance();

	public EmpDAO2() {
		System.out.println("Emp DAO에서의 conn" + dbManager.getConnection());

	}

	// 삭제
	//이 메서드를 호출한 자에게 에러를 전달한다. 이때 호출자는 이 예외에 대한 처리 의무를 부담하게 된다 
	public int delete(int deptno) throws Exception{
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		con = dbManager.getConnection();

		
		//이 메서드 내에서 에러가 발생하였더라도, 예외를 처리해버리면 외부의 이 메서드 호출자가 어떤 에러가 발생했는지 알 수 없다
		//따라서 외부의 호출자에게 에러를 전달해주는 방법을 사용하는데, 이때 사용하는 예외전달 기법이 thorows임 
		String sql = "delete emp where deptno=?"; // mysql은 from을 넣자
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, deptno);
		pstmt.executeUpdate();//자동 확정중..
		dbManager.release(pstmt);
		
		//문법의 에러가 아닌, 원하는 쿼리가 수행되지 않았을 경우 일부러 개발자가 에러를 일으키면 된다 
		if(result<1) {//문법상의 에러가 아니라, 프로그램의 흐름상 dml이 수행되지 않았을 경우..
		throw new Exception(); //일부러 에러를 발생 
		}


		return result;
	}
}
