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
		//ShapeDraftman.shapesView.getGraphics().setFont(this.font);		//pb n'est pas pris en compte dans width
		FontRenderContext context = ShapeDraftman.shapesView.getGraphics().getFontMetrics().getFontRenderContext();
		//int width = ShapeDraftman.shapesView.getGraphics().getFontMetrics().stringWidth(s);
		//int height = ShapeDraftman.shapesView.getGraphics().getFontMetrics().getHeight();
		//Rectangle rect = new Rectangle(0, 0, width, height);
		return font.getStringBounds(s, context).getBounds();
		//return rect;
	}
	
	/*public static void main(String[] args) {
		FontAttributes fa = new FontAttributes(new Font(Font.SERIF, Font.ITALIC, 20), Color.BLUE);
		System.out.println("fontattributes getbounds : " + fa.getBounds("hello"));
	}*/
}