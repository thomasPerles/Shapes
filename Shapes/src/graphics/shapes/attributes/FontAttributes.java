package graphics.shapes.attributes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class FontAttributes extends Attributes {

	public static String FONT_ID = "FONT";
	private Font font;
	private Color fontColor;
	private Rectangle bounds;
	
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
		return new Rectangle(font.getSize(), font.);
	}
	*/
	
}