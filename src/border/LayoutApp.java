package border;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

public class LayoutApp extends JFrame{
	JPanel p_west;
	JPanel p_regist; //제품 정보 입력 영역 : 보더를 적용예정 
	JPanel p_area;
	
	JPanel p_center;
	JPanel p_list;	//제품목록 제목보더를 붙일 영역
	JTable table;
	JScrollPane scroll;
	
	public LayoutApp() {
		//	서쪽 
		p_west = new JPanel();
		p_regist = new JPanel();
		p_area = new JPanel();
		
		p_center = new JPanel();
		p_list = new JPanel();
		table = new JTable(14, 8);
		scroll = new JScrollPane(table);
		
		//각각의 패널에 적절한 영역인 보더를 적용하되 제목이 있는 보더를 적용해보자 
		Border registBorder = BorderFactory.createTitledBorder("제품정보");
        Border etcBorder=BorderFactory.createTitledBorder("기타정보");
        Border listBorder=BorderFactory.createTitledBorder("제품리스트");
		
        p_regist.setBorder(registBorder);//보더적용 
        p_area.setBorder(etcBorder);
        p_list.setBorder(listBorder);
        
		p_west.setPreferredSize(new Dimension(200, 500));
		p_regist.setPreferredSize(new Dimension(180, 230));
		p_area.setPreferredSize(new Dimension(180, 200));	
		p_list.setPreferredSize(new Dimension(680, 400));
		scroll.setPreferredSize(new Dimension(670, 390));
		
		p_west.add(p_regist);
		p_west.add(p_area);
		p_list.add(scroll);
		
		add(p_west, BorderLayout.WEST);
		add(p_list);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(900, 500);
	}
	
	public static void main(String[] args) {
		new LayoutApp();
	}
}
