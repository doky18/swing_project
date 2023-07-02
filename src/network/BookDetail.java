package network;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class BookDetail extends Page {

	JTextField t_title;
	JTextField t_author;
	// JComboBox<String> genre; //장르 카테고리가 나올 박스
	JTextField t_genre;
	JTextField t_publisher;
	JTextField t_note;
	JLabel la_title;
	JLabel la_author;
	JLabel la_genre;
	JLabel la_publisher;
	JLabel la_note;

	JButton bt_edit;
	JButton bt_del;
	JButton bt_list;

	Book book;

	public BookDetail(AppMain appMain) {
		super(appMain);
		
		Color c=new Color(216, 239, 243);
		this.setBackground(c);

		la_title = new JLabel("책 제목");
		t_title = new JTextField();
		la_author = new JLabel("작가");
		t_author = new JTextField();
		la_genre = new JLabel("장르");
		t_genre = new JTextField();
		// genre = new JComboBox<String>();
		la_publisher = new JLabel("출판사");
		t_publisher = new JTextField();
		la_note = new JLabel("메모");
		t_note = new JTextField();

		bt_edit = new JButton("edit");
		bt_del = new JButton("delete");
		bt_list = new JButton("목록으로");

		// style
		t_title.setPreferredSize(new Dimension(600, 25));
		t_author.setPreferredSize(new Dimension(600, 25));
		t_genre.setPreferredSize(new Dimension(600, 25));
		t_publisher.setPreferredSize(new Dimension(600, 25));
		t_note.setPreferredSize(new Dimension(600, 250));

		la_title.setPreferredSize(new Dimension(100, 50));
		la_title.setFont(new Font("Arial Black", Font.PLAIN, 15));
		la_author.setPreferredSize(new Dimension(100, 50));
		la_author.setFont(new Font("Arial Black", Font.PLAIN, 15));
		la_genre.setPreferredSize(new Dimension(100, 50));
		la_genre.setFont(new Font("Arial Black", Font.PLAIN, 15));
		la_publisher.setPreferredSize(new Dimension(100, 50));
		la_publisher.setFont(new Font("Arial Black", Font.PLAIN, 15));
		la_note.setPreferredSize(new Dimension(100, 50));
		la_note.setFont(new Font("Arial Black", Font.PLAIN, 15));

		bt_edit.setPreferredSize(new Dimension(90, 25));
		bt_del.setPreferredSize(new Dimension(90, 25));
		bt_list.setPreferredSize(new Dimension(90, 25));

		// add
		add(la_title);
		add(t_title);
		add(la_author);
		add(t_author);
		add(la_genre);
		add(t_genre);
		add(la_publisher);
		add(t_publisher);
		add(la_note);
		add(t_note);

		add(bt_edit);
		add(bt_del);
		add(bt_list);

		// back to list
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appMain.showHide(AppMain.BOOKPAGE);// 화면전환
			}
		});

		// bt_edit
		bt_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// "update book set title=?, author=?, genre=?, publisher=?, note=? where
				// book_idx=?";
				if (JOptionPane.showConfirmDialog(appMain, "수정하시겠습니까?") == JOptionPane.OK_OPTION) {

					book.setTitle(t_title.getText());
					book.setAuthor(t_author.getText());
					book.setGenre(t_genre.getText());
					book.setPublisher(t_publisher.getText());
					book.setNote(t_note.getText());

					int result = appMain.bookDAO.update(book);

					if (result > 0) {
						JOptionPane.showMessageDialog(appMain, "수정완료");

						// 목록 다시 갱신...
						BookPage bookPage = (BookPage) appMain.page[AppMain.BOOKPAGE];
						bookPage.getList();

						appMain.showHide(AppMain.BOOKPAGE);// 화면전환
					}
				}
			}
		});

		// bt_delete
		bt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(appMain, "삭제하시겠습니까?") == JOptionPane.OK_OPTION) {
					int result = appMain.bookDAO.delete(book.getBook_idx());

					if (result > 0) { // 삭제 성공
						JOptionPane.showMessageDialog(appMain, "삭제완료");

						// 목록 갱신
						BookPage bookPage = (BookPage) appMain.page[AppMain.BOOKPAGE];
						bookPage.getList();

						appMain.showHide(AppMain.BOOKPAGE);// 화면전환
					}
				}
			}
		});

	}

	// 게시물 한 건 가져오기
	public void getDetail(int book_idx) {
		System.out.println("넘어온 idx" + book_idx);
		book = appMain.bookDAO.select(book_idx);// dao로부터 레코드를 받아오기

		t_title.setText(book.getTitle()); // 제목
		t_author.setText(book.getAuthor()); // 작가
		t_genre.setText(book.getGenre()); // 장르
		t_publisher.setText(book.getPublisher()); // 출판사
		t_note.setText(book.getNote()); // 비고
	}
}