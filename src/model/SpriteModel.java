package model;

import sprite.Spec;
import sprite.Sprite;

public class SpriteModel {
	public Sprite sprite;
	public String filename;
	private static SpriteModel model;
	
	/**Singleton constructor for the SpriteModel.
	 */
	private SpriteModel() {
		// Genereate a new empty small Sprite.
		System.out.println("Sprite Model initialized.");
		this.sprite = new Sprite(Spec.S, Spec.S);
		this.filename = null;
	}
	
	/**Set the current sprite that will be used and manipulated by the program.
	 * @param sprite The new sprite
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**Obtain the current sprite being used.
	 */
	public Sprite getSprite() {
		return this.sprite;
	}
	
	/**Set a new current name of the file this program is pointing to.
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**Set the program such that it is not referring to any existing
	 * filename.
	 */
	public void removeFilename() {
		this.filename = null;
	}
	
	/**
	 * @return The current name of the file in use, or
	 * null if it is a new sprite file.
	 */
	public String getFilename() {
		return this.filename;
	}
	
	/**
	 * @return Obtain the singleton instance of the sprite Model.
	 */
	public static SpriteModel getInstance() {
		if (model == null)
			model = new SpriteModel();
		return model;
	}
}
