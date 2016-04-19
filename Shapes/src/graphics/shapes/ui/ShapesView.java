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
		this.controller.setView(this);
		this.draftman = new ShapeDraftman(this);
		this.addMouseListener(this.controller);
		this.addMouseMotionListener(this.controller);
		this.addKeyListener(this.controller);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.draftman.setGraphics(g);
		SCollection model = (SCollection) this.getModel();
		if (model == null)
			return;
		model.accept(this.draftman);
		
	}
}