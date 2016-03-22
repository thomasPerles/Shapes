package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;

import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.attributes.ColorAttributes;
import graphics.ui.View;

public class ShapesView extends View{
	
	//private Dimension size;
	
	public ShapesView(SCollection model2) {
		super(model2);
	}

	public void paintComponent(Graphics g) {
		/*SCollection collections = (SCollection) this.getModel();
		for (Iterator it = collections.iterator() ; it.hasNext() ; ) {
			
		}
		ColorAttributes ca = (ColorAttributes) r.getAttributes(ColorAttributes.ID);
		g.setColor(ca.strokedColor);
		Rectangle rect = rect.getBounds();
		g.drawRect(x, y, width, height);*/
		
		g.setColor(Color.RED);
		g.drawOval(5, 5, 50, 50);
		g.drawString("Hello world !!!!!!!!!!!!!!! ahaha :-D", 60, 60);
		
		g.fillRect(100,  100,  100,  50);
		
	}
	
	/*
	public void setPreferredSize(Dimension dim) {
		this.size = dim;
	}
	*/
}