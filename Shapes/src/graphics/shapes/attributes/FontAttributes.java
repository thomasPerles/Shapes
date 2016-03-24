package graphics.shapes.attributes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

public class FontAttributes extends Attributes {

	public static String FONT_ID = "FONT";
	private Font font;
	private Color fontColor;
	private Rectangle bounds;
	
	public FontAttributes() {
		this.font = new Font(Font.MONOSPACED, Font.BOLD, Font.PLAIN);
		this.fontColor = Color.BLACK;
	}
	
	public FontAttributes(Font font, Color fontColor) {
		this.font = font;
		this.fontColor = fontColor;
	}
	
	public Font font() {
		return this.font;
	}
	
	public Color fontColor() {
		return this.fontColor;
	}
	
	public String getId() {
		return FONT_ID;
	}
	
	/*
	public Rectangle getBounds() {
		return bounds;
	}
	*/
	
}