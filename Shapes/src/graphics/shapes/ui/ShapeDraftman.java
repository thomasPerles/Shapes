package graphics.shapes.ui;

import java.awt.Graphics;
import java.util.Iterator;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.ShapeVisitor;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;

public class ShapeDraftman implements ShapeVisitor {
	
	public static ShapesView shapesView;
	private Graphics g;
	
	public ShapeDraftman(ShapesView shapesView, Graphics g) {
		this.shapesView = shapesView;
		this.g = g;
	}

	public void visitRectangle(SRectangle rectangle) {
		/*
		System.out.println(rectangle.getAttributes(FontAttributes.FONT_ID));
		ColorAttributes color = (ColorAttributes) rectangle.getAttributes(ColorAttributes.COLOR_ID);
		*/
		/*
		if (((ColorAttributes) rectangle.getAttributes(ColorAttributes.COLOR_ID)).isFilled()) {
			g.setColor(((ColorAttributes) rectangle.getAttributes(ColorAttributes.COLOR_ID)).filledColor);
			g.fillRect(rectangle.getRectangle().x, rectangle.getRectangle().y, rectangle.getRectangle().width, rectangle.getRectangle().height);
		}
		if (((ColorAttributes) rectangle.getAttributes(ColorAttributes.COLOR_ID)).isStroked()) {
			g.setColor(((ColorAttributes) rectangle.getAttributes(ColorAttributes.COLOR_ID)).strokedColor);
			g.drawRect(rectangle.getRectangle().x, rectangle.getRectangle().y, rectangle.getRectangle().width, rectangle.getRectangle().height);
		}
		*/
	}
	
	public void visitCircle(SCircle circle) {
		g.drawOval(circle.getBounds().x, circle.getBounds().y, circle.getBounds().width, circle.getBounds().height);
	}
	
	public void visitText(SText text) {
		g.drawString(text.getText(), 30, 30);
	}
	
	public void visitCollection(SCollection collection) {
	}
	/*
	attention it.next() prend l'élément suivant  =>  à tester
	
	public void visitCollection(SCollection collection) {
		for (Iterator it = collection.iterator(); it.hasNext();) {
			if (it.next().getClass() == SRectangle.class)
				this.visitRectangle((SRectangle) it.next());
			if (it.next().getClass() == SCircle.class)
				this.visitCircle((SCircle) it.next());
			if (it.next().getClass() == SText.class)
				this.visitText((SText) it.next());
			if (it.next().getClass() == SCollection.class)
				this.visitCollection((SCollection) it.next());
		}
	}
	*/
}