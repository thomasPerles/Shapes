package graphics.shapes;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class SCircle extends Shape {

	private int radius;
	private Point loc;
	
	public SCircle(Point point, int r) {
		this.radius = r;
		this.loc = point;
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
		this.setLoc(new Point(dx, dy));
	}

	public Rectangle getBounds() {
		return new Rectangle(loc.x, loc.y, 2*radius, 2*radius);
	}

	public void accept(ShapeVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	public void draw(Graphics g) {
		g.drawOval(loc.x, loc.y, 2*radius, 2*radius);
	}

}