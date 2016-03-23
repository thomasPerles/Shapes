package graphics.shapes.attributes;

import java.awt.Color;

public class ColorAttributes extends Attributes {
	
	public static String ID = "colors";
	public boolean filled = false;
	public boolean stroked = true;
	public Color filledColor = Color.BLACK;
	public Color strokedColor = Color.BLACK;

	public ColorAttributes(boolean fill, boolean all, Color filledColor,Color strokedColor) {
		this.filled = fill;
		this.stroked= all;
		this.filledColor = filledColor;
		this.strokedColor = strokedColor;
	}

	public String getId() {
		return ID;
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public boolean isStroked() {
		return stroked;
	}

	public void setStroked(boolean stroked) {
		this.stroked = stroked;
	}

	public Color getFilledColor() {
		return filledColor;
	}

	public void setFilledColor(Color filledColor) {
		this.filledColor = filledColor;
	}

	public Color getStrokedColor() {
		return strokedColor;
	}

	public void setStrokedColor(Color strokedColor) {
		this.strokedColor = strokedColor;
	}
	
}