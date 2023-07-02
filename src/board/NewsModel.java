package board;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

//뉴스게시물을 보여줄 JTable에 정보를 제공해주는 모델 객체 
public class NewsModel extends AbstractTableModel{

	List <News> newsList = new ArrayList<News>();		//실제 데이터로 바뀌는 시점 -> 등록 성공!이 뜰 때 
	String[] columnName = {"news_idx","제목","작성자","등록일","조회수"};
	
	public int getRowCount() { //층수 
		return newsList.size();
	}

	public int getColumnCount() {//호수 
		return 5;
	}
	
	public String getColumnName(int col) {
		return columnName[col];		//여기에 배열 대입 
	}

	public Object getValueAt(int row, int col) {
		News news = (News)newsList.get(row);	//층수에서 배열이 아닌, 보다 객체지향적인 DTO를 꺼낸다.. 
		String value = null;
		
		switch(col) {
		case 0: value = Integer.toString(news.getNews_idx()); break;
		case 1: value = news.getTitle(); break;
		case 2: value = news.getWriter(); break;
		case 3: value = news.getRegdate(); break;
		case 4: value = Integer.toString(news.getHit()); break;
		
		}
		return value;
	}
	
}
