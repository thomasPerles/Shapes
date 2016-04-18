package graphics.shapes;

import java.awt.Graphics;

public interface ShapeVisitor {
	
	public void visitRectangle(SRectangle rectangle);
	public void visitCircle(SCircle circle);
	public void visitPolygon(SPolygon polygon);
	public void visitText(SText text);
	public void visitCollection(SCollection collection);
}
