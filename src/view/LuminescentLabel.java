package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class LuminescentLabel extends JLabel {
	private static final long serialVersionUID = 200;
	
	public LuminescentLabel(String text) {
		super(text, CENTER);
		this.setFont(new Font("Century Gothic", Font.BOLD, 16));
		this.setForeground(Color.WHITE);
		this.setPreferredSize(new Dimension(256, 32));
	}
	
	public LuminescentLabel(String text, int alignment) {
		super(text, alignment);
		this.setFont(new Font("Century Gothic", Font.BOLD, 16));
		this.setForeground(Color.WHITE);
		this.setPreferredSize(new Dimension(256, 32));
	}
}
