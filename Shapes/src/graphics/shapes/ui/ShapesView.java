package graphics.shapes.ui;

import java.awt.Graphics;
import java.awt.Point;

import graphics.shapes.SCollection;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.ui.View;

public class ShapesView extends View{
	
	private ShapeDraftman draftman;
	
	public ShapesView(SText model) {
		super(model);
		this.draftman = new ShapeDraftman(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		SText model = (SText) this.getModel();

		if (model == null)
			return;

		model.accept(this.draftman, g);
		this.draftman.visitText(model, g);
		/*SCollection collections = (SCollection) this.getModel();
		for (Iterator it = collections.iterator() ; it.hasNext() ; ) {
			
		}
		ColorAttributes ca = (ColorAttributes) r.getAttributes(ColorAttributes.ID);
		g.setColor(ca.strokedColor);
		Rectangle rect = rect.getBounds();
		g.drawRect(x, y, width, height);*/
		
		//g.setColor(Color.RED);
		//g.drawOval(5, 5, 50, 50);
		//g.drawString("Hello world !!!!!!!!!!!!!!! ahaha :-D", 60, 60);
		
		//g.fillRect(100,  100,  100,  50);
		
	}
}