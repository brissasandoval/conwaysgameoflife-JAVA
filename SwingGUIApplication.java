package SwingGUIHW;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Serialization.Rectangle;
import SwingGUIHW.UpdatedDisplay;

//start with application given to us, set them both side by side 

public class SwingGUIApplication extends JFrame {

	// -- serialization identifier -- system generated
	private static final long serialVersionUID = -6167569334213042018L;

	// -- set the size of the JFrame. JPanels will adapt to this size
	private final int WIDTH = 500;
	private final int HEIGHT = 500;

	private Timer animationTimer = null;

	private GraphicPanelInner graphicsPanel;
	private ControlPanelInner controlPanel;
	private BottomPanel Bot;
	public int RowInput = 15;
	public int ColInput = 15;
	public boolean isPressed;
	public boolean GridCheckedBox;
	public boolean CellCheckedBox;
	public boolean WrapCheckedBox;
//    public boolean mouseclicked;

//    UpdatedDisplay implement = new UpdatedDisplay(graphicsPanel);
	UpdatedDisplay ConwaysGameofLife;// = new UpdatedDisplay(graphicsPanel);

	int h = 0;
	int w = 0;
	// public int[][]grid = new int[RowInput][ColInput];//two dimensional array

	public JButton textareaButton;
//    private ControlPanelOuter controlPanel1;

//	UpdatedDisplay UpdateDisplay = new UpdatedDisplay(graphicsPanel);

	public SwingGUIApplication() {
		// -- construct the base JFrame first
		super("Java Swing Application");

		// -- set the application title
		this.setTitle("Java Swing Application (New Title)");

		// -- initial size of the frame: width, height
		this.setSize(WIDTH, HEIGHT);

		// -- center the frame on the screen
		this.setLocationRelativeTo(null);

		// -- shut down the entire application when the frame is closed
		// if you don't include this the application will continue to
		// run in the background and you'll have to kill it by pressing
		// the red square in eclipse
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// -- set the layout manager and add items
		// 5, 5 is the border around the edges of the areas
		setLayout(new BorderLayout(5, 5));

		// -- construct a JPanel for graphics
		graphicsPanel = new GraphicPanelInner();
		this.add(graphicsPanel, BorderLayout.CENTER);
		ConwaysGameofLife = new UpdatedDisplay(graphicsPanel, 15, 15);
		//Dr. Reinhart -- removed this call as there is no setGraphics in UpdateDisplay
		//UpdateDisplay.setGraphics(graphicsPanel);

		// -- construct a JPanel for controls
		controlPanel = new ControlPanelInner();
		this.add(controlPanel, BorderLayout.WEST);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JButton("SOUTH"), BorderLayout.SOUTH);

		// -- construct a JPanel for controls
		Bot = new BottomPanel();
		this.add(Bot, BorderLayout.SOUTH);

		// -- Timer will generate an event every 10mSec once it is started
		// First parameter is the delay in mSec, second is the ActionListener
		// that will handle the timer events
//		animationTimer = new Timer(1000, // mSec
//				// -- ActionListener (event handler) for the timer event
//				// an example of Real-Time Programming
//				// events occur at arbitrary times and our program
//				// must be prepared to deal with them
//				new ActionListener() {
//					public void actionPerformed(ActionEvent arg0) {
//						System.out.println("tic");
//						UpdateDisplay.universecalc();
//
//						graphicsPanel.repaint();
//					}
//				});

		// -- can make it so the user cannot resize the frame
		this.setResizable(false);

		// -- paint the graphics canvas before the initial display
		graphicsPanel.repaint();

		// -- show the frame on the screen
		this.setVisible(true);

