package network;

import java.awt.Font;

import javax.swing.JLabel;

public class BlockNum extends JLabel {
	   String n;
	   JournalPage journalPage;

	   public BlockNum(String n, JournalPage journalPage) {
	      super(n);
	      this.n = n;
	      this.journalPage = journalPage;

	      this.setFont(new Font("Verdana", Font.BOLD, 30));

	   }
	}