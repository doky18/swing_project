package table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

public class EmpDAO {
	//C(Insert)R(select)U(Update)D(Delete)
	DBManager dbManager=DBManager.getInstance();
	
	//모든 레코드 가져오기 
	public List selectAll() {
		List list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		con=dbManager.getConnection();
		String sql="select * from emp2";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery(); //rs를 list에 담기때문에 rs 구구절절 안함 
			
			while(rs.next()) {
				Emp2 emp = new Emp2();
				
		        emp.setEmpno(rs.getInt("empno"));
	            emp.setEname(rs.getString("ename"));
	            emp.setJob(rs.getString("job"));
	            emp.setMgr(rs.getInt("mgr"));
	            emp.setHiredate(rs.getString("hiredate"));
	            emp.setSal(rs.getInt("sal"));
	            emp.setComm(rs.getInt("comm"));
	            emp.setDeptno(rs.getInt("deptno"));
	            
				list.add(emp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		
		return list;
	}
	//1건 수정하기 
	public int update(Emp2 emp) {
		int result =0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		con=dbManager.getConnection();
		String sql = "update emp set ename=? , job=?, hiredate =?, sal=? , comm=? where empno=?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, emp.getEname());
			pstmt.setString(2,  emp.getJob());
			pstmt.setString(3, emp.getHiredate());
			pstmt.setInt(4, emp.getSal());
			pstmt.setInt(5, emp.getComm());
			pstmt.setInt(6, emp.getEmpno());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		return result;
	}
}
