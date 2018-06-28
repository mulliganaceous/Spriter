package io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import model.SpriteModel;
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
	
	/**Perform save and update the model and filename.
	 * @param model The model being updated.
	 * @param filename The target filename.
	 * @return If the save is successful.
	 */
	public boolean save(SpriteModel model, String filename) {
		try {
			OutputStream fos = new FileOutputStream(filename + Spec.FILE_EXT);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			for (int k = 0; k < sb.length(); k++) {
				bos.write(sb.codePointAt(k));
				System.out.printf("%02X ", sb.codePointAt(k));
				if (k % 16 == 15)
					System.out.println();
			}
			System.out.printf("Saved sprite to %s\n", filename + Spec.FILE_EXT);
			model.setFilename(filename + Spec.FILE_EXT);
			model.update();
			bos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
