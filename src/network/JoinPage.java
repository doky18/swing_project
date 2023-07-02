package network;

import java.awt.BorderLayout;
import java.awt.Color;
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

import util.StringUtil;

public class JoinPage extends Page{
	JLabel la_title;
	JPanel panel;
	JPanel north;
	JPanel center;
	JPanel west;
	JPanel east;
	JPanel south;
	JLabel la_email;
	JLabel la_id;
	JLabel la_pass;
	JTextField t_email;
	JTextField t_id;
	JPasswordField t_pass;
	JButton bt_login;
	JButton bt_join;
	
	LogMemeberDAO logMemeberDAO;
	
	public JoinPage(AppMain appMain) {
		super(appMain);
		logMemeberDAO = new OracleLogMember();
		
		la_title = new JLabel("SIGN UP");
		panel = new JPanel();
		north = new JPanel();
		center = new JPanel();
		west = new JPanel();
		east = new JPanel();
		south = new JPanel();
		
		la_email = new JLabel("E-mail");
		t_email = new JTextField(25);
		la_id = new JLabel("ID");
		t_id = new JTextField(25);
		la_pass = new JLabel("Password");
		t_pass = new JPasswordField(25);

		bt_login = new JButton("로그인");
		bt_join = new JButton("회원가입");

		//스타일 적용
		la_title.setPreferredSize(new Dimension(350, 200));
		la_title.setFont(new Font("Arial Black", Font.BOLD, 55));
		la_title.setHorizontalAlignment(SwingConstants.CENTER);

		la_email.setPreferredSize(new Dimension(100, 50));
		la_id.setPreferredSize(new Dimension(100, 50));
		la_pass.setPreferredSize(new Dimension(100, 50));
		
		Dimension d = new Dimension(350, 30);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		t_email.setPreferredSize(d);

		//setPreferredSize(new Dimension(650, 550));
		panel.setPreferredSize(new Dimension(650, 550));
		west.setPreferredSize(new Dimension(100, 0));
		east.setPreferredSize(new Dimension(100, 0));
		
		//조립
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
		center.add(la_email);
		center.add(t_email);
		center.add(la_id);
		center.add(t_id);
		center.add(la_pass);
		center.add(t_pass);

		south.add(bt_login);
		south.add(bt_join);
		
		//setBackground(Color.YELLOW);

		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		// 회원가입 버튼과 리스너연결
		bt_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
	}

	public void login() {
		appMain.showHide(appMain.LOGINPAGE);
	}

	public void regist() {
		LogMember logMember = new LogMember(); // empty 상태인 DTO
		logMember.setId(t_id.getText());
		logMember.setPass(StringUtil.getConvertedPassword(new String(t_pass.getPassword())));
		logMember.setEmail(t_email.getText());

		int result = logMemeberDAO.insert(logMember);
		if (result > 0) {
			JOptionPane.showMessageDialog(this, "가입 성공!!");
			appMain.showHide(appMain.LOGINPAGE);
		}else {
			JOptionPane.showMessageDialog(this, "가입 실패!!");
		}
	}

}