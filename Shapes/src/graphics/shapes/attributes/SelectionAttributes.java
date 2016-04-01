package graphics.shapes.attributes;

public class SelectionAttributes extends Attributes {

	public static String SELECTION_ID = "selection";
	private boolean selected;
	
	public SelectionAttributes() {
		this.selected = false;
	}
	
	public String getId() {
		return SELECTION_ID;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void select() {
		selected = true;
	}
	
	public void unselect() {
		selected = false;
	}
	
	public void toggleSelection() {
		selected = !(selected);
	}
}