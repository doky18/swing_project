package network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import network.util.DBManager;

//Oracle의  테이블에 대한 CRUD를 수행하는 전담 객체 LogMemberDAO

public class OracleLogMember implements LogMemeberDAO {
	DBManager dbManager = DBManager.getInstance();

	public int insert(LogMember logMember) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		con=dbManager.getConnection();

		String sql = "insert into logmember(member_idx, id, pass, email)";
		sql += " values(seq_logmember.nextval, ?, ?, ?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, logMember.getId());
			pstmt.setString(2, logMember.getPass());
			pstmt.setString(3, logMember.getEmail());

			result = pstmt.executeUpdate(); // 쿼리 실행
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}

		return result;
	}

	public int update(LogMember logMember) {
		return 0;
	}

	public int delete(int member_idx) {
		return 0;
	}

	public List selectAll() {
		return null;
	}

	public LogMember select(int member_idx) {
		return null;
	}

	public LogMember select(LogMember logMember) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LogMember obj = null; // 로그인 성공시 회원정보를 담아둘 DTO
		
		con = dbManager.getConnection();

		String sql = "select * from logmember where id=? and pass=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, logMember.getId());
			pstmt.setString(2, logMember.getPass());

			rs = pstmt.executeQuery();
			if (rs.next()) { // 레코드가 있다면 조건에 일치하는 회원이 존재한다는 것이고 회원이 존재한다는 것은 로그인 성공이라는 의미
//	            로그인이 성공하면 로그인한 회원의 정보를 담아서 반환
				obj = new LogMember(); // empty 상태의 DTO 생성
				obj.setMember_idx(rs.getInt("member_idx"));
				obj.setId(rs.getString("id"));
				obj.setPass(rs.getString("pass"));
				obj.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return obj;
	}
}
