package graphics.shapes.ui;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
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
import java.util.ArrayList;
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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
	private JButton refresh, delete;
	private JTextField textString;
	private JFormattedTextField widthRectangle, heightRectanlge,  radiusCircle;
	private JComboBox<Integer> textSize;
	private JComboBox<String> textFont;
	private JButton textColor, textBackgroundColor, strokedRectColor, filledRectColor, strokedCircleColor, filledCircleColor;
	
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
		this.middleComponnent.add(delete, BorderLayout.PAGE_END);
	}	
	
	/**
	 * Construction des boutons pour générer les formes
	 * Pour l'ajout de nouvelles formes rajouter dans cette méthodes la création d'un nouveau boutton
	 */
	public void buildShapesButtons() {
		shapesButton = new JPanel();
		shapesButton.setLayout(new FlowLayout());		
		
		JButton bText = new JButton("Text");
		bText.setPreferredSize(new Dimension(100,100));
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
		bRectangle.setPreferredSize(new Dimension(100,100));
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
		bCircle.setPreferredSize(new Dimension(100,100));
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

		/*JButton bPolygon = new JButton("Polygon");
		bPolygon.setSize(new Dimension(50, 50));
		bPolygon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SPolygon tmp = new SPolygon();
				tmp.addAttributes(defaultSelection);
				tmp.addAttributes(defaultColor);
				model.add(tmp);
				repaint();
			}
		});*/
		
		shapesButton.add(bText);
		shapesButton.add(bRectangle);
		shapesButton.add(bCircle);
		//shapesButton.add(bPolygon);
	}
	
	/**
	 * création du panel permettant de modifier les paramètres des formes
	 */
	public void buildShapesManager() {
		this.shapesManager = new JPanel();
		this.shapesManager.setLayout(new GridLayout(3, 1));
		buildTextManager(this.shapesManager);
		buildRectangleManager(this.shapesManager);
		buildCircleManager(this.shapesManager);
	}
	
	//rajouter keylistener pour tous les composants
	public void buildTextManager(JPanel shapesManager) {
		JPanel textManager = new JPanel();
		textManager.setLayout(new BorderLayout());
		
		textManager.setBorder(BorderFactory.createTitledBorder("Text"));
		textManager.setLayout(new GridLayout(5, 2));
		
		textManager.add(new JLabel("Text : "));
		textString = new JTextField(); textManager.add(textString);
		
		textManager.add(new JLabel("Font : "));
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fonts = ge.getAvailableFontFamilyNames();
		textFont = new JComboBox<>(fonts); textManager.add(textFont);
		
		textManager.add(new JLabel("Size : "));
		Integer[] sizes = new Integer[101]; sizes[0] = 12;
		for(int i = 1; i <= 100; i++) {
			sizes[i] = sizes[i-1] + 1;
		}
		textSize = new JComboBox<>(sizes); textManager.add(textSize);
		
		textManager.add(new JLabel("Stroked color : "));
		textColor = new JButton();
		textColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = textColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Font color for text",
		            initialBackground);
		        if (background != null) {
		          textColor.setBackground(background);
		        }
			}
		}); textManager.add(textColor);
		
		textManager.add(new JLabel("Filled color : "));
		textBackgroundColor = new JButton();
		textBackgroundColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = textBackgroundColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Background color for text",
		            initialBackground);
		        if (background != null) {
		        	textBackgroundColor.setBackground(background);
		        }
			}
		}); textManager.add(textBackgroundColor);

		shapesManager.add(textManager);
	}
	
	public void buildRectangleManager(JPanel shapesManager) {
		JPanel rectangleManager = new JPanel();
		rectangleManager.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Rectangle"));
		panel.setLayout(new GridLayout(4, 2));
		
		panel.add(new JLabel("Width : "));
		widthRectangle = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		panel.add(widthRectangle);
		
		panel.add(new JLabel("Height : "));
		heightRectanlge = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		panel.add(heightRectanlge);
		
		panel.add(new JLabel("Stroked color : "));
		strokedRectColor = new JButton();
		strokedRectColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = strokedRectColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Stroked color for Rectanlge",
		            initialBackground);
		        if (background != null) {
		        	strokedRectColor.setBackground(background);
		        }
			}
		}); panel.add(strokedRectColor);
		
		panel.add(new JLabel("Filled color : "));
		filledRectColor = new JButton();
		filledRectColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = filledRectColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Filled color for Rectanlge",
		            initialBackground);
		        if (background != null) {
		        	filledRectColor.setBackground(background);
		        }
			}
		}); panel.add(filledRectColor);
		
		rectangleManager.add(panel, BorderLayout.LINE_START);
		shapesManager.add(rectangleManager);		
	}

	public void buildCircleManager(JPanel shapesManager) {
		JPanel circleManager = new JPanel();
		circleManager.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Circle"));
		panel.setLayout(new GridLayout(3, 2));

		panel.add(new JLabel("Radius : "));
		radiusCircle = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		panel.add(radiusCircle);
		
		panel.add(new JLabel("Stroked color : "));
		strokedCircleColor = new JButton();
		strokedCircleColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = strokedCircleColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Stroked color for Circle",
		            initialBackground);
		        if (background != null) {
		        	strokedCircleColor.setBackground(background);
		        }
			}
		}); panel.add(strokedCircleColor);
		
		
		panel.add(new JLabel("Filled color : "));
		filledCircleColor = new JButton();
		filledCircleColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialBackground = filledCircleColor.getBackground();
		        Color background = JColorChooser.showDialog(null, "Filled color for Circle",
		            initialBackground);
		        if (background != null) {
		        	filledCircleColor.setBackground(background);
		        }
			}
		}); panel.add(filledCircleColor);
		

		circleManager.add(panel, BorderLayout.LINE_START);
		shapesManager.add(circleManager);
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
			FontAttributes fontAttributes = new FontAttributes(new Font((String)textFont.getSelectedItem(), Font.PLAIN, (int)textSize.getSelectedItem()), textFont.getBackground());
			sText.addAttributes(fontAttributes);
		}
		else {
			font.setFont(new Font((String)textFont.getSelectedItem(), Font.BOLD ,(int)textSize.getSelectedItem()));
			font.setFontColor(textFont.getBackground());
		}
		ColorAttributes color = (ColorAttributes)sText.getAttributes(ColorAttributes.COLOR_ID);
		if(color == null) {
			ColorAttributes colorAttributes = new ColorAttributes(true, true, this.textBackgroundColor.getBackground(), textColor.getBackground());
			sText.addAttributes(colorAttributes);
		}
		else {
			color.setFilledColor(this.textBackgroundColor.getBackground());
			color.setStrokedColor(this.textColor.getBackground());
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
	}
}
