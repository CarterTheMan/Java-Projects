package AStarDone2;

import java.awt.Color;
import java.awt.Graphics;

public class cell {
	
	cellType c;			// The enum type of the cell
	cell parent = null;	// In the maze path, the parent cell is the cell that leads to this cell
	int xLoc;			// The xLocation of this cell
	int yLoc;			// The yLocation of this cell
	int gCost; 			// Distance from start
	int hCost; 			// Distance from end
	int fCost; 			// gCost and hCost added together
	
	public cell(int xLoc, int yLoc) {
		c = cellType.normalU;		// Cells are initially set to normal unsearched
		this.xLoc = xLoc;
		this.yLoc = yLoc;
	}
	
	/**
	 * sets the color of the cell based on its cell type
	 * @param g
	 * @return color of the call
	 */
	public Color color(Graphics g) {
		if (c == cellType.start) {
			return Color.GREEN;
		} else if (c == cellType.normalU) {
			return Color.WHITE;
		} else if (c == cellType.normalS) {
			return Color.LIGHT_GRAY;
		} else if (c == cellType.wall) {
			return Color.BLACK;
		} else if (c == cellType.end) {
			return Color.RED;
		}
		return Color.YELLOW;
	} 
	
	public cell getParent() {
		return parent;
	}
	
	public void setParent(cell c) {
		parent = c;
	}
	
	public int getXLoc() {
		return xLoc;
	}
	
	public int getYLoc() {
		return yLoc;
	}
	
	public void setCell(cellType c) {
		this.c = c;
	}
	
	public cellType getCell() {
		return c;
	}
	
	public void setGCost(int gCost) {
		this.gCost = gCost;
	}
	
	public int getGCost() {
		return gCost;
	}
	
	public void setHCost(int hCost) {
		this.hCost = hCost;
	}
	
	public int getHCost() {
		return hCost;
	}
	
	public int getFCost() {
		return hCost + gCost;
	}
}
