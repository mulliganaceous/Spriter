package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import sprite.Spec;
import sprite.Sprite;

/**Converts the sprite into a save file
 * @author !MULLIGANACEOUS!
 */
public class Savefile {
	StringBuilder sb;
	Sprite sprite;
	public Savefile(Sprite sprite) {
		this.sb = new StringBuilder();
		this.sprite = sprite;
		this.convertToBinary();
	}

	private void convertToBinary() {
		this.sb.append((char) 255);
		this.sb.append((char) 239);
		this.sb.append((char) sprite.getHeight());
		this.sb.append((char) sprite.getWidth());
		for (int k = 1; k <= 0xF; k++) {
			this.sb.append((char) 0);
			if (sprite.getPalette()[k] != null) {
				this.sb.append((char) sprite.getPalette()[k].getRed());
				this.sb.append((char) sprite.getPalette()[k].getGreen());
				this.sb.append((char) sprite.getPalette()[k].getBlue());
			} else {
				this.sb.append((char) 0);
				this.sb.append((char) 0);
				this.sb.append((char) 0);
			}
		}
		for (int h = 0; h < sprite.getHeight(); h++) {
			for (int w = 0; w < sprite.getWidth(); w++) {
				this.sb.append((char) sprite.getPixelValue(h, w));
			}
		}
	}
	
	public void outputToScreen() {
		String str = sb.toString();
		for (int k = 0; k < str.length(); k++) {
			int ascii = str.charAt(k);
			System.out.printf("%02X ", ascii);
			if (k % 16 == 15)
				System.out.println();
		}
		System.out.println();
	}
	
	public boolean save(String path) {
		File file = new File(path + Spec.FILE_EXT);
		try {
			PrintWriter pw = new PrintWriter(file);
			BufferedWriter bw = new BufferedWriter(pw);
			bw.write(sb.toString());
			System.out.printf("Saved sprite to %s\n", path + Spec.FILE_EXT);
			bw.close();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