		// -- set keyboard focus to the graphics panel
		graphicsPanel.setFocusable(true);
		graphicsPanel.requestFocus();

	}

	// -- Inner class for the graphics panel
	// inner classes are convenient as they have full access to all
	// outer (containing) class member variables and functions regardless of access
	// modifier. Their down side is that they cannot be instantiated by
	// other, separate classes. They should only be used if they are not
	// going to be used by other applications (not involved in code reuse)
	public class GraphicPanelInner extends JPanel implements MouseMotionListener {

		private static final long serialVersionUID = 7056793999538384084L;
		public int Xcord;
		public int Ycord;

//        public boolean mouseclicked;

		public GraphicPanelInner() {
			super();
			// sets default background color
			this.setBackground(Color.YELLOW);
			this.prepareActionHandlers();
			this.addMouseMotionListener(this);
		}

		private void prepareActionHandlers() {
			// -- The JPanel can have a mouse listener if desired
			// note that the listener is an anonymous object (it has no specified reference)
			this.addMouseListener(new MouseListener() {

				@Override
				// -- mouse button when up and down without moving the cursor
				public void mouseClicked(MouseEvent event) {
					
					if (CellCheckedBox) {

						if (CellCheckedBox) {
							double horzs = graphicsPanel.getWidth() / (double) ColInput;
							double verts = graphicsPanel.getHeight() / (double) RowInput;

							// compute the row and column in the 2D grid array
//                        					grid[(int)(event.getX() / verts)][(int)(event.getY() / horzs)] = 1;
							//UpdateDisplay.universe[0][(int) (event.getY() / verts)][(int) (event.getX() / horzs)] = 1;
							ConwaysGameofLife.setCell((int) (event.getY() / verts), (int) (event.getX() / horzs), true);
							System.out.println("Mouse Clicked at (" + (event.getX() / horzs) + ", "
									+ (event.getY() / verts) + ")");

						} else {
							double horzs = graphicsPanel.getWidth() / (double) ColInput;
							double verts = graphicsPanel.getHeight() / (double) RowInput;

//                         					grid[(int)(event.getX() / verts)][(int)(event.getY() / horzs)] = 0;

							
							//UpdateDisplay.universe[0][(int) (event.getY() / verts)][(int) (event.getX() / horzs)] = 0;
							ConwaysGameofLife.setCell((int) (event.getY() / verts), (int) (event.getX() / horzs), false);

						}
						
						graphicsPanel.repaint();
					}

				}

				@Override
				// -- where the cursor entered the window
				public void mouseEntered(MouseEvent event) {
					System.out.println("Mouse Entered at (" + event.getX() + ", " + event.getY() + ")");
				}

				@Override
				// -- where the cursor exited the window
				public void mouseExited(MouseEvent event) {
					System.out.println("Mouse Exited at (" + event.getX() + ", " + event.getY() + ")");
				}

				@Override
				// -- when a button is pressed
				public void mousePressed(MouseEvent event) {

					// -- BUTTON1 is the left, BUTTON3 is the right
					if (event.getButton() == MouseEvent.BUTTON1) {
						System.out.println("Left button pressed");
					} else if (event.getButton() == MouseEvent.BUTTON3) {
						System.out.println("Right button pressed");
					}

				}

				@Override
				// -- when a button is released
				public void mouseReleased(MouseEvent event) {
					// -- BUTTON1 is the left, BUTTON3 is the right
					if (event.getButton() == MouseEvent.BUTTON1) {
						System.out.println("Left button released");
					} else if (event.getButton() == MouseEvent.BUTTON3) {
						System.out.println("Right button released");
					}
					graphicsPanel.requestFocus();
					repaint();
				}

			});

			// -- keyboard listener
			// note that the JPanel must have focus for these to
			// generate events. You can click the mouse in the JPanel
			// or call graphicsPanel.requestFocus();
			// listener is created as an anonymous object
			this.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent event) {
					System.out.println("key pressed: " + event.getKeyCode());
					graphicsPanel.repaint();
				}

				@Override
				public void keyPressed(KeyEvent event) {
					System.out.println("key pressed: " + event.getKeyCode());
					graphicsPanel.repaint();
				}

				@Override
				public void keyReleased(KeyEvent event) {
					System.out.println("key released: " + event.getKeyCode());
					graphicsPanel.repaint();
				}

			});
		}

