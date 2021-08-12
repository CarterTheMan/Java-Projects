package RocketEvolution;

import java.awt.Color;
import java.awt.Graphics;

public class avgPoints {

	double xPos, yPos;
	String fOS;
	
	public avgPoints(double xPos, double yPos, String fOS) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.fOS = fOS;			// "first" or "second"
	}
	
	public double xPos() {
		return xPos;
	}
	
	public double yPos() {
		return yPos;
	}
	
	public void setFOS(String fOS) {
		this.fOS = fOS;
	}
	
	/**
	 * If fOS is "first" then it sets the color to green and if the fOS is "second" then it is set to yellow
	 * @param g
	 * @return Color of this set of points
	 */
	public Color color(Graphics g) {
		if (fOS.equals("first")) {
			return Color.GREEN;
		} else if (fOS.equals("second")) {
			return Color.YELLOW;
		} else {
			return Color.WHITE;
		}
	}
	
}
