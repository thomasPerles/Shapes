package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SCircle extends Shape {

	private int radius;	//rayon du cercle
	private Point loc;	//point en haut a gauche
	
	public SCircle() {
		this.radius = 50;
		this.loc = new Point(50, 50);
	}
	
	public SCircle(Point point, int radius) {
		this.radius = radius;
		this.loc = point;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
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
		visitor.visitCircle(this);
	}
	
	public String toString() {
		return "SCercle [radius=" + radius+ "]";
	}
}