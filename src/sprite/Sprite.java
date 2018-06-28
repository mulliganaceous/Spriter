package sprite;

import java.awt.Color;

/**Represents a Sprite object.
 * A Sprite is an array of pixels. It currently
 * supports up to 15 colors with color values 1 to F.
 * The color value 0 is always transparent, and F
 * always refer to a custom variable color.
 * @author !MULLIGANACEOUS!
 */
public class Sprite {
	private byte[][] spritesheet;
	private Color[] palette;
	
	/**Create a Sprite based on dimensions
	 * @param h The height of the Sprite
	 * @param w The width of the Sprite
	 */
	public Sprite(int h, int w) {
		this.spritesheet = new byte[h][w];
		this.palette = new Color[16];
		// Default palettes
		this.palette[1] = Color.WHITE;
		this.palette[2] = Color.RED;
		this.palette[3] = Color.GREEN;
		this.palette[4] = Color.BLUE;
		this.palette[5] = Color.YELLOW;
		this.palette[6] = Color.CYAN;
		this.palette[7] = Color.MAGENTA;
		this.palette[8] = Color.GRAY;
		this.palette[9] = new Color(127,0,0);
		this.palette[10] = new Color(0,127,0);
		this.palette[11] = new Color(0,0,127);
		this.palette[12] = new Color(127,127,0);
		this.palette[13] = new Color(0,127,127);
		this.palette[14] = new Color(127,0,127);
		this.palette[15] = null; // Special color
	}
	
	/**Set a new color value from this Sprite 
	 * @param h The height of the Sprite
	 * @param w The width of the Sprite
	 * @param value The new color value
	 */
	public void setPixelValue(int h, int w, int value) {
		if (value < 0 || value > 0xF) {
			try {
				throw new PixelValueException();
			} catch (PixelValueException e) {
				e.printStackTrace();
			}
		}
		this.spritesheet[h][w] = (byte) value;
	}
	
	/**Obtain the current color value from that pixel of the Sprite.
	 * @param h The height of the Sprite
	 * @param w The width of the Sprite
	 * @return The color value
	 */
	public byte getPixelValue(int h, int w) {
		return this.spritesheet[h][w];
	}
	
	/**Obtain the current color from that pixel of the Sprite.
	 * @param h The height of the Sprite
	 * @param w The width of the Sprite
	 * @return The color
	 */
	public Color getPixelColor(int h, int w) {
		return this.palette[this.spritesheet[h][w]];
	}
	
	/**Change the palette color of that palette.
	 * @param value The color value (must be 1 to 15)
	 * @param color The new color
	 */
	public void setPaletteColor(int value, Color color) {
		this.palette[value] = color;
		System.out.printf("Color %d changing to (%d, %d, %d)\n", value, 
				color.getRed(),color.getGreen(),color.getBlue());
	}
	
	/**
	 * @return Obtain the current pallete.
	 */
	public Color[] getPalette() {
		return this.palette;
	}
	
	/**
	 * @return Obtain the height (vertical direction) of the Sprite.
	 */
	public int getHeight() {
		return this.spritesheet.length;
	}
	
	/**
	 * @return Obtain the width (horizontal dimension) of the Sprite.
	 */
	public int getWidth() {
		return this.spritesheet[0].length;
	}
}
