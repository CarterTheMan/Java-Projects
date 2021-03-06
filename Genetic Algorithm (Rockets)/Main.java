package RocketEvolution;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JPanel {

	static int size = 400;
	static int width = 800;
	static int height = 800; 
	static int targetX = width / 3;			
	static int targetY = height / 5;		
	static int startX = width / 2;
	static int startY = height - 100;
	static int numOfRockets = 250;
	static int generation = 1;
	static double mutationRate = .1;
	static double averageX = 0;
	static double averageY = 0;
	static ArrayList<Rockets> rockets = new ArrayList<Rockets>();
	static ArrayList<Rockets> fitness = new ArrayList<Rockets>();
	static ArrayList<avgPoints> first = new ArrayList<avgPoints>();
	static ArrayList<avgPoints> second = new ArrayList<avgPoints>();

	
	/** 
	 * This method updates everything on the canvas with the graphics. It will continue to iterate through this 
	 * until the program is ended.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		//Handles averages and draws the average points
		averageX = 0;
		averageY = 0;
		for (int j = 0; j < rockets.size(); j++) {
			averageX += (rockets.get(j).getX() / numOfRockets);
			averageY += (rockets.get(j).getY() / numOfRockets);
		}
		first.add(new avgPoints(averageX, averageY, "first"));

		//Handles changes from one generation to the next
		if (rockets.get(rockets.size() - 1).getCounter() == 399) {
			//Prints all output
			System.out.println("Generation: " + generation);
			System.out.println("Target: (" + targetX + ", " + targetY + ")");
			System.out.println("Average: (" + averageX + ", " + averageY + ")");
			System.out.println("To target: " + distToTarget(averageX, averageY));
			System.out.println(" ");

			//Evaluate fitness and does all crossover
			fitness = evaluateFitness();
			rockets = crossover();

			//Clears all ArrayLists and copies for the second array
			second.clear();
			for (int i = 0; i < first.size(); i++) {
				second.add(first.get(i));
			}
			for (int i = 0; i < second.size(); i++) {
				second.get(i).setFOS("second");
			}
			first.clear();

			//Adds another generation
			generation++;
		}

		//Draws the second average points
		for (int i = 0; i < second.size(); i++) {
			avgPoints temp = second.get(i);
			temp.setFOS("second");
			g.setColor(temp.color(g));
			g.fillOval((int) temp.xPos - 3, (int) temp.yPos - 5, 6, 10);
		}

		//Handles all the rockets normal processes
		for (int i = 0; i < rockets.size(); i++) {
			Rockets temp = rockets.get(i);
			g.setColor(temp.color(g));
			temp.move();
			g.fillOval((int) temp.xPos - 3,(int) temp.yPos - 5, 6, 10);
		}

		//Draws the first average point
		for (int i = 0; i < first.size(); i++) {
			avgPoints temp = first.get(i);
			g.setColor(temp.color(g));
			g.fillOval((int) temp.xPos - 3, (int) temp.yPos - 5, 6, 10);
		}


		//Borders and Target
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, width, 0);
		g.drawLine(0, 0, 0, height);
		g.drawLine(width, 0, width, height);
		g.drawLine(0, height, width, height);
		g.fillOval(targetX - 5, targetY - 5, 10, 10);

		//Makes this method update every time upon completion
		repaint();
	}

	/**
	 * Finds the fitness of the rockets and makes the fitness arraylist
	 * @return returned
	 */
	public static ArrayList<Rockets> evaluateFitness() {
		ArrayList<Rockets> returned = new ArrayList<Rockets>();					//Array to be returned
		double fitnessDist = distToTarget(startX, startY);						//Distance to target from start (600)
		double fitnessNum = fitnessDist / 100;									//Distance between each 1 item subtracted (6)

		for (int i = 0; i < rockets.size(); i++) {								//Repeats for the pop of rockets
			Rockets temp = rockets.get(i);										//Gets rocket from ArrayList
			if (true) {											//
				double tempDist = distToTarget(temp.getX(), temp.getY());		//Gets rockets distance to target
				int fitInit = 100;												//How many will be in fitness array to start with
				int fitSubtract = (int) ((int) tempDist / fitnessNum);			//How much will be subtracted from fitness array added amount
				fitInit = fitInit - fitSubtract;								//Subtracted that number
				fitInit *= fitInit;												//Squares the fitness number

				for (int j = 0; j < fitInit; j++) {								//Repeats for the initial fitness to be added
					returned.add(temp);											//Adds the new numbers to the fitness array
				}
			}
		}

		return returned;
	}

	/**
	 * Finds the distance from the input rocket to the target
	 * @param x
	 * @param y
	 * @return
	 */
	public static double distToTarget(double x, double y) {
		double dist = Math.sqrt(((targetX - x) * (targetX - x)) + ((targetY - y) * (targetY - y)));
		return dist;
	}

	/**
	 * This method does the crossover between two rockets. From the fitness arraylist, two random parents are chosen. 
	 * It then randomly chooses the genes (vectors) for the child based on the parents. It does this for every rocket. 
	 * @return returned
	 */
	public static ArrayList<Rockets> crossover() {
		ArrayList<Rockets> returned = new ArrayList<Rockets>();

		for (int i = 0; i < rockets.size(); i++) {
			Random rand1 = new Random();
			int rand11 = rand1.nextInt(fitness.size());
			Random rand2 = new Random();
			int rand21 = rand2.nextInt(fitness.size());
			Rockets parentA = fitness.get(rand11);
			Rockets parentB = fitness.get(rand21);
			double[] xChange = new double[size];
			double[] yChange = new double[size];

			for (int j = 0; j < size; j++) {
				Random rand = new Random();
				int r = rand.nextInt(100);
				if (r <= 49) {
					xChange[j] = parentA.xChange[j];
					yChange[j] = parentA.yChange[j];
				} else {
					xChange[j] = parentB.xChange[j];
					yChange[j] = parentB.yChange[j];
				}
			}

			returned.add(new Rockets(startX, startY, xChange, yChange, width, height, size, targetX, targetY));

		}

		return returned;
	}

	/**
	 * This method does the mutation (as the name implies) of each rocket. There has to be some level of random change for the 
	 * possibility of improvement and this method does just that. For every gene of the rocket, it decides if that gene will 
	 * change and what that change would be. 
	 * @param num
	 * @return returned
	 */
	public static double mutation(double num) {
		double returned = num;
		Random rand = new Random();
		int r = rand.nextInt(100);
		if (r <= mutationRate) {
			Random rand1 = new Random();
			double rand11 = (rand1.nextDouble() * 1) + 1;
			return rand11;
		}
		return returned;
	}

	public static void main(String[] args) {
		Main m = new Main();

		// Handles everything to do with the canvas of the graphics.
		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.getContentPane().add(new Main());
		frame.setLocationRelativeTo(null);
		frame.setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(m);

		// Makes all the initial rockets in the first generation
		for (int i = 0; i < numOfRockets; i++) {
			double rand11 = 0;
			double rand21 = 0;
			while (rand11 == 0 && rand21 == 0) {
				Random rand1 = new Random();
				rand11 = rand1.nextDouble() * 2;
				Random rand2 = new Random();
				rand21 = rand2.nextDouble() * 2;
			}
			rand11 = randNeg(rand11);
			rand21 *= -1;
			double[] xChange = new double[size];
			double[] yChange = new double[size];
			for (int j = 0; j < size; j++) {
				xChange[j] = rand11;
				yChange[j] = rand21;
			}
			rockets.add(new Rockets(startX, startY, xChange, yChange, width, height, size, targetX, targetY));
		}
		
		//Once done, everything in the paintComponent() method will keep the program continuously running
	}

	/**
	 * Takes a random imput number and has a 50% change to make it a negative
	 * @param num
	 * @return num
	 */
	public static double randNeg(double num) {
		Random rand = new Random();
		int r = rand.nextInt(100);
		if (r <= 49) {
			num *= -1;
		}
		return num;
	}

}
