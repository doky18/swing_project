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

import board.BoardMain;
import board.ListPage;

public class JournalDetail extends Page {
	// 수정하기
	JTextField t_title;
	JTextField t_writer;
	JTextField t_detail;
	
	JLabel la_title;
	JLabel la_writer;
	JLabel la_detail;
	
	JButton bt_edit;
	JButton bt_del;
	JButton bt_list;

	JBoardDAO jBoardDAO;
	JournalBoard jBoard;

	public JournalDetail(AppMain appMain) {
		super(appMain);
		jBoardDAO = appMain.jBoardDAO;
		
		Color c=new Color(235, 173, 194);
		this.setBackground(c);

		la_title = new JLabel("책 제목");
		t_title = new JTextField();
		t_writer = new JTextField();
		la_detail = new JLabel("내용");
		t_detail = new JTextField();

		bt_edit = new JButton("edit");
		bt_del = new JButton("delete");
		bt_list = new JButton("list");

		// style
		t_title.setPreferredSize(new Dimension(600, 25));
		t_detail.setPreferredSize(new Dimension(600, 400));

		la_title.setPreferredSize(new Dimension(100, 50));
		la_title.setFont(new Font("Arial Black", Font.PLAIN, 15));
		
		la_detail.setPreferredSize(new Dimension(100, 50));
		la_detail.setFont(new Font("Arial Black", Font.PLAIN, 15));
		
		bt_edit.setPreferredSize(new Dimension(90, 25));
		bt_del.setPreferredSize(new Dimension(90, 25));
		bt_list.setPreferredSize(new Dimension(90, 25));

		// add
		add(la_title);
		add(t_title);
		add(la_detail);
		add(t_detail);
		add(bt_edit);
		add(bt_del);
		add(bt_list);

		// bt_edit
		bt_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(appMain, "수정하시겠습니까?") == JOptionPane.OK_OPTION) {
					// 보고있던 DTO를 변경
					jBoard.setTitle(t_title.getText()); // 수정한 값을 다시 dto로 넣어줘야지...
					jBoard.setContent(t_detail.getText());

					int result = appMain.jBoardDAO.update(jBoard);

					if (result > 0) {
						JOptionPane.showMessageDialog(appMain, "수정완료");
						// 목록 다시 갱신...
						JournalPage journalPage = (JournalPage) appMain.page[appMain.JOURNALPAGE];
						journalPage.getList();

						// 목록 페이지 보여주기
						appMain.showHide(appMain.JOURNALPAGE);
					}
				}
			}
		});

		// bt_delete
		bt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(appMain, "삭제하시겠습니까?") == JOptionPane.OK_OPTION) {
					
					int result = appMain.jBoardDAO.delete(jBoard.getJboard_idx());
					
					if (result > 0) { // 삭제 성공
						JOptionPane.showMessageDialog(appMain, "삭제완료");

						// 목록 갱신
						JournalPage journalPage = (JournalPage) appMain.page[appMain.JOURNALPAGE];
						journalPage.getList();

						// 목록 페이지 보여주기
						appMain.showHide(appMain.JOURNALPAGE);
					}
				}
			}
		});

		// back to list
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appMain.showHide(AppMain.JOURNALPAGE);
			}
		});
	}

	// 게시물 한 건 가져오기
	public void getDetail(int jboard_idx) {
		System.out.println("넘어온 idx" + jboard_idx);
		jBoard = appMain.jBoardDAO.select(jboard_idx);// dao로부터 레코드를 받아오기

		// 화면에 출력 (ui에 반영)
		t_title.setText(jBoard.getTitle());
		t_detail.setText(jBoard.getContent());
	}

}
