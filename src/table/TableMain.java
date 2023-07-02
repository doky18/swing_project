package table;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import network.util.DBManager;

public class TableMain extends JFrame{
	DBManager dbManager = DBManager.getInstance();
	JTable table;
	JScrollPane scroll;
	EmpModel model;
	EmpDAO dao = new EmpDAO();
	
	public TableMain() {
		table = new JTable(model = new EmpModel(this));
		scroll = new JScrollPane(table);
		
		add(scroll);
		
		setSize(800, 700);
		setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});
		
		getList();
	}
	
	//테이블 리스트 가져오기 
	public void getList() {
		model.list=dao.selectAll();
		table.updateUI();
	}
	
	public static void main(String[] args) {
		new TableMain();
	}
}
