package network;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import util.ImageManager;
import util.StringUtil;

public class LoginPage extends Page {
	JLabel pic;
	
	JLabel la_title;
	JLabel la_id;
	JLabel la_pass;
	JTextField t_id;
	JPasswordField t_pass;
	JButton bt_login;
	JButton bt_join;
	
	JPanel panel;
	JPanel north;
	JPanel center;
	JPanel west;
	JPanel east;
	JPanel south;
	
	ImageManager imageManager = new ImageManager();
	LogMemeberDAO logMemeberDAO;

	public LoginPage(AppMain appMain) {
		super(appMain);
		logMemeberDAO = new OracleLogMember();
		
		String path2 = "res/child1.png";
		pic = new JLabel(imageManager.getIcon(path2, 220, 120));

		la_title = new JLabel("LOGIN");
		panel = new JPanel();
		north = new JPanel();
		center = new JPanel();
		west = new JPanel();
		east = new JPanel();
		south = new JPanel();
		
		la_id = new JLabel("ID");
		t_id = new JTextField(18);
		la_pass = new JLabel("Password");
		t_pass = new JPasswordField(18);
		
		bt_login = new JButton("로그인");
		bt_join = new JButton("회원가입");


		// 스타일 적용
		la_title.setPreferredSize(new Dimension(350, 200));
		la_title.setFont(new Font("Arial Black", Font.BOLD, 55));
		la_title.setHorizontalAlignment(SwingConstants.CENTER);

		la_id.setPreferredSize(new Dimension(100, 50));
		la_pass.setPreferredSize(new Dimension(100, 50));
		
		Dimension d = new Dimension(350, 30);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);

		//setPreferredSize(new Dimension(800, 550));
		panel.setPreferredSize(new Dimension(650, 550));
		west.setPreferredSize(new Dimension(100, 0));
		east.setPreferredSize(new Dimension(150, 0));
		
		pic.setBounds(500, 100, 120, 90);

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

		north.add(la_title);
		center.add(la_id);
		center.add(t_id);
		center.add(la_pass);
		center.add(t_pass);
		center.add(pic);
	
		south.add(bt_login);
		south.add(bt_join);
		
		// 로그인 버튼과 리스너 연결
		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginCheck();
			}
		});
		

		// 회원가입 버튼과 리스너 연결
		bt_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textClear();
				appMain.showHide(appMain.JOINPAGE);
			}
		});
	}
	public void loginCheck() {
		//id, pass를 하나의 DTO에 담아서 전달하기
		LogMember logMember=new LogMember();//empty 상태
		logMember.setId(t_id.getText());
		logMember.setPass(StringUtil.getConvertedPassword(new String(t_pass.getPassword())));
		
		logMember = logMemeberDAO.select(logMember);//위에서 다 써서 덮어씀
		if(logMember==null) {
			JOptionPane.showMessageDialog(this, "로그인 정보가 올바르지 않습니다");
		}else {
			JOptionPane.showMessageDialog(this, "WELCOME BACK "+logMember.getId()+" !");
			textClear();
			//ClientMain에 사용자 정보 보관해두기!
			appMain.logMember =logMember;  //로그인하면 appmain이 사용자에 대한 정보를 갖고있게 하기 
			//채팅창 보여주기
			appMain.showHide(appMain.BOOKPAGE);
		}
	}
	
	//다 지우기 
	public void textClear() {
		t_id.setText("");
		t_pass.setText("");
	}
	
}