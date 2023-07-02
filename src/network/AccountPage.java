package network;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import util.ImageManager;

public class AccountPage extends Page {
	JLabel pic;
	
	JPanel panel;
	JPanel north;
	JPanel center;
	JPanel west;
	JPanel east;
	JPanel south;
	JLabel la_title;
	
	JButton bt_logout;
	JButton bt_join;
	
	ImageManager imageManager = new ImageManager();

	public AccountPage(AppMain appMain) {
		super(appMain);
		Color c=new Color(230, 230, 250);
		this.setBackground(c);
		
		String path2 = "res/child1.png";
		pic = new JLabel(imageManager.getIcon(path2, 320, 220));

		//la_title = new JLabel("나의 계정");
		panel = new JPanel();
		north = new JPanel();
		center = new JPanel();
		west = new JPanel();
		east = new JPanel();
		south = new JPanel();
		
		bt_logout = new JButton("로그아웃");
		bt_join = new JButton("회원가입");

		//la_title.setPreferredSize(new Dimension(350, 200));
		//la_title.setFont(new Font("Arial Black", Font.BOLD, 45));
		//la_title.setHorizontalAlignment(SwingConstants.CENTER);

		//setPreferredSize(new Dimension(650, 550));
		panel.setPreferredSize(new Dimension(650, 550));
		west.setPreferredSize(new Dimension(100, 0));
		east.setPreferredSize(new Dimension(150, 0));
		north.setPreferredSize(new Dimension(0, 200));
		
		panel.setBackground(c);
		center.setBackground(c);
		north.setBackground(c);
		east.setBackground(c);
		west.setBackground(c);
		south.setBackground(c);
		
		pic.setBounds(240, 300, 320, 220);
		
		//add(la_title);
		// 조립
		add(panel);
		add(center);
		add(north);
		add(east);
		add(west);
		add(south);
		
		panel.setLayout(new BorderLayout());
		panel.add(west, BorderLayout.WEST);
		panel.add(north, BorderLayout.NORTH);
		panel.add(center);
		panel.add(east, BorderLayout.EAST);
		panel.add(south, BorderLayout.SOUTH);

		center.add(pic);
		center.add(bt_logout);
		center.add(bt_join);
		
		//로그아웃 버튼을 누르면 로그인 페이지
		bt_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				appMain.showHide(appMain.LOGINPAGE);
				appMain.logMember=null;
			}
		});
		
		bt_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appMain.showHide(appMain.JOINPAGE);
			}
		});

	}

}
