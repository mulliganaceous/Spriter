package view;

import java.awt.Color;
import java.awt.Font;

/**The Luminescent interface sets all the GUI style to a luminescent
 * glow-in-the dark style (technically phosphorescence.
 * @author !MULLIGANACEOUS!
 */
public interface Luminescent {
	Color GITD = new Color(51,255,153);
	Color GITD2 = new Color(0,204,51);
	public static final Font LARGE_FONT = new Font("Century Gothic", 1, 16);
	public static final Font SMALL_FONT = new Font("Century Gothic", 0, 12);
	public static final Font TINY_FONT = new Font("Century Gothic", 0, 8);
	
	public static Color textColor(Color bg) {
		if (bg == null)
			return Color.WHITE;
		int r = bg.getRed();
		int g = bg.getGreen();
		int b = bg.getBlue();
		if ((r+b+g)/3 > 51)
			return Color.BLACK;
		else 
			return Color.WHITE;
	}

	public static Color bgColor(Color fg) {
		if (fg == null)
			return Color.WHITE;
		int r = fg.getRed();
		int g = fg.getGreen();
		int b = fg.getBlue();
		if ((r+b+g)/3 > 0x20)
			return Color.BLACK;
		else 
			return Color.WHITE;
	}
}
