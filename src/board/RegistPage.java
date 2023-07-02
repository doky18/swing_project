package board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RegistPage extends Page {
	
	JTextField t_title;
	JTextField t_writer;
	JTextField t_detail;
	JButton bt_regist;
	JButton bt_list;

	public RegistPage(BoardMain boardMain) {
		super(boardMain);
	
		t_title = new JTextField();
		t_writer = new JTextField();
		t_detail = new JTextField();
		
		bt_regist = new JButton("등록");
		bt_list = new JButton("목록");
		//this.setBackground(Color.GREEN);
		
		//style
		t_title.setPreferredSize(new Dimension(850, 30));
		t_writer.setPreferredSize(new Dimension(850, 30));
		t_detail.setPreferredSize(new Dimension(850, 450));
		
		//add
		add(t_title);
		add(t_writer);
		add(t_detail);
		add(bt_regist);
		add(bt_list);
		
		//글 목록 버튼과 리스너 연결
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			boardMain.showHide(BoardMain.LISTPAGE);
			}
		});
		
		//글 등록 버튼과 리스너 연결
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//빈 상태의 DTO생성 
				News news = new News();
				
				//dto에 글 내용 채워넣기
				news.setTitle(t_title.getText());	//제목 채우기 
				news.setWriter(t_writer.getText()); //작성자 채우기 
				news.setDetail(t_detail.getText());	//내용 채우
				
				int result = boardMain.newsDAO.insert(news);		//뉴스 dto 한 건이 들어가면 됨 
				if(result>0) {
					JOptionPane.showMessageDialog(boardMain, "등록성공");
					
					//목록 가져오기 
					List newsList=boardMain.newsDAO.selectAll(); //이 변수가 테이블 모델에게로 가야 
					
					//모델이 현재 보유한 newsList의 주소값을 위의 newsList로 대체 
					ListPage listPage = (ListPage)boardMain.pages[BoardMain.LISTPAGE];// pages를 형변환 해준 
					listPage.model.newsList = newsList;			//데이터 교체!! 
					listPage.table.updateUI();
				}
			//boardMain.showHide(BoardMain.LISTPAGE);
			}
		});

	}
}
