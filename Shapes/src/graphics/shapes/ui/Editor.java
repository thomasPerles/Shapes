package graphics.shapes.ui;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
//import graphics.shapes.SPolygon;
import graphics.shapes.SPolygonRegulier;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.shapes.attributes.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
	private Color defaultStroked = Color.BLUE, defaultFilled = Color.WHITE, defaultText = Color.GRAY;
	private JPanel leftComponnent, rightComponnent, middleComponnent;
	private JPanel shapesManager, shapesButton;
	private JButton refresh, delete, deleteAll, group,split;
	private JTextField textString;
	private JFormattedTextField widthRectangle, heightRectanlge, radiusCircle, nbpoints, radiusPolygonRegulier;
	private JComboBox<Integer> fontSize;
	private JComboBox<String> fontName;
	private JButton fontColor, textStrokedColor, textFilledColor, strokedRectColor, filledRectColor, strokedCircleColor, filledCircleColor, strokedPolygonColor, filledPolygonColor;
	
	public Editor()
	{	
		super("Shapes Editor");

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
	}
	
	public void buildView() {
		this.buildLeftComponnent();
		this.buildMiddleComponnent();
		middleComponnent.setBorder(BorderFactory.createMatteBorder(0,1,0,1,new Color(122, 122, 119)));
		this.buildRightComponnent();
		
		this.setLayout(new BorderLayout());
		leftComponnent.setPreferredSize(new Dimension(300, 200));
		this.add(leftComponnent, BorderLayout.LINE_START);
		this.add(middleComponnent, BorderLayout.CENTER);
		this.add(rightComponnent, BorderLayout.LINE_END);
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
		refresh = new JButton("Refresh");
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
		liste.setCellRenderer(new ListeCellRenderer());
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

		delete = new JButton("Delete");
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
		
		deleteAll = new JButton("Delete all");
		deleteAll.setSize(new Dimension(50, 50));
		deleteAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setShapesCollection(new ArrayList<>());
				repaint();
			}
		});
		
		group = new JButton("Group");
		group.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SCollection newCollection = new SCollection();
				newCollection.addAttributes(new SelectionAttributes());
				for(Iterator<Shape> it = model.iterator(); it.hasNext();) {
					Shape s  = (Shape)it.next();
					SelectionAttributes selection = (SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID);
					if(selection.isSelected()) {
						selection.toggleSelection();
						newCollection.add(s);
					}
				}
				model.getShapesCollection().removeAll(newCollection.getShapesCollection());
				model.add(newCollection);
				repaint();
			}
		});
		
		split = new JButton("Split");
		split.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<SCollection> listSCollection = new ArrayList<SCollection>();
				for(Iterator<Shape> it = model.iterator(); it.hasNext();) {
					Shape s  = (Shape)it.next();
					SelectionAttributes selection = (SelectionAttributes)s.getAttributes(SelectionAttributes.SELECTION_ID);
					if(selection.isSelected() && ((SCollection)s).isCollection()) {
						listSCollection.add((SCollection)s);
						it.remove();
					}
				}
				for(SCollection sc : listSCollection) {
					for(Iterator<Shape> it = sc.iterator(); it.hasNext();) {
						Shape s = (Shape) it.next();
						s.addAttributes(new SelectionAttributes());
						model.add(s);
					}
				}
				repaint();
				
			}
		});
		
		middleButtons.add(group);
		middleButtons.add(split);
		middleButtons.add(delete);
		middleButtons.add(deleteAll);
		
		
		this.middleComponnent.add(middleButtons, BorderLayout.PAGE_START);
	}	
	
	/**
	 * Construction des boutons pour générer les formes
	 * Pour l'ajout de nouvelles formes rajouter dans cette méthodes la création d'un nouveau boutton
	 */
	public void buildShapesButtons() {
		shapesButton = new JPanel();
		shapesButton.setLayout(new FlowLayout());		
		
		Icon iText = new ImageIcon("src/img/text.png");
		JButton bText = new JButton(iText);
		bText.setBackground(new Color(192, 250, 206));
		bText.setPreferredSize(new Dimension(55,55));
		bText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SText tmp = new SText();
				tmp.addAttributes(new SelectionAttributes());
				tmp.addAttributes(new ColorAttributes(true,true,defaultFilled,defaultStroked));
				tmp.addAttributes(new FontAttributes());
				model.add(tmp);
				repaint();
			}
		});

		Icon iRectangle = new ImageIcon("src/img/rectangle.png");
		JButton bRectangle = new JButton(iRectangle);
		bRectangle.setBackground(new Color(250, 194, 187));
		bRectangle.setPreferredSize(new Dimension(55,55));
		bRectangle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SRectangle tmp = new SRectangle();
				tmp.addAttributes(new SelectionAttributes());
				tmp.addAttributes(new ColorAttributes(true,true,defaultFilled,defaultStroked));
				model.add(tmp);
				repaint();
			}
		});

		Icon iCircle = new ImageIcon("src/img/circle.png");
		JButton bCircle = new JButton(iCircle);
		bCircle.setBackground(new Color(171, 228, 247));
		bCircle.setPreferredSize(new Dimension(55,55));
		bCircle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SCircle tmp = new SCircle();
				tmp.addAttributes(new SelectionAttributes());
				tmp.addAttributes(new ColorAttributes(true,true,defaultFilled,defaultStroked));
				model.add(tmp);
				repaint();
			}
		});

		Icon iPolygon = new ImageIcon("src/img/polygon.png");
		JButton bPolygonRegulier = new JButton(iPolygon);
		bPolygonRegulier.setBackground(new Color(249, 252, 159));
		bPolygonRegulier.setPreferredSize(new Dimension(55, 55));
		bPolygonRegulier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SPolygonRegulier tmp = new SPolygonRegulier();
				tmp.addAttributes(new SelectionAttributes());
				tmp.addAttributes(new ColorAttributes(true,true,defaultFilled,defaultStroked));
				model.add(tmp);
				repaint();
			}
		});
		
		shapesButton.add(bText);
		shapesButton.add(bRectangle);
		shapesButton.add(bCircle);
		//TODO //shapesButton.add(bPolygon);
		shapesButton.add(bPolygonRegulier);
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
		buildPolygonRegulierManager(this.shapesManager);
	}
	
	//TODO//rajouter keylistener pour tous les composants
	public void buildTextManager(JPanel shapesManager) {
		JPanel textManager = new JPanel();
		textManager.setBackground(new Color(192, 250, 206));
		textManager.setBorder(BorderFactory.createTitledBorder("Text"));
		textManager.setLayout(new GridLayout(6, 2));
		
		textManager.add(new JLabel("Text : "));
		textString = new JTextField("Hello !!!");
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
		fontColor.setBackground(defaultText);
		fontColor.setPreferredSize(new Dimension(100, 25));
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
		textStrokedColor.setBackground(defaultStroked);
		textStrokedColor.setPreferredSize(new Dimension(100, 25));
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
		textFilledColor.setBackground(defaultFilled);
		textFilledColor.setPreferredSize(new Dimension(100, 25));
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
		rectangleManager.setBackground(new Color(250, 194, 187));
		rectangleManager.setBorder(BorderFactory.createTitledBorder("Rectangle"));
		rectangleManager.setLayout(new GridLayout(6, 2));
		
		rectangleManager.add(new JLabel("Width : "));
		widthRectangle = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		widthRectangle.setValue(75);
		rectangleManager.add(widthRectangle);
		
		rectangleManager.add(new JLabel("Height : "));
		heightRectanlge = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		heightRectanlge.setValue(50);
		rectangleManager.add(heightRectanlge);
		
		rectangleManager.add(new JLabel("Stroked color : "));
		strokedRectColor = new JButton();
		strokedRectColor.setBackground(defaultStroked);
		strokedRectColor.setPreferredSize(new Dimension(100, 25));
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
		filledRectColor.setBackground(defaultFilled);
		filledRectColor.setPreferredSize(new Dimension(100, 25));
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
		rectangleManager.add(new JLabel(""));
		rectangleManager.add(new JLabel(""));
		rectangleManager.add(new JLabel(""));
		rectangleManager.add(new JLabel(""));
		
		//rectangleManager.add(panel, BorderLayout.LINE_START);
		shapesManager.add(rectangleManager);		
	}

	public void buildCircleManager(JPanel shapesManager) {
		JPanel circleManager = new JPanel();
		circleManager.setBackground(new Color(171, 228, 247));;
		circleManager.setBorder(BorderFactory.createTitledBorder("Circle"));
		circleManager.setLayout(new GridLayout(6, 2));

		circleManager.add(new JLabel("Radius : "));
		radiusCircle = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		radiusCircle.setValue(50);
		circleManager.add(radiusCircle);
		
		circleManager.add(new JLabel("Stroked color : "));
		strokedCircleColor = new JButton();
		strokedCircleColor.setBackground(defaultStroked);
		strokedCircleColor.setPreferredSize(new Dimension(100, 25));
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
		filledCircleColor.setBackground(defaultFilled);
		filledCircleColor.setPreferredSize(new Dimension(100, 25));
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
		circleManager.add(new JLabel(""));
		circleManager.add(new JLabel(""));
		circleManager.add(new JLabel(""));
		
		shapesManager.add(circleManager);
	}
	
	public void buildPolygonRegulierManager(JPanel shapesManager) {
		JPanel polygonManager = new JPanel();
		polygonManager.setBackground(new Color(249, 252, 159));
		polygonManager.setBorder(BorderFactory.createTitledBorder("Polygon Régulier"));
		polygonManager.setLayout(new GridLayout(6, 2));
		
		polygonManager.add(new JLabel("nombre de points : "));
		nbpoints = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		nbpoints.setValue(6);
		polygonManager.add(nbpoints);
		
		polygonManager.add(new JLabel("radius : "));
		radiusPolygonRegulier = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		radiusPolygonRegulier.setValue(50);
		polygonManager.add(radiusPolygonRegulier);
		
		polygonManager.add(new JLabel("Stroked color : "));
		strokedPolygonColor = new JButton();
		strokedPolygonColor.setBackground(defaultStroked);
		strokedPolygonColor.setPreferredSize(new Dimension(100, 25));
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
		filledPolygonColor.setBackground(defaultFilled);
		filledPolygonColor.setPreferredSize(new Dimension(100, 25));
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
		polygonManager.add(new JLabel(""));
		polygonManager.add(new JLabel(""));
		
		shapesManager.add(polygonManager);
	}
	
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
						if(shape.getClass() == SPolygonRegulier.class) setPolygonRegulier ((SPolygonRegulier)shape);
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
	/*
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
	*/
	public void setPolygonRegulier(SPolygonRegulier sPolygonRegulier) {
		int nbpoints = Integer.parseInt(this.nbpoints.getText());
		sPolygonRegulier.setnPoints(nbpoints);
		int radius = Integer.parseInt(this.radiusPolygonRegulier.getText());
		sPolygonRegulier.setRadius(radius);
		
		ColorAttributes color = (ColorAttributes)sPolygonRegulier.getAttributes(ColorAttributes.COLOR_ID);
		if(color == null) {
			ColorAttributes colorAttributes = new ColorAttributes(true, true, filledPolygonColor.getBackground(), strokedPolygonColor.getBackground());
			sPolygonRegulier.addAttributes(colorAttributes);
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
	
	 private static class ListeCellRenderer extends DefaultListCellRenderer {
	        public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
	            Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
	            if ( index % 2 == 0 ) {
	                c.setBackground(new Color(245,218,243));
	            }
	            else {
	                c.setBackground(new Color(216,236,242));
	            }
	            return c;
	        }
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
		jsonShapes.readShapesFromJson("src/jsonFiles/onePolygonRegulier.json");
	}
}
