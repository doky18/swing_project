package network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

public class BookDAO {
	DBManager dbManager = DBManager.getInstance();

	// 게시물 한 건 넣기
	public int insert(Book book) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnection();

		String sql = "insert into book(book_idx, title, author, genre, publisher, note)";
		sql += " values(seq_news.nextval, ?,?,?,?,?)";

		try {
			pstmt = con.prepareStatement(sql);
			// 바인드 변수 지정
			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getAuthor());
			pstmt.setString(3, book.getGenre());
			pstmt.setString(4, book.getPublisher());
			pstmt.setString(5, book.getNote());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

	// 레코드 한 건 가져오기
	public Book select(int book_idx) {
		Book book = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = dbManager.getConnection();
		String sql = "select * from book where book_idx=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, book_idx);// pk 넘겨 받음
			rs = pstmt.executeQuery();

			if (rs.next()) {// 레코드가 있다면 dto 생성
				// title, author, genre, publisher, note
				book = new Book();
				book.setBook_idx(rs.getInt("book_idx"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setGenre(rs.getString("genre"));
				book.setPublisher(rs.getString("publisher"));
				book.setNote(rs.getString("note"));
				book.setRegdate(rs.getString("regdate"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return book;
	}

	// 모든 레코드 가져오기
	public List selectAll() {
		List list = new ArrayList();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = dbManager.getConnection();

		String sql = "select * from book order by book_idx asc";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();

				book.setBook_idx(rs.getInt("book_idx"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setGenre(rs.getString("genre"));
				book.setPublisher(rs.getString("publisher"));
				book.setNote(rs.getString("note"));
				book.setRegdate(rs.getString("regdate"));

				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 한 건 수정하기
	public int update(Book book) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0; // 반환 값으로 사용해야해서 try문 밖으로 빼줌

		con = dbManager.getConnection();
		String sql = "update book set title=?, author=?, genre=?, publisher=?, note=? where book_idx=?";

		try {
			pstmt = con.prepareStatement(sql);
			// title, author, genre, publisher, note
			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getAuthor());
			pstmt.setString(3, book.getGenre());
			pstmt.setString(4, book.getPublisher());
			pstmt.setString(5, book.getNote());
			pstmt.setInt(6, book.getBook_idx());

			result = pstmt.executeUpdate(); // 쿼리 수행

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

	// 한 건 삭제
	public int delete(int book_idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;

		con = dbManager.getConnection();
		String sql = "delete from book where book_idx=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, book_idx);
			result = pstmt.executeUpdate(); // DML수행 후 반영된 레코드 수 반환

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
}
