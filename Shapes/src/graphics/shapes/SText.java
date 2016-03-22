package graphics.shapes;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class SText extends Shape {

	private String text;
	private Point loc;
	
	
	
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
		//loc et fontsize*textsize
		return null;
	}

	public void accept(ShapeVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	public void draw(Graphics g) {
		g.drawString(text, loc.x, loc.y);
	}

}