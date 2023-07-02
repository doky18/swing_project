package network;

import javax.swing.JPanel;

public class Cell extends JPanel{
	String title;				//날짜
	String content;		//내용
	int fontSize;
	int x;
	int y;
	
	/*부모 생성자 설정 -> 자식은 상속 받을 때 super(매개변수)*/
	public Cell(String title, String content, int fontSize, int x ,int y) {
		this.title=title;
		this.content=content;
		this.fontSize=fontSize;
		this.x=x;
		this.y=y;
	}
}
