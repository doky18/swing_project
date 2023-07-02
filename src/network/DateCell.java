package network;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class DateCell extends Cell { // 아래에 컨텐츠도 추가 할 수 있을
	Color color = Color.WHITE; // 디폴트로 흰 색
	AppMain appMain;

	public DateCell(AppMain appMain, String title, String content, int fontSize, int x, int y) {
		super(title, content, fontSize, x, y);
		this.appMain=appMain;

		Border border = new LineBorder(Color.DARK_GRAY);
		setBorder(border);

		this.addMouseListener(new MouseAdapter() {		//클릭 했을 때 노란색으로 채우기
			public void mouseClicked(MouseEvent e) {
				Color b=new Color(255, 209, 204);
				
				if (color == Color.WHITE) {
					color = b; 
					
					//콤보박스에 날짜 채워넣기
					//appMain.setDateInfo(DateCell.this.title);			//여기서 this라고하면 내부익명 this니까 DateCell거라고 해줘야 함
				} else if(color==Color.GREEN){
					return;
				}else{
					color = Color.WHITE; 							//재클릭시 흰색으로
				}
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, 120, 120); // 채우기 전에 지워주기
		// 배경색 적용하기
		g2.setColor(color); // 배경색은 고정이 아니라 바뀌는 것이니까 변수로 받아서 해주기
		g2.fillRect(0, 0, 100, 100);

		g2.setColor(Color.DARK_GRAY);
		g2.drawString(title, x, y);//날짜 그리기
		g2.drawString(content, x-30, y+25);//내용그리기
	}
}
