package network;

import java.awt.Dimension;

import javax.swing.JPanel;

public class Page extends JPanel{
	AppMain appMain;	//모든 페이지는 메인프레임을 통해 서로 접근할 수 있도록 
	
	public Page(AppMain appMain) {
		this.appMain=appMain;
		setPreferredSize(new Dimension(800, 600));
	}
}
