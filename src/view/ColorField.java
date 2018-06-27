package view;

public class ColorField extends LuminescentField {
	private static final long serialVersionUID = 250;

	public ColorField() {
		super(2);
		this.setFont(Luminescent.LARGE_FONT);
	}
	
	public int getHexValue() {
		return Integer.parseInt(this.getText(), 16);
	}
}
