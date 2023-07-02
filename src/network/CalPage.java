package network;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import network.util.DBManager;

public class CalPage extends Page {
	DBManager dbManager = DBManager.getInstance();
	DiaryDAO diaryDAO = new DiaryDAO();

	/* 센터영역 ----플로우를 적용하기 위한 센터의 최상위 컨테이너 */
	JPanel p_center;
	JPanel p_title; // 현재 연, 월 및 이전, 다음 버튼의 영역
	JPanel p_dayOfWeek; // 요일 셀(JPanel)이 올 곳
	JPanel p_dayOfMonth; // 날짜 셀이 붙여질 곳
	JButton bt_prev;
	JLabel la_title; // 현재 연, 월
	JButton bt_next;

	/* 요일 처리 */
	DayCell[] dayCells = new DayCell[7];
	String[] dayTitle = { "S", "M", "T", "W", "T", "F", "S" };

	/* 날짜 처리 */
	DateCell[][] dateCells = new DateCell[6][7];

	/* 현재 사용자가 보게 될 날짜 정보 */
	Calendar currentObj = Calendar.getInstance();

	public CalPage(AppMain appMain) {
		super(appMain);
		Color w=new Color(175, 207, 175);
		this.setBackground(w);
		
		p_center = new JPanel();
		p_title = new JPanel();
		p_dayOfWeek = new JPanel();
		p_dayOfMonth = new JPanel();
		bt_prev = new JButton("이전");
		la_title = new JLabel("2022-12");
		bt_next = new JButton("다음");

		p_center.setPreferredSize(new Dimension(750, 520));
		p_title.setPreferredSize(new Dimension(750, 45));
		p_dayOfWeek.setPreferredSize(new Dimension(750, 50));
		p_dayOfMonth.setPreferredSize(new Dimension(750, 410));
		la_title.setPreferredSize(new Dimension(150, 45));

		/* 색깔 조정 */
		p_center.setBackground(w);
		p_title.setBackground(w);
		p_dayOfWeek.setBackground(w);
		la_title.setFont(new Font("Dodum", Font.BOLD | Font.ITALIC, 25));

		p_dayOfWeek.setLayout(new GridLayout(1, 7));
		p_dayOfMonth.setLayout(new GridLayout(6, 7)); // **** 이거 안했었네

		/* 붙이기 */

		p_center.add(p_title);
		p_title.add(bt_prev);
		p_title.add(la_title);
		p_title.add(bt_next);
		p_center.add(p_dayOfWeek);
		p_center.add(p_dayOfMonth);

		add(p_center);
		
		createDayOfWeek(); // 요일 출력
		createDayOfMonth(); // 날짜 출력

		calculate(); // 날짜개산

		/* 다음 월 */
		bt_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 현재 날짜 객체 정보를 먼저 얻자~!
				int mm = currentObj.get(Calendar.MONTH);
				currentObj.set(Calendar.MONTH, mm + 1); // (월옵션, 현재월+1)
				System.out.println("당신이 보고있는 현재 월은" + (mm + 1));
				calculate();
			}
		});

		/* 이전 월 */
		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 현재 날짜 객체 정보를 먼저 얻자~!
				int mm = currentObj.get(Calendar.MONTH);
				currentObj.set(Calendar.MONTH, mm - 1); // (월옵션, 현재월+1)
				System.out.println("당신이 보고있는 현재 월은" + (mm - 1));
				calculate();
			}
		});
	}
	
	/* 요일 출력 */
	public void createDayOfWeek() {
		// 7개를 패널에 부착
		for (int i = 0; i < dayCells.length; i++) {
			dayCells[i] = new DayCell(dayTitle[i], "", 19, 40, 30); // 태어날 때 가지고 태어날 속성들
			p_dayOfWeek.add(dayCells[i]); // 화면에 부착
		}
	}

	/* 날짜 출력 */
	public void createDayOfMonth() {
		int n = 0; // 날짜랑 관련 없음.. 이건 그냥 칸 개수를 세는 용도
		for (int i = 0; i < dateCells.length; i++) { // 층수
			for (int a = 0; a < dateCells[i].length; a++) { // 호수
				dateCells[i][a] = new DateCell(this.appMain, " ", " ", 12, 40, 10);// 태어날 떄 내용 없음
				// 패널에 부착!!
				p_dayOfMonth.add(dateCells[i][a]);
			}
		}
	}

	public void calculate() {
		printTitle(); // 제목 출력
		getStartDayOfWeek();
		getLastDayOfMonth();
		printDate(); // 날짜출력
		printLog(); // 기록된 다이어리 출력
	}

	/* 제목 */
	public void printTitle() {
		int yy = currentObj.get(Calendar.YEAR);
		int mm = currentObj.get(Calendar.MONDAY);

		String str = yy + "년" + (mm + 1) + "월";

		la_title.setText(str);
	}

	/* 날짜 */
	public void printDate() {
		int n = 0; // 시작시점 지표
		int d = 0; // 날짜 출력변수

		// 모든 셀에 접근하여 알맞는 문자 출력
		for (int i = 0; i < dateCells.length; i++) {
			for (int a = 0; a < dateCells[i].length; a++) {
				DateCell cell = dateCells[i][a]; // 추출완료
				n++;

				if (n >= getStartDayOfWeek() && d < getLastDayOfMonth()) {// d<getLastDayOfMonth() <- 0부터 시작하니까 <=이 아니라
																			// <
					d++; // 꾹 참았다가, n이 시작 요일 이상일 때 부터 ++
					cell.title = Integer.toString(d); // 날짜를 출력할 것
				} else {
					cell.title = "";
				}
			}
		}
		p_dayOfMonth.repaint();
	}

	public void printLog() {
		int yy = currentObj.get(Calendar.YEAR);
		int mm = currentObj.get(Calendar.MONTH);

		List<Diary> diaryList = diaryDAO.selectAll(yy, mm); // 현재 보고있는 연도, 현재보고 있는 월
		System.out.println("등록된 다이어리 수는 " + diaryList.size());

		/* 현재 월의 모든 날짜를 대상으로 반복문 수행 */
		for (int i = 0; i < dateCells.length; i++) {
			for (int a = 0; a < dateCells[i].length; a++) {
				if (dateCells[i][a].title.equals("") == false) { // ""가 아닐때만 parseInt 할 것
					/* 날짜 숫자 추출하기 ->parseInt하면 "", "a" 이런건 안됨, "3"만 됨. 그래서 if 조건문 줌 */
					int date = Integer.parseInt(dateCells[i][a].title);

					// 불러온 데이터만큼...
					for (int x = 0; x < diaryList.size(); x++) {
						Diary obj = diaryList.get(x); // 다이어리 한 건 추출
						if (date == obj.getDd()) {
							dateCells[i][a].color = Color.GREEN;
							dateCells[i][a].content = obj.getContent();
						}
					}
				}
			}
		}
		p_dayOfMonth.repaint();
	}

	public int getStartDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		int yy = currentObj.get(Calendar.YEAR); // 년도
		int mm = currentObj.get(Calendar.MONDAY); // 월

		cal.set(yy, mm, 1); // 1일로 조작하자 왜?***
		int day = cal.get(Calendar.DAY_OF_WEEK); // 요일 추출!

		return day;
	}

	public int getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		int yy = currentObj.get(Calendar.YEAR); // 년도
		int mm = currentObj.get(Calendar.MONDAY); // 월

		cal.set(yy, mm + 1, 0); // 0일->전 달의 마지막 날
		int date = cal.get(Calendar.DATE); // 요일 추출!

		return date;
	}
}
