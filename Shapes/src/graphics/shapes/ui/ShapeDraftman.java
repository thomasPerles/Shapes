package graphics.shapes.ui;

import java.awt.Graphics;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.ShapeVisitor;

public class ShapeDraftman implements ShapeVisitor {
	
	public static ShapesView shapesView;
	private Graphics g;
	
	public ShapeDraftman(ShapesView shapesView, Graphics g) {
		this.shapesView = shapesView;
		this.g = g;
	}

	@Override
	public void visitRectangle(SRectangle rectangle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitCircle(SCircle circle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitText(SText text) {
		g.drawString(text.getText(), 30, 30);
		
	}

	@Override
	public void visitCollection(SCollection collection) {
		// TODO Auto-generated method stub
		
	}

}
