package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SRectangle extends Shape {

	private Rectangle rect;
	private int width, height;
	public int decx;
	public int decy;
	
	public SRectangle() {
		this.width = 150;
		this.height = 100;
		this.rect = new Rectangle (50, 50, this.width, this.height);
	}
	
	public SRectangle(int largeur, int longueur) {
		this.width = longueur;
		this.height = largeur;
		this.rect = new Rectangle(0, 0, this.width, this.height);	//x, y, width, height
	}

	public SRectangle(Point p, int largeur, int longueur) {
		this.width = longueur;
		this.height = largeur;
		this.rect = new Rectangle(p.x, p.y, this.width, this.height);
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public Rectangle getRectangle() {
		return this.rect;
	}

	public Point getLoc() {
		return this.rect.getLocation();
	}

	public void setLoc(Point p) {
		this.rect.setLocation(p);
		
	}

	public void translate(int x, int y) {
		int dx = x - this.rect.x;
		int dy = y - this.rect.y;
		this.setLoc(new Point(dx, dy));
	}

	public Rectangle getBounds() {
		return this.rect;
	}
	
	public void accept(ShapeVisitor visitor) {
		visitor.visitRectangle(this);
	}
}