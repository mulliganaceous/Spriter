package view;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import model.SpriteModel;
import sprite.Sprite;

public class ColorButton extends JButton {
	private static final long serialVersionUID = 151;
	private int val;
	private SpriteModel spriteModel;
	private SpriteModel model;
	private boolean fixed;
	private boolean active;
	private Color color;
	public ColorButton(int val, SpriteModel model) {
		super(Integer.toHexString(val).toUpperCase());
		this.val = val;
		this.spriteModel = model;
		this.model = model;
		this.fixed = false;
		this.active = false;
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setFont(Luminescent.LARGE_FONT);
		this.color = this.model.getSprite().getPalette()[this.val];
		this.setBorder(null);
	}
	
	public void setBackground(Color bg) {
		super.setBackground(bg);
		this.setForeground(Luminescent.textColor(bg));
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void fixPalette() {
		super.setBackground(Color.BLACK);
		if (this.val == 0)
			this.setForeground(Color.WHITE);
		else
			this.setForeground(Luminescent.GITD);
		this.fixed = true;
	}
	
	public void setSelected(boolean active) {
		this.active = active;
	}
	
	public void updateColor() {
		Sprite sprite = model.getSprite();
		sprite = this.spriteModel.getSprite();
		if (!this.fixed) {
			// Update color
			this.color = sprite.getPalette()[this.val];
			if (this.active) {
				this.setBackground(Luminescent.bgColor(sprite.getPalette()[this.val]));
				this.setForeground(sprite.getPalette()[this.val]);
				this.setBorder(BorderFactory.createLineBorder(this.getForeground(), 4));
			} 
			else {
				this.setBackground(sprite.getPalette()[this.val]);
				this.setBorder(null);
			}
		}
		else {
			// Do not update color
			if (this.active) {
				this.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1, 1));
			} else {
				this.setBorder(null);
			}
		}
	}
}