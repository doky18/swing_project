package network;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import network.util.DBManager;
import util.ImageManager;

public class AppMain extends JFrame {
	
	JPanel p_west;
	JLabel pic;
	JLabel title;	//title 이미지
	JPanel p_drawer; // 안드로이드 서랍
	JPanel p_list; // row들이 부착될 곳(flow방식)
	JPanel p_num; // 페이지 라벨 넘버들이 setBounds로 부착될 곳

	JLabel la_ham; // 서랍장에 붙일 햄버거 메뉴(paint로 그리지 않는 이유 : 클릭하려고)

	boolean loginFlag;// false가 디폴트임(최초에 로그인 안했으므로)
	// int index =createPage(); // 내가 몇번째 버튼인지

	SideMenu[] sideMenu = new SideMenu[4]; // 사이드 메뉴

	Page[] page = new Page[10];

	// 중앙의 페이지가 붙여질 컨테이너 역할의 패널
	JPanel p_container = new JPanel();
	public static final int BOOKPAGE = 0;
	public static final int JOURNALPAGE = 1;
	public static final int CALPAGE = 2;
	public static final int ACCOUNTPAGE = 3;
	public static final int LOGINPAGE = 4;
	public static final int JOINPAGE = 5;
	public static final int JONRNALREGIST = 6;
	public static final int JONRNALDETAIL = 7;
	public static final int BOOKREGIST = 8;
	public static final int BOOKDETAIL = 9;

	DBManager dbManager = DBManager.getInstance();
	JBoardDAO jBoardDAO = new JBoardDAO();
	BookDAO bookDAO = new BookDAO();

	LogMember logMember;

	// 애니메이션 구현을 위한 루프 쓰레드
	Thread loopThread;
	double a = 0.05;
	double targetX;
	double x = -160;
	boolean fold = false;
	
	ImageManager imageManager = new ImageManager();
	
	public AppMain() {
		super("Read-in");
		
		Color b=new Color(255, 247, 230);
		p_west = new JPanel();
		
		String path = "res/lib.png";
		pic = new JLabel(imageManager.getIcon(path, 230, 600));
		
		String path2= "res/title22.png";
		title = new JLabel(imageManager.getIcon(path2, 170, 130));
		
		p_drawer = new JPanel();
		p_list = new JPanel();
		p_num = new JPanel();

		try {
			URL url = new URL("https://cdn1.iconfinder.com/data/icons/education-filled-outline-8/64/Education-Filled_17-256.png");
			la_ham = new JLabel(new ImageIcon(url));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		loopThread = new Thread() {
			public void run() {
				while (true) {
					tick();
					render();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		};
		loopThread.start();

		// Style
		p_west.setPreferredSize(new Dimension(200, 600));
		p_west.setBackground(Color.BLACK);
		p_drawer.setPreferredSize(new Dimension(200, 600));
		p_drawer.setBackground(b);

		p_container.setBackground(Color.WHITE);

		p_west.setLayout(null);

		p_drawer.setBounds((int) x, 0, 200, 600);
		
		pic.setBounds(0, 0, 230, 600);

//      부착
		p_drawer.setLayout(null);
		la_ham.setBounds(170, 0, 50, 60);
		p_drawer.add(la_ham);
		title.setBounds(0, 50, 170, 130);
		p_drawer.add(title);
		p_west.add(pic);
		add(p_drawer);
		add(p_west, BorderLayout.WEST);
		add(p_container);

		createMenu(); //
		createPage();
		checkLogin(BOOKPAGE); // --------------------------*****************
		showHide(LOGINPAGE);

		setSize(1000, 600);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});

//      햄버거 메뉴와 리스너 연결
		la_ham.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (fold) {
					targetX = 0;
				} else {
					targetX = -170;
				}
				fold = !fold;
			}
		});
	}

	public void createPage() {
		page[0] = new BookPage(this);
		page[1] = new JournalPage(this);
		page[2] = new CalPage(this);
		page[3] = new AccountPage(this);
		page[4] = new LoginPage(this);
		page[5] = new JoinPage(this);
		page[6] = new JournalRegist(this);
		page[7] = new JournalDetail(this);
		page[8] = new BookRegist(this);
		page[9] = new BookDetail(this);

		// 부착
		for (int i = 0; i < page.length; i++) {
			p_container.add(page[i]);
		}
	}

	public void createMenu() {
		sideMenu[0] = new SideMenu("Library", this, BOOKPAGE);
		sideMenu[1] = new SideMenu("My Log", this, JOURNALPAGE);
		sideMenu[2] = new SideMenu("Calendar", this, CALPAGE);
		sideMenu[3] = new SideMenu("Account", this, ACCOUNTPAGE);

		// 서랍장에 메뉴 부착
		for (int i = 0; i < sideMenu.length; i++) {
			p_drawer.add(sideMenu[i]);
			sideMenu[i].setBounds(20, 210 + 55 * i, 130, 45);
		}
		p_drawer.updateUI();
	}

	// 페이지 보여주기 전에 로그인 완료 여부를 확인하기
	public boolean checkLogin(int n) {
		if (logMember != null) { // login
			return false;
		}
		return true;
	}

	public void showHide(int n) {
		// 멤버에 대한 정보가 null 이라면 로그인과 회원가입만!
		if ((n != LOGINPAGE) && (n != JOINPAGE) && (logMember == null)) {
			JOptionPane.showMessageDialog(this, "로그인을 해주세요");
			return;
		}
		for (int i = 0; i < page.length; i++) {
			if (i == n) {
				page[i].setVisible(true);
			} else {
				page[i].setVisible(false);
			}
		}
	}
	

	public void tick() {// 물리적 변화
		x = x + a * (targetX - x);
	}

	public void render() {// 그래픽처리
		p_drawer.setBounds((int) x, 0, 200, 600);
		p_drawer.updateUI();
	}

	public static void main(String[] args) {
		new AppMain();
	}
}