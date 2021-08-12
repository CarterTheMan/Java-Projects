package AStarDone2;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.util.*;
import java.util.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;

/*
 * Files have the format where when 
 * top row is cWidth, cHeight, width, height
 * and the other rows are the maze
 * $ = wall, S = start, E = end, B = blank (empty)
 */

public class Main extends JPanel {

	static int cellSize = 50;			//The dimensions of each cell (cellSize * cellSize)
	static int cellAmount = 15;			//The number of each cells in height and width
	static int cWidth = cellSize;		//cell width (25)
	static int cHeight = cellSize;		//cell height (25)
	static int height = cellAmount;		//how many cells tall (30)
	static int width = cellAmount;		//how many cells wide (30)
	static int click = 0;				//Counter for how much the mouse is clicked
	static int x = -1;					//The x position when the mouse clicks
	static int y = -1;					//The y position when the mouse clicks
	static int moves = 0; 				//Counts how many moves it is from start to end
	static boolean contWall = true;		//When false, stops from adding walls to the maze
	static boolean end = false;			//True when the end exists and is not found
	static ArrayList<cell> cells = new ArrayList<>();	
	static ArrayBasedStack<cell> stack;
	static ArrayBasedQueue<cell> queue;	
	static ArrayList<cell> AStar;
	static cell start = null;			//Cell in the start position
	static cell ending = null;			//Cell in the end position
	static boolean findEnd = false;		//True when the and needs to be found
	static boolean bfs = false;			//When true, starts the breadth first search
	static boolean dfs = false;			//When true, starts the depth first search
	static boolean aStar = false;		//When true, starts the A* search
	
	/**
	 * Constructor and sets the key and mouse listeners
	 */
	public Main() {
		MouseListener mouse = new MyMouseListener();
		addMouseListener(mouse);

		KeyListener listener = new MyKeyListener();
		addKeyListener(listener);
		setFocusable(true);
	}

