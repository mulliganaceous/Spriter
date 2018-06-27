package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.SpriteModel;
import sprite.Sprite;
import view.*;

public class ControlsPanel extends JPanel {
	private static final long serialVersionUID = 101;
	// Link to model and associated panel
	private SpriteModel model;
	private SpritePanel panel;
	private SpritePanel minipanel;
	// Palette buttons
	private JPanel palettePanel;
	private ColorButton[] paletteButton;
	private ColorField rField, gField, bField;
	private LuminescentButton changeColorButton;
	// New sprite button
	private JButton newSpriteS, newSpriteL, newSpriteOth;
	// Load and save
	private LuminescentField fileField;
	private LuminescentButton loadButton, loadPathButton, saveButton, gifButton;
	private LuminescentLabel fileLabel;
	
	// Current palette chosen
	private int selectedValue;
	
	public ControlsPanel(SpriteModel model) {
		this.model = model;
		this.setPreferredSize(new Dimension(256, 512));
		this.setLocation(360, 0);
		this.setBackground(Color.BLACK);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Add controls
		// Palettes
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(256, 192));
		this.selectedValue = 1;
		
		topPanel.add(new LuminescentLabel("Palette selection"));
		this.palettePanel = new JPanel();
		this.paletteButton = new ColorButton[16];
		this.palettePanel.setLayout(new GridLayout(2, 8));
		for (int k = 0; k <= 0xF; k++) {
			this.paletteButton[k] = new ColorButton(k, this.model);
			this.paletteButton[k].setPreferredSize(new Dimension(30, 30));
			this.paletteButton[k].addActionListener(new ColorButtonListener(k));
			
			this.palettePanel.add(this.paletteButton[k]);
		}
		this.paletteButton[0].fixPalette();
		this.paletteButton[15].fixPalette();
		this.paletteButton[15].setForeground(view.Luminescent.GITD);

		topPanel.add(palettePanel);
		
		// Change Color
		JPanel changeColorPanel = new JPanel();
		changeColorPanel.setBackground(null);
		this.rField = new ColorField();
		this.gField = new ColorField();
		this.bField = new ColorField();
		this.changeColorButton = new LuminescentButton("Modify Color");
		this.changeColorButton.setPreferredSize(new Dimension(112, 32));
		this.changeColorButton.addActionListener(new ColorChangeListener());
		changeColorPanel.add(this.rField);
		changeColorPanel.add(this.gField);
		changeColorPanel.add(this.bField);
		changeColorPanel.add(this.changeColorButton);
		topPanel.add(changeColorPanel);
		
		topPanel.setBackground(Color.BLACK);
		topPanel.setBorder(BorderFactory.createLineBorder(Luminescent.GITD,2));
		this.add(topPanel);
		
		// File Panel
		JPanel newFilePanel = new JPanel();
		newFilePanel.setPreferredSize(new Dimension(256, 272));
		
		newFilePanel.add(new LuminescentLabel("New Sprite"));
		this.newSpriteS = new LuminescentButton("8×8");
		this.newSpriteS.setPreferredSize(new Dimension(120, 32));
		this.newSpriteS.addActionListener(new NewSpriteListener(8, 8));
		this.newSpriteL = new LuminescentButton("16×16");
		this.newSpriteL.setPreferredSize(new Dimension(120, 32));
		this.newSpriteL.addActionListener(new NewSpriteListener(16, 16));
		this.newSpriteOth = new LuminescentButton("Others");
		this.newSpriteOth.setPreferredSize(new Dimension(240, 32));
		newFilePanel.add(newSpriteS);
		newFilePanel.add(newSpriteL);
		newFilePanel.add(newSpriteOth);
		