//        private  void settingCell(int x, int y) {
//        	  int h = this.getHeight();
//        	  int w = this.getWidth();
//        	  
//        	 
//  				double horzspacing = w/ (double) UpdateDisplay.getWidth();
//  				double vertspacing = h / (double) UpdateDisplay.getHeight();
//  				
//  				UpdateDisplay.setCell((int)((y / vertspacing)), (int)((x / horzspacing)), CellCheckedBox);
//  			
//          }

		// -- Mouse motion event handlers
		@Override
		// -- cursor movement with a button pressed
		public void mouseDragged(MouseEvent event) {
			//Dr. Reinhart -- replaced commented code (below) with updated code from mouseClicked method
			if (CellCheckedBox) {
				double horzs = graphicsPanel.getWidth() / (double) ColInput;
				double verts = graphicsPanel.getHeight() / (double) RowInput;

				// compute the row and column in the 2D grid array
//            					grid[(int)(event.getX() / verts)][(int)(event.getY() / horzs)] = 1;
				//UpdateDisplay.universe[0][(int) (event.getY() / verts)][(int) (event.getX() / horzs)] = 1;
				ConwaysGameofLife.setCell((int) (event.getY() / verts), (int) (event.getX() / horzs), true);
				System.out.println("Mouse Clicked at (" + (event.getX() / horzs) + ", "
						+ (event.getY() / verts) + ")");

			} else {
				double horzs = graphicsPanel.getWidth() / (double) ColInput;
				double verts = graphicsPanel.getHeight() / (double) RowInput;

//             					grid[(int)(event.getX() / verts)][(int)(event.getY() / horzs)] = 0;

				
				//UpdateDisplay.universe[0][(int) (event.getY() / verts)][(int) (event.getX() / horzs)] = 0;
				ConwaysGameofLife.setCell((int) (event.getY() / verts), (int) (event.getX() / horzs), false);

			}

//			if (CellCheckedBox) {
//				double horzs = graphicsPanel.getWidth() / (double) ColInput;
//				double verts = graphicsPanel.getHeight() / (double) RowInput;
//
//				// compute the row and column in the 2D grid array
////				grid[(int)(event.getX() / verts)][(int)(event.getY() / horzs)] = 1;
//				UpdateDisplay.universe[0][(int) (event.getY() / verts)][(int) (event.getX() / horzs)] = 1;
//
//			} else {
//				double horzs = graphicsPanel.getWidth() / (double) ColInput;
//				double verts = graphicsPanel.getHeight() / (double) RowInput;
//
////				grid[(int)(event.getX() / verts)][(int)(event.getY() / horzs)] = 0;
//
//				UpdateDisplay.universe[0][(int) (event.getY() / verts)][(int) (event.getX() / horzs)] = 0;
//
//			}
			graphicsPanel.repaint();

		}

		@Override
		// -- cursor movement with no buttons pressed
		public void mouseMoved(MouseEvent event) {
//        	double horzs = graphicsPanel.getHeight() / (double)ColInput;
//			double verts = graphicsPanel.getWidth() / (double)RowInput;
//            System.out.println("Mouse moved to (" + event.getX()  + ", " + event.getY() + ")");
//            System.out.println("Mouse moved at (" + (int)(event.getX() / verts) + ", " + (int)(event.getY() / horzs) + ")");
		}

		// -- action listeners should not perform time consuming computations
		// since they are running at a high priority and will not be
		// interrupted leaving the GUI non-responsive

		// -- prepare the controls and their associated action listeners

		// -- this override sets the desired size of the JPanel which is
		// used by some layout managers -- default desired size is 0,0
		// which is, in general, not good -- will pull from layout manager
		public Dimension getPreferredSize() {
			return new Dimension(50, 50);
		}

		// -- this override is where all the painting should be done.
		// DO NOT call it directly. Consider it an event handler.
		// Rather, call repaint() and let the
		// event handling system decide when to call it
		// DO NOT put graphics function calls elsewhere in the code, although
		// legal, it's bad practice and could destroy the integrity of the display
		// This function is used for all "permanent" painting
