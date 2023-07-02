package network;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class JRow extends JPanel {
	int no;// 게시물 순번
	JournalBoard jBoard;

	JLabel la_no;
	JLabel la_title;
	JLabel la_content;
	JLabel la_regdate;

	JournalPage journalPage;

	public JRow(int no, JournalBoard jBoard, JournalPage journalPage) {
		this.jBoard = jBoard;
		this.journalPage = journalPage;

		Border border = new LineBorder(Color.GRAY);
		this.setPreferredSize(new Dimension(750, 40));

		// 라벨생성
		la_no = new JLabel(Integer.toString(no));
		la_title = new JLabel(jBoard.getTitle());
		la_content = new JLabel(jBoard.getContent());
		la_regdate = new JLabel(jBoard.getRegdate());

		// 부착하기 전에 라벨들의 간격조정을 위한 너비설정
		la_no.setPreferredSize(new Dimension(30, 35));
		la_title.setPreferredSize(new Dimension(100, 35));
		la_content.setPreferredSize(new Dimension(270, 35));
		la_regdate.setPreferredSize(new Dimension(100, 35));

		add(la_no);
		add(la_title);
		add(la_content);
		add(la_regdate);

		// 제목라벨과 리스너 연결
		la_title.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// JOptionPane.showMessageDialog(null, Integer.toString(no));

				// 상세내용 db가져오기
				JournalDetail jDetail = (JournalDetail) journalPage.appMain.page[AppMain.JONRNALDETAIL];
				jDetail.getDetail(jBoard.getJboard_idx()); // 디테일의 메서드에 jboard가 가진 idx 넘겨주기

				// 상세페이지 보여주기
				journalPage.appMain.showHide(AppMain.JONRNALDETAIL);
			}

			public void mouseEntered(MouseEvent e) {
				// 마우스가 올려진 애들만 배경색.. 나머지는 빼기
				int index = journalPage.rows.indexOf(JRow.this); // this라고 하면 내부익명클래스라서 안됨
				journalPage.selectMark(index); // no는 게시물 넘버링이고, 리스트에 담겨있는 row 순번을 넘겨서 색깔 효과를 주자 
			}
		});

	}
}
