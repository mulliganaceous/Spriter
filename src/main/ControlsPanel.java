package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import io.Loadfile;
import io.Savefile;
import model.SpriteModel;
import sprite.Spec;
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
	private OtherSpriteDialog dialog;
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
		JPanel topPanel = new LuminescentPanel();
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
		this.updateRGBFields();
		topPanel.add(changeColorPanel);
		
		this.add(topPanel);
		
		// File Panel
		JPanel newFilePanel = new LuminescentPanel();
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
		this.newSpriteOth.addActionListener(new OtherSpriteListener());
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
		this.loadButton.addActionListener(new LoadFileListener());
		this.saveButton.addActionListener(new SaveFileListener());
		newFilePanel.add(fileField);
		newFilePanel.add(loadButton);
		newFilePanel.add(loadPathButton);
		newFilePanel.add(saveButton);
		newFilePanel.add(gifButton);
		
		this.add(newFilePanel);
		
		// Bottom Pannel
		JPanel bottomPanel = new LuminescentPanel();
		bottomPanel.setPreferredSize(new Dimension(256, 48));
		bottomPanel.setLayout(null);
		
		this.minipanel = new SpritePanel(this.model, 2);
		this.minipanel.setDrawBorders(false);
		this.minipanel.setBounds(2, 2, 32, 32);
		bottomPanel.add(minipanel);
		
		this.fileLabel = new LuminescentLabel("", SwingConstants.LEFT);
		this.fileLabel.setBorder(BorderFactory.createLineBorder(Luminescent.GITD2));
		this.fileLabel.setFont(Luminescent.TINY_FONT);
		this.fileLabel.setSize(240, 16);
		this.fileLabel.setText("File: " + this.model.getFilename());
		this.fileLabel.setBounds(2, 34, 252, 12);
		bottomPanel.add(fileLabel);
		
		this.add(bottomPanel);
		
		// Dialogs
		this.dialog = new OtherSpriteDialog(ControlsPanel.this);
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
	 * @param val The color value index to be modified (colors 1 to E only)
	 * @param color The new color
	 */
	private void changeColor(Color color) {;
		Sprite sprite = ControlsPanel.this.model.getSprite();
		if (this.selectedValue > 0 && this.selectedValue < 0xF)
			sprite.setPaletteColor(this.selectedValue, color);
		else
			System.out.printf("Color %d cannot be changed.", this.selectedValue);
		this.model.update();
	}
	
	/**Edit a new sprite
	 * @param h The height of the sprite
	 * @param w The width of the sprite
	 */
	public void newSprite(int h, int w) {
		this.model.removeFilename();
		this.model.setSprite(new Sprite(h, w));
		this.dialog.setVisible(false);
		this.model.update();
		System.gc(); // Garbage collect here
		System.out.printf("New %d×%d sprite created.\n", h, w);
	}
	
	/**Update all the components of the ControlsPanel so the visual
	 * states are synchronized.
	 */
	public void update() {
		this.minipanel.update();
		this.updateRGBFields();
		this.fileLabel.setText("File: " + this.model.getFilename());
	}
	
	/**Changes the color RGB fields based on color of the palettes.
	 */
	public void updateRGBFields() {
		for (int k = 0; k <= 0xF; k++) {
			if (k == this.selectedValue)
				this.paletteButton[k].setSelected(true);
			else
				this.paletteButton[k].setSelected(false);
			this.paletteButton[k].updateColor();
		}
		Color curBackground = this.paletteButton[this.selectedValue].getColor();
		if (curBackground != null) {
			this.rField.setEnabled(true);
			this.gField.setEnabled(true);
			this.bField.setEnabled(true);
			this.rField.setText(Integer.toHexString(curBackground.getRed()));
			this.gField.setText(Integer.toHexString(curBackground.getGreen()));
			this.bField.setText(Integer.toHexString(curBackground.getBlue()));
		}
		else {
			this.rField.setEnabled(false);
			this.gField.setEnabled(false);
			this.bField.setEnabled(false);
		}
	}
	
	/**Action listener to select color
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
			ControlsPanel.this.updateRGBFields();
		}
	}
	
	/**Action listener to change a color on the palette
	 * @author !MULLIGANACEOUS!
	 */
	private class ColorChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				int r = ControlsPanel.this.rField.getHexValue();
				int g = ControlsPanel.this.gField.getHexValue();
				int b = ControlsPanel.this.bField.getHexValue();
				Color color = new Color(r,g,b);
				ControlsPanel.this.changeColor(color);
			} catch (NumberFormatException exc) {
				System.out.println("Not a hex value");
			} catch (IllegalArgumentException exc) {
				System.out.println("Color value out of range");
			}
		}
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
			ControlsPanel.this.newSprite(h,w);
		}
	}
	
	/**Action listener to generate new custom sprite.
	 * @author !MULLIGANACEOUS!
	 */
	private class OtherSpriteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			dialog.setLocation(ControlsPanel.this.getLocationOnScreen());
			dialog.setVisible(true);
		}
	}

	/**Action listener to load file
	 * @author !MULLIGANACEOUS!
	 */
	public class LoadFileListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String filename = ControlsPanel.this.fileField.getText();
			String curPath = ControlsPanel.this.model.getFilename();
			Loadfile ldf;
			if (!filename.equals("")) {
				ldf = new Loadfile(Spec.FILE_FOLDER + filename);
				ldf.load(ControlsPanel.this.model);
			}
			else if (curPath != null) {
				ldf = new Loadfile(Spec.FILE_FOLDER + curPath);
				ldf.load(ControlsPanel.this.model);
			}
			else
				System.err.println("Cannot load nameless sprite.");
		}
	}

	
	/**Action listener to save file
	 * @author !MULLIGANACEOUS!
	 */
	private class SaveFileListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Savefile spr = new Savefile(ControlsPanel.this.model.getSprite());
			String path = ControlsPanel.this.fileField.getText();
			String curPath = ControlsPanel.this.model.getFilename();
			if (!path.equals("")) {
				spr.save(ControlsPanel.this.model, Spec.FILE_FOLDER + path);
			}
			else if (curPath != null) {
				spr.save(ControlsPanel.this.model, Spec.FILE_FOLDER + curPath);
			}
			else
				System.err.println("Cannot save nameless sprite.");
			ControlsPanel.this.model.update();
		}
	}
}
