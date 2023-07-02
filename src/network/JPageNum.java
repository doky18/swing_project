package network;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

//페이징 처리시의 한 페이지를 표현하는 라벨 (클릭 가능하게 + 해당 페이지 번호를 보유하도록)
public class JPageNum extends JLabel{
	String n;
	JournalPage journalPage;
	
	public JPageNum(String n, JournalPage journalPage) {
		super(n);
		this.n=n;
		this.journalPage=journalPage;

		this.setFont(new Font("Arial black", Font.BOLD, 20));
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("현재 보고있는 페이지는"+ n);
				journalPage.pagingManager.setCurrentPage(Integer.parseInt(n));
			
				journalPage.getList();
				journalPage.effectPage(n);
			}
		});
	}

}
