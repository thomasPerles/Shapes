package graphics.shapes.ui;

import java.awt.Graphics;
import java.util.Iterator;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.shapes.ShapeVisitor;
import graphics.shapes.attributes.ColorAttributes;
public class ShapeDraftman implements ShapeVisitor {
	
	public static ShapesView shapesView;
	private Graphics g;
	
	public ShapeDraftman(ShapesView shapesView, Graphics g) {
		this.shapesView = shapesView;
		this.g = g;
	}

	public void visitRectangle(SRectangle rectangle) {
		ColorAttributes color = (ColorAttributes) rectangle.getAttributes(ColorAttributes.COLOR_ID);
		System.out.println(color);
		if (color.isFilled()) {
			g.setColor(color.filledColor);
			g.fillRect(rectangle.getRectangle().x, rectangle.getRectangle().y, rectangle.getRectangle().width, rectangle.getRectangle().height);
		}
		if (color.isStroked()) {
			g.setColor(color.strokedColor);
			g.drawRect(rectangle.getRectangle().x, rectangle.getRectangle().y, rectangle.getRectangle().width, rectangle.getRectangle().height);
		}
	}
	
	public void visitCircle(SCircle circle) {
		g.drawOval(circle.getBounds().x, circle.getBounds().y, circle.getBounds().width, circle.getBounds().height);
	}
	
	public void visitText(SText text) {
		g.drawString(text.getText(), 30, 30);
	}
	
	//attention it.next() prend l'élément suivant  =>  à tester
	
	public void visitCollection(SCollection collection) {
		
		for (Iterator it = collection.iterator(); it.hasNext();) {
			Shape shape = (Shape) it.next();
			if (shape.getClass() == SRectangle.class)
				this.visitRectangle((SRectangle) shape);
			if (shape.getClass() == SCircle.class)
				this.visitCircle((SCircle) shape);
			if (shape.getClass() == SText.class)
				this.visitText((SText) shape);
			if (shape.getClass() == SCollection.class)
				this.visitCollection((SCollection) shape);
		}
	}
	
}