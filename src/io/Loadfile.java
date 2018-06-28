package io;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import model.SpriteModel;
import sprite.Spec;
import sprite.Sprite;

public class Loadfile {
	private String path;
	public Loadfile(String filename) {
		this.path = filename + Spec.FILE_EXT;
	}
	
	private Sprite convertToSprite() throws SpriteException, IOException {
		Sprite sprite = null;
			InputStream f = new FileInputStream(this.path);
			BufferedInputStream bis = new BufferedInputStream(f);
			int b;
			int k = 0;
			
			// Initial
			bis.read();
			bis.read();
			int h = bis.read();
			int w = bis.read();
			// System.out.printf("%d %d\n", h, w);
			sprite = new Sprite(h, w); 
			
			// Color palette
			int val = 1;
			int red, green, blue;
			while (val <= 0xF) {
				bis.read();
				red = bis.read();
				green = bis.read();
				blue = bis.read();
				System.out.printf("%02X %02X %02X\n", red, green, blue);
				sprite.setPaletteColor(val, new Color(red, green, blue));
				
				val++;
			}
			
			// Sprite sheet
			b = bis.read();
			while (b > -1) {
				// Use division and modulus, it is in row-major order:
				// Height (row coordinate) is int-quotient
				// width (col coordinate) is remainder
				System.out.printf("(%d %d)", k / w, k % w);
				sprite.setPixelValue(k / w, k % w, b);
				/* */
				System.out.printf("%02X ", b);
				if (k % 16 == 15)
					System.out.println();
				// */
				// Next read
				b = bis.read();
				k++;
			}
			// Check sprite dimensions. 
			// The final number is the total number of pixels.
			if (k != h*w) {
				System.out.printf("%d %d",k, h*w-1);
				bis.close();
				f.close();
				throw new SpriteException();
			}
			bis.close();
			f.close();
			System.out.println();
			return sprite;
	}
	
	/**Perform load given a path file.
	 * @param model The model being affected.
	 * @return The load being successful.
	 */
	public boolean load(SpriteModel model) {
		try {
			Sprite sprite = this.convertToSprite();
			model.setFilename(this.path);
			model.setSprite(sprite);
		} catch (FileNotFoundException exc) {
			System.err.printf("File %s cannot be found!\n", this.path);
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (SpriteException e) {
			System.err.printf("Sprite exception occurred!");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
