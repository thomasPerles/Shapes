package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class SCollection extends Shape {

	private ArrayList shapesCollection;
	public int decx;
	public int decy;
	
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

	public Point getLoc() {		//renvoie le coin en haut a gauche en prenant les coordonnées min de chaque figure 
		Point ref = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		for (Iterator it = iterator(); it.hasNext(); ) {
			Point tmp = ((Shape) it.next()).getLoc();
			if (tmp.x < ref.x)
				ref.x = tmp.x;
			if (tmp.y < ref.y)
				ref.y = tmp.y;
		}
		return ref;
	}

	public void setLoc(Point p) {		//place toutes les figures au point p
		for (Iterator it = iterator(); it.hasNext(); )
			((Shape) it.next()).setLoc(p);
	}

	public void translate(int x, int y) {
		for (Iterator it = iterator(); it.hasNext(); )
			((Shape) it.next()).translate(x,y);
	}

	public Rectangle getBounds() {		//pour chaque figure on compare les coordonnées xmin, xmax, ymin et ymax 
		int xmin = Integer.MAX_VALUE;
		int xmax = Integer.MIN_VALUE;
		int ymin = Integer.MAX_VALUE;
		int ymax = Integer.MIN_VALUE;
		for (Iterator it = iterator(); it.hasNext(); ) {
			Rectangle tmp = ((Shape) it.next()).getBounds();
			if (tmp.x < xmin)
				xmin = tmp.x;
			if (tmp.x + tmp.width > xmax)
				xmax = tmp.x + tmp.width;
			if (tmp.y < ymin)
				ymin = tmp.y;
			if (tmp.y + tmp.height > ymax)
				ymax = tmp.y + tmp.height;
		}
		return (new Rectangle(xmin, ymin, xmax - xmin, ymax - ymin));
	}

	public void accept(ShapeVisitor visitor) {
		visitor.visitCollection(this);
	}
}