//        Boolean mouseclicked = true;
		public boolean mouseclicked;

		@Override
		public void paint(Graphics g)

		{
			// UpdateDisplay.setGraphics(graphicsPanel);

			// -- the base class paintComponent(g) method ensures
			// the drawing area will be cleared properly. Do not
			// modify any attributes of g prior to sending it to
			// the base class
			super.paintComponent(g);

			// -- for legacy reasons the parameter comes in as type Graphics
			// but it is really a Graphics2D object. Cast it up since the
			// Graphics2D class is more capable
			Graphics2D graphicsContext = null;
			if (g instanceof Graphics2D) {
				graphicsContext = (Graphics2D) g;
			} else {
				System.out.println("you have an old JVM");
				return;
			}

			int height = this.getHeight();
			int width = this.getWidth();

			{

				if (GridCheckedBox) {
					// -- overlay a grid to fill the entire space evenly
					int horzs = ConwaysGameofLife.getuniverse().length;
//            		int horzs = UpdateDisplay.getWidth();
					// width is the width of the graphics panel and the double horzs is
					double horzspacing = width / (double) horzs;
					double x0 = 0.0;
					graphicsContext.setColor(Color.BLACK);
					for (int i = 0; i < horzs; ++i) {
						graphicsContext.drawLine((int) x0, 0, (int) x0, height);
						x0 += horzspacing;
					}

					int verts = ConwaysGameofLife.getuniverse()[0].length;
//                    int verts= UpdateDisplay.getHeight();
					double vertspacing = height / (double) verts;
					double y0 = 0.0;
					graphicsContext.setColor(Color.BLACK);
					for (int i = 0; i < verts; ++i) {
						graphicsContext.drawLine(0, (int) y0, width, (int) y0);
						y0 += vertspacing;

					}

				} else {

				}

				int horzs = ConwaysGameofLife.getuniverse().length;

				int verts = ConwaysGameofLife.getuniverse()[0].length;
				double horzspacing = width / (double) horzs;
				double vertspacing = height / (double) verts;
				graphicsContext.setColor(Color.BLACK);
				for (int i = 0; i < ConwaysGameofLife.getuniverse().length; ++i) {
					for (int j = 0; j < ConwaysGameofLife.getuniverse()[0].length; ++j) {
						if (ConwaysGameofLife.getuniverse()[i][j] == 1) {
							// draw the ellipse
							// convert from 2D grid coordinates to graphicsPanel coordinates
							//
							int xul = (int) (horzspacing * j);
							int yul = (int) (vertspacing * i); // upper left hand corner
							int xlr = (int) (horzspacing * (j + 1));
							int ylr = (int) (vertspacing * (i + 1)); // lower right hand corner

							// subtracht lower right minus upper left to get size of the ellipse
							graphicsContext.setColor(Color.BLACK);
							graphicsContext.fillOval(xul, yul, xlr - xul, ylr - yul);

						}

					}

				}
				

				// in the fill oval function you specify the x, y and width, height, subtract
				// the radius from x and y to shift it up
				// do : 2* radiusX && 2 * radiusY (if we calulate row * vertspacing && col *
				// horzspacing)
				// point on the grid line itself is = radiusx =(row + 1 vertspacing -x)
				// when you click off draw cell it should erase whatevere area you click
				// trunkade = casting to an integer (round down for you)

			}
		}
	}
