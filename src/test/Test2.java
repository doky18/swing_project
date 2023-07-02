package test;

import java.awt.*;

public class Test2 extends Frame{
	Label la_title;
    Panel p_center;
    Panel p_south;
    Label la_id;
    Label la_pass;
    TextField t_id;
    TextField t_pass;
    Button bt_login;
    Button bt_regist;

    public Test2(){
        la_title=new Label("LOGIN");
        p_center=new Panel();
        p_south=new Panel();
        la_id=new Label("ID");
        la_pass=new Label("Pass");
        t_id=new TextField();
        t_pass=new TextField();
        bt_login=new Button("로그인");
        bt_regist=new Button("가입");

        //스타일 적용
        p_center.setBackground(Color.GREEN); //센터 패널에 배경색

        //조립
        //아이디,패스워드 등을 입력폼들을 p_center패널에 부착
        p_center.add(la_id);
        p_center.add(t_id);
        p_center.add(la_pass);
        p_center.add(t_pass);

        //플로우 레이아웃에서 공간이 부족하면 밑으로 밀려나는 현상을 이용하여 컴포넌트를 정렬해보자
        la_id.setPreferredSize(new Dimension(130,25));
        t_id.setPreferredSize(new Dimension(130,25));

        la_pass.setPreferredSize(new Dimension(130,25));
        t_pass.setPreferredSize(new Dimension(130,25));


        add(p_center,BorderLayout.CENTER);
        p_south.add(bt_login);//남쪽패널에 버튼 부착
        p_south.add(bt_regist);
        add(p_south,BorderLayout.SOUTH);//p_south프레임에 부착

        //윈도우 보여주기
        setSize(320,150);
        setVisible(true);
    }

	//객체가 가지고 있는 속성이나 매서드가 스태틱 영역에 올라갈 경우, 그 애만 쓰고 싶은 경우엔 new 쓸 필요 없음
	//왜냐면 인스턴스가 필요없기 때문에
    public static void main(String[] args) {  //얘도 call by ref.
        new Test2();
    }
}