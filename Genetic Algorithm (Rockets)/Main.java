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
	static int targetX = width / 3;			//width / 2
	static int targetY = height / 5;		//100
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
			//System.out.println(fitness.size());
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
			temp.travel();
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

		repaint();
	}

	public static ArrayList<Rockets> evaluateFitness() {
		ArrayList<Rockets> returned = new ArrayList<Rockets>();					//Array to be returned
		double fitnessDist = distToTarget(startX, startY);						//Distance to target from start (600)
		double fitnessNum = fitnessDist / 100;									//Distance between each 1 item subtracted (6)

		for (int i = 0; i < rockets.size(); i++) {								//Repeats for the pop of rockets
			Rockets temp = rockets.get(i);										//Gets rocket from ArrayList
			//temp.distFar(fitnessDist);										//Sets far enough to true if fit arguments
			if (true) {		//temp.farEnough									//
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

	public static double distToTarget(double x, double y) {
		double dist = Math.sqrt(((targetX - x) * (targetX - x)) + ((targetY - y) * (targetY - y)));
		return dist;
	}

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
					xChange[j] = parentA.getXChaOri()[j];
					yChange[j] = parentA.getYChaOri()[j];
					//xChange[j] = mutation(xChange[j]);
					//yChange[j] = mutation(yChange[j]);
				} else {
					xChange[j] = parentB.getXChaOri()[j];
					yChange[j] = parentB.getYChaOri()[j];
					//xChange[j] = mutation(xChange[j]);
					//yChange[j] = mutation(yChange[j]);
				}
			}

			returned.add(new Rockets(startX, startY, xChange, yChange, width, height, size, false, targetX, targetY));

		}

		return returned;
	}

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

		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.getContentPane().add(new Main());
		frame.setLocationRelativeTo(null);
		frame.setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(m);

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
			rockets.add(new Rockets(startX, startY, xChange, yChange, width, height, size, false, targetX, targetY));
		}

	}

	public static double randNeg(double num) {
		Random rand = new Random();
		int r = rand.nextInt(100);
		if (r <= 49) {
			num *= -1;
		}
		return num;
	}

}
