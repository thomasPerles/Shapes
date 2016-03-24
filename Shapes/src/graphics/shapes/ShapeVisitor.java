package graphics.shapes;

import java.awt.Graphics;

public interface ShapeVisitor {
	
	public void visitRectangle(SRectangle rectangle);
	public void visitCircle(SCircle circle);
	public void visitText(SText text, Graphics g);
	public void visitCollection(SCollection collection);
}
