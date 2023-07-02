package network;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class BookRegist extends Page {

	JTextField t_title;
	JTextField t_author;
	//JComboBox<String> genre; //장르 카테고리가 나올 박스 
	JTextField t_genre;
	JTextField t_publisher;
	JTextField t_note;
	JLabel la_title;
	JLabel la_author;
	JLabel la_genre;
	JLabel la_publisher;
	JLabel la_note;

	JButton bt_regist;
	JButton bt_list;
	
	BookDAO bookDAO;

	public BookRegist(AppMain appMain) {
		super(appMain);
		this.appMain = appMain;
		
		Color c=new Color(216, 239, 243);
		this.setBackground(c);
		
		la_title = new JLabel("책 제목");
		t_title = new JTextField();
		la_author = new JLabel("작가");
		t_author = new JTextField();
		la_genre = new JLabel("장르");
		t_genre = new JTextField();
		//genre = new JComboBox<String>();
		la_publisher = new JLabel("출판사");
		t_publisher = new JTextField();
		la_note = new JLabel("메모");
		t_note = new JTextField();

		bt_regist = new JButton("regist");
		bt_list = new JButton("list");
		
		//style
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
		
		bt_regist.setPreferredSize(new Dimension(90, 25));
		bt_list.setPreferredSize(new Dimension(90, 25));
		
		//add
		add(la_title);
		add(t_title);
		add(la_author);
		add(t_author);
		add(la_genre);
		add(t_genre);
		//add(genre);
		add(la_publisher);
		add(t_publisher);
		add(la_note);
		add(t_note);

		add(bt_regist);
		add(bt_list);
	
		// 등록 버튼과 리스너 연결
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist(); // 레코드 한 건 넣기
			}
		});

		// 목록 버튼과 리스너 연결
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JournalPage journalPage = (JournalPage) appMain.page[AppMain.JOURNALPAGE];
				journalPage.getList();// 디비 갱신 해주고,,

				appMain.showHide(AppMain.BOOKPAGE);// 화면전환
			}
		});
	}
	
	public void regist() {
		Book book = new Book(); // empty dto
		
		book.setTitle(t_title.getText());
		book.setAuthor(t_author.getText());
		book.setGenre(t_genre.getText());
		book.setPublisher(t_publisher.getText());
		book.setNote(t_note.getText());

		int result = appMain.bookDAO.insert(book);
		
		System.out.println("result is" + result);
		
		if (result > 0) {
			JOptionPane.showMessageDialog(appMain, "등록 완료");
			
			//목록 가져오기 
			List bookList=appMain.bookDAO.selectAll(); //이 변수가 테이블 모델에게로 가야 
			
			//모델이 현재 보유한 newsList의 주소값을 위의 newsList로 대체 
			BookPage bookPage = (BookPage)appMain.page[appMain.BOOKPAGE];// pages를 형변환 해준 
			bookPage.model.bookList = bookList;			//데이터 교체!! 
			bookPage.table.updateUI();
			
			appMain.showHide(appMain.BOOKPAGE);
		}
	}
	

}
