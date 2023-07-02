package ani.qna;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

//사이드 메뉴를 정의한다 
public class SideMenu extends JLabel {
	AppMain appMain;
	int targetPage; //자기가 갈 페이지 보관해두기 어느 페이지로 갈 건 지 

	public SideMenu(String label, AppMain appMain, int targetPage) {
		super(label); // 부모에게 전달
		this.appMain = appMain;
		this.targetPage=targetPage;
		
		setFont(new Font("Dotum", Font.BOLD, 30));

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				appMain.showHide(targetPage);
			}
		});
	}
}
