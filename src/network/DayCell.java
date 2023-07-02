package network;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

//요일을 출력하는 셀
public class DayCell extends Cell{			//제일 상단에 요일이 나올 곳

	/*생성자*/
	//********
	public DayCell(String title,String content, int fontSize, int x ,int y) {//태어날 때 
		super(title, content, fontSize, x, y);
	}
	
	/*오버라이딩 하기 위한 매서드*/
	protected void paintComponent(Graphics g) {
		Graphics2D g2 =(Graphics2D)g;
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, 100, 80);
		
		g2.setColor(Color.WHITE);			//
		Font font = new Font("Verdana", Font.BOLD|Font.ITALIC, 15);
		g2.setFont(font);
		g2.drawString(title, x, y);		//"넣을 글자", 글이 붙을 위치(좌표)
	}
}