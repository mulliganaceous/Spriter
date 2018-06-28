package model;

import main.ControlsPanel;
import main.SpritePanel;
import sprite.Spec;
import sprite.Sprite;

public class SpriteModel {
	public Sprite sprite;
	public String filename;
	public SpritePanel view;
	public ControlsPanel controls;
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
	 * This action also updates all the associated components.
	 * @param sprite The new sprite
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		this.update();
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
	
	public void setView(SpritePanel view) {
		this.view = view;
	}
	
	public void setControls(ControlsPanel controls) {
		this.controls = controls;
	}
	
	/**Update all the associated components
	 */
	public void update() {
		this.view.update();
		this.controls.update();
		// System.out.println("State updated");
	}
	
	/**Update all the associated GUI appearances
	 */
	public void updateGUI() {
		this.view.updateGUI();
		this.controls.update();
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
