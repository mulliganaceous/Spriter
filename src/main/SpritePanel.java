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
	private int dim;
	private int tile;
	private int drawTile;
	private SpriteModel model;
	private ControlsPanel currentControl;
	
	private boolean drawBorders;
	
	public SpritePanel(SpriteModel model) {
		this.tile = 32;
		this.dim = tile*16;
		this.initialize(model);
		this.drawTile = adjustedTileSize(model);
	}
	
	public SpritePanel(SpriteModel model, int tilesize) {
		this.tile = tilesize;
		this.dim = tile*16;
		this.initialize(model);
		this.drawTile = adjustedTileSize(model);
	}
	
	private void initialize(SpriteModel model) {
		this.model = model;
		this.setPreferredSize(new Dimension(dim, dim));
		this.setBackground(Color.BLACK);
		this.drawBorders = true;
	}
	
	private int adjustedTileSize(SpriteModel model) {
		// Adjust tile size for large sprites
		Sprite sprite = this.model.getSprite();
		int maxdim = sprite.getHeight();
		if (sprite.getWidth() > maxdim)
			maxdim = sprite.getWidth();
		if (maxdim > 16)
			return dim / 32;
		else
			return tile;
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
					g.fillOval(drawTile*x, drawTile*y, drawTile, drawTile);
				}
				else if (sprite.getPixelValue(y, x) > 0) {
					g.setColor(sprite.getPixelColor(y, x));
					g.fillRect(drawTile*x, drawTile*y, drawTile, drawTile);
				}
				else {
					g.setColor(Color.BLACK);
					g.fillRect(drawTile*x, drawTile*y, drawTile, drawTile);
					g.setColor(Color.DARK_GRAY);
				}
				
				// Draw borders
				if (this.drawBorders) {
					g.setColor(Color.DARK_GRAY);
					g.drawRect(drawTile*x, drawTile*y, drawTile, drawTile);
					// Indicate emptiness
					if (sprite.getPixelValue(y, x) == 0) {
						int x2 = (int) (drawTile*(x + 0.5));
						int y2 = (int) (drawTile*(y + 0.5));
						g.drawLine(x2 - drawTile/8, y2 - drawTile/8, x2 + drawTile/8, y2 + drawTile/8);
					}
				}
			}
		}
		
		// Draw super-borders
		if (this.drawBorders 
				&& sprite.getHeight() % 8 == 0 
				&& sprite.getWidth() % 8 == 0) {
			g.setColor(Color.GRAY);
			for (int y = 0; y < sprite.getHeight(); y += 8) {
				for (int x = 0; x < sprite.getWidth(); x += 8) {
					g.drawRect(drawTile*x, drawTile*y, 8*drawTile, 8*drawTile);
				}
			}
		}
	}
	
	/* MouseListener methods */
	private void mouseAction(MouseEvent m) {
		Sprite sprite = this.model.getSprite();
		int xw = m.getX()/drawTile;
		int yh = m.getY()/drawTile;
		int val = this.currentControl.getSelectedValue();
		if (xw < sprite.getWidth() && yh < sprite.getHeight()
				&& xw >= 0 && yh >= 0) {
			sprite.setPixelValue(yh, xw, val);
		}
		this.model.updateGUI();
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
		this.drawTile = adjustedTileSize(this.model);
		this.repaint();
	}
	
	public void updateGUI() {
		this.repaint();
	}
}
