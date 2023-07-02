package network;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class JournalRegist extends Page {
	JTextField t_title;
	JTextField t_writer;
	JTextField t_detail;
	JLabel la_title;
	JLabel la_writer;
	JLabel la_detail;
	
	JButton bt_regist;
	JButton bt_list;

	AppMain appMain;
	JBoardDAO jBoardDAO;

	public JournalRegist(AppMain appMain) {
		super(appMain);
		this.appMain = appMain;
		jBoardDAO = appMain.jBoardDAO;
		
		Color c=new Color(235, 173, 194);
		this.setBackground(c);

		la_title = new JLabel("책 제목");
		t_title = new JTextField();
		la_detail = new JLabel("내용");
		t_detail = new JTextField();

		bt_regist = new JButton("regist");
		bt_list = new JButton("list");

		// style
		t_title.setPreferredSize(new Dimension(600, 25));
		t_detail.setPreferredSize(new Dimension(600, 450));

		la_title.setPreferredSize(new Dimension(100, 50));
		la_title.setFont(new Font("Arial Black", Font.PLAIN, 15));
		
		la_detail.setPreferredSize(new Dimension(100, 50));
		la_detail.setFont(new Font("Arial Black", Font.PLAIN, 15));
		
		bt_regist.setPreferredSize(new Dimension(100, 25));
		bt_list.setPreferredSize(new Dimension(100, 25));
		
		// add
		add(la_title);
		add(t_title);
		add(la_detail);
		add(t_detail);
		add(bt_regist);
		add(bt_list);

		// 등록 버튼과 리스너 연결
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist(); //레코드 한 건 넣기 
			}
		});
		
		// 목록 버튼과 리스너 연결
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JournalPage journalPage = (JournalPage) appMain.page[AppMain.JOURNALPAGE];
				journalPage.getList();//디비 갱신 해주고,, 
				
				appMain.showHide(AppMain.JOURNALPAGE);//화면전환 
			}
		});
	}
	
	public void regist() {
		JournalBoard jBoard = new JournalBoard(); // empty dto
		jBoard.setTitle(t_title.getText());
		jBoard.setContent(t_detail.getText());

		int result = jBoardDAO.insert(jBoard);
		//System.out.println("result is" + result);
		if (result > 0) {
			JOptionPane.showMessageDialog(appMain, "done");
		}
		this.updateUI();
	}

}
