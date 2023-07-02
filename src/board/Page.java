package board;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/*모든 페이지들의 최상위 객체 (공통 기능을 보유함)*/
public class Page extends JPanel {
	BoardMain boardMain;
	
	
	/*------------------------------------
		생성자 정의 
	------------------------------------*/
	public Page(BoardMain boardMain) {
		this.boardMain=boardMain;
		
		this.setPreferredSize(new Dimension(900, 650));
	}
	
	
	
	/*------------------------------------
		메서드 정의 
	------------------------------------*/

	/*------------------------------------
	실행부 정의 
	------------------------------------*/
}
