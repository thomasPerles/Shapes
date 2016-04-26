package extension;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.Shape;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.SelectionAttributes;


public class ShapesJson {

	 public SCollection readShapesFromJson(String fileName) {
	        SCollection fileToCollection = new SCollection();
	        ArrayList<Shape> shapes = new ArrayList<>();
	        JSONParser parser = new JSONParser();
	        try {
	            Object obj = parser.parse(new FileReader(fileName));
	            JSONObject jsonObject = (JSONObject) obj;

	            JSONArray shapesArray = (JSONArray) jsonObject.get("shapes");
	            for (int i = 0; i < shapesArray.size(); i++) { // on itère sur le tableau de shapes
	                JSONObject shapeTmp = (JSONObject) shapesArray.get(i);
	                //la classe du shape
	                Shape shapeToSave = null;
	                switch (shapeTmp.get("class").toString()) {
		                case "Rectangle":
		                    shapeToSave = this.createRectangle(shapeTmp);
		                    break;
	                }
	                shapes.add(shapeToSave);
	                fileToCollection.setShapesCollection(shapes);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        return fileToCollection;
	 }

	public Shape createRectangle(JSONObject shapeTmp) {
		int width = Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("width").toString());
		int height = Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("height").toString());
		
		boolean filled = Boolean.parseBoolean(((JSONObject)shapeTmp.get("color")).get("filled").toString());
		boolean stroked = Boolean.parseBoolean(((JSONObject)shapeTmp.get("color")).get("stroked").toString());
		String filledColorString = ((JSONObject)shapeTmp.get("color")).get("filledColor").toString();
		Color filledColor = Color.decode(filledColorString);
		String strokedColorString = ((JSONObject)shapeTmp.get("color")).get("strokedColor").toString();
		Color strokedColor = Color.decode(strokedColorString);
		
		ColorAttributes colorAttributes = new ColorAttributes(filled, stroked, filledColor, strokedColor);
		SRectangle rectangle = new SRectangle(width, height);
		
		rectangle.addAttributes(colorAttributes);
		rectangle.addAttributes(new SelectionAttributes());
		
		//System.out.println("width : " + width + " height : " + height + " filled : " + filled +" "+ filledColor + " " + strokedColor);
		return rectangle;
	}
}