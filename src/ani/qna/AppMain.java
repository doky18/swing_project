package ani.qna;

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
import javax.swing.JPanel;

import network.util.DBManager;

public class AppMain extends JFrame {
	JPanel p_west;
	JPanel p_drawer; // 안드로이드 서랍
	JPanel p_list; // row들이 부착될 곳(flow방식)
	JPanel p_num; // 페이지 라벨 넘버들이 setBounds로 부착될 곳

	JLabel la_ham; // 서랍장에 붙일 햄버거 메뉴(paint로 그리지 않는 이유 : 클릭하려고)

	// 사이드 메뉴
	SideMenu[] sideMenu = new SideMenu[4];

	Page[] page = new Page[6];

	// 중앙의 페이지가 붙여질 컨테이너 역할의 패널
	JPanel p_container = new JPanel();
	public static final int PRODUCTPAGE = 0;
	public static final int LOGPAGE = 1;
	public static final int ACCOUNTPAGE = 2;
	public static final int QNAPAGE = 3;
	public static final int QNAREGIST =4;
	public static final int QNADETAIL=5;

	DBManager dbManager = DBManager.getInstance();
	ReBoardDAO reboardDAO = new ReBoardDAO();

	// 애니메이션 구현을 위한 루프 쓰레드
	Thread loopThread;
	double a = 0.05;
	double targetX;
	double x = -160;
	boolean fold = true;

	public AppMain() {
		p_west = new JPanel();
		p_drawer = new JPanel();
		p_list = new JPanel();
		p_num = new JPanel();

		try {
			URL url = new URL("https://cdn4.iconfinder.com/data/icons/wirecons-free-vector-icons/32/menu-alt-64.png");
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
		p_west.setPreferredSize(new Dimension(200, 700));
		p_west.setBackground(Color.BLACK);
		p_drawer.setPreferredSize(new Dimension(200, 700));
		p_drawer.setBackground(Color.YELLOW);

		p_container.setBackground(Color.MAGENTA);

		p_west.setLayout(null);// 위치를 자유롭게 설정하기 위해 배치관리자를 사용하지 않는다

		p_drawer.setBounds((int) x, 0, 200, 700);

//      부착
		p_drawer.setLayout(null);
		la_ham.setBounds(160, 0, 40, 40);
		p_drawer.add(la_ham);
		add(p_drawer);
		add(p_west, BorderLayout.WEST);
		add(p_container);

		createMenu(); //
		createPage();
		showHide(QNAPAGE);

		setSize(1000, 700);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
			}
		});
		
//      햄버거 메뉴와 리스너 연결
		la_ham.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (fold) {
					targetX = 0;
				} else {
					targetX = -160;
				}
				fold = !fold;
			}
		});
	}

	public void createPage() {
		page[0] = new ProductPage(this);
		page[1] = new LogPage(this);
		page[2] = new AccountPage(this);
		page[3] = new QnAPage(this);
		page[4] = new QnARegist(this);
		page[5] = new QnADetail(this);

		// 부착
		for (int i = 0; i < page.length; i++) {
			p_container.add(page[i]);
		}
	}

	public void createMenu() {
		sideMenu[0] = new SideMenu("책장", this, PRODUCTPAGE);
		sideMenu[1] = new SideMenu("내 기록", this, LOGPAGE);
		sideMenu[2] = new SideMenu("달력", this, ACCOUNTPAGE);
		sideMenu[3] = new SideMenu("마이페이지", this, QNAPAGE);

		// 서랍장에 메뉴 부착
		for (int i = 0; i < sideMenu.length; i++) {
			p_drawer.add(sideMenu[i]);
			sideMenu[i].setBounds(20, 150 + 55 * i, 130, 45);
		}
		p_drawer.updateUI();
	}

	public void showHide(int n) {
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
		p_drawer.setBounds((int) x, 0, 200, 700);
		p_drawer.updateUI();
	}

	public static void main(String[] args) {
		new AppMain();
	}
}