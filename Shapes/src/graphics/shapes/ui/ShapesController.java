package graphics.shapes.ui;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import graphics.shapes.SCollection;
import graphics.shapes.Shape;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.ui.Controller;

public class ShapesController extends Controller {

	private ShapesView view;
	private boolean move;
	
	public ShapesController(Object newModel) {
		super(newModel);
	}
	
	public void setView(ShapesView view) {
		this.view = view;
	}
	
	public void translateSelected(int dx, int dy) {
		for (Iterator<Shape> it = ((SCollection) this.getModel()).iterator(); it.hasNext(); ) {
			Shape s = (Shape) it.next();
			if (((SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID)).isSelected())
				s.translate(dx - s.decx + s.getBounds().x, dy - s.decy + s.getBounds().y);
		}
	}
	
	public Shape getTarget(int x, int y) {
		for (Iterator<Shape> it = ((SCollection) this.getModel()).iterator(); it.hasNext(); ) {
			Shape s = (Shape) it.next();
			Rectangle tmp = s.getBounds();
			if ((x > tmp.x) && (x < tmp.x + tmp.width) && (y > tmp.y) && (y < tmp.y + tmp.height)) {
				return s;
			}	
		}
		return null;
	}
	
	public void unselectAll() {
		for (Iterator<Shape> it = ((SCollection) this.getModel()).iterator(); it.hasNext(); ) {
			Shape s = (Shape) it.next();
			((SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID)).unselect();
		}
	}
	
	
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Shape s = getTarget(x, y);
		if(s!=null) {
			SelectionAttributes selection = (SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID);
			
			if ((s != null) && (selection.isSelected())) {
				this.move = true;
				for (Iterator<Shape> it = ((SCollection) this.getModel()).iterator(); it.hasNext(); ) {
					Shape s2 = (Shape) it.next();
					if (((SelectionAttributes)s2.getAttributes(SelectionAttributes.SELECTION_ID)).isSelected()) {
						s2.decx = x - s2.getBounds().x;
						s2.decy = y - s2.getBounds().y;
					}
				}
			} else
				this.move = false;
		}
		else {
			unselectAll();
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		/*Shape s = getTarget(e.getX(), e.getY());
		if(s!=null) {
			if (((SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID)).isSelected()) {
				int n = 300;
				int x = 50;
				for(int i = 0 ; i < n+1 ; i++) {
					this.translateSelected(e.getX() - x + ((int) (x*Math.cos(i*2*Math.PI/n))), e.getY() + ((int) (x*Math.sin(i*2*Math.PI/n))));
					this.view.update(this.view.getGraphics());
				}
			}
		}
		else {
			unselectAll();
		}*/
	}
	
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Shape s = getTarget(x, y);
		if(s!=null) {
			if (!e.isShiftDown())
				this.unselectAll();
			
			if (s != null)
				((SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID)).toggleSelection();
		}
		else {
			unselectAll();
		}
		this.view.update(this.view.getGraphics());
	}
	
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
	
	public void mouseMoved(MouseEvent e) {
	}
	
	public void mouseDragged(MouseEvent e) {
		if (move) {
			this.translateSelected(e.getX(), e.getY());
		}
		this.view.update(this.view.getGraphics());
	}
	
	public void keyTyped(KeyEvent e) {
	}
	
	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
}