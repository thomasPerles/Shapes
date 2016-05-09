package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SPolygonRegulier extends SPolygon {
	
	public int nPoints, radius, x[], y[];
	public Point loc;	//point au centre
	
	public SPolygonRegulier() {
		this.nPoints = 6;
		this.radius = 50;
		this.loc = new Point(100, 100);
		this.x = new int[nPoints];
		this.y = new int[nPoints];
		for(int i = 0; i < nPoints; i++) {
			this.x[i] = (int) ( this.radius * Math.cos(Math.PI * 2 * i / this.nPoints)) + this.loc.x + this.radius;
			this.y[i] = (int) (this.radius * Math.sin(Math.PI * 2 * i / this.nPoints)) + this.loc.y  + this.radius;
		}
	}
	
	public SPolygonRegulier(int n, int r, Point loc) {
		this.nPoints = n;
		this.radius = r;
		this.loc = loc;
		this.x = new int[n];
		this.y = new int[n];
		for(int i = 0; i < n; i++) {
			this.x[i] = (int) (r * Math.cos(Math.PI * 2 * i / n)) + loc.x + r;
			this.y[i] = (int) (r * Math.sin(Math.PI * 2 * i / n)) + loc.y + r;
		}
	}
	
	public int getnPoints() {
		return nPoints;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public int[] getX() {
		return x;
	}
	
	public int[] getY() {
		return y;
	}
	
	public Point getLoc() {
		return loc;
	}
	
	public void setnPoints(int nPoints) {
		this.nPoints = nPoints;
		this.x = new int[nPoints];
		this.y = new int[nPoints];
		for(int i = 0; i < nPoints; i++) {
			this.x[i] = (int) (this.radius * Math.cos(Math.PI * 2 * i / nPoints)) + this.loc.x + this.radius;
			this.y[i] = (int) (this.radius * Math.sin(Math.PI * 2 * i / nPoints)) + this.loc.y + this.radius;
		}
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
		for(int i = 0; i < nPoints; i++) {
			this.x[i] = (int) (radius * Math.cos(Math.PI * 2 * i / this.nPoints)) + this.loc.x + radius;
			this.y[i] = (int) (radius * Math.sin(Math.PI * 2 * i / this.nPoints)) + this.loc.y + radius;
		}
	}
	
	public void setLoc(Point loc) {
		this.loc = loc;
		for(int i = 0; i < nPoints; i++) {
			this.x[i] = (int) (this.radius * Math.cos(Math.PI * 2 * i / this.nPoints)) + loc.x + this.radius;
			this.y[i] = (int) (this.radius * Math.sin(Math.PI * 2 * i / this.nPoints)) + loc.y + this.radius;
		}
	}
	
	public SPolygon getPolygonRegulier() {
		return this;
	}
	
	public void setPolygonRegulier(int nPoints, int radius, Point loc) {
		this.nPoints = nPoints;
		this.radius = radius;
		this.loc = loc;
		this.x = new int[nPoints];
		this.y = new int[nPoints];
		for(int i = 0; i < nPoints; i++) {
			this.x[i] = (int) (radius * Math.cos(Math.PI * 2 * i / nPoints)) + loc.x + radius;
			this.y[i] = (int) (radius * Math.sin(Math.PI * 2 * i / nPoints)) + loc.y + radius;
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(this.loc.x, this.loc.y, 2*this.radius, 2*this.radius);
	}
	
	public void translate(int x, int y) {
		this.setLoc(new Point(x - this.loc.x, y - this.loc.y));
	}
	
	public void accept(ShapeVisitor visitor) {
		visitor.visitPolygonRegulier(this);
	}
	
	public String toString() {
		//return "SPolygonRegulier [nPoints: " + this.nPoints + ", radius: " + this.radius + " et loc: " + this.loc + "]";
		String s = "SPolygonRegulier [nPoints: " + this.nPoints + ", radius: " + this.radius + " et loc: " + this.loc + "]" + "\nx : ";
		for (int i = 0; i<nPoints; i++){
			s = s + "\t" + x[i];
		}
		s = s + "\ny : ";
		for (int i = 0; i<nPoints; i++){
			s = s + "\t" + y[i];
		}
		return s;
	}
	/*
	public static void main(String[] args) {
		SPolygonRegulier poly = new SPolygonRegulier(5, 50, new Point(100, 100));
		System.out.println(poly.toString());
	}*/
}