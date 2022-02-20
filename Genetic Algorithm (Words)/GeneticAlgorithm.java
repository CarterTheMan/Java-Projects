package main;

import java.util.*;

public class main {

	static String alphabet = "abcdefghijklmnopqrstuvwxyz.,!? ";

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<String> fitness;
		int populationSize = 50;	// The longer your statement, the larger the populationSize
		double mutationRate = .05;	// The longer your statement, the lower the mutationRate
		int generation = 1;
		boolean targetGotten = false;

		System.out.println("What is the target phrase? (lowercase please)");
		String target = input.nextLine().toLowerCase();

		ArrayList<String> population = initialization(target, populationSize);
		displayPopulation(population, generation);
		targetGotten = targetFound(population, target, generation);

		while (!targetGotten) {
			fitness = selection(population, target);
			population = reproduction(fitness, populationSize);
			population = mutation(population, mutationRate);
			displayPopulation(population, generation);
			generation++;
			targetGotten = targetFound(population, target, generation);
		}
	}

	/**
	 * Creates the initial population
	 * @param target
	 * @param populationSize
	 * @return population
	 */
	public static ArrayList<String> initialization(String target, int populationSize) {
		ArrayList<String> population = new ArrayList<String>();
		for (int i = 0; i < populationSize; i++) {
			String temp = "";
			for (int j = 0; j < target.length(); j++) {
				Random random = new Random();
				temp += alphabet.charAt(random.nextInt(alphabet.length()));
			}
			population.add(temp);
		}
		return population;
	}

	/**
	 * Creates the fitness list
	 * @param population
	 * @param target
	 * @return the fitness list
	 */
	public static ArrayList<String> selection(ArrayList<String> population, String target) {
		ArrayList<String> fitness = new ArrayList<String>();
		for (int i = 0; i < population.size(); i++) {
			int counter = 0;
			for (int j = 0; j < target.length(); j++) {
				if (population.get(i).charAt(j) == target.charAt(j)) {
					counter++;
				}
			}
			for (int j = 0; j < counter; j++) {
				fitness.add(population.get(i));
			}
		}
		return fitness;
	}

	/**
	 * Does the reproduction based on the fitness list
	 * @param fitness
	 * @param populationSize
	 * @return population of children
	 */
	public static ArrayList<String> reproduction(ArrayList<String> fitness, int populationSize) {
		ArrayList<String> population = new ArrayList<String>();

		for (int i = 0; i < populationSize; i++) {
			Random randomA = new Random();
			String parentA = fitness.get(randomA.nextInt(fitness.size()));
			Random randomB = new Random();
			String parentB = fitness.get(randomB.nextInt(fitness.size()));

			population.add(crossover(parentA, parentB));
		}

		return population;
	}

	/**
	 * Crosses over between parents to create child
	 * @param parentA
	 * @param parentB
	 * @return the child of parents A and b
	 */
	public static String crossover(String parentA, String parentB) {
		String child = "";
		for (int i = 0; i < parentA.length(); i++) {
			Random random = new Random();
			if (random.nextInt(100) < 50) {
				child += parentA.charAt(i);
			} else {
				child += parentB.charAt(i);
			}
		}
		return child;
	}
	
	/**
	 * Mutates the population
	 * @param children
	 * @param mutationRate
	 * @return new population
	 */
	public static ArrayList<String> mutation(ArrayList<String> children, double mutationRate) {
		ArrayList<String> population = new ArrayList<String>();
		
		for (int i = 0; i < children.size(); i++) {
			String newChild = "";
			for (int j = 0; j < children.get(i).length(); j++) {
				Random mutationChance = new Random();
				if (mutationChance.nextDouble() * 100 <= mutationRate) {
					Random newLetter = new Random();
					newChild += alphabet.charAt(newLetter.nextInt(alphabet.length()));
				} else {
					newChild += children.get(i).charAt(j);
				}
			}
			population.add(newChild);
		}
		
		return population;
	}

	/**
	 * Displays the population to the console for user to see
	 * @param population
	 * @param generation
	 */
	public static void displayPopulation(ArrayList<String> population, int generation) {
		System.out.println("\n\n\n\n");
		System.out.println("Generation: " + generation);
		for (int i = 0; i < population.size(); i++) {
			System.out.println(i+1 + ": " + population.get(i));
		}
	}

	/**
	 * Determines if the target has been found to end the program and display results
	 * @param population
	 * @param target
	 * @param generation
	 * @return true or false
	 */
	public static boolean targetFound(ArrayList<String> population, String target, int generation) {
		for (int i = 0; i < population.size(); i++) {
			if (population.get(i).equals(target)) {
				System.out.println("\nTarget found on line " + (i+1) + " of generation " + generation + "!");
				return true;
			}
		}
		return false;
	}

}