//use point class to store the x and y variables in order to put the ellipse in the correct position 

	// -- Inner class for control panel
	// aka west panel
	public class ControlPanelInner extends JPanel {
		private static final long serialVersionUID = -8776438726683578403L;
		// -- push buttons
		private JButton readtextfieldButton;
		private JButton saveButton;
		private JButton loadButton;
		private JTextField RowsField;
		private JTextField RowText;
		private JTextField ColumnsField;
		private JTextField ColumnText;
//		private JTextField WrapButton;

		public int[][] data;// two dimensional array

		// number of rows in the array and column
		private int numRows;
		private int numCols;

		// -- a fixed label, can be changed by the program but not the user
		private JLabel GridL = new JLabel("Grid");
		private JLabel RowL = new JLabel("Rows");

		private JTextField RowT = new JTextField("15");

		private JLabel ColumnsL = new JLabel("Columns");

		private JTextField ColumnT = new JTextField("15");
		
		

		// GRID ON CHECKBOX
		JCheckBox c1 = new JCheckBox("Grid on");

		// DRAW CELL CHECKBOX
		JCheckBox c2 = new JCheckBox("Draw cell");
		
		//Wrap checkbox 
		JCheckBox WrapButton = new JCheckBox("Wrap");

		// -- area to hold multiple lines of text
		private JTextArea textArea;
		private JScrollPane scrollableTextArea;
		private JRadioButton radio1;
		private JRadioButton radio2;
		private ButtonGroup bg;
		private JCheckBox checkbox;

		// constructor for everything in the south panel
		// everything must be put inside your constructor in order for it to work
		public ControlPanelInner() {

			// -- set up buttons
			prepareButtonHandlers();
			// -- set the layout manager
			// this will determine how items are added to the JPanel
			// the goal is to keep the GUI usable no matter what the user
			// does to it
			// setLayout(new FlowLayout());
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			// -- construct the JTextField, 5 characters wide
			RowsField = new JTextField("Default", 5);

			this.add(GridL);

			this.add(textareaButton);

			RowT.setPreferredSize(new Dimension(200, 70));
			this.add(RowL);
			RowT.setMaximumSize(new Dimension(100, 30));

			this.add(RowT);
			RowT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String rowsText = RowT.getText();
					RowInput = Integer.parseInt(RowT.getText());
					System.out.println("Number of grid rows entered: " + RowInput);
					graphicsPanel.repaint();
				}
			});

			this.add(ColumnsL);
			ColumnT.setMaximumSize(new Dimension(100, 30));
			this.add(ColumnT);
			ColumnT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String rowsText = ColumnT.getText();
					ColInput = Integer.parseInt(ColumnT.getText());
					System.out.println("Number of column rows entered: " + ColInput);
					graphicsPanel.repaint();
				}
			});

			this.add(c1);

			c1.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					// if checkbox is selected it is true, everything else is done in the graphics
					// panel
					if (c1.isSelected()) {
						GridCheckedBox = true;
						System.out.println("Grid on checkbox is checked");
						graphicsPanel.repaint();
					} else {
						GridCheckedBox = false;
						// repaint function calls back to the paint function and updates the paint
						// repaint = updates display
						graphicsPanel.repaint();
					}
				}

			});

			this.add(c2);

			c2.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (c2.isSelected()) {
						CellCheckedBox = true;
						System.out.println("Draw cell checkbox is checked");
					} else {
						CellCheckedBox = false;
						System.out.println("Draw cell checkbox is unchecked");
					}
				}

			});
			
			this.add(WrapButton);

			WrapButton.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (WrapButton.isSelected()) {
						ConwaysGameofLife.WrapGrid(true);
						System.out.println("WrapGrid checkbox is checked");
					} else {
						ConwaysGameofLife.WrapGrid(false);
						System.out.println("WrapGrid checkbox is unchecked");
					}
				}

			});

			// adding a mouse listener to checkbox aka "c2"
			// mouseadapter = implementation of mouselistener
			// mousevent = tracking when the mouse is pressed, released, clicked, moved,
			// dragged, when it enters and exits
			c2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// getting selected cell

					// you need to convert your pixels to squares
					// when you click the cell you need to map x and y to row, col and set a 1 there
					// should by x/# cell's cols and y/# of cell rows
					// need a repaint because it's gonna draw the grid or not and it'll loop through
					// the array
					// (then you make an if statement if its a 1 you draw a circle if it's a 0 you
					// dont draw anything)
					// do not do any drawing inside the actionlistener
					// draw/erase ellipse if draw box is checked or unchecked

				}

			});

			this.add(loadButton);

			this.add(saveButton);
			// -- add a JTextArea with scroll bars, 7 rows, 5 columns
			// scrollbar areas will show as soon as the JScrollPane
			// is constructed. If you remove the calls to setHorizontalScrollBarPolicy
			// and setVerticalScrollBarPolicy the scrollbars will only show
			// when needed
			textArea = new JTextArea(7, 5);
		}

		// -- construct JButtons and add event handlers
		private void prepareButtonHandlers() {

			textareaButton = new JButton("Set size");
			textareaButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					isPressed = true;

					// parse converts string -> int , RowT.getText gets the text at the box
					RowInput = Integer.parseInt(RowT.getText());
					System.out.print(RowInput);

					ColInput = Integer.parseInt(ColumnT.getText());
					System.out.print(ColInput);
					ConwaysGameofLife.universe = new int[2][RowInput][ColInput];
					graphicsPanel.repaint();

					for (int i = 0; i < 10; ++i) {
						textArea.append("Very Wide String " + i + "\n");
					}
					// -- send focus back to the graphicsPanel

					graphicsPanel.repaint();
					graphicsPanel.requestFocus();
				}
			});
			// this and load is where you add the file code (serialization)
			saveButton = new JButton("Save");
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser jfc = new JFileChooser();
					if (jfc.showDialog(null, "Save") == JFileChooser.APPROVE_OPTION) {
						// File fileToSave = jfc.getSelectedFile();
						System.out.println(jfc.getSelectedFile().getName());
						//object is seralized first in the objectoutputstream, then serialized in the fileoutputstream and written into bytecode
						try {
							// -- wrap the target disc file in a raw byte FileOutputStream (object inside file)
							FileOutputStream fileOut = new FileOutputStream(jfc.getSelectedFile());
							
							// -- wrap the FileOutputStream in an ObjectOutputStream for serialization
							ObjectOutputStream out = new ObjectOutputStream(fileOut);
							
							// -- write the array of objects to the file
							out.writeObject(ConwaysGameofLife);
							//flush the stream at critical points in your code
							//when your done with the file close them 
							out.flush();
							
							// -- close the stream
							out.close();
							fileOut.close();
							graphicsPanel.repaint();
							
							System.out.println("Serialized data is saved in JFCFileChooser");
						} catch (IOException i) {
							// -- in case the file cannot be opened
							System.out.println("can't open file");
						}

				        
					}
					// -- send focus back to the graphicsPanel
					graphicsPanel.requestFocus();
				}
			});
			loadButton = new JButton("Load");
			loadButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser jfc = new JFileChooser();
					if (jfc.showDialog(null, "Load") == JFileChooser.APPROVE_OPTION) {
						 //File fileToLoad = jfc.getSelectedFile();
//						 ImageIO.write(null, name, fileToLoad);
						System.out.println(jfc.getSelectedFile().getName());
						
						try {
							// -- set the static variable to show that it will not change on the read
							Rectangle.a = 0;
							// -- wrap the target disc file in a Stream (object inside file)
							FileInputStream fileIn = new FileInputStream(jfc.getSelectedFile());
							ObjectInputStream in = new ObjectInputStream(fileIn);
							// -- read the object from the file
							//    note that this returns an Object and therefore must be cast
							//    to the appropriate type to ensure usability
							Object ob = in.readObject();
							
							// -- close the stream
							in.close();
							fileIn.close();

							// -- verify the object class and cast (RTTI operation)
							if (ob instanceof UpdatedDisplay) {
								ConwaysGameofLife =(UpdatedDisplay)ob;
							}
							else {
								//System.out.println("System can't open file");
								System.exit(0);
							}
							graphicsPanel.repaint();
						} catch (IOException i) {
							// -- in case the file cannot be opened
							System.out.println("can't open file");
							return;
						} catch (ClassNotFoundException c) {
							// -- in case the Rectangle.class file cannot be found after 
							//    reading the file
							System.out.println("class not found");
							return;
						}
						
					}
					
