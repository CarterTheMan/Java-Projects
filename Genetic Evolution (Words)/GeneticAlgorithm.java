package Main;

import java.util.*;
import java.util.Random;

public class GeneticAlgorithm {
	
	public static void main(String [] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<String> pop = new ArrayList<String>();
		ArrayList<String> fitness = new ArrayList<String>();
		int popSize = 250;
		int generation = 1;
		double mutationRate = .5;
		String alphabet = "abcdefghijklmnopqrstuvwxyz ";
		
		//Gets the users input for the key (the desired end output)
		System.out.println("What is the key? ");
		String key = input.nextLine().toLowerCase();
		
		//Creates the first initial String that is the same size as the key
		for (int i = 0; i < popSize; i++) {
			String temp = "";
			for (int j = 0; j < key.length(); j++) {
				Random rand = new Random();
				temp += alphabet.charAt(rand.nextInt(27));
			}
			pop.add(new String(temp));
		}
		
		//While loop that repeats until the key has been found within the String
		while(finish(pop, key, generation)) {
			fitness = fitnessArray(pop, key);
			pop = crossover(fitness, popSize, mutationRate);
			print(pop, generation);
			generation++;
			System.out.println(" ");
		}		
	}
	
	//Checks the String to search and see if the key has been found
	/** 
	 * @param pop
	 * @param key
	 * @param generation
	 * @return true or false depending on if the key has been found
	 */
	public static boolean finish(ArrayList<String> pop, String key, int generation) {
		for(int i = 0; i < pop.size(); i++) {
			if (pop.get(i).contentEquals(key)) {
				System.out.println("Generation: " + generation);
				System.out.println("Line: " + (i + 1));
				return false;
			}
		}
		return true;
	}
	
	//Created the fitness array-list based on the String
	//Returns the actual fitness array-list
	/**
	 * @param pop
	 * @param key
	 * @return temp
	 */
	public static ArrayList<String> fitnessArray(ArrayList<String> pop, String key) {
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < pop.size(); i++) {
			String name = pop.get(i);
			int fitness = 0;
			for (int j = 0; j < key.length(); j++) {
				if (name.charAt(j) == key.charAt(j)) {
					fitness++;
				}
			}
			fitness *= fitness;
			for (int j = 0; j < fitness; j++) {
				temp.add(new String(name));
			}
		}
		return temp;
	}
	
	//Method that chooses two parents and creates a child between the two
	/** 
	 * @param fitness
	 * @param size
	 * @param mutationRate
	 * @return pop
	 */
	public static ArrayList<String> crossover(ArrayList<String> fitness, int size, double mutationRate) {
		ArrayList<String> pop = new ArrayList<String>();
		
		for (int i = 0; i < size; i++) {
			Random rand1 = new Random();
			int rand11 = rand1.nextInt(fitness.size());
			Random rand2 = new Random();
			int rand21 = rand2.nextInt(fitness.size());
			String parentA = fitness.get(rand11);
			String parentB = fitness.get(rand21);
			String temp = "";
			
			for (int j = 0; j < fitness.get(0).length(); j++) {
				Random rand = new Random(100);
				if (rand.nextInt() <= 49) {
					temp += parentA.charAt(j);
				} else {
					temp += parentB.charAt(j);
				}
			}
			
			//Sends each member of the String through the mutation method
			temp = mutation(temp, mutationRate);
			pop.add(new String(temp));
		}
		return pop;
	}
	
	//Changes individual characters in the individual that goes through this method
	/**
	 * @param name
	 * @param mutationRate
	 * @return name
	 */
	public static String mutation(String name, double mutationRate) {
		Random rand1 = new Random();
		String alphabet = "abcdefghijklmnopqrstuvwxyz ";
		for (int i = 0; i < name.length(); i++) {
			Random rand2 = new Random();
			if (100 * rand1.nextDouble() <= mutationRate) {
				if (i == 0) {
					return alphabet.charAt(rand2.nextInt(27)) + name.substring(1, name.length());
				} else if (i == name.length()) {
					return name.substring(0, name.length() - 1) + alphabet.charAt(rand2.nextInt(27));
				} else {
					return name.substring(0, i) + alphabet.charAt(rand2.nextInt(27)) + name.substring(i + 1, name.length());
				}
			}
		}
		return name;
	}
	
	//Prints the String out along with the generation
	/**
	 * @param pop
	 * @param generation
	 */
	public static void print(ArrayList<String> pop, int generation) {
		System.out.println("Generation: " + generation);
		for (int i = 0; i < pop.size(); i++) {
			System.out.println((i + 1) + ": " + pop.get(i));
		}
	}
}
