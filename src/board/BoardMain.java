package board;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import network.util.DBManager;

public class BoardMain extends JFrame {
	
	JPanel content; 	//모든 페이지가 flowlayout으로 붙을 수 있도록, 컨테이너 정의 
	
	
	//글 목록 페이지, 글 쓰기 페이지, 상세보기 페이지
	Page[] pages= new Page[3];	//프로그램에 사용될 페이지들을 담을 배열
	
	public static final int LISTPAGE = 0;
	public static final int REGISTPAGE = 1;
	public static final int DETAILPAGE = 2;
	
	//싱글턴 패턴으로 dbM 인스터스가 생성되고, 생성자에서 Connection  한개를 올리는 코드에 의해 DB는 컴넥션을 보유하게됨
	//추후 여러 DAO들이 DBM의 인스턴스 생성을 시도해도 디비는 싱글턴이므로 오직 1개의 인스턴스만 생성..
	DBManager dbManager=DBManager.getInstance();
	NewsDAO newsDAO = new NewsDAO();
	CommentsDAO commentsDAO = new CommentsDAO();
	
	/*------------------------------------
	 			생성자 정의 
	------------------------------------*/
	public BoardMain() {
		content = new JPanel();
		
		//페이지 생성하기
		pages[0] = new ListPage(this);		//글 목록 
		pages[1] = new RegistPage(this);	//글 등록 
		pages[2] = new DetailPage(this);	//글 보기 
		
		add(content);
		
		//페이지 부착
		for(int i=0;i<pages.length; i++) {
			content.add(pages[i]);	//페이지는 컨텐트에 부착 
		}
		
		setSize(900, 700);
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		showHide(LISTPAGE);
		
		//윈도우창과 리스너 연결
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//1) 데이터베이스 닫기
				dbManager.release(dbManager.getConnection());
				
				//2) 시스템 종료 
				System.exit(0);
			}
		});
	}

	/*------------------------------------
				메서드 정의 
	------------------------------------*/
	public void showHide(int n) {
		for(int i=0; i<pages.length;i++) {
			if(n==i) {
				pages[i].setVisible(true);
			} else {
				pages[i].setVisible(false);
			}
		}
	}

	/*------------------------------------
			실행부 정의 
	------------------------------------*/
	public static void main(String[] args) {
		new BoardMain();
	}
}