	/**
	 * Paints and controls the color of the maze
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Makes the cells
		for (int i = 0; i < cells.size(); i++) {
			cell temp = cells.get(i);
			g.setColor(temp.color(g));
			g.fillRect(temp.getXLoc(), temp.getYLoc(), cHeight, cWidth);
		}

		g.setColor(Color.BLACK);

		//Makes the column lines
		for (int i = 0; i < height + 1; i++) {
			g.drawLine(i * cWidth, 0, i * cWidth, height * cHeight);
		}

		//Makes horizontal lines
		for (int i = 0; i < width + 1; i++) {
			g.drawLine(0, i * cHeight, width * cWidth, i * cHeight);
		}
		
		//Does the breadth First Search
		if (bfs) {
			try {
				// Waits .1 seconds between searching each cell
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Pulls the next cell from the queue
			cell temp = queue.dequeue();
			
			// Actions for if we dequeued the end cell
			if (temp.getCell().equals(cellType.end)) {
				end = false;				
				findEnd = true;				// Starts the trace from the end cell
				bfs = false;				// Ends the bfs search
			} else if (!temp.getCell().equals(cellType.start)) {
				// Sets the type of the cell from normal unsearched to normal searched
				temp.setCell(cellType.normalS);
			}
			
			int xLoc = temp.getXLoc();
			int yLoc = temp.getYLoc();
			
			//Adds the above cell to the queue if it is a normal cell or the end cell and as long as the cell isn't already in the queue
			if (yLoc - cHeight >= 0) {
				cell c = checkCellSearch(xLoc, yLoc - cHeight);
				if ((c.getCell().equals(cellType.normalU) || c.getCell().equals(cellType.end)) && !queue.contains(c)) {
					c.setParent(temp);
					queue.enqueue(c);
				}
			}
			//Adds the left cell to the queue if it is a normal cell or the end cell and as long as the cell isn't already in the queue
			if (xLoc + cWidth < width * cWidth) {
				cell c = checkCellSearch(xLoc + cWidth, yLoc);
				if ((c.getCell().equals(cellType.normalU) || c.getCell().equals(cellType.end)) && !queue.contains(c)) {
					c.setParent(temp);
					queue.enqueue(c);
				}
			}
			//Adds the below cell to the queue if it is a normal cell or the end cell and as long as the cell isn't already in the queue
			if (yLoc + cHeight < height * cHeight) {
				cell c = checkCellSearch(xLoc, yLoc + cHeight);
				if ((c.getCell().equals(cellType.normalU) || c.getCell().equals(cellType.end)) && !queue.contains(c)) {
					c.setParent(temp);
					queue.enqueue(c);
				}
			}
			//Adds the right cell to the queue if it is a normal cell or the end cell and as long as the cell isn't already in the queue
			if (xLoc - cWidth >= 0) {
				cell c = checkCellSearch(xLoc - cWidth, yLoc);
				if ((c.getCell().equals(cellType.normalU) || c.getCell().equals(cellType.end)) && !queue.contains(c)) {
					c.setParent(temp);
					queue.enqueue(c);
				}
			}
		}
		
		//Does the depth first search
		if (dfs) {
			try {
				// Waits .1 seconds between searching each cell
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Pulls the next cell from the stack
			cell temp = stack.pop();
			
			// Actions for if we pop the end cell
			if (temp.getCell().equals(cellType.end)) {
				end = false;
				findEnd = true;				// Starts the trace from the end cell
				dfs = false;				// Ends the bfs search
			} else if (!temp.getCell().equals(cellType.start)) {
				// Sets the type of the cell from normal unsearched to normal searched
				temp.setCell(cellType.normalS);
			}
			
			int xLoc = temp.getXLoc();
			int yLoc = temp.getYLoc();
			
			//Adds the right cell to the stack if it is a normal cell or the end cell and as long as the cell isn't already in the stack
			if (xLoc - cWidth >= 0) {
				cell c = checkCellSearch(xLoc - cWidth, yLoc);
				if ((c.getCell().equals(cellType.normalU) || c.getCell().equals(cellType.end)) && !stack.contains(c)) {
					c.setParent(temp);
					stack.push(c);
				}
			}
			//Adds the below cell to the stack if it is a normal cell or the end cell and as long as the cell isn't already in the stack
			if (yLoc + cHeight < height * cHeight) {
				cell c = checkCellSearch(xLoc, yLoc + cHeight);
				if ((c.getCell().equals(cellType.normalU) || c.getCell().equals(cellType.end)) && !stack.contains(c)) {
					c.setParent(temp);
					stack.push(c);
				}
			}
			//Adds the left cell to the stack if it is a normal cell or the end cell and as long as the cell isn't already in the stack
			if (xLoc + cWidth < width * cWidth) {
				cell c = checkCellSearch(xLoc + cWidth, yLoc);
				if ((c.getCell().equals(cellType.normalU) || c.getCell().equals(cellType.end)) && !stack.contains(c)) {
					c.setParent(temp);
					stack.push(c);
				}
			}
			//Adds the above cell to the stack if it is a normal cell or the end cell and as long as the cell isn't already in the stack
			if (yLoc - cHeight >= 0) {
				cell c = checkCellSearch(xLoc, yLoc - cHeight);
				if ((c.getCell().equals(cellType.normalU) || c.getCell().equals(cellType.end)) && !stack.contains(c)) {
					c.setParent(temp);
					stack.push(c);
				}
			}
		}
		
		if (aStar) {
			try {
				// Waits .1 seconds between searching each cell
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Created a temporary cell for starting information
			cell temp = AStar.get(0);
			
			// Goes through the arraylist and check to find the smallest fCost
			// It two tie, then it chooses whichever of the two have the smallest hCost
			for (int i = 0; i < AStar.size(); i++) {
				if (AStar.get(i).getFCost() < temp.getFCost()) {
					temp = AStar.get(i);
				} else if (AStar.get(i).getFCost() == temp.getFCost() && AStar.get(i).getHCost() < temp.getHCost()) {
					temp = AStar.get(i);
				}
			}
			
			//Checks to see if this is the end cell.
			//If it isn't the end then it makes sure its not the start
			//If it isn't either, then it sets it to a searched cell
			if (temp.getCell().equals(cellType.end)) {
				end = false;
				findEnd = true;
				aStar = false;
			} else if (!temp.getCell().equals(cellType.start)) {
				temp.setCell(cellType.normalS);
			}
			
			int xLoc = temp.getXLoc();
			int yLoc = temp.getYLoc();
			
			AStar.remove(temp);
			
			// Adds the right cell to AStar if it is a normal cell or the end cell and as long as the cell isn't already in the AStar
			if (xLoc - cWidth >= 0) {
				cell c = checkCellSearch(xLoc - cWidth, yLoc);
				if ((c.getCell().equals(cellType.normalU) || c.getCell().equals(cellType.end)) && !AStar.contains(c)) {
					c.setParent(temp);
					c.setGCost(distBetween(c, start));
					c.setHCost(distBetween(c, ending));
					AStar.add(c);
				}
			}
			// Adds the below cell to AStar if it is a normal cell or the end cell and as long as the cell isn't already in the AStar
			if (yLoc + cHeight < height * cHeight) {
				cell c = checkCellSearch(xLoc, yLoc + cHeight);
				if ((c.getCell().equals(cellType.normalU) || c.getCell().equals(cellType.end)) && !AStar.contains(c)) {
					c.setParent(temp);
					c.setGCost(distBetween(c, start));
					c.setHCost(distBetween(c, ending));
					AStar.add(c);
				}
			}
			// Adds the left cell to AStar if it is a normal cell or the end cell and as long as the cell isn't already in the AStar
			if (xLoc + cWidth < width * cWidth) {
				cell c = checkCellSearch(xLoc + cWidth, yLoc);
				if ((c.getCell().equals(cellType.normalU) || c.getCell().equals(cellType.end)) && !AStar.contains(c)) {
					c.setParent(temp);
					c.setGCost(distBetween(c, start));
					c.setHCost(distBetween(c, ending));
					AStar.add(c);
				}
			}
			// Adds the top cell to AStar if it is a normal cell or the end cell and as long as the cell isn't already in the AStar
			if (yLoc - cHeight >= 0) {
				cell c = checkCellSearch(xLoc, yLoc - cHeight);
				if ((c.getCell().equals(cellType.normalU) || c.getCell().equals(cellType.end)) && !AStar.contains(c)) {
					c.setParent(temp);
					c.setGCost(distBetween(c, start));
					c.setHCost(distBetween(c, ending));
					AStar.add(c);
				}
			}
			
		}

		//Finds the path to the end
		if (findEnd) {
			ending = ending.parent;
			moves++;
			//Creates parent trail to start
			if (!ending.getCell().equals(cellType.start)) {
				ending.setCell(cellType.toStart);
			} else {
				findEnd = false;
				end = false;
				System.out.println("Solved in " + moves + " moves");
			}
		}

		repaint();
	}
	
	/**
	 * A* helper methods for finding gCost and hCost
	 * @param one
	 * @param two
	 * @return straightLine
	 */
	public static int distBetween(cell one, cell two) {
		int distX = one.xLoc - two.xLoc;
		
		if (distX < 0) {
			distX *= -1;
		}
		int distY = one.yLoc - two.yLoc;
		if (distY < 0) {
			distY *= -1;
		}
		
		//Handles all straight movements
		int straightLine = 0;
		while (distX > 0) { 
			straightLine += cellSize;
			distX -= cellSize;
		}
		while (distY > 0) {
			straightLine += cellSize;
			distY -= cellSize;
		}
		
		return straightLine;
	}

