package extension;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SPolygon;
import graphics.shapes.SPolygonRegulier;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
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
		                case "Cercle":
		                	shapeToSave = this.createCercle(shapeTmp);
		                	break;
		                case "Texte":
		                	shapeToSave = this.createTexte(shapeTmp);
		                	break;
		                case "Collection":
		                	shapeToSave = this.createCollection(shapeTmp);
		                	break;
		                case "Polygon":
		                	shapeToSave = this.createPolygon(shapeTmp);
		                	break;
		                case "PolygonRegulier":
		                	shapeToSave = this.createPolygonRegulier(shapeTmp);
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
		Point loc = new Point(Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("x").toString()), Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("y").toString()));
		
		boolean filled = Boolean.parseBoolean(((JSONObject)shapeTmp.get("color")).get("filled").toString());
		boolean stroked = Boolean.parseBoolean(((JSONObject)shapeTmp.get("color")).get("stroked").toString());
		String filledColorString = ((JSONObject)shapeTmp.get("color")).get("filledColor").toString();
		Color filledColor = Color.decode(filledColorString);
		String strokedColorString = ((JSONObject)shapeTmp.get("color")).get("strokedColor").toString();
		Color strokedColor = Color.decode(strokedColorString);
		
		ColorAttributes colorAttributes = new ColorAttributes(filled, stroked, filledColor, strokedColor);
		SRectangle rectangle = new SRectangle(loc, width, height);
		
		rectangle.addAttributes(colorAttributes);
		rectangle.addAttributes(new SelectionAttributes());
		
		return rectangle;
	}
	
	public Shape createCercle(JSONObject shapeTmp) {
		int radius = Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("radius").toString());
		Point loc = new Point(Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("x").toString()), Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("y").toString()));
		
		boolean filled = Boolean.parseBoolean(((JSONObject)shapeTmp.get("color")).get("filled").toString());
		boolean stroked = Boolean.parseBoolean(((JSONObject)shapeTmp.get("color")).get("stroked").toString());
		String filledColorString = ((JSONObject)shapeTmp.get("color")).get("filledColor").toString();
		Color filledColor = Color.decode(filledColorString);
		String strokedColorString = ((JSONObject)shapeTmp.get("color")).get("strokedColor").toString();
		Color strokedColor = Color.decode(strokedColorString);
		
		ColorAttributes colorAttributes = new ColorAttributes(filled, stroked, filledColor, strokedColor);
		SCircle cercle = new SCircle(loc, radius);
		
		cercle.addAttributes(colorAttributes);
		cercle.addAttributes(new SelectionAttributes());
		return cercle;
	}
	
	public Shape createTexte(JSONObject shapeTmp) {
		Point loc = new Point(Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("x").toString()), Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("y").toString()));
		String text = ((JSONObject)shapeTmp.get("prop")).get("text").toString();
		
		boolean filled = Boolean.parseBoolean(((JSONObject)shapeTmp.get("color")).get("filled").toString());
		boolean stroked = Boolean.parseBoolean(((JSONObject)shapeTmp.get("color")).get("stroked").toString());
		String filledColorString = ((JSONObject)shapeTmp.get("color")).get("filledColor").toString();
		Color filledColor = Color.decode(filledColorString);
		String strokedColorString = ((JSONObject)shapeTmp.get("color")).get("strokedColor").toString();
		Color strokedColor = Color.decode(strokedColorString);
		
		String fontNameString = ((JSONObject)shapeTmp.get("font")).get("name").toString();
		String fontStyleString = ((JSONObject)shapeTmp.get("font")).get("style").toString();
		int fontSize = Integer.parseInt(((JSONObject)shapeTmp.get("font")).get("size").toString());
		Font font = Font.decode(fontNameString + "-" + fontStyleString + "-" + fontSize);
		String fontColorString = ((JSONObject)shapeTmp.get("font")).get("color").toString();
		Color fontColor = Color.decode(fontColorString);
		
		ColorAttributes colorAttributes = new ColorAttributes(filled, stroked, filledColor, strokedColor);
		FontAttributes fontAttributes = new FontAttributes(font, fontColor);
		SText texte = new SText(loc, text);
		
		texte.addAttributes(colorAttributes);
		texte.addAttributes(fontAttributes);
		texte.addAttributes(new SelectionAttributes());
		return texte;
	}
	
	public Shape createCollection(JSONObject shapeTmp) {
		SCollection collection = new SCollection();
		JSONArray shapeArray2 = (JSONArray) shapeTmp.get("shapes");
		for (int i = 0; i < shapeArray2.size(); i++) {
			JSONObject shapeTmp2 = (JSONObject) shapeArray2.get(i);
			Shape shapeToSave = null;
            switch (shapeTmp2.get("class").toString()) {
	        	case "Rectangle":
	        		shapeToSave = this.createRectangle(shapeTmp2);
	        		break;
	            case "Cercle":
	            	shapeToSave = this.createCercle(shapeTmp2);
	            	break;
	            case "Texte":
	            	shapeToSave = this.createTexte(shapeTmp2);
	            	break;
	            case "Collection":
	            	shapeToSave = this.createCollection(shapeTmp2);
	            	break;
            }
            collection.addAttributes(new SelectionAttributes());
            collection.add(shapeToSave);
		}
		return collection;
	}
	
	public Shape createPolygon(JSONObject shapeTmp) {
		int nbpoints = Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("nbpoints").toString());
		int x[] = new int[nbpoints], y[] = new int[nbpoints];
		for (int i = 1 ; i <= nbpoints ; i++) {
			x[i-1] = Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("x" + i).toString());
			y[i-1] = Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("y" + i).toString());
		}
		
		boolean filled = Boolean.parseBoolean(((JSONObject)shapeTmp.get("color")).get("filled").toString());
		boolean stroked = Boolean.parseBoolean(((JSONObject)shapeTmp.get("color")).get("stroked").toString());
		String filledColorString = ((JSONObject)shapeTmp.get("color")).get("filledColor").toString();
		Color filledColor = Color.decode(filledColorString);
		String strokedColorString = ((JSONObject)shapeTmp.get("color")).get("strokedColor").toString();
		Color strokedColor = Color.decode(strokedColorString);
		
		ColorAttributes colorAttributes = new ColorAttributes(filled, stroked, filledColor, strokedColor);
		SPolygon polygon = new SPolygon(nbpoints, x, y);
		
		polygon.addAttributes(colorAttributes);
		polygon.addAttributes(new SelectionAttributes());
		
		return polygon;
	}
	
	public Shape createPolygonRegulier(JSONObject shapeTmp) {
		int nbpoints = Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("nbpoints").toString());
		int radius = Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("radius").toString());
		int x = Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("x").toString());
		int y = Integer.parseInt(((JSONObject)shapeTmp.get("prop")).get("y").toString());
		
		boolean filled = Boolean.parseBoolean(((JSONObject)shapeTmp.get("color")).get("filled").toString());
		boolean stroked = Boolean.parseBoolean(((JSONObject)shapeTmp.get("color")).get("stroked").toString());
		String filledColorString = ((JSONObject)shapeTmp.get("color")).get("filledColor").toString();
		Color filledColor = Color.decode(filledColorString);
		String strokedColorString = ((JSONObject)shapeTmp.get("color")).get("strokedColor").toString();
		Color strokedColor = Color.decode(strokedColorString);
		
		ColorAttributes colorAttributes = new ColorAttributes(filled, stroked, filledColor, strokedColor);
		SPolygonRegulier polygon = new SPolygonRegulier(nbpoints, radius, new Point(x, y));
		
		polygon.addAttributes(colorAttributes);
		polygon.addAttributes(new SelectionAttributes());
		
		return polygon;
	}
}