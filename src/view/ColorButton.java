package view;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JButton;

import model.SpriteModel;
import sprite.Sprite;

public class ColorButton extends JButton {
	private static final long serialVersionUID = 151;
	private int val;
	private SpriteModel spriteModel;
	private Sprite sprite;
	private boolean fixed = false;
	public ColorButton(int val, SpriteModel model) {
		super(Integer.toHexString(val).toUpperCase());
		this.val = val;
		this.spriteModel = model;
		this.sprite = model.getSprite();
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setFont(Luminescent.LARGE_FONT);
		this.setBackground(this.sprite.getPalette()[this.val]);
		this.setBorder(null);
	}
	
	public void setBackground(Color bg) {
		super.setBackground(bg);
		this.setForeground(Luminescent.textColor(bg));
	}
	
	public void fixPalette() {
		this.setForeground(Color.WHITE);
		this.setBackground(Color.BLACK);
		this.fixed = true;
	}
	
	public void updateColor() {
		this.sprite = this.spriteModel.getSprite();
		if (!this.fixed)
			this.setBackground(this.sprite.getPalette()[this.val]);
		else 
			System.out.println("Color " + val + " cannot be modified.");
	}
}