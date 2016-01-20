package Project1;

import javafx.scene.paint.Color;

/**
 * This class creates a Bug lifeform. This class extends from Herbivore and inherits all of its methods.
 */
public class Bug extends Herbivore {


	/**
	 * The bug constructor that creates a new bug with the following entered attributes.
	 * @param dx - X dimensions of the map
	 * @param dy - ç
	 * @param name - the name of the bug
	 * @param xpos -  the X-position of the bug
	 * @param ypos - the Y-position of the bug
	 * @param energy -the energy level of the bug
	 * @param ID - the ID of the bug
	 * @param smellRange - the smellrange of the bug
	 */
	public Bug(int dx, int dy, String name, int xpos, int ypos, int energy, int ID, int smellRange ) {
		super(dx, dy, "Bug");
		setName(name);
		setBugID(ID);
		setEnergy(energy);
		setSymbol(name.charAt(0));
		setXpos(xpos);
		setYpos(ypos);
		setSmellrange(smellRange);
	}

	/**
	 * Sets the colour which bugs should be displayed on the grid
	 * @return - returns the color that will represents the bug
	 */
	public Color getFill() {
		return Color.RED;
	}


}
