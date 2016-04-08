package graphics.shapes.ui;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.Shape;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.ui.Controller;

public class ShapesController extends Controller {

	private ShapesView view;
	private boolean move;
	private boolean shiftDown;
	private int decx;
	private int decy;
	
	
	public ShapesController(Object newModel) {
		super(newModel);
	}
	
	public void setView(ShapesView view) {
		this.view = view;
	}
	
	public void translateSelected(int dx, int dy) {
		for (Iterator it = ((SCollection) this.getModel()).iterator(); it.hasNext(); ) {
			Shape s = (Shape) it.next();
			if (((SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID)).isSelected())
				s.translate(dx - this.decx + s.getBounds().x, dy - this.decy + s.getBounds().y);
		}
	}
	
	public Shape getTarget(int x, int y) {
		Shape target = new SRectangle();
		for (Iterator it = ((SCollection) this.getModel()).iterator(); it.hasNext(); ) {
			Shape s = (Shape) it.next();
			Rectangle tmp = s.getBounds();
			if ((x > tmp.x) && (x < tmp.x + tmp.width) && (y > tmp.y) && (y < tmp.y + tmp.height)) {
				target = s;
				break;
			}	
		}
		return target;
	}
	
	public void unselectAll() {
		for (Iterator it = ((SCollection) this.getModel()).iterator(); it.hasNext(); ) {
			Shape s = (Shape) it.next();
			((SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID)).unselect();
		}
	}
	
	public boolean shiftDown() {
		return shiftDown;
	}

	/*public void selectUnselectShape(Shape s) {
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
	}*/
	
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println("Mouse pressed : x = " + x + ", y = " + y);
		Shape s = getTarget(x, y);
		SelectionAttributes selection = (SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID);
		if ((s != null) && (selection.isSelected())) {
			this.move = true;
			this.decx = x - s.getBounds().x;
			this.decy = y - s.getBounds().y;
		} else
			this.move = false;
	}
	
	public void mouseReleased(MouseEvent e) {
		Shape s = getTarget(e.getX(), e.getY());
		if (((SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID)).isSelected()) {
			int n = 300;
			int x = 50;
			for(int i = 0 ; i < n ; i++) {
				this.translateSelected(e.getX() - x + ((int) (x*Math.cos(i*2*Math.PI/n))), e.getY() + ((int) (x*Math.sin(i*2*Math.PI/n))));
				this.view.update(this.view.getGraphics());
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Shape s = getTarget(x, y);
		
		if (!this.shiftDown())
			this.unselectAll();
		
		if (s != null)
			((SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID)).toggleSelection();
		this.view.update(this.view.getGraphics());
		/*if(getTarget(x,y) != null)
			selectUnselectShape(getTarget(x,y));
		else
			unselectAll();*/
		System.out.println("Mouse clicked : x = " + x + ", y = " + y);
	}
	
	public void mouseEntered(MouseEvent e) {
		/*Shape s = getTarget(e.getX(), e.getY());
		if (s != null)
			System.out.println("mouse entered : " + s.toString());
	*/}

	public void mouseExited(MouseEvent e) {
		//System.out.println("mouse exited");
	}
	
	public void mouseMoved(MouseEvent e) {
	}
	
	public void mouseDragged(MouseEvent e) {
		System.out.println("Move " + move);
		if (move) {
			this.translateSelected(e.getX(), e.getY());
			System.out.println("translate selected : " + e.getX() + " " + e.getY());
			
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