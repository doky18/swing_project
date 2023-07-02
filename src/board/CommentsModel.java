package board;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

//댓글 JTable이 ㅊ마고할 데이터를 제공하는 모델 클래스 
public class CommentsModel extends AbstractTableModel{

	ArrayList <Comments> list = new ArrayList<Comments>();
	String[] columnName= {"댓글메시지","작성자","작성일"};  //각각 0,1,2 번째
	
	public int getRowCount() {
		return list.size();
	}

	public int getColumnCount() {
		return columnName.length;
	}
	
	public String getColumnName(int col) {
		return super.getColumnName(col);
	}

	public Object getValueAt(int row, int col) {
		String value = null;
		
		Comments comments=list.get(row);		//해당 층에 있는 DTO에 접근 
		switch(col) {
		case 0:value=comments.getCTitle(); break;
		case 1:value=comments.getCwriter();break;
		case 2:value=comments.getCregdate();break;
		
		
		}
		return null;
	}

}
