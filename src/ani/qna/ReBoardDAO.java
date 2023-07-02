package ani.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

//reboard 테이블에 대한 CRUD 담당 객체 
public class ReBoardDAO {
	DBManager dbManager = DBManager.getInstance();

	public int insert(ReBoard reboard) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;

		con = dbManager.getConnection();

		// oracle : insert 시 외부에 있는 시퀀스를 이용하므로, insert하는 시점에 pk 값 및 team이 결정 될 수 있다.
		String sql = "insert into reboard(reboard_idx, title, writer, content, team)";
		sql += " values(seq_reboard.nextval, ? , ? , ? , seq_reboard.nextval)";
		// 인서트 문 하나에 nextval도 하나. nextval을 여러번 날려도 같은 숫자

		// mysql : insert가 된 이후에나 pk 값이 존재하므로, 먼저 insert부터 완료한 후, 추후 가장 최근에 들어간 pk를 조사해야
		// 한다
		// String sql="insert into (title, writer, content)";
		// sql+="values(?,?,?)";
		try {
			pstmt1 = con.prepareStatement(sql);
			pstmt1.setString(1, reboard.getTitle());
			pstmt1.setString(2, reboard.getWriter());
			pstmt1.setString(3, reboard.getContent());
			result = pstmt1.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt1);
			dbManager.release(pstmt2);
			dbManager.release(pstmt3);
		}
		return result;

	}

	// 답변게시판의 모든 레코드 가져오기
	// 동물 종류별로 뭉쳐다니기(order by category desc), 종류내에서의 정렬 (rank asc)
	public List selectAll() {
		List list = new ArrayList();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = dbManager.getConnection();

		String sql = "select * from reboard order by team desc, step asc";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReBoard reBoard = new ReBoard();
				reBoard.setReboard_idx(rs.getInt("reboard_idx"));
				reBoard.setTitle(rs.getString("title"));
				reBoard.setWriter(rs.getString("writer"));
				reBoard.setContent(rs.getString("content"));
				reBoard.setRegdate(rs.getString("regdate"));
				reBoard.setHit(rs.getInt("hit"));
				reBoard.setTeam(rs.getInt("team"));
				reBoard.setStep(rs.getInt("step"));
				reBoard.setDepth(rs.getInt("depth"));

				list.add(reBoard);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 레코드 한건 가져오기
	// 이 메서드를 호출하는 자는, 반환값이 null인 경우 일치하는 데이터가 없음을 판단할 수 있다
	// 반환값이 null이 아닌 경우, 조건과 일치하는 레코드가 있다고 판단하면 됨.
	public ReBoard select(int reboard_idx) {
		ReBoard reboard = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = dbManager.getConnection();
		String sql = "select * from reboard where reboard_idx=?"; // * 사이에 띄어쓰기 잘 하기 --언제나 한 건

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reboard_idx);// pk 넘겨 받음
			rs = pstmt.executeQuery();

			if (rs.next()) {//레코드가 있다면 dto 생성 
				reboard = new ReBoard();
				reboard.setReboard_idx(rs.getInt("reboard_idx"));
				reboard.setTitle(rs.getString("title"));
				reboard.setWriter(rs.getString("writer"));
				reboard.setContent(rs.getString("content"));
				reboard.setRegdate(rs.getString("regdate"));
				reboard.setHit(rs.getInt("hit"));
				reboard.setTeam(rs.getInt("team"));
				reboard.setStep(rs.getInt("step"));
				reboard.setDepth(rs.getInt("depth"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}

		return reboard;
	}
	//답변이 들어갈 자리를 조사 
	public int selectMaxStep(ReBoard reboard) {
		int step = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		con=dbManager.getConnection();
		StringBuilder sb = new StringBuilder();
		sb.append("select max(step) as step from reboard");
		sb.append(" where team=? and depth=? and step > ?");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setInt(1, reboard.getTeam()); 	//내 본 글 team
			pstmt.setInt(2, reboard.getDepth());	//내 본 글의 depth보다 1 큰 애들
			pstmt.setInt(3, reboard.getStep()); 	//내 본 글의 step 
			rs=pstmt.executeQuery();
			
			if(rs.next()) {//선행된 답변이 있다면 ..
				step=rs.getInt("step");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}		
		return step;
	}
	
	//답변이 들어갈 자리 확보 (답변이 들어갈 자리에 이미 선행된 step이 있다면 한 칸 씩 밀리게 ..)
	public int updateStep(ReBoard reboard) {
		int result=0;
		Connection con = null;
		PreparedStatement pstmt =null;
		
		con=dbManager.getConnection();
		String sql="update reboard set step=step+1";
		sql+=" where team=? and step > ?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, reboard.getTeam());
			pstmt.setInt(2, reboard.getStep()); //selectMaxStep()반환받은 step 
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		
		return result;
	}
	
	//답변등록
	public int reply(ReBoard reboard) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		con=dbManager.getConnection();
		
		//oracle
		String sql="insert into reboard(reboard_idx, title, writer, content , team, step, depth)";
		sql+=" values(seq_reboard.nextval, ?,?,?,?,?,?)";		//6개 물음표 
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, reboard.getTitle());
			pstmt.setString(2, reboard.getWriter());
			pstmt.setString(3, reboard.getContent());
			pstmt.setInt(4, reboard.getTeam());	//내 본 글의 team 
			pstmt.setInt(5, reboard.getStep()+1);	//
			pstmt.setInt(6, reboard.getDepth()+1);
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		return result;
	}
}
/*
 * mysql
 * 
 * public int insert2(ReBoard reBoard) {
 * 
 * int result = 0; Connection con = null; PreparedStatement pstmt = null;
 * ResultSet rs = null; ///방금들어간 프라이머리 키 조사하려구
 * 
 * 
 * con = dbManager.getConnection();
 * 
 * //insert가 된 이후에나 pk값이 존재함으로, insert 부터 완료한 후 추후 가장 최근에 들어간 pk를 조사해야 한다.
 * String sql = "insert into reboard(title, writer, content) values(?, ?,?)"; //
 * team..값은 idx가 생성되야 얺을 수 있어서 못넣음 , step, depth는 그래서 넣지 않음
 * 
 * try { pstmt = con.prepareStatement(sql);
 * 
 * pstmt.setString(1, reBoard.getTitle()); pstmt.setString(2,
 * reBoard.getWriter()); pstmt.setString(3, reBoard.getContent());
 * 
 * result = pstmt.executeUpdate(); //방금 insert 완료 dbManager.release(pstmt); //위의
 * pstmt 종료 if(result>0) { //+= 아님, 위의 커리문이 날라가버림. sql =
 * "select last_insert_id() ad reboard_idx from reboard"; pstmt =
 * con.prepareStatement(sql); //다시 pstmt 생성 rs = pstmt.executeQuery(); //select
 * 문 쿼리 실행
 * 
 * if(rs.next()) { //레코드가 존재한다면.. //team을 reboard_idx와 동일하게 만들어줄거야..
 * dbManager.release(pstmt); sql =
 * "update reboard set team=? where reboard_idx=? "; //? 두자리 모두, last_insert
 * idx가 들어오면 되는것. pstmt = con.prepareStatement(sql); pstmt.setInt(1,
 * rs.getInt("reboard_idx")); pstmt.setInt(2, rs.getInt("reboard_idx"));
 * 
 * result = pstmt.executeUpdate(); } }
 * 
 * } catch (SQLException e) { e.printStackTrace(); }finally {
 * dbManager.release(pstmt, rs); }
 * 
 * return result; }
 */
