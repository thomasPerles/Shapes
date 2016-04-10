package graphics.shapes;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.shapes.ui.ShapeDraftman;

public class SText extends Shape {

	private String text;
	private Point loc;
	public int decx;
	public int decy;
	
	public SText() {
		this.text = "Hello !!!";
		this.loc = new Point(10, 10);
	}
	
	public SText(Point point, String string) {
		this.loc = point;
		this.text = string;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Point getLoc() {
		return this.loc;
	}

	public void setLoc(Point p) {
		this.loc = p;
	}

	public void translate(int x, int y) {
		int dx = x - loc.x;
		int dy = y - loc.y;
		setLoc(new Point(dx, dy));
	}

	public Rectangle getBounds() {
		/*//cf https://docs.oracle.com/javase/tutorial/2d/text/measuringtext.html
		int width = ShapeDraftman.shapesView.getGraphics().getFontMetrics().stringWidth(text);
		int height = ShapeDraftman.shapesView.getGraphics().getFontMetrics().getHeight();		//renvoie 16
		//int height = ShapeDraftman.shapesView.getGraphics().getFont().getSize();		//renvoie 12
		Rectangle rect = new Rectangle(this.loc.x, this.loc.y - height, width, height);
		return rect;
		*/
		Rectangle tmp = ((FontAttributes)this.getAttributes(FontAttributes.FONT_ID)).getBounds(this.text);
		Rectangle rect = new Rectangle(this.loc.x, this.loc.y, tmp.width, tmp.height);
		return rect; 
		
		/* premiere version
		FontMetrics fontMetrics = ShapeDraftman.shapesView.getGraphics().getFontMetrics();
		FontRenderContext context = fontMetrics.getFontRenderContext();
		Font font = ((FontAttributes)this.getAttributes(FontAttributes.FONT_ID)).font();
		Rectangle2D bounds = font.getStringBounds(this.text, context);
		//System.out.println("rect2D bounds : " + bounds.toString());		//probleme
		//return (Rectangle) bounds;		//pas du type Rectangle
		return bounds.getBounds();		//renvoie (0, 0, 0, 0)
		//return (new Rectangle(10, 100, 50, 100));		//pour tester */
		/*Rectangle rect = new Rectangle(bounds.getBounds().x, bounds.getBounds().y, bounds.getBounds().width, bounds.getBounds().height);
		return rect;*/
		
		/* renvoie (0, 0, 0, 0)
		Font font = ((FontAttributes)this.getAttributes(FontAttributes.FONT_ID)).font();
		FontRenderContext context = ShapeDraftman.shapesView.getGraphics().getFontMetrics().getFontRenderContext();
		int textwidth = (int)(font.getStringBounds(text, context).getWidth());
		int textheight = (int)(font.getStringBounds(text, context).getHeight());
		Rectangle rect = new Rectangle(this.loc.x, this.loc.y, textwidth, textheight);
		return rect;*/
	}

	public void accept(ShapeVisitor visitor) {
		visitor.visitText(this);
	}
}