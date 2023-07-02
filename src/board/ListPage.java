package board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/*게시물 리스트 페이지 정의*/
public class ListPage extends Page {

	JTable table;
	JScrollPane scroll;
	JButton bt_regist; // 글쓰기 버튼
	NewsModel model;

	/*------------------------------------
			생성자 정의 
	------------------------------------*/
	public ListPage(BoardMain boardMain) {
		super(boardMain);

		// this.setBackground(Color.BLUE);

		table = new JTable(model = new NewsModel()); // **테이블 이걸로 바꿨음~!!
		scroll = new JScrollPane(table);
		bt_regist = new JButton("글 등록");

		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		scroll.setPreferredSize(new Dimension(870, 600));
		add(scroll);
		add(bt_regist);

		getList();

		// 글 등록 버튼과 리스너 연결
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardMain.showHide(BoardMain.REGISTPAGE);
			}
		});

		// 테이블에 마우스 리스너 연결
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 제목만을 클릭해야지 페이지 넘어가기  
				if (table.getSelectedColumn() == 1) {
					//페이지 보여주기 전에, 상세내용 처리 
					DetailPage detailPage = (DetailPage)boardMain.pages[BoardMain.DETAILPAGE];
					
					//사용자가 어디를 클릭하던, 0번째 컬럼의 값을 자겨오자! 
					String value=(String)table.getValueAt(table.getSelectedRow(), 0);
					System.out.println("선택한 news_idx값은 "+value);
					detailPage.getDetail(Integer.parseInt(value));
					
					// 클릭시 상세보기 페이지 보여주기
					boardMain.showHide(BoardMain.DETAILPAGE);
				}
			}
		});
	}

	// 조회
	public void getList() {
		model.newsList = boardMain.newsDAO.selectAll();
		table.updateUI();
	}
}