//					FileOutputStream fs = new FileOutputStream();
					// -- send focus back to the graphicsPanel
					graphicsPanel.requestFocus();
				}
			});
		}

		// -- sets the size of the JPanel
		public Dimension getPreferredSize() {
			return new Dimension(100, 500);
		}
	}

	// south panel
	public class BottomPanel extends JPanel {

		private JButton startButton;
		private JSlider speedslider;
		private boolean isRunning;
		private Thread thread;

//        setLayout(new FlowLaout(this, BorderLayout.PAGE_AXIS));
		private JLabel Animation = new JLabel("Animation");

		private JButton startB = new JButton("Start");

		public BottomPanel() {

			// sets layout manager to flowlayout
			setLayout(new FlowLayout());

//    	Ani.setPreferredSize(new Dimension(400, 70));
			this.add(Animation);
//        
//        
//        startB.setPreferredSize(new Dimension(70, 35));
			this.add(startB);

			startB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

// 		   

					if (ConwaysGameofLife == null) {
						// created runnable object
						// UpdateDisplay = new UpdatedDisplay(graphicsPanel);
//     		UpdateDisplay.setnextgeneration(graphicsPanel.nextgenreation);
						// start thread
						thread = new Thread(ConwaysGameofLife);
						thread.start();
						// change label name once started
						startB.setText("Stop");

						// created a function called isRunning in runnable to create
					} else if (!ConwaysGameofLife.isRunning()) {

						// start exisitng runnable object
						thread = new Thread(ConwaysGameofLife);
						thread.start();
						// change label name to stop
						startB.setText("Stop");

					} else {
						// stop runnable
						ConwaysGameofLife.stop();

						// change label back to start once the interface is terminated
						startB.setText("Start");
					}
				}
			});

			speedslider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
			add(speedslider);
			speedslider.setVisible(true);
