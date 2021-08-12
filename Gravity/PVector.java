package Gravity;

public class PVector {

	double x;
	double y;
	double maxSpeed = 25;
	
	PVector() {
		x = 0;
		y = 0;
	}
	
	PVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	PVector(PVector p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	void setX(double x) {
		this.x = x;
	}
	
	void setY(double y) {
		this.y = y;
	}
	
	double getX() {
		return x;
	}
	
	double getY() {
		return y;
	}
	
	/**
	 * Finds the magnitude of the movement
	 * @return the total magnitude of the PVector
	 */
	double mag() {
		return Math.sqrt((x * x) + (y * y));
	}
	
	/**
	 * Normalizes the PVector 
	 */
	void normalize() {
		x = x / mag();
		y = y / mag();
	}
	
	void add(PVector p) {
		x += p.x;
		y += p.y;
	}
	
	void sub(PVector p) {
		x -= p.x;
		y -= p.y;
	}
	
	void multiply(double d) {
		x *= d;
		y *= d;
	}
	
	void multiply(PVector p) {
		x *= p.x;
		y *= p.y;
	}
	
	void divide(double d) {
		x /= d;
		y /= d;
	}
	
	void divide(PVector p) {
		x /= p.x;
		y /= p.y;
	}
	
	@Override
	public String toString() {
		return x + " " + y;
	}
	
}
