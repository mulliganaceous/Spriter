package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import sprite.IncorrectSizeException;
import sprite.Spec;
import view.*;

/**A Dialog which specifies you to create a custom-size sprite.
 * @author !MULLIGANACEOUS!
 */
public class OtherSpriteDialog extends JDialog {
	private static final long serialVersionUID = 153;
	private ControlsPanel controls;
	private LuminescentField hField, wField;
	private LuminescentButton wide, tall, giant, custom;
	
	public OtherSpriteDialog(ControlsPanel controls) {
		this.controls = controls;
		this.setTitle("New custom sprite");
		this.setBackground(Color.BLACK);
		this.setLayout(new GridLayout(2,1));
		LuminescentPanel presetPanel = new LuminescentPanel();
		LuminescentPanel customPanel = new LuminescentPanel();
		
		this.wide = new LuminescentButton("Wide");
		this.wide.setPreferredSize(new Dimension(64,32));
		this.tall = new LuminescentButton("Tall");
		this.tall.setPreferredSize(new Dimension(64,32));
		this.giant = new LuminescentButton("Giant");
		this.giant.setPreferredSize(new Dimension(64,32));
		this.wide.addActionListener(new NewSpriteListener(Spec.S, Spec.L));
		this.tall.addActionListener(new NewSpriteListener(Spec.L, Spec.S));
		this.giant.addActionListener(new NewSpriteListener(Spec.G, Spec.G));
		
		presetPanel.add(wide);
		presetPanel.add(tall);
		presetPanel.add(giant);
		
		this.hField = new LuminescentField(2);
		LuminescentLabel times = new LuminescentLabel("×");
		times.setPreferredSize(null);
		this.wField = new LuminescentField(2);
		this.custom = new LuminescentButton("Custom Size");
		this.custom.addActionListener(new NewCustomSpriteListener());
		
		customPanel.add(hField);
		customPanel.add(times);
		customPanel.add(wField);
		customPanel.add(custom);
		
		this.getContentPane().add(presetPanel);
		this.getContentPane().add(customPanel);
		this.setResizable(false);
		this.pack();
		
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	private void newCustomSprite() throws IncorrectSizeException  {
		int h = -1;
		int w = -1;
		try {
			h = Integer.parseInt(this.hField.getText());
			w = Integer.parseInt(this.wField.getText());
		} catch (NumberFormatException exc) {
			throw new NumberFormatException();
		}
		if (h < 1 || h > 32 || w < 1 || w > 32)
			throw new IncorrectSizeException();
		// New sprite generates here
		OtherSpriteDialog.this.controls.newSprite(h,w);
	}
	
	/**Action listener to generate new sprite.
	 * @author !MULLIGANACEOUS!
	 */
	private class NewSpriteListener implements ActionListener {
		private int h, w;
		
		public NewSpriteListener(int h, int w) {
			this.h = h;
			this.w = w;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			OtherSpriteDialog.this.controls.newSprite(h,w);
			OtherSpriteDialog.this.setVisible(false);
		}
	}
	
	private class NewCustomSpriteListener implements ActionListener {
		public NewCustomSpriteListener() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				newCustomSprite();
			} catch (IncorrectSizeException exc) {
				System.out.println("Size must be between 1 to 32");
			} catch (NumberFormatException exc) {
				System.out.println("Input mismatch");
			} finally {
				OtherSpriteDialog.this.setVisible(false);
			}
		}
	}
}