	//Main method
	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Would you like to create your own maze (1) or uplead a file (2)");
		int choice = input.nextInt();

		JFrame frame = null;
		
		if (choice == 1) {
			
			// Created the canvas for the graphics
			Main m = new Main();
			frame = new JFrame();
			frame.setSize(width * cWidth, height * cHeight);
			frame.getContentPane().add(new Main());
			frame.setBackground(Color.WHITE);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			frame.add(m);
			
			//Adds all cells to a arraylist
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					cells.add(new cell(i * cHeight, j * cWidth));
				}
			}
			
			// Asks user for search type input
			System.out.println("What kind of search do you want?");
			System.out.println("1 - breadth first search");
			System.out.println("2 - depth first search");
			System.out.println("3 - A* search");
			int search = input.nextInt();
			
			if (search == 1) {
				try {
					breadthFirstSearch(end);
				} catch (InterruptedException e) {
					System.out.println("Help 1");
				} catch (NullPointerException e) {
					System.out.println("No solution to the maze");
				}
			} else if (search == 2) {
				try {
					depthFirstSearch(end);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (search == 3) {
				aStarSearch(end);
			}
			
		} else if (choice == 2) {
			
			// Asks for the input file and makes the maze based on that
			System.out.println("What is the name of the file? ");
			File f = new File(input.next());
			Scanner in = new Scanner(f);
			int lineC = 0;
			int cellCounter = 0;
			while (in.hasNextLine()) {
				String line = in.nextLine();
				Scanner linesc = new Scanner(line);
				if (lineC == 0) {
					cWidth = linesc.nextInt();
					cHeight = linesc.nextInt();
					width = linesc.nextInt();
					height = linesc.nextInt();
					//Make a canvas for the graphics
					Main m = new Main();
					frame = new JFrame();
					frame.setSize(width * cWidth, height * cHeight);
					frame.getContentPane().add(new Main());
					frame.setBackground(Color.WHITE);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
					frame.add(m);
					//Adds all cells to a array-list
					for (int i = 0; i < height; i++) {
						for (int j = 0; j < width; j++) {
							cells.add(new cell(i * cHeight, j * cWidth));
						}
					}
				} else {
					while (linesc.hasNext()) {
						String n = linesc.next();
						cell temp = cells.get(cellCounter);
						if (n.equals("$")) {
							temp.setCell(cellType.wall);
						} else if (n.equals("S")) {
							temp.setCell(cellType.start);
							start = temp;
						} else if (n.equals("E")) {
							temp.setCell(cellType.end);
							ending = temp;
							end = true;
						} else if (n.equals("B")) {
							temp.setCell(cellType.normalU);
						}
						cellCounter++;
					}
				}
				lineC++;
			}
			
			// Asks user for search type input
			System.out.println("What kind of search do you want?");
			System.out.println("1 - breadth first search");
			System.out.println("2 - depth first search");
			System.out.println("3 - A* search");
			int search = input.nextInt();
			if (search == 1) {
				try {
					breadthFirstSearch(end);
				} catch (InterruptedException e) {
					System.out.println("Help 1");
				} catch (NullPointerException e) {
					System.out.println("No solution to the maze");
				}
			} else if (search == 2) {
				try {
					depthFirstSearch(end);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (search == 3) {
				aStarSearch(end);
			}
		}
	}

	/**
	 * Allows the user to set up the maze
	 * @param click
	 * @param cont
	 */
	public static void setMaze(int click, boolean cont) {
		if (click == 1) {
			checkCellClick().setCell(cellType.start);
			start = checkCellClick();
		}
		if (click == 2) {
			checkCellClick().setCell(cellType.end);
			ending = checkCellClick();
			end = true;
		}
		if (click >= 3 && cont) {
			checkCellClick().setCell(cellType.wall);
		} else if (cont == false) {

		}
	}

	/**
	 * Finds the cell that was last clicked
	 * @return temp or null
	 */
	public static cell checkCellClick() {
		for (int i = 0; i < cells.size(); i++) {
			cell temp = cells.get(i);
			if (x>temp.getXLoc() && x<temp.getXLoc()+cWidth && y>temp.getYLoc() && y<temp.getYLoc()+cHeight) {
				return temp;
			}
		}

		return null;
	}

	/**
	 * Finds the indicated cell when searching
	 * @param x
	 * @param y
	 * @return temp or null
	 */
	public static cell checkCellSearch(int x, int y) {
		for (int i = 0; i < cells.size(); i++) {
			cell temp = cells.get(i);
			if (temp.getXLoc() == x && temp.getYLoc() == y) {
				return temp;
			}
		}

		return null;
	}

	/**
	 * The searching program (Breadth First)
	 * @param end
	 * @throws InterruptedException
	 */
	public static void breadthFirstSearch(boolean end) throws InterruptedException {
		queue = new ArrayBasedQueue<cell>(height * width);
		queue.enqueue(start);
		bfs = true;
	}
	
	/**
	 * The searching program (Depth First)
	 * @param end
	 * @throws InterruptedException
	 */
	public static void depthFirstSearch(boolean end) throws InterruptedException { 
		stack = new ArrayBasedStack<cell>(height * width);
		stack.push(start);
		dfs = true;
	}
	
	/**
	 * The searching program (A*) 
	 * @param end
	 */
	public static void aStarSearch(boolean end) {
		AStar = new ArrayList<cell>();
		AStar.add(start);
		aStar = true;
	}

	/**
	 * For the mouse listener for when the user click on a part in the maze
	 */
	public class MyMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			click++;
			try {
				setMaze(click, contWall);
			} catch (NullPointerException a) {

			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	/**
	 * For the keyboard listener
	 */
	public class MyKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ALT) {
				contWall = false;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

}