//        speedslider.setBounds(300, 180, WIDTH, HEIGHT);
//        speedslider.setPreferredSize(new Dimension(300, 10));
			speedslider.setMajorTickSpacing(20);
			speedslider.setMinorTickSpacing(5);
			speedslider.setPaintTicks(true);
			speedslider.setPaintLabels(true);
			speedslider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					if (!source.getValueIsAdjusting()) {
						int speed = source.getValue();
						System.out.println("You are at slider " + speed);
						//Dr. Reinhart -- added this call to change the speed of the thread
						ConwaysGameofLife.setSpeed(speed);
					}
				}
			});

		}

//       public void actionPerformed(ActionEvent e) {
//    	   
//    	   
//    	   if(!isRunning) {
//    		   
//    		   UpdateDisplay.stop();
//    		   thread.interrupt();
//    		   startB.setText("Stop");
//    		   isRunning = true;
//    		   
//    	   }else {
//    		   //stop running and do something else which would be setting the label back to start
//    		   new Thread(UpdateDisplay).start();
//    		 
//    		   startB.setText("Start");
//    		   isRunning = false;
//    	   }
//       }

		public Dimension getPreferredSize() {
			return new Dimension(50, 90);
		}

	}

	public static void main(String[] args) {
		// -- can run as an anonymous object since Swing
		// is multi-threaded (the main function can terminate
		// its thread while the Swing thread continues on)
		// the object is created on the heap but has no stack reference
		// thus we call it "anonymous"
		new SwingGUIApplication();
		// -- this line demonstrates that the Swing JFrame runs in
		// its own thread
		System.out.println("Main thread terminating");

	}
}
