package graphics.shapes.ui;

import java.awt.Graphics;

import graphics.shapes.SCollection;
import graphics.ui.View;

public class ShapesView extends View{
	
	private ShapeDraftman draftman;
	private ShapesController controller; 
	
	public ShapesView(SCollection model) {
		super(model);
		this.controller = new ShapesController(model);
		this.addMouseListener(this.controller);
		this.controller.setView(this);
	}

	public void paintComponent(Graphics g) {
		this.draftman = new ShapeDraftman(this, g);
		SCollection model = (SCollection) this.getModel();

		if (model == null)
			return;

		model.accept(this.draftman);
		
	}
}