package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

//Comments 테이블에 대한 CRUD를 처리하는 객체 
public class CommentsDAO {
	DBManager dbManager = DBManager.getInstance();

	//댓글 등록 (부모 테이블의 pk를 함께 넣어줘야 함)
	public int insert(Comments comments) {
		int result =0; 
		Connection con = null;
		PreparedStatement pstmt = null;
		
		con=dbManager.getConnection();
		String sql="insert into comments(comments_idx, news_idx, ctitle, cwriter)";
		sql+=" values(seq_comments.nextval, ?, ?, ?)";
		
		//mysql
		//String sql ="insert into comments(news_idx, ctitle, cwriter) values(?,?,?)";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, comments.getNews().getNews_idx()); //부모키
			pstmt.setString(2, comments.getCTitle());
			pstmt.setString(3, comments.getCwriter());
			
			result = pstmt.executeUpdate();		//쿼리 실행
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	
	
	//댓글 목록 가져오기 (특정 뉴스기사에 딸린.. )
	public List select(News news) { //상세보기에서 살려놓았던 바로 그 news DTO를 넘겨받음 
		List list = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		con = dbManager.getConnection();
		String sql="select * from comments where news_idx=?";	//보고있는 뉴스기사 
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, news.getNews_idx());
			rs=pstmt.executeQuery();
			
			//rs에 들어있는 여러 레코드들을 자바의 객체로 변환
			while(rs.next()) {
				Comments comments = new Comments(); //empty
				comments.setComments_idx(rs.getInt("comments_idx"));
				comments.setCTitle(rs.getString("ctitle"));
				comments.setCwriter(rs.getString("cwriter"));
				comments.setCregdate(rs.getString("cregdate"));	
				
				comments.setNews(news);	//상세보기에서 넘겨받은 news 다시 대입 
				list.add(comments); //리스트에 추가 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		
		return list; 
	}
}
