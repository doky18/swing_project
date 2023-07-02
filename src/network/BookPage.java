package network;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class BookPage extends Page {
	JPanel p_west;
	JComboBox<String> box;
	JTextField t_search;
	JButton bt_search;
	
	JPanel p_center;
	JPanel p_south;
	JPanel p_list;// 책 목록 제목보더를 붙일 영역

	JTable table;
	JScrollPane scroll;
	JButton bt_regist;
	
	BookModel model;
	
	String[] searchArray = {"GENRE","시","소설","공상과학","역사"};

	public BookPage(AppMain appMain) {
		super(appMain);
		
		Color c=new Color(196, 231, 237);
		this.setBackground(c);
		
		p_west = new JPanel();
		box = new JComboBox<>(searchArray);
		t_search = new JTextField(20);
		bt_search = new JButton("검색");
		
		p_south = new JPanel();
		
		p_center = new JPanel();

		p_list = new JPanel();
		table = new JTable(model = new BookModel());
		scroll = new JScrollPane(table);

		bt_regist = new JButton("책 추가");

		// 각각의 패널에 적절한 영역인 보더를 적용하되 제목이 있는 보더를 적용해보자
		//Border listBorder = BorderFactory.createTitledBorder("My Library");
		//p_list.setBorder(listBorder);

		//table.getColumnModel().getColumn(1).setPreferredWidth(300);
		p_west.setPreferredSize(new Dimension(650, 50));
		p_south.setPreferredSize(new Dimension(650, 80));
		p_list.setPreferredSize(new Dimension(650, 430));
		scroll.setPreferredSize(new Dimension(650, 400));
		bt_regist.setPreferredSize(new Dimension(120, 30));
		
		p_west.setBackground(c);
		p_south.setBackground(c);
		p_list.setBackground(c);
		
		p_west.add(box);
		p_west.add(t_search);
		p_west.add(bt_search);
		
		p_south.add(bt_regist);
		p_list.add(scroll);
		
		add(p_west);
		add(p_list);
		add(p_south);

		getList();

		// 글 등록 버튼과 리스너 연결
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appMain.showHide(appMain.BOOKREGIST);
			}
		});
		
		// 테이블에 마우스 리스너 연결
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 클릭시 상세보기 페이지 보여주기
				appMain.showHide(appMain.BOOKDETAIL);
				
				int row = table.getSelectedRow();
				Book book = model.bookList.get(row);
				BookDetail bookDetail = (BookDetail)appMain.page[AppMain.BOOKDETAIL];
				bookDetail.getDetail(book.getBook_idx());
			}
			
		});
	}
	
	//조회하기 
	public void getList() {
		model.bookList = appMain.bookDAO.selectAll();
		table.updateUI();
	}

}
