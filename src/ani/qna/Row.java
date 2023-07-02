package ani.qna;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

// 페이징 처리를 위해 JTable을 이용하지 않고 손수 Row를 재정의하여 사용해보자
public class Row extends JPanel {
	int no; // 게시물 순번
	ReBoard reboard;

	JLabel la_no;
	JLabel la_title;
	JLabel la_writer;
	JLabel la_regdate;
	JLabel la_hit;

	QnAPage qnaPage;

	public Row(int no, ReBoard reboard, QnAPage qnaPage) {
		this.reboard = reboard;
		this.qnaPage = qnaPage;

//      테두리를 영어로 : Border
		Border border = new LineBorder(Color.GRAY);
		this.setPreferredSize(new Dimension(750, 40));

		// 라벨생성
		la_no = new JLabel(Integer.toString(no));
		la_title = new JLabel(reboard.getTitle());
		la_writer = new JLabel(reboard.getWriter());
		la_regdate = new JLabel(reboard.getRegdate());
		la_hit = new JLabel(Integer.toString(reboard.getHit()));

		// 부착하기 전에 라벨들의 간격조정을 위한 너비설정
		la_no.setPreferredSize(new Dimension(30, 35));
		la_title.setPreferredSize(new Dimension(400, 35));
		la_writer.setPreferredSize(new Dimension(120, 35));
		la_regdate.setPreferredSize(new Dimension(100, 35));
		la_hit.setPreferredSize(new Dimension(30, 35));

		add(la_no);

		// 공백 보더
		Border b = BorderFactory.createEmptyBorder(0, reboard.getDepth() * 50, 0, 0);
		la_title.setBorder(b); // 라벨에 보더 적용
		add(la_title);
		add(la_writer);
		add(la_regdate);
		add(la_hit);

		// 제목라벨과 리스너 연결
		la_title.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// JOptionPane.showMessageDialog(null, Integer.toString(no));

				// 상세내용 db가져오기
				QnADetail qnaDetail = (QnADetail) qnaPage.appMain.page[AppMain.QNADETAIL];
				qnaDetail.getDetail(reboard.getReboard_idx()); // 겟디테일의 메서드에 reboard가 가진 idx 넘겨주기

				// 상세페이지 보여주기
				qnaPage.appMain.showHide(AppMain.QNADETAIL);
			}

			public void mouseEntered(MouseEvent e) {
				// 마우스가 올려진 애들만 배경색.. 나머지는 빼기
				// Row인 내가 배열의 ㅕㅊ번째에 들어있는지 역조사
				int index = qnaPage.rows.indexOf(Row.this); // this라고 하면 내부익명클래스라서 안됨
				qnaPage.showLight(index); // no는 게시물 넘버링이고, 리스트에 담겨있는 row 순번을 넘겨야 한다.
			}
		});

	}

}