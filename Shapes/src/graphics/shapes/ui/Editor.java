package graphics.shapes.ui;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SPolygon;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.shapes.attributes.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import extension.ShapesJson;

@SuppressWarnings("serial")
public class Editor extends JFrame
{
	ShapesView sview;
	SCollection model;
	private JPanel leftComponnent, rightComponnent, middleComponnent;
	private JPanel shapesManager, shapesButton;
	private JButton refresh, delete, save;
	private JTextField textString;
	private JFormattedTextField widthRectangle, heightRectanlge, radiusCircle, nbpoints, x[], y[];
	private JComboBox<Integer> fontSize;
	private JComboBox<String> fontName;
	//private JComboBox<Font> fontStyle;
	private JButton fontColor, textStrokedColor, textFilledColor, strokedRectColor, filledRectColor, strokedCircleColor, filledCircleColor, strokedPolygonColor, filledPolygonColor;
	
	public Editor()
	{	
		super("Shapes Editor");

		//System.out.println(getClass().getResource("rectangle.jpg"));

		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent evt)
			{
				System.exit(0);
			}
		});
		
		this.buildModel();
		
		this.sview = new ShapesView(this.model);
		this.sview.setPreferredSize(new Dimension(600,600));
		buildView();
		//this.sview.add(middleComponnent);
		//this.getContentPane().add(this.sview, java.awt.BorderLayout.CENTER);
	}
	
	public void buildView() {
		this.buildLeftComponnent();
		this.buildMiddleComponnent();
		middleComponnent.setBorder(BorderFactory.createMatteBorder(0,5,0,5,Color.cyan));
		this.buildRightComponnent();
		
		this.setLayout(new BorderLayout());
		this.add(leftComponnent, BorderLayout.LINE_START);
		this.add(middleComponnent, BorderLayout.CENTER);
		this.add(rightComponnent, BorderLayout.LINE_END);
		
		this.getContentPane().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {		
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F5) refresh.doClick();
				if (e.getKeyCode() == KeyEvent.VK_DELETE) delete.doClick();
			}
		});
	}
	
	/**
	 * Création du panel de gauche
	 */
	public void buildLeftComponnent() {
		this.leftComponnent = new JPanel();
		this.leftComponnent.setLayout(new BorderLayout());
		this.buildShapesButtons();
		this.buildShapesManager();
		this.leftComponnent.add(shapesButton, BorderLayout.PAGE_START);
		this.leftComponnent.add(shapesManager);
		refresh = new JButton("Refresh (F5)");
		refreshListener();
		this.leftComponnent.add(refresh, BorderLayout.PAGE_END);
		
	}
	
	/**
	 * Création du panel de droite
	 */
	public void buildRightComponnent() {
		this.rightComponnent = new JPanel();
		String [] stringModel = new File(Paths.get("").toAbsolutePath().toString()+"/src/jsonFiles").list();
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(String s : stringModel) {
			model.addElement(s);
		}
		JList<String> liste = new JList<>(model);
		//liste.add("Voila", new JLabel("Premier élément")); // TODO
		liste.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String filename = liste.getSelectedValue();
				fileToShapes(filename);
				repaint();
			}
		});
		this.rightComponnent.add(liste);
	}
	
	public void fileToShapes(String filename) {
		ShapesJson json = new ShapesJson();
		SCollection newCollection = json.readShapesFromJson("src/jsonFiles/"+filename);
		this.model.setShapesCollection(newCollection.getShapesCollection());
	}
	
	/**
	 * création du panel du milieu
	 */
	public void buildMiddleComponnent() {
		this.middleComponnent = new JPanel();
		middleComponnent.setLayout(new BorderLayout());
		this.middleComponnent.add(this.sview);
		
		JPanel middleButtons = new JPanel();
		middleButtons.setLayout(new GridLayout(1, 2));

		delete = new JButton("Delete (suppr)");
		delete.setSize(new Dimension(50, 50));
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Iterator<Shape> it = model.iterator(); it.hasNext();) {
					Shape s  = (Shape)it.next();
					SelectionAttributes selection = (SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID);
					if(selection.isSelected()) {
						it.remove();
					}
					repaint();
				}
			}
		});
		middleButtons.add(delete);
		
		save = new JButton("Save (CTRL+S)");
		save.setSize(new Dimension(50, 50));
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Enregistrement
			}
		});
		middleButtons.add(save);
		
		this.middleComponnent.add(middleButtons, BorderLayout.PAGE_END);
	}	
	
	/**
	 * Construction des boutons pour générer les formes
	 * Pour l'ajout de nouvelles formes rajouter dans cette méthodes la création d'un nouveau boutton
	 */
	public void buildShapesButtons() {
		shapesButton = new JPanel();
		shapesButton.setLayout(new FlowLayout());		
		
		JButton bText = new JButton("Text");
		bText.setPreferredSize(new Dimension(100,50));
		bText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SText tmp = new SText();
				tmp.addAttributes(new SelectionAttributes());
				tmp.addAttributes(new ColorAttributes(true,true,Color.RED,Color.GREEN));
				tmp.addAttributes(new FontAttributes());
				model.add(tmp);
				repaint();
			}
		});
		
		//System.out.println(Paths.get("").toAbsolutePath().toString());
		/*ImageIcon rectangleIcon = new ImageIcon(Paths.get("").toAbsolutePath().toString()+"/src/rectangle.jpg");
		JButton bRectangle = new JButton(rectangleIcon);*/ // Pour rajouter une image aux bouttons
		JButton bRectangle = new JButton("Rectangle");
		bRectangle.setPreferredSize(new Dimension(100,50));
		bRectangle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SRectangle tmp = new SRectangle();
				tmp.addAttributes(new SelectionAttributes());
				tmp.addAttributes(new ColorAttributes(true,true,Color.BLUE,Color.BLACK));
				model.add(tmp);
				repaint();
			}
		});
		
		JButton bCircle = new JButton("Circle");
		bCircle.setPreferredSize(new Dimension(100,50));
		bCircle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SCircle tmp = new SCircle();
				tmp.addAttributes(new SelectionAttributes());
				tmp.addAttributes(new ColorAttributes(true,true,Color.GRAY,Color.BLACK));
				model.add(tmp);
				repaint();
			}
		});

		JButton bPolygon = new JButton("Polygon");
		bPolygon.setPreferredSize(new Dimension(100, 50));
		bPolygon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SPolygon tmp = new SPolygon();
				tmp.addAttributes(new SelectionAttributes());
				tmp.addAttributes(new ColorAttributes(true,true,Color.GREEN,Color.BLUE));
				model.add(tmp);
				repaint();
			}
		});
		
		shapesButton.add(bText);
		shapesButton.add(bRectangle);
		shapesButton.add(bCircle);
		shapesButton.add(bPolygon);
	}
	
	/**
	 * création du panel permettant de modifier les paramètres des formes
	 */
	public void buildShapesManager() {
		this.shapesManager = new JPanel();
		this.shapesManager.setLayout(new GridLayout(4, 1));
		buildTextManager(this.shapesManager);
		buildRectangleManager(this.shapesManager);
		buildCircleManager(this.shapesManager);
		buildPolygonManager(this.shapesManager);
	}
	
	//TODO//rajouter keylistener pour tous les composants
	public void buildTextManager(JPanel shapesManager) {
		JPanel textManager = new JPanel();
		textManager.setBorder(BorderFactory.createTitledBorder("Text"));
		textManager.setLayout(new GridLayout(6, 2));
		
		textManager.add(new JLabel("Text : "));
		textString = new JTextField();
		textManager.add(textString);
		
		textManager.add(new JLabel("Font name: "));		//pb : on ne définit pas le style de font
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fonts = ge.getAvailableFontFamilyNames();
		fontName = new JComboBox<>(fonts);
		textManager.add(fontName);
		
		//pour changer le style d'un JTextField "field" : field.setFont(field.getFont().deriveFont(Font.BOLD | Font.ITALIC));
		
		/*		enlever aussi le commentaire dans les attributs : private JComboBox<Font> fontStyle;
		 * 		déforme le leftComponent 
		textManager.add(new JLabel("Font name and style: "));
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] styles = ge.getAllFonts();
		fontStyle = new JComboBox<>(styles);
		textManager.add(fontStyle);
		*/
		textManager.add(new JLabel("Font size : "));
		Integer[] sizes = new Integer[101]; sizes[0] = 12;
		for(int i = 1; i <= 100; i++) {
			sizes[i] = sizes[i-1] + 1;
		}
		fontSize = new JComboBox<>(sizes);
		textManager.add(fontSize);
		
		textManager.add(new JLabel("Font color : "));
		fontColor = new JButton();
		fontColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = fontColor.getBackground();
				Color background = JColorChooser.showDialog(null, "Font color for text", initialBackground);
				if (background != null) {
					fontColor.setBackground(background);
		        }
			}
		});
		textManager.add(fontColor);
		
		textManager.add(new JLabel("Stroked color : "));
		textStrokedColor = new JButton();
		textStrokedColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = textStrokedColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Stroked color for text", initialBackground);
		        if (background != null) {
		          textStrokedColor.setBackground(background);
		        }
			}
		});
		textManager.add(textStrokedColor);
		
		textManager.add(new JLabel("Filled color : "));
		textFilledColor = new JButton();
		textFilledColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = textFilledColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Background color for text", initialBackground);
		        if (background != null) {
		        	textFilledColor.setBackground(background);
		        }
			}
		});
		textManager.add(textFilledColor);

		shapesManager.add(textManager);
	}
	
	public void buildRectangleManager(JPanel shapesManager) {
		JPanel rectangleManager = new JPanel();
		rectangleManager.setBorder(BorderFactory.createTitledBorder("Rectangle"));
		rectangleManager.setLayout(new GridLayout(4, 2));
		
		rectangleManager.add(new JLabel("Width : "));
		widthRectangle = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		rectangleManager.add(widthRectangle);
		
		rectangleManager.add(new JLabel("Height : "));
		heightRectanlge = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		rectangleManager.add(heightRectanlge);
		
		rectangleManager.add(new JLabel("Stroked color : "));
		strokedRectColor = new JButton();
		strokedRectColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = strokedRectColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Stroked color for Rectangle", initialBackground);
		        if (background != null) {
		        	strokedRectColor.setBackground(background);
		        }
			}
		});
		rectangleManager.add(strokedRectColor);
		
		rectangleManager.add(new JLabel("Filled color : "));
		filledRectColor = new JButton();
		filledRectColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = filledRectColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Filled color for Rectangle", initialBackground);
		        if (background != null) {
		        	filledRectColor.setBackground(background);
		        }
			}
		});
		rectangleManager.add(filledRectColor);
		
		//rectangleManager.add(panel, BorderLayout.LINE_START);
		shapesManager.add(rectangleManager);		
	}

	public void buildCircleManager(JPanel shapesManager) {
		JPanel circleManager = new JPanel();
		circleManager.setBorder(BorderFactory.createTitledBorder("Circle"));
		circleManager.setLayout(new GridLayout(3, 2));

		circleManager.add(new JLabel("Radius : "));
		radiusCircle = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		circleManager.add(radiusCircle);
		
		circleManager.add(new JLabel("Stroked color : "));
		strokedCircleColor = new JButton();
		strokedCircleColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = strokedCircleColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Stroked color for Circle", initialBackground);
		        if (background != null) {
		        	strokedCircleColor.setBackground(background);
		        }
			}
		});
		circleManager.add(strokedCircleColor);
		
		circleManager.add(new JLabel("Filled color : "));
		filledCircleColor = new JButton();
		filledCircleColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = filledCircleColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Filled color for Circle", initialBackground);
		        if (background != null) {
		        	filledCircleColor.setBackground(background);
		        }
			}
		});
		circleManager.add(filledCircleColor);
		
		shapesManager.add(circleManager);
	}
	
	public void buildPolygonManager(JPanel shapesManager) {
		JPanel polygonManager = new JPanel();
		polygonManager.setBorder(BorderFactory.createTitledBorder("Polygon"));
		polygonManager.setLayout(new GridLayout(5, 2));
		
		polygonManager.add(new JLabel("nombre de points : "));
		nbpoints = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		polygonManager.add(nbpoints);
		
		//TODO
		polygonManager.add(new JLabel("x : "));
		for (int i = 1; i <= (int) nbpoints.getValue(); i++) {
			x[i] = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
			polygonManager.add(x[i]);
		}
		polygonManager.add(new JLabel("y : "));
		for (int i = 1; i <= (int) nbpoints.getValue(); i++) {
			y[i] = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
			y[i+1] = new JFormattedTextField();
			polygonManager.add(y[i]);
		}
		/*polygonManager.add(new JLabel("x : "));
		x[0] = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		polygonManager.add(x[0]);
		polygonManager.add(new JLabel("y : "));
		y[0] = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		polygonManager.add(y[0]);
		
		for (int i = 1; i <= (int) nbpoints.getValue(); i++) {
			polygonManager.add(new JLabel("x%i : ", i));
			x[i] = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
			polygonManager.add(x[i]);
			polygonManager.add(new JLabel("y%i : ", i));
			y[i] = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
			polygonManager.add(y[i]);
		}*/
		
		polygonManager.add(new JLabel("Stroked color : "));
		strokedPolygonColor = new JButton();
		strokedPolygonColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = strokedPolygonColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Stroked color for Polygon", initialBackground);
		        if (background != null) {
		        	strokedPolygonColor.setBackground(background);
		        }
			}
		});
		polygonManager.add(strokedPolygonColor);
		
		polygonManager.add(new JLabel("Filled color : "));
		filledPolygonColor = new JButton();
		filledPolygonColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = filledPolygonColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Filled color for Polygon", initialBackground);
		        if (background != null) {
		        	filledPolygonColor.setBackground(background);
		        }
			}
		});
		polygonManager.add(filledPolygonColor);
		
		shapesManager.add(polygonManager);
	}
	
	//public void addListenerColor  (shaep)
	
	public void refreshListener() {
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Iterator<Shape> it = model.iterator(); it.hasNext();) {
					Shape shape = (Shape)it.next();
					SelectionAttributes selection = (SelectionAttributes)shape.getAttributes(SelectionAttributes.SELECTION_ID);
					if(selection.isSelected()) {
						if(shape.getClass() == SText.class) setText((SText)shape);
						if(shape.getClass() == SRectangle.class) setRectangle((SRectangle)shape);
						if(shape.getClass() == SCircle.class) setCircle((SCircle)shape);
						if(shape.getClass() == SPolygon.class) setPolygon ((SPolygon)shape);
					}
				}
				repaint();
			}
		});
	}
	
	public void setText(SText sText) {
		sText.setText(this.textString.getText());
		FontAttributes font = (FontAttributes)sText.getAttributes(FontAttributes.FONT_ID);
		if(font == null) {
			FontAttributes fontAttributes = new FontAttributes(new Font((String)fontName.getSelectedItem(), Font.PLAIN, (int)fontSize.getSelectedItem()), fontColor.getBackground());
			sText.addAttributes(fontAttributes);
		}
		else {
			font.setFont(new Font((String)fontName.getSelectedItem(), Font.BOLD ,(int)fontSize.getSelectedItem()));
			font.setFontColor(fontColor.getBackground());
		}
		ColorAttributes color = (ColorAttributes)sText.getAttributes(ColorAttributes.COLOR_ID);
		if (color == null) {
			ColorAttributes colorAttributes = new ColorAttributes(true, true, this.textFilledColor.getBackground(), textStrokedColor.getBackground());
			sText.addAttributes(colorAttributes);
		}
		else {
			color.setFilledColor(this.textFilledColor.getBackground());
			color.setStrokedColor(this.textStrokedColor.getBackground());
		}
		
	}
	
	public void setRectangle(SRectangle sRectangle) {
		sRectangle.setWidth(Integer.parseInt(this.widthRectangle.getText()));
		sRectangle.setHeight(Integer.parseInt(this.heightRectanlge.getText()));
		ColorAttributes color = (ColorAttributes)sRectangle.getAttributes(ColorAttributes.COLOR_ID);
		if(color == null) {
			ColorAttributes colorAttributes = new ColorAttributes(true, true, filledRectColor.getBackground(), strokedRectColor.getBackground());
			sRectangle.addAttributes(colorAttributes);
		}
		else {
			color.setFilledColor(filledRectColor.getBackground());
			color.setStrokedColor(strokedRectColor.getBackground());
		}
	}
	
	public void setCircle(SCircle sCircle) {
		sCircle.setRadius(Integer.parseInt(this.radiusCircle.getText()));
		ColorAttributes color = (ColorAttributes)sCircle.getAttributes(ColorAttributes.COLOR_ID);
		if(color == null) {
			ColorAttributes colorAttributes = new ColorAttributes(true, true, filledCircleColor.getBackground(), strokedCircleColor.getBackground());
			sCircle.addAttributes(colorAttributes);
		}
		else {
			color.setFilledColor(filledCircleColor.getBackground());
			color.setStrokedColor(strokedCircleColor.getBackground());
		}
	}
	
	public void setPolygon(SPolygon sPolygon) {
		int nbpoints = Integer.parseInt(this.nbpoints.getText());
		sPolygon.setnPoints(nbpoints);
		int x [] = new int[nbpoints];
		int y [] = new int[nbpoints];
		for(int i = 0; i < nbpoints; i++) {
			x[i] = Integer.parseInt(this.x[i].getText());
			y[i] = Integer.parseInt(this.y[i].getText());
		}
		
		ColorAttributes color = (ColorAttributes)sPolygon.getAttributes(ColorAttributes.COLOR_ID);
		if(color == null) {
			ColorAttributes colorAttributes = new ColorAttributes(true, true, filledPolygonColor.getBackground(), strokedPolygonColor.getBackground());
			sPolygon.addAttributes(colorAttributes);
		}
		else {
			color.setFilledColor(filledPolygonColor.getBackground());
			color.setStrokedColor(strokedPolygonColor.getBackground());
		}
	}
	
	/**
	 * Création du model : initialisation de la SCollection
	 */
	private void buildModel()
	{
		this.model = new SCollection();
		this.model.addAttributes(new SelectionAttributes());
	}
	
	public static void main(String[] args)
	{
		Editor self = new Editor();
		self.pack();
		self.setVisible(true);
		self.setLocationRelativeTo(null);
		ShapesJson jsonShapes = new ShapesJson();
		jsonShapes.readShapesFromJson("src/jsonFiles/oneRectangle.json");
		jsonShapes.readShapesFromJson("src/jsonFiles/oneCercle.json");
		jsonShapes.readShapesFromJson("src/jsonFiles/oneTexte.json");
		jsonShapes.readShapesFromJson("src/jsonFiles/oneCollection.json");
		jsonShapes.readShapesFromJson("src/jsonFiles/test.json");
		jsonShapes.readShapesFromJson("src/jsonFiles/onePolygon.json");
	}
}
