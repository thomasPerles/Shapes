package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SPolygon extends Shape {

	public int nPoints;
	public int x[];
	public int y[];
	
	public SPolygon() {
		this.nPoints = 6;
		this.x = new int[nPoints];
		this.y = new int[nPoints];
		int x[] = {20, 40, 130, 150, 130, 40};
		int y[] ={50, 25, 25, 50, 75, 75};
		this.x = x;
		this.y = y;
	}
	
	public SPolygon(int nPoints, int x[], int y[]) {
		this.nPoints = nPoints;
		this.x = new int[nPoints];
		this.y = new int[nPoints];
		for(int i = 0; i < nPoints; i++) {
			this.x[i] = x[i];
			this.y[i] = y[i];
		}
	}
	
	public int getnPoints() {
		return nPoints;
	}
	
	public void setnPoints(int nPoints) {
		this.nPoints = nPoints;
	}
	
	public int[] getX() {
		return x;
	}
	
	public void setX(int[] x) {
		this.x = x;
	}
	
	public int[] getY() {
		return y;
	}
	
	public void setY(int[] y) {
		this.y = y;
	}
	
	public SPolygon getPolygon() {
		return this;
	}
	
	public void setPolygon(int nPoints, int x[], int y[]) {
		this.nPoints = nPoints;
		this.x = new int[nPoints];
		this.y = new int[nPoints];
		for(int i = 0; i < nPoints; i++) {
			this.x[i] = x[i];
			this.y[i] = y[i];
		}
	}
	
	public Point getLoc() {
		return this.getBounds().getLocation();
	}
	
	public void setLoc(Point p) {
		int dx = p.x - this.getBounds().x; 
		int dy = p.y - this.getBounds().y;
		for(int i = 0; i < nPoints; i++) {
			x[i] += dx;
			y[i] += dy;
		}
	}
	
	public Rectangle getBounds() {
		int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
		for(int i = 0; i < nPoints; i++) {
			if(minX > x[i]) minX = x[i];
			if(maxX < x[i]) maxX = x[i];
			if(minY > y[i]) minY = y[i];
			if(maxY < y[i]) maxY = y[i];
		}
		return new Rectangle(minX, minY, maxX-minX, maxY-minY);
	}
	
	public void translate(int x, int y) {
		this.setLoc(new Point(x - this.getLoc().x, y - this.getLoc().y));
	}
	
	public void accept(ShapeVisitor visitor) {
		visitor.visitPolygon(this);
	}
	
	public String toString() {
		String tmp = "SPolygon [nPoints:" + this.nPoints + "\n\tx: " + this.x[0];
		for (int i = 1; i < this.nPoints; i++)
			tmp = tmp + ", " + this.x[i];
		tmp = tmp + "\n\ty: " + this.y[0];
		for (int i = 1; i < this.nPoints; i++)
			tmp = tmp + ", " + this.y[i];
		return tmp;
	}
}