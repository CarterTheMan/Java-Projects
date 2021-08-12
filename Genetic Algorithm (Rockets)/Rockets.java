package RocketEvolution;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Rockets {
	
	int size;
	int counter, width, height;
	double xPos, yPos, targetX, targetY, traveled;
	double[] xChange = new double[size];
	double[] yChange = new double[size];
	double[] xChangeOriginal = new double[size];	
	double[] yChangeOriginal = new double[size];	
	boolean farEnough;
	
	Rockets(double xPos, double yPos, double[] xChange, double[] yChange, int width, int height, int size, boolean farEnough, double targetX, double targetY) {
		this.xPos = xPos;								// Starting x position
		this.yPos = yPos;								// Starting y position
		this.size = size;								// Number of genes in the rocket
		this.farEnough = farEnough;						// Boolean for if the rocket is close enough to the target dot
		for (int i = 0; i < size; i++) {
			this.xChange = xChange;						// The X genes of the rocket
			this.yChange = yChange;						// the Y genes of the rocket
			this.xChangeOriginal = xChange;				
			this.yChangeOriginal = yChange;				
		}
		counter = 0;									// Counter to keep track of what gene the program is on
		this.width = width;								// Width of canvas
		this.height = height;							// Height of canvas
		traveled = 0;									// Keeps track of how far the rocket has traveled
		this.targetX = targetX;							// The target dot's x position
		this.targetY = targetY;							// The target dot's y position
	}
	
	/**
	 * Sets the color of the rockets
	 * @param g
	 * @return red or white
	 */
	public Color color(Graphics g) {
		if (counter < 399) { 
			return Color.RED;
		} else {
			return Color.WHITE;
		}
	}
	
	/**
	 * Is what actually makes the rockets move.
	 */
	public void move() {
		xPos = xPos + xChange[counter];
		yPos = yPos + yChange[counter];
		
		//If the rockets run into with wall, then they freeze
		if (xPos > width || xPos < 0) {
			for (int i = counter; i < xChange.length; i++) {
				xChange[i] = 0;
				yChange[i] = 0;
			}
		}
		
		//If the rockets run into the floor of ceiling, then they freeze
		if (yPos > height || yPos < 0) {
			for (int i = counter; i < yChange.length; i++) {
				xChange[i] = 0;
				yChange[i] = 0;
			}
		}
		
		counter++;
	}
	
	/**
	 * Finds the distance from the rocket to the target dot
	 * @param x
	 * @param y
	 * @return dist
	 */
	public double distToTarget(double x, double y) {
		double dist = Math.sqrt(((targetX - x) * (targetX - x)) + ((targetY - y) * (targetY - y)));
		return dist;
	}
	
	/**
	 * Keeps track of how far the rocket has traveled
	 */
	public void travel() {
		double hypotenuse = Math.sqrt((xChange[counter] * xChange[counter]) + (yChange[counter] * yChange[counter]));
		traveled += hypotenuse;
	}
	
	/**
	 * Finds if the rocket is close enough to the target dot. If it is, then that rockets 'far enough' boolean is set to true
	 * @param dist
	 */
	public void distFar(double dist) {
		double hypotenuse = Math.sqrt((xChange[0] * xChange[0]) + (yChange[0] * yChange[0]));
		if (hypotenuse * size >= dist - 25) {	
			farEnough = true;
		}
	}
	
	public int getCounter() {
		return  counter;
	}
	
	public int getSize() {
		return size;
	}
	
	public double getX() {
		return xPos;
	}
	
	public double getY() {
		return yPos;
	}
	
	public double[] getXChaOri() {
		return xChangeOriginal;
	}
	
	public double[] getYChaOri() {
		return yChangeOriginal;
	}
}
