package network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

public class DiaryDAO {
	DBManager dbManager = DBManager.getInstance();

	// CRUD 중 insert를 정의한다
	public int insert(Diary diary) { // diary라는 봉투를 이렇게 사용할 것
		System.out.println("호출 후 d=" + diary);
		Connection con = dbManager.getConnection();
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "insert into jdiary(jdiary_idx, yy, mm, dd, content, icon)";
		sql += " values(seq_diary.nextval, ?,?,?,?,?)"; // 바인드 변수는 순번이 1부터 시작함

		try {
			pstmt = con.prepareStatement(sql); // con은 싱글턴 패턴의 DBManager가 가지고 있음

			// getter
			pstmt.setInt(1, diary.getYy());
			pstmt.setInt(2, diary.getMm());
			pstmt.setInt(3, diary.getDd());
			pstmt.setString(4, diary.getContent());
			pstmt.setString(5, diary.getIcon());

			result = pstmt.executeUpdate(); // 쿼리 실행

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

	/* 해당 월에 등록된 다이어리 가져오기 */
	public List selectAll(int yy, int mm) { // void 대신 resultSet으로 뱓으면 무슨 문제가 생길까? 닫는건 db매니저에서 하기로 했는데.. 여기서 닫아도 상관없음 대신
											// 코드가 섞여버림.
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Diary> list = new ArrayList<Diary>();

		String sql = "select * from diary where yy=? and mm=?"; // 년과 월이 같은 데이터를 가지고 오십시오
		sql += " order by diary_idx asc";

		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, yy);
			pstmt.setInt(2, mm);
			rs = pstmt.executeQuery(); // 쿼리수행 및 테이블 반환

			while (rs.next()) {// 한 칸 이동

				/* 비어있는 DTO 하나 생성하기. 왜? 레코드 한 건을 담기 위해서 */
				Diary diary = new Diary();
				
				diary.setDiary_idx(rs.getInt("diary_idx"));
				diary.setYy(rs.getInt("yy"));
				diary.setMm(rs.getInt("mm"));
				diary.setDd(rs.getInt("dd"));
				diary.setContent(rs.getString("content"));
				diary.setIcon(rs.getString("icon"));

				/* 채워진 dto를 ArrayList에 추가하자 */
				list.add(diary);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);  // rs를 여기서 닫아 죽여버리고 dto(ArrayList)로 대체해주자. 왜 ArrayList? 레코드에 등록 순서가 있기 때문에
		}
		return list;
	}
}

