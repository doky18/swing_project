package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

public class NewsDAO {

	DBManager dbManager = DBManager.getInstance();

	// 게시물 한 건 넣기
	public int insert(News news) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0; // DML 성공여부를 반환해줄 변수

		// 여기에서 db 접속을 얻어오게 되면, finally에서도 다시 끊어줘야 하므로,
		// db접속 시점을 프레임 띄울때 db 닫는 시점을 프레임 닫을때로 변경
		// 싱글턴으로 선언했기 때문에 우리가 dao의 메서드 여기저기서 호출해도 상관 없다.
		// connection은 어차피 한개, 프레임 닫을때만 db가 닫히도록 처리하자

		con = dbManager.getConnection();
		// oracle
		String sql = "insert into news(news_idx, title, writer, detail)";
		sql += " values(seq_news.nextval, ?,?,?)";
		// mysql
		// String sql="insert into news(title, writer, detail) values(?,?,?)";

		try {
			pstmt = con.prepareStatement(sql);
			// 바인드 변수 지정
			pstmt.setString(1, news.getTitle());
			pstmt.setString(2, news.getWriter());
			pstmt.setString(3, news.getDetail());

			// 바인드 변수지정 끝났으면, 쿼리 수행
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result; // dml 수행 결과를 반환해줌
	}

	// 모든 게시물 가져오기
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<News> list = new ArrayList<News>();

		con = dbManager.getConnection();
		// String sql = "select * from news order by news_idx desc";

		// 댓글 테이블과 조인하여 가져오기!!

		StringBuilder sb = new StringBuilder();
		sb.append("select n.news_idx as news_idx , title, writer, regdate, hit, count(c.comments_idx) as cnt");
		//제발 from절 포함하여 모든 append안에 한칸 띄우는거 잊지 마세요
		sb.append(" from  news  n left outer join  comments c");
		sb.append(" on n.news_idx = c.news_idx");
		//우리, 등록일 ,조회수도 게시물에 포함시켜야 하므로 group by에 추가해야 합니다
		sb.append(" group by n.news_idx, title, writer,  regdate, hit");

		try {
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) { // rs는 곧 닫힐 예정이므로 rs를 대체할 객체를 이용
				News news = new News(); // empty dto;
				news.setNews_idx(rs.getInt("news_idx"));
				if (rs.getInt("cnt") > 0) {
					news.setTitle(rs.getString("title") + "[" + rs.getInt("cnt") + "]");
				} else {
					news.setTitle(rs.getString("title"));
				}
				// news.setCnt(rs.getInt("cnt"));
				news.setWriter(rs.getString("writer"));
				// news.setDetail(rs.getString("detail")); 목록에서 가져올 필요 없
				news.setRegdate(rs.getString("regdate"));
				news.setHit(rs.getInt("hit"));

				list.add(news); // 리스트에 추가하기
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;// ************** 리턴값 잘 주기
	}

	// news 기사 한 건 가져오기 ---9:15
	public News select(int news_idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		News news = null;

		con = dbManager.getConnection();
		String sql = "select * from news where news_idx=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, news_idx);
			rs = pstmt.executeQuery();

			// 해당되는 조건의 레코드가 있다면..
			if (rs.next()) {
				news = new News(); // empty 상태
				news.setNews_idx(rs.getInt("news_idx"));
				news.setTitle(rs.getString("title"));
				news.setDetail(rs.getString("detail"));
				news.setRegdate(rs.getString("regdate"));
				news.setHit(rs.getInt("hit"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return news;
	}

	// 한 건 수정하기
	public int update(News news) { // 매개변수에 dto 넣어주기
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0; // 반환 값으로 사용해야해서 try문 밖으로 빼줌

		con = dbManager.getConnection();
		String sql = "update news set title=?, writer=?, detail=? where news_idx=?";

		try {
			pstmt = con.prepareStatement(sql);
			// 바인드 변수 주기,, 4개니까 낱개로 얻어오지 말고 DTO로 전달받자
			pstmt.setString(1, news.getTitle());
			pstmt.setString(2, news.getWriter());
			pstmt.setString(3, news.getDetail());
			pstmt.setInt(4, news.getNews_idx());

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
	public int delete(int news_idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;

		con = dbManager.getConnection();
		String sql = "delete from news where news_idx=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, news_idx); // 바인드 변수값 지정
			result = pstmt.executeUpdate(); // DML수행 후 반영된 레코드 수 반환

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

}
