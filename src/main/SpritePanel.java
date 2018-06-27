package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import model.SpriteModel;
import sprite.Sprite;

public class SpritePanel extends JPanel implements MouseInputListener {
	private static final long serialVersionUID = 100;
	private int dim = 512;
	private int tile = 512/16;
	private SpriteModel model;
	private ControlsPanel currentControl;
	
	private boolean drawBorders;
	
	public SpritePanel(SpriteModel model) {
		this.tile = 32;
		this.dim = tile*16;
		this.initialize(model);
	}
	
	public SpritePanel(SpriteModel model, int tilesize) {
		this.tile = tilesize;
		this.dim = tile*16;
		this.initialize(model);
	}
	
	private void initialize(SpriteModel model) {
		this.model = model;
		this.setPreferredSize(new Dimension(dim, dim));
		this.setBackground(Color.BLACK);
		this.drawBorders = true;
	}
	
	public SpriteModel getModel() {
		return this.model;
	}
	
	public void setAssociatedControls(ControlsPanel control) {
		this.currentControl = control;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void setDrawBorders(boolean b) {
		this.drawBorders = b;
	}
	
	/* Graphics context */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Sprite sprite = this.model.getSprite();
		for (int y = 0; y < sprite.getHeight(); y++) {
			for (int x = 0; x < sprite.getWidth(); x++) {
				// Draw relevant color
				if (sprite.getPixelValue(y, x) == 0xF) {
					g.setColor(Color.WHITE);
					g.fillOval(tile*x, tile*y, tile, tile);
				}
				else if (sprite.getPixelValue(y, x) > 0) {
					g.setColor(sprite.getPixelColor(y, x));
					g.fillRect(tile*x, tile*y, tile, tile);
				}
				else {
					g.setColor(Color.BLACK);
					g.fillRect(tile*x, tile*y, tile, tile);
					g.setColor(Color.DARK_GRAY);
				}
				
				// Draw borders
				if (this.drawBorders) {
					g.setColor(Color.DARK_GRAY);
					g.drawRect(tile*x, tile*y, tile, tile);
					// Indicate emptiness
					if (sprite.getPixelValue(y, x) == 0) {
						int x2 = (int) (tile*(x + 0.5));
						int y2 = (int) (tile*(y + 0.5));
						g.drawLine(x2 - tile/8, y2 - tile/8, x2 + tile/8, y2 + tile/8);
					}
				}
			}
		}
		this.repaint();
	}
	
	/* MouseListener methods */
	private void mouseAction(MouseEvent m) {
		Sprite sprite = this.model.getSprite();
		int xw = m.getX()/tile;
		int yh = m.getY()/tile;
		int val = this.currentControl.getSelectedValue();
		if (xw < sprite.getWidth() && yh < sprite.getHeight()
				&& xw >= 0 && yh >= 0) {
			sprite.setPixelValue(yh, xw, val);
		}
		this.update();	
	}
	
	@Override
	public void mouseEntered(MouseEvent m) {}
	@Override
	public void mouseExited(MouseEvent m) {}
	@Override
	public void mouseClicked(MouseEvent m) {}
	@Override
	public void mouseMoved(MouseEvent m) {}
	@Override
	public void mouseReleased(MouseEvent m) {}

	@Override
	public void mousePressed(MouseEvent m) {
		this.mouseAction(m);
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		this.mouseAction(m);
	}
	
	public void update() {
		this.repaint();
	}
}
