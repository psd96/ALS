package Project1;

import javafx.scene.paint.Color;

/**
 * This class creates a Bug lifeform. This class extends from Herbivore and inherits all of its methods.
 */
public class Bug extends Herbivore {


	/**
	 * The bug constructor that creates a new bug with the following entered attributes.
	 * @param dx - X dimensions of the map
	 * @param dy - X dimensions of the map
	 * @param name - the name of the bug
	 * @param xpos -  the X-position of the bug
	 * @param ypos - the Y-position of the bug
	 * @param energy -the energy level of the bug
	 * @param ID - the ID of the bug
	 * @param smellRange - the smellrange of the bug
	 */
	public Bug(int dx,int dy, String name, int ID, int xpos, int ypos, int smellRange, int energy) {
		super(dx, dy, "Bug",name,ID,xpos,ypos,smellRange,energy);
	}

	/**
	 * Sets the colour which bugs should be displayed on the grid
	 * @return - returns the color that will represents the bug
	 */
	public Color getFill() {
		return Color.RED;
	}


}
