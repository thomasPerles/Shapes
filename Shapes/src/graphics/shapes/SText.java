package graphics.shapes;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.ui.ShapeDraftman;

public class SText extends Shape {

	private String text;
	private Point loc;
	
	public SText() {
		this.text = "Hello !!!";
		this.loc = new Point(10, 10);
	}
	
	public SText(Point point, String string) {
		this.loc = point;
		this.text = string;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Point getLoc() {
		return this.loc;
	}

	public void setLoc(Point p) {
		this.loc = p;
	}

	public void translate(int x, int y) {
		int dx = x - loc.x;
		int dy = y - loc.y;
		setLoc(new Point(dx, dy));
	}

	public Rectangle getBounds() {
		FontMetrics fontMetrics = ShapeDraftman.shapesView.getGraphics().getFontMetrics();
		FontRenderContext context = fontMetrics.getFontRenderContext();
		Font font = ((FontAttributes)this.getAttributes(FontAttributes.FONT_ID)).font();
		Rectangle2D bounds = font.getStringBounds(this.text, context);
		return (Rectangle) bounds;
	}

	public void accept(ShapeVisitor visitor, Graphics g) {
		visitor.visitText(this, g);
	}
}