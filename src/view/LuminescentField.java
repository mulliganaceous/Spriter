package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class LuminescentField extends JTextField implements Luminescent {
	private static final long serialVersionUID = 203;

	public LuminescentField(int len) {
		super(len);
		// Style
		this.setFont(LARGE_FONT);
		this.setBackground(GITD);
		this.setForeground(Color.BLACK);
		this.setBorder(BorderFactory.createLineBorder(GITD));
		this.setPreferredSize(new Dimension(len*4,32));
	}
	
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			this.setBackground(GITD);
			this.setBorder(BorderFactory.createLineBorder(GITD));
		}
		else {
			this.setText("");
			this.setBackground(Color.BLACK);
			this.setBorder(BorderFactory.createDashedBorder(GITD2));
		}
	}
}
