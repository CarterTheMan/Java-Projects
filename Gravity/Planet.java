package Gravity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Planet {
	
	PVector position;
	PVector velocity;
	PVector acceleration;
	Random rand = new Random();
	int color;
	double mass;
	
	Planet(PVector position, double mass) {
		this.position = position;				// Sets the initial position equal to the input position
		this.mass = mass;						// Sets the mass equal to the input mass 
		velocity = new PVector(0, 0);			// Sets the initial velocity to 0
		acceleration = new PVector(0, 0);		// Sets the initial acceleration to 0
		color = rand.nextInt(7);				// Sets a random color between 0 and 7
	}
	
	/**
	 * Moved the planet and resets the acceleration
	 */
	void update() {
		edges();
		velocity.add(acceleration);
		position.add(velocity);
		acceleration.multiply(0);
	}
	
	/**
	 * This method finds how much the given planet is affected by another planets mass and changed the given planets 
	 * movement accordingly
	 * @param p
	 */
	void attract(Planet p) {
		int g = 1;
		double str = 50;			//The higher, the lesser the gravity so the slower they move
		PVector force = new PVector(position.x - p.position.x, position.y - p.position.y);
		double d = force.mag();
		force.normalize();
		double strength = (g * mass * p.mass) / (d * d);
		force.multiply(strength/str);
		acceleration.sub(force);
	}
	
	/**
	 * If the planet goes past the edge, then the speed is reduced so that it will stay on screen
	 */
	void edges() {
		if (position.x < 0) {
			velocity.multiply(.01);
			position.x = 0;
			velocity.x *= -1;
		}
		if (position.y < 0) {
			velocity.multiply(.01);
			position.y = 0;
			velocity.x *= -1;
		}
		if (position.x > Main.width) {
			velocity.multiply(.01);
			position.x = Main.width;
			velocity.x *= -1;
		}
		if (position.y > Main.height) {
			velocity.multiply(.01);
			position.y = Main.height;
			velocity.x *= -1;
		}
	}
	
	/**
	 * This method returns the color of the planet 
	 * @param g
	 * @return color of the planet
	 */
	public Color color(Graphics g) {
		if (color == 0) {
			return Color.RED;
		} else if (color == 1) {
			return Color.ORANGE;
		} else if (color == 2) {
			return Color.YELLOW;
		} else if (color == 3) {
			return Color.GREEN;
		} else if (color == 4) {
			return Color.BLUE;
		} else if (color == 5) {
			return Color.MAGENTA;
		} else if (color == 6) {
			return Color.PINK;
		} else {
			return Color.RED;
		}
	}
	
	/**
	 * Allows the mass of a planet to be changed
	 * @param mass
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}

}
