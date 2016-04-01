package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.Shape;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.ui.Controller;

public class ShapesController extends Controller {

	private ShapesView view;
	
	public ShapesController(Object newModel) {
		super(newModel);
	}
	
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//System.out.println("Mouse pressed : x = " + x + ", y = " + y);
	}
	
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(getTarget(x,y) != null)
			selectUnselectShape(getTarget(x,y));
		else unselectAll();
	}
	
	public void selectUnselectShape(Shape s) {
		SelectionAttributes selattrib = (SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID);
		Rectangle tmp = s.getBounds();
		if(selattrib != null) {
			if(!selattrib.isSelected()) {
				selattrib.select();
				this.view.getGraphics().setColor(Color.WHITE);
				this.view.getGraphics().fillRect(tmp.x - 4, tmp.y -4 , 4, 4);
				//this.view.getGraphics().fillRect(tmp.x + tmp.width, tmp.y + tmp.height, 4, 4);
				ColorAttributes c = (ColorAttributes) s.getAttributes(ColorAttributes.COLOR_ID);
				c.setStrokedColor(Color.YELLOW);
				System.out.println("objet selectionné " + tmp);
			}
			else {
				selattrib.unselect();
				this.view.getGraphics().clearRect(tmp.x - 4, tmp.y -4 , 4, 4);
				//this.view.getGraphics().clearRect(tmp.x + tmp.width, tmp.y + tmp.height, 4, 4);
				System.out.println("objet déselectionné " + tmp);
			}
		}
	}
	
	public void unselectAll() {
		for (Iterator it = ((SCollection) this.getModel()).iterator(); it.hasNext(); ) {
			Shape s = (Shape) it.next();
			((SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID)).unselect();
		}
	}

	public void setView(ShapesView view) {
		this.view = view;
	}
	
	public Shape getTarget(int x, int y) {
		Shape target = new SRectangle();
		for (Iterator it = ((SCollection) this.getModel()).iterator(); it.hasNext(); ) {
			Shape s = (Shape) it.next();
			Rectangle tmp = s.getBounds();
			SelectionAttributes selattrib = (SelectionAttributes) s.getAttributes(SelectionAttributes.SELECTION_ID);
			if ((x > tmp.x) && (x < tmp.x + tmp.width) && (y > tmp.y) && (y < tmp.y + tmp.height)) {
				target = s;
				break;
			}
		}
		return target;
	}
	
}
