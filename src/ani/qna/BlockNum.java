package ani.qna;

import java.awt.Font;
import javax.swing.JLabel;

// 페이징 처리시 한 페이지를 표현하는 라벨(클릭 가능하게+해당 페이지 번호 보유)

public class BlockNum extends JLabel {
   String n;
   QnAPage qnaPage;

   public BlockNum(String n, QnAPage qnaPage) {
      super(n);
      this.n = n;
      this.qnaPage = qnaPage;

      this.setFont(new Font("Verdana", Font.BOLD, 30));

   }
}