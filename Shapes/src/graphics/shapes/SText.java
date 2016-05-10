package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import graphics.shapes.attributes.FontAttributes;

public class SText extends Shape {

	private String text;
	private Point loc;
	
	public SText() {
		this.text = "Hello !!!";
		this.loc = new Point(25, 25);
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
		Rectangle tmp = ((FontAttributes)this.getAttributes(FontAttributes.FONT_ID)).getBounds(this.text);
		Rectangle rect = new Rectangle(this.loc.x, this.loc.y, tmp.width, tmp.height);
		return rect; 
	}

	public void accept(ShapeVisitor visitor) {
		visitor.visitText(this);
	}
	
	public String toString() {
		return "SText [texte: " + this.text + "]";
	}
}