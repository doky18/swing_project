package network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;


//C(Insert)R(select)U(Update)D(Delete)
public class JBoardDAO {
	DBManager dbManager = DBManager.getInstance();

	// 레코드 한 건 넣기
	public int insert(JournalBoard journalBoard) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnection();

		String sql = "insert into journalBoard(jboard_idx, title, content)";
		sql += " values(seq_journalBoard.nextval, ?, ?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, journalBoard.getTitle());
			pstmt.setString(2, journalBoard.getContent());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

	// 레코드 한 건 가져오기
	public JournalBoard select(int jboard_idx) {
		JournalBoard jBoard = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = dbManager.getConnection();
		String sql = "select * from journalBoard where jboard_idx=?"; // * 사이에 띄어쓰기 잘 하기 --언제나 한 건

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, jboard_idx);// pk 넘겨 받음
			rs = pstmt.executeQuery();

			if (rs.next()) {// 레코드가 있다면 dto 생성
				jBoard = new JournalBoard();
				jBoard.setJboard_idx(rs.getInt("jboard_idx"));
				jBoard.setTitle(rs.getString("title"));
				jBoard.setContent(rs.getString("content"));
				jBoard.setRegdate(rs.getString("regdate"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return jBoard;
	}

	// 모든 레코드 가져오기
	public List selectAll() {
		List list = new ArrayList();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = dbManager.getConnection();

		String sql = "select * from journalBoard order by jboard_idx desc";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JournalBoard jBoard = new JournalBoard();
				
				jBoard.setJboard_idx(rs.getInt("jboard_idx"));
				jBoard.setTitle(rs.getString("title"));
				jBoard.setContent(rs.getString("content"));
				jBoard.setRegdate(rs.getString("regdate"));

				list.add(jBoard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 한 건 수정하기
	public int update(JournalBoard journalBoard) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0; // 반환 값으로 사용해야해서 try문 밖으로 빼줌

		con = dbManager.getConnection();
		String sql = "update journalBoard set title=?, content=? where jboard_idx=?";

		try {
			pstmt = con.prepareStatement(sql);
			// 바인드 변수 주기,, 4개니까 낱개로 얻어오지 말고 DTO로 전달받자
			pstmt.setString(1, journalBoard.getTitle());
			pstmt.setString(2, journalBoard.getContent());
			pstmt.setInt(3, journalBoard.getJboard_idx());

			// DML문이므로 executeUpdate() 메서드 호출. 이때 이 메서드 호출 후 반환되는 값은
			// 이 쿼리 수행시 영향을 받은 레코드 수가 반환되므로, 반환값이 0인 경우, 수정된 레코드가 없다는 뜻.

			result = pstmt.executeUpdate(); // 쿼리 수행

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	
	// 한 건 삭제
		public int delete(int jboard_idx) {
			Connection con = null;
			PreparedStatement pstmt = null;
			int result = 0;

			con = dbManager.getConnection();
			String sql = "delete from journalBoard where jboard_idx=?";

			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, jboard_idx); // 바인드 변수값 지정
				result = pstmt.executeUpdate(); // DML수행 후 반영된 레코드 수 반환

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbManager.release(pstmt);
			}
			return result;
		}
}
