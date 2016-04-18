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
		int x[] = {20, 25, 145, 150, 145, 25};
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

	public void translate(int x, int y) {
		Point loc = this.getLoc();
		int locX = loc.x + x;
		int locY = loc.y + y;
		this.setLoc(new Point(locX, locY));
		for(int i = 0; i < nPoints; i++) {
			this.x[i] += x;
			this.y[i] += y;
		}
	}

	@Override
	public Rectangle getBounds() {
		int minX = 0, minY = 0, maxX = 0, maxY = 0;
		for(int i = 0; i < nPoints; i++) {
			if(minX < x[i]) minX = x[i];
			if(maxX > x[i]) maxX = x[i];
			if(minY < y[i]) minY = y[i];
			if(maxY > y[i]) maxY = y[i];
		}
		return new Rectangle(minX, minY, maxX-minX, maxY-minY);
	}

	@Override
	public void accept(ShapeVisitor visitor) {
		visitor.visitPolygon(this);
		
	}

	@Override
	public Point getLoc() {
		return this.getBounds().getLocation();
	}

	@Override
	public void setLoc(Point p) {
		int dx = p.x - this.getBounds().x; 
		int dy = p.y - this.getBounds().y;
		for(int i = 0; i < nPoints; i++) {
			x[i] += dx;
			y[i] += dy;
		}
	}
}