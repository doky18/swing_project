package ani.qna;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QnADetail extends Page {
	// 상세보기
	JTextField t_title;
	JTextField t_writer;
	JTextField t_detail;
	JButton bt_edit;
	JButton bt_del;
	JButton bt_list;
	JButton bt_replyform;

	// 답변달기
	JPanel p_reply; // 답변폼을 보였다 안보였다 처리하기 위함
	JTextField t_title2;
	JTextField t_writer2;
	JTextField t_detail2;
	JButton bt_reply;
	JButton bt_cancel; // 답변취소버튼

	//AppMain appMain;

	ReBoardDAO reboardDAO;
	ReBoard reboard;	//현재 보고 있는 글 

	public QnADetail(AppMain appMain) {
		super(appMain);
		//this.appMain = appMain;
		reboardDAO = appMain.reboardDAO;

		t_title = new JTextField(50);
		t_writer = new JTextField(50);
		t_detail = new JTextField(60);

		p_reply = new JPanel();
		t_title2 = new JTextField(50);
		t_writer2 = new JTextField(50);
		t_detail2 = new JTextField(60);

		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");
		bt_list = new JButton("목록");
		bt_replyform = new JButton("답변");

		bt_reply = new JButton("reply");
		bt_cancel = new JButton("cancel");

		// this.setBackground(Color.GREEN);

		// style
		t_title.setPreferredSize(new Dimension(740, 30));
		t_writer.setPreferredSize(new Dimension(740, 30));
		t_detail.setPreferredSize(new Dimension(740, 150));

		p_reply.setPreferredSize(new Dimension(740, 300));
		t_title2.setPreferredSize(new Dimension(740, 30));
		t_writer2.setPreferredSize(new Dimension(740, 30));
		t_detail2.setPreferredSize(new Dimension(740, 150));

		// add
		add(t_title);
		add(t_writer);
		add(t_detail);
		add(bt_edit);
		add(bt_del);
		add(bt_list);
		add(bt_replyform);

		add(p_reply);
		p_reply.add(t_title2);
		p_reply.add(t_writer2);
		p_reply.add(t_detail2);
		p_reply.add(bt_reply);
		p_reply.add(bt_cancel);
		
		p_reply.setVisible(false);//답변폼 숨기기 

		
		// 답변폼 보이기 
		bt_replyform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p_reply.setVisible(true);
			}
		});
		
		//답변하기 
		bt_reply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

	}
	//게시물 한 건 가져오기 
	public void getDetail(int reboard_idx) {
		System.out.println("넘어온 idx"+reboard_idx);
		reboard = appMain.reboardDAO.select(reboard_idx);//dao로부터 레코드를 받아오기 
		
		//화면에 출력 (ui에 반영)
		t_title.setText(reboard.getTitle());
		t_writer.setText(reboard.getWriter());
		t_detail.setText(reboard.getContent());
	}
	
	//현재 읽은 글에 대한 답변 등록하기 
	public void reply() {
		int maxStep = appMain.reboardDAO.selectMaxStep(reboard);
		if(maxStep>0) {//누군가 있다.. 
			reboard.setStep(maxStep);
			appMain.reboardDAO.updateStep(reboard);//기득권 세력 물러나게하기! (자리 확보)
		}
		ReBoard dto = new ReBoard();	//답변을 채울 dto
		dto.setTitle(t_title2.getText());
		dto.setWriter(t_writer2.getText());
		dto.setContent(t_detail2.getText());
		dto.setTeam(reboard.getTeam()); //내가 보는 글의 팀 
		dto.setStep(maxStep);
		dto.setDepth(reboard.getDepth());
		
		int result = appMain.reboardDAO.reply(reboard);	//답변 등록..
		
		if(result>0) {
			JOptionPane.showMessageDialog(appMain, "답변 등록");
		}
	}

}
