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
		this.xPos = xPos;
		this.yPos = yPos;
		this.size = size;
		this.farEnough = farEnough;
		for (int i = 0; i < size; i++) {
			this.xChange = xChange;
			this.yChange = yChange;
			this.xChangeOriginal = xChange;
			this.yChangeOriginal = yChange;
		}
		counter = 0;
		this.width = width;
		this.height = height;
		traveled = 0;
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
	public Color color(Graphics g) {
		if (counter < 399) { 
			return Color.RED;
		} else {
			return Color.WHITE;
		}
	}
	
	public void move() {
		xPos = xPos + xChange[counter];
		yPos = yPos + yChange[counter];
		if (xPos > width || xPos < 0) {
			for (int i = counter; i < xChange.length; i++) {
				xChange[i] = 0;
				yChange[i] = 0;
			}
		}
		if (yPos > height || yPos < 0) {
			for (int i = counter; i < yChange.length; i++) {
				xChange[i] = 0;
				yChange[i] = 0;
			}
		}
		
		counter++;
	}
	
	public double distToTarget(double x, double y) {
		double dist = Math.sqrt(((targetX - x) * (targetX - x)) + ((targetY - y) * (targetY - y)));
		return dist;
	}
	
	public void travel() {
		double hypotenuse = Math.sqrt((xChange[counter] * xChange[counter]) + (yChange[counter] * yChange[counter]));
		traveled += hypotenuse;
	}
	
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
