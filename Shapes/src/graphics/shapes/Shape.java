package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.TreeMap;

import graphics.shapes.attributes.Attributes;

public abstract class Shape {

	public TreeMap<String, Attributes> attributs;
	
	public Shape() {
		this.attributs = new TreeMap<String, Attributes>();
	}
	
	public void addAttributes(Attributes a) {
		attributs.put(a.getId() ,a);
	}
	
	public Attributes getAttributes(String key) {
		return (Attributes) this.attributs.get(key);
	}
	
	public abstract Point getLoc();
	public abstract void setLoc(Point p);
	public abstract void translate(int x, int y);
	public abstract Rectangle getBounds();
	public abstract void accept(ShapeVisitor visitor);
}