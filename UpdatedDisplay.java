package SwingGUIHW;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JTextField;

import SwingGUIHW.SwingGUIApplication.GraphicPanelInner;


//When an object is serialized, It saves all the values of the instance variables of that object. 
//So that a similar object can be created in the heap.
public class UpdatedDisplay implements Runnable, Serializable {
	
	private boolean stop;
	private boolean running;
	private boolean Wrap;
	private GraphicPanelInner GPanel;
  	
  	
  	//an array, of an array, of an array
  	//3D array = at index 0 that stores the orignial array, and the second array holds the new generation
	//private int universe[][][];
	public int universe[][][];
	private int matrix = 0;
	private int speed=1000;
	
//	public UpdatedDisplay() {
//		universe = new int [2][20][20];
//		
//	}
	
	public UpdatedDisplay(GraphicPanelInner GPanel, int r, int c ) {
		//this = the current object in method or constructor + eliminates confusion between class 
		//attributes and parameters with the same name 
		universe = new int [2][r][c];
		this.GPanel = GPanel;
	}
	
	
	
	public UpdatedDisplay (int h, int w) {
		universe = new int [2][h][w];
	}

	//Dr. Reinhart -- changed name of function from getSpeed to setSpeed
	public void setSpeed(int speed) {
		this.speed = speed * 20;
	}
	
	public int[][] getuniverse() {
		return universe[matrix];
	}
	
	public int getHeight(){
		return universe[matrix].length;
	}
	
	public int getWidth() {
		return universe[matrix][0].length;
	}
	
	public void stop() {
		stop = true;
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void WrapGrid(boolean WrapButton) {
		this.Wrap = WrapButton;
	}
	
	public void setCell(int row, int col, boolean draw) throws ArrayIndexOutOfBoundsException {
		try {
			if(draw == false) {
				//Dr. Reinhart -- it was = 1, should be = 0
				universe[matrix][row][col] = 0;
			}else {
				//Dr. Reinhart -- it was = 0, should be = 1
				universe[matrix][row][col] = 1;
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			
		}
	}
	
	public void universecalc() {
		int next;
		int count = 0;
		//telling us which array we're processing
		if(matrix == 0) {
			//n+1 array
			next = 1;
		}else {
			next = 0;
		}

		//accessing the active matrix where the row is
		//Dr. Reinhart -- changed <= to < for both for loop conditions
		for(int i = 0; i < universe[matrix].length; ++i) {
			//accessing the column access at the row which is why i is there
			for(int j = 0; j < universe[matrix][i].length; ++j) {
				//Dr. Reinhart -- set count=0 before counting each cell
				count = 0;
				//accessing the borders of (0,0)
				for(int k = -1; k <= 1; ++k) {
					for(int z = -1; z <= 1; ++z) {
						try {
							//continue means next line 
							//we dont want to check the 0,0 cell because thats where our oval is 
							//setting to 0 thats why we need the continue or else it's going to stay in this if statement 
							if(Wrap) {
								int row = (i + k + universe[matrix].length) % universe[matrix].length;
								int col = (j + z + universe[matrix][0].length) % universe[matrix][0].length;
								count += universe[matrix][row][col];
								
							} else {
								if(k == 0 && z == 0)continue;
								if(universe[matrix][i+k][j+z] > 0) {
									//we want to count how many cells are within the neighborhood
									++count;
								
							}
							//if matrix is active then: 
							//access to the neighborhood, it's getting each neighbor of each oval placed
							//it cycles through each neighbor 
							//if a cell exists at one of the neighbors, then we count
							
							//for wrapping add a modulo (%) to each universe add 1, add the width again, then a modulo, which wrapped the cell back around 
							//same thing for all the sides 
							//add number of rows, modulo the number of rows (ex i + k + universe[active].length  % the same line + 1
//							if(universe[matrix][i+k][j+z] > 0) {
//								//we want to count how many cells are within the neighborhood
//								++count;
								
							}
						}
						catch(IndexOutOfBoundsException e){
							
						}
					}
				}
				
				//input grid here??
				//then call to that new grid if the cells are alive
				//now we check if the cells are alive or dead 
				//if rows and cols of the new matrix are 0 
				
				if(Wrap) {
					count -= universe[matrix][i][j];
				}
				
				if( universe[matrix][i][j] == 0) {
					if(count == 3) {
						universe[next][i][j]=1;
					}else {
						//assigning next and active to one another so they are essentially reading each other 
						universe[next][i][j]= universe[matrix][i][j];
					}
					
				}
				else {
				if(count < 2 || count > 3) {
					universe[next][i][j] = 0;
				}else {
					universe[next][i][j]= universe[matrix][i][j];
				}
				}
				
			}
			//Dr. Reinhart -- move this statement out of the for loop, only want to switch
			//to the other array when the computation is complete
			//matrix = next;
		}
		matrix = next;
		
		
	}
	
	

	@Override
	public void run() {
		running = true;
		stop = false;
		while(!stop) {
			//Dr. Reinhart -- added call to universecalc() and removed println
			//System.out.println("running");
			universecalc();
			GPanel.repaint();
			try {
				
			//sleep command means taking a break (taking a nap) 
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
	
