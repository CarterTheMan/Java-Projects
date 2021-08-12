package Gravity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	
	public static ArrayList<Planet> planet = new ArrayList<Planet>();
	public static int width = 800;			//Width fo the window
	public static int height = 800;			//Height of the window
	public static int reset = 0;
	public static int resetRate = 2500;		//How often the program restarts with new planets
	public static int size = 50;			//Number of planets 
	public static int maxSize = 50;			//Maximum size of the planets
	public static int minSize = 10;			//Minimum size of the planets
	
	//This displays the actual graphics of the program 
	/**
	 * @param Graphics
	 */
	public void paintComponent(Graphics g) {
		//Makes a white background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);		
		
		//For every planet in planets, change the color and make the ball
		for (Planet p : planet) {
			g.setColor(p.color(g));
			g.fillOval((int)p.position.getX()-((int)p.mass/2), (int)p.position.getY()-((int)p.mass/2), (int)p.mass, (int)p.mass);
		}
		
		//For every planet, get gravity effect from other planets and move the planet accordingly
		for (int i = 0; i < planet.size(); i++) {
			Planet temp = planet.get(i);
			
			for (int j = 0; j < planet.size(); j++) {
				if (j != i && dist(temp, planet.get(j)) > 25) {
					temp.attract(planet.get(j));
				}
			}
			
			temp.update();
		}
		
		//Adds one to reset
		reset++;
		
		//Clears the planet ArrayList, refills the ArrayList with new planets and resets the reset variable
		if (reset % resetRate == 0) {
			planet.clear();
			for (int i = 0; i < size; i++) {
				Random r1 = new Random();
				Random r2 = new Random();
				Random size = new Random();
				int sizeDifference = maxSize - minSize;
				planet.add(new Planet(new PVector(r1.nextInt(width), r2.nextInt(height)), size.nextInt(sizeDifference) + minSize));
			}
			reset = 0;
		}
		
		//Keep looking through the paintComponent
		repaint();
	}
	
	//Finds the distance between two planets using the distance formula
	/**
	 * @param p1
	 * @param p2
	 * @return Math.sqrt(firstPart + secondPart)
	 */
	public double dist(Planet p1, Planet p2) {
		double firstPart = (p2.position.x - p1.position.x) * (p2.position.x - p1.position.x);
		double secondPart = (p2.position.y - p1.position.y) * (p2.position.y - p1.position.y);
		return Math.sqrt(firstPart + secondPart);
	}

	public static void main(String [] args) {
		Main m = new Main();

		// Handles the creation of the canvas where the graphics are
		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.getContentPane().add(new Main());
		frame.setLocationRelativeTo(null);
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(m);
		
		//Creates the initial population to fill the planet ArrayList
		for (int i = 0; i < size; i++) {
			Random r1 = new Random();
			Random r2 = new Random();
			Random size = new Random();
			int sizeDifference = maxSize - minSize;
			planet.add(new Planet(new PVector(r1.nextInt(width), r2.nextInt(height)), size.nextInt(sizeDifference) + minSize));
		}
	}
	
}
