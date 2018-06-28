package main;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.*;
import model.*;

/**The main GUI of this program.
 * @author !MULLIGANACEOUS!
 */
public class MainGUI extends JFrame {
	private static final long serialVersionUID = 0;
	private static MainGUI inst;
	private SpriteModel model;
	private SpritePanel panel;
	private ControlsPanel control;
	
	private MainGUI() {
		super();
		this.setLayout(new FlowLayout());
		// Set up model and components
		this.model = SpriteModel.getInstance();
		this.panel = new SpritePanel(model);
		this.getContentPane().add(panel);
		this.control = new ControlsPanel(model);
		this.control.setAssociatedSpritePanel(this.panel);
		this.getContentPane().add(control);
		this.model.setView(panel);
		this.model.setControls(control);
		// Set up dimensions
		this.setTitle("Spriter");
		this.setSize(512, 768);
		this.setBackground(Color.BLACK);
		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static MainGUI getInstance() {
		if (inst == null)
			inst = new MainGUI();
		return inst;
	}
	
	public static void main(String[] args) {
		MainGUI m = MainGUI.getInstance();
		m.setVisible(true);
	}
}
