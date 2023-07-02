package board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DetailPage extends Page {

	JTextField t_title;
	JTextField t_writer;
	JTextField t_detail;
	JButton bt_edit;
	JButton bt_del;
	JButton bt_list;

	News news; // 다른 메서드들에서 상세보기 내용을 참고해야 하므로 멤버변수로 빼둠

	// 댓글 입력
	JPanel p_form;
	JTextField c_title; // 댓글 메시지
	JTextField c_writer; // 댓글 작성자
	JButton bt_regist; // 댓글 등록
	CommentsModel model;
	JTable ctable; // 댓글 테이블
	JScrollPane cscroll;

	public DetailPage(BoardMain boardMain) {
		super(boardMain);

		// this.setBackground(Color.ORANGE);

		t_title = new JTextField();
		t_writer = new JTextField();
		t_detail = new JTextField();

		bt_edit = new JButton("edit");
		bt_del = new JButton("delete");
		bt_list = new JButton("목록");
		// this.setBackground(Color.GREEN);

		// comments
		p_form = new JPanel();
		c_title = new JTextField();
		c_writer = new JTextField();
		bt_regist = new JButton("등록");
		ctable = new JTable(model = new CommentsModel());
		cscroll = new JScrollPane(ctable);
		// ctable.getColumnModel().getColumns(0);

		// style
		t_title.setPreferredSize(new Dimension(850, 30));
		t_writer.setPreferredSize(new Dimension(850, 30));
		t_detail.setPreferredSize(new Dimension(850, 150));

		p_form.setPreferredSize(new Dimension(850, 50));
		c_title.setPreferredSize(new Dimension(500, 25));
		c_writer.setPreferredSize(new Dimension(150, 25));
		cscroll.setPreferredSize(new Dimension(850, 250));

		// add
		add(t_title);
		add(t_writer);
		add(t_detail);
		add(bt_edit);
		add(bt_del);
		add(bt_list);

		// 댓글 폼 부착
		p_form.add(c_title);
		p_form.add(c_writer);
		p_form.add(bt_regist);
		add(p_form);
		add(cscroll);

		// 글 목록 버튼과 리스너 연결
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListPage listPage = (ListPage) boardMain.pages[BoardMain.LISTPAGE];
				listPage.getList();

				boardMain.showHide(BoardMain.LISTPAGE);
			}
		});

		// 수정 버튼과 리스너 연결
		bt_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// update news set title=?, writer=?, detail=? where news_idx=? -> news DAO
				if (JOptionPane.showConfirmDialog(boardMain, "수정하시겠습니까?") == JOptionPane.OK_OPTION) {
					// 상세보기 시, 멤버변수로 빼놓았던 객체를 넣어준다
					// 보고있던 news DTO를 변경
					news.setTitle(t_title.getText()); // 수정한 값을 다시 dto로 넣어줘야지...
					news.setWriter(t_writer.getText());
					news.setDetail(t_detail.getText());

					int result = boardMain.newsDAO.update(news);

					if (result > 0) {
						JOptionPane.showConfirmDialog(boardMain, "수정완료");
						// 목록 다시 갱신...
						ListPage listPage = (ListPage) boardMain.pages[BoardMain.LISTPAGE];
						listPage.getList();
					}
				}

			}
		});

		// 삭제 버튼과 리스너 연결
		bt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(boardMain, "삭제하시겠습니까?") == JOptionPane.OK_OPTION) {
					int result = boardMain.newsDAO.delete(news.getNews_idx());
					if (result > 0) { // 삭제 성공
						JOptionPane.showMessageDialog(boardMain, "삭제완료");

						// 목록 갱신
						ListPage listPage = (ListPage) boardMain.pages[BoardMain.LISTPAGE];
						listPage.getList();

						// 목록 페이지 보여주기
						boardMain.showHide(BoardMain.LISTPAGE);
					}
				}
			}
		});

		// 댓글 등록 버튼과 리스너 연결
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 등록 dao 호출

				// 비어있는 dto를 생성하여, 댓글 1건 담아서 전달!
				Comments comments = new Comments(); // empty dto
				comments.setNews(news); // foreign key 담기
				comments.setCTitle(c_title.getText()); // 댓글 메시지
				comments.setCwriter(c_writer.getText()); // 댓글 등록자명

				int result = boardMain.commentsDAO.insert(comments);
				boardMain.commentsDAO.insert(comments);

				if (result > 0) {// 댓글 등록이 성공되면 목록 갱신
					System.out.println("성공");
					getCommentsList();
				}
			}
		});
	}

	// 댓글목록 가져오기
	public void getCommentsList() {
		List list = boardMain.commentsDAO.select(news); // 딸려있는 댓글 목록 가져오기
		model.list = (ArrayList) list;
		ctable.updateUI();
	}

	// 상세내용 가져오기
	public void getDetail(int news_idx) {
		news = boardMain.newsDAO.select(news_idx); // 반환형은 뉴스
		// 내용 채우기
		t_title.setText(news.getTitle()); // 제목
		t_writer.setText(news.getWriter()); // 작성자
		t_detail.setText(news.getDetail()); // 내용
	}
}
