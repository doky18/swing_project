package ani.qna;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LogPage extends Page{
	JPanel p_list; // Row 패널들이 붙여질 영역
	JPanel p_num; // 페이지 번호가 붙여질 영역
	JButton bt_regist; // 글 등록
	JButton bt_list;
	
	public LogPage(AppMain appMain) {
		super(appMain);
		//this.setBackground(Color.ORANGE);
	
		p_list = new JPanel();
		p_num = new JPanel();
		bt_regist = new JButton("저널 등록");
		bt_list = new JButton("목록");

		p_list.setPreferredSize(new Dimension(650, 500));
		p_num.setPreferredSize(new Dimension(650, 50));

		p_list.setBackground(Color.WHITE);
		p_num.setBackground(Color.WHITE);

		//조
		add(bt_regist);
		add(bt_list);
		add(p_list);
		add(p_num);
		
		//getList();
		
		// 글쓰기 버튼과 리스너연결
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appMain.showHide(AppMain.LOGPAGE);
			}
		});
	
	}

}
