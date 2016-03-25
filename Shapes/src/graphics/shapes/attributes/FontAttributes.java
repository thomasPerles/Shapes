package graphics.shapes.attributes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import graphics.shapes.SCircle;
import graphics.shapes.ui.Editor;
import graphics.shapes.ui.ShapeDraftman;

public class FontAttributes extends Attributes {

	public static String FONT_ID = "FONT";
	private Font font;
	private Color fontColor;
	//private Rectangle bounds;
	
	public FontAttributes() {
		//this.font = new Font(Font.MONOSPACED, Font.BOLD, Font.PLAIN);		//pb : BOLD et PLAIN sont des styles		
		this.font = new Font(Font.MONOSPACED, Font.PLAIN, 20);		//new Font(String name, int style, int size);
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
	
	public Rectangle getBounds(String s) {
		/*
		int width = ShapeDraftman.shapesView.getGraphics().getFontMetrics().stringWidth(s);
		int height = ShapeDraftman.shapesView.getGraphics().getFontMetrics().getHeight();		//renvoie 16
		
		Rectangle rect = new Rectangle(0, 0, width, height);		//comment recuperer le point de reference du texte? 
		return rect;*/
		
		//TODO
		return (new Rectangle(0, 0, 0, 0));
	}
	
	/*public static void main(String[] args) {
		FontAttributes fa = new FontAttributes(new Font(Font.SERIF, Font.ITALIC, Font.ITALIC), Color.BLUE);
		fa.getBounds("hello");
	}*/
}