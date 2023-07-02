package network;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class BookModel extends AbstractTableModel{

	List <Book> bookList = new ArrayList<Book>();
	String[] columnName = {"no", "제목", "작가", "장르", "출판사", "비고","등록일"};
	
	public int getRowCount() { //층수 
		return bookList.size();
	}

	public int getColumnCount() { //호수 
		return 7;
	}
	
	public String getColumnName(int col) {
		return columnName[col];		//여기에 배열 대입 
	}

	public Object getValueAt(int row, int col) {
		Book book =(Book)bookList.get(row); //dto 하나꺼내기 위해서 
		String value = null;
		
		switch(col) {
		case 0: value = Integer.toString(book.getBook_idx());break;
		case 1: value = book.getTitle();break;
		case 2: value = book.getAuthor();break;
		case 3: value = book.getGenre();break;
		case 4: value = book.getPublisher();break;
		case 5: value = book.getNote();break;
		case 6: value = book.getRegdate();break;
		}
		
		return value;
	}

}
