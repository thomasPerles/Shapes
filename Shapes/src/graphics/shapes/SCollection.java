package graphics.shapes;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class SCollection extends Shape {

	private ArrayList shapesCollection;
	
	public SCollection() {
		super();
		this.shapesCollection = new ArrayList();
	}
	
	public Iterator iterator() {
		return(shapesCollection.iterator());
	}
	
	public void add(Shape s) {
		this.shapesCollection.add(s);
	}

	public Point getLoc() {
		for (Iterator it = iterator(); it.hasNext(); ) {
			Point tmp = ((Shape) it.next()).getLoc(); 
		}
		return null;
	}

	public void setLoc(Point p) {
		// TODO Auto-generated method stub
		
	}

	public void translate(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(ShapeVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
}