		newFilePanel.add(new LuminescentLabel("Load Sprite"));
		this.fileField = new LuminescentField(10);
		this.loadButton = new LuminescentButton("Load");
		this.loadButton.setPreferredSize(new Dimension(64, 32));
		this.loadPathButton = new LuminescentButton("Load Path...");
		this.loadPathButton.setPreferredSize(new Dimension(240, 32));
		this.saveButton = new LuminescentButton("Save Sprite");
		this.saveButton.setPreferredSize(new Dimension(120, 32));
		this.gifButton = new LuminescentButton("Save GIF");
		this.gifButton.setPreferredSize(new Dimension(120, 32));
		newFilePanel.add(fileField);
		newFilePanel.add(loadButton);
		newFilePanel.add(loadPathButton);
		newFilePanel.add(saveButton);
		newFilePanel.add(gifButton);
		
		newFilePanel.setBackground(Color.BLACK);
		newFilePanel.setBorder(BorderFactory.createLineBorder(Luminescent.GITD,2));
		this.add(newFilePanel);
		
		// Bottom Pannel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(256, 48));
		
		this.minipanel = new SpritePanel(this.model, 2);
		this.minipanel.setDrawBorders(false);
		bottomPanel.add(minipanel);
		
		this.fileLabel = new LuminescentLabel("");
		fileLabel.setFont(Luminescent.TINY_FONT);
		this.minipanel.setSize(255, 16);
		this.minipanel.setBackground(Color.BLUE);
		fileLabel.setText("File: " + this.model.getFilename());
		bottomPanel.add(fileLabel);
		
		bottomPanel.setBackground(Color.BLACK);
		bottomPanel.setBorder(BorderFactory.createLineBorder(Luminescent.GITD,2));
		this.add(bottomPanel);
	}
	
	public int getSelectedValue() {
		return this.selectedValue;
	}
	
	public void setAssociatedSpritePanel(SpritePanel panel) {
		this.panel = panel;
		this.panel.setAssociatedControls(this);
	}
	
	public SpritePanel getAssociatedSpritePanel() {
		return this.panel;
	}
	
	/**Change the color of one of the colors of the sprite palette, and
	 * consequently update the palette selection screen.
	 * @param val The color value index to be modified
	 * @param color The new color
	 */
	private void changeColor(int val, Color color) {;
		Sprite sprite = ControlsPanel.this.model.getSprite();
		int selectedValue = ControlsPanel.this.selectedValue;
		
		sprite.setPaletteColor(selectedValue, color);
		ControlsPanel.this.update();
		System.out.printf("Color %d changing to (%d, %d, %d)\n", selectedValue, 
				color.getRed(),color.getGreen(),color.getBlue());
	}
	
	/**Edit a new sprite
	 * @param h The height of the sprite
	 * @param w The width of the sprite
	 */
	public void newSprite(int h, int w) {
		this.model.removeFilename();
		this.model.setSprite(new Sprite(h, w));
		this.update();
		System.out.printf("New %d×%d sprite created.\n", h, w);
	}
	
	/**Update all the components of the ControlsPanel so the visual
	 * states are synchronized.
	 */
	public void update() {
		for (int k = 1; k < 0xF; k++) {
			this.paletteButton[k].updateColor();
		}
		this.minipanel.update();
	}
	
	/**
	 * @author !MULLIGANACEOUS!
	 */
	private class ColorButtonListener implements ActionListener {
		int val;
		public ColorButtonListener(int val) {
			this.val = val;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ControlsPanel.this.selectedValue = this.val;
		}
	}
	
	/**
	 * @author !MULLIGANACEOUS!
	 */
	private class ColorChangeListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				int r = ControlsPanel.this.rField.getHexValue();
				int g = ControlsPanel.this.gField.getHexValue();
				int b = ControlsPanel.this.bField.getHexValue();
				int selectedValue = ControlsPanel.this.selectedValue;
				Color color = new Color(r,g,b);
				ControlsPanel.this.changeColor(selectedValue, color);
			} catch (NumberFormatException exc) {
				System.out.println("Not a hex value");
				return;
			} catch (IllegalArgumentException exc) {
				System.out.println("Color value out of range");
			}
		}
	}
	
	/**
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
			ControlsPanel.this.newSprite(h,w);
		}
	}
}
