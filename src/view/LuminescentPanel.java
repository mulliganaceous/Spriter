package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class LuminescentPanel extends JPanel {
	private static final long serialVersionUID = 200;
	
	public LuminescentPanel() {
		super();
		this.setFont(new Font("Century Gothic", Font.BOLD, 16));
		this.setBackground(Color.BLACK);
		this.setForeground(Luminescent.GITD);
		this.setBorder(BorderFactory.createLineBorder(Luminescent.GITD,2));
	}
}
