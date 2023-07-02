package emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import network.util.DBManager;

//Dept테이블에 대한 CURD 객체 
public class DeptDAO {
	DBManager dbManager = DBManager.getInstance();
	
	public DeptDAO() {
	System.out.println("DeptDAO에서의 conn"+dbManager.getConnection());
	}
	
	//삭제 
	public int delete(int deptno) {
		int result=0;
		Connection con = null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		String sql="delete dept where deptno=?"; //mysql은 from을 넣자 
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,deptno);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		
		return result;
	}
}
