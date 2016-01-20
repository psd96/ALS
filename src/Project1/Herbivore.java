package Project1;

import java.io.Serializable;
import java.util.Random;


/**
 * This class sets the attributes of a Herbivore. It extends from LifeForm class and inherits all of its methods.
 */
public abstract class Herbivore extends LifeForm implements Serializable {

	/**
	 * This is the herbivore constructor. Within this it sets the map diemnsions and the specie of the LifeForm
	 * @param dx - X dimensions of the map
	 * @param dy - X dimensions of the map
	 * @param specie - the specie of the animal
	 * @param name - the name of the bug
	 * @param xpos -  the X-position of the bug
	 * @param ypos - the Y-position of the bug
	 * @param energy -the energy level of the bug
	 * @param ID - the ID of the bug
	 * @param smellRange - the smellrange of the bug
	 */
	public Herbivore(int dx,int dy, String specie, String name, int ID, int xpos, int ypos, int smellRange, int energy) {
		super(dx, dy, specie ,name,ID,xpos,ypos,smellRange,energy);
	}


	/**
	 * This method will find the nearest shelter in a given direction.
	 * It will return if a shelter is found in a given direction.
	 * @param direction - passes in which direction the method should check
	 * @return - returns if a shelter is found in a given direction.
	 */
	public boolean smellShelter(Direction direction) {
		boolean found;
		switch (direction) {
		case North:
			found = false;
			for (int i = 0; i < this.smellrange; i++) {
				if (this.ypos - i - 1 >= 0) {
					if (Character.isUpperCase(this.grid[this.xpos][this.ypos - i - 1])) {
						break;
					} else if (this.grid[this.xpos][this.ypos - i - 1] == '^') {
						found = true;
					}
				}
			}
			return found;
		case East:
			found = false;
			for (int i = 0; i < this.smellrange; i++) {
				if (this.xpos + i + 1 < size_x) {
					if (Character.isUpperCase(this.grid[this.xpos + 1 + i][this.ypos])) {
						break;
					} else if (this.grid[this.xpos + 1 + i][this.ypos] == '^') {
						found = true;
					}
				}
			}
			return found;
		case South:
			found = false;
			for (int i = 0; i < this.smellrange; i++) {
				if (this.ypos + 1 + i < size_y) {
					if (Character.isUpperCase(this.grid[this.xpos][this.ypos + 1 + i])) {
						break;
					} else if (this.grid[this.xpos][this.ypos + 1 + i] == '^') {
						found = true;
					}
				}
			}
			return found;
		case West:
			found = false;
			for (int i = 0; i < smellrange; i++) {
				if (this.xpos - 1 - i >= 0) {
					if (Character.isUpperCase(this.grid[this.xpos - 1 - i][this.ypos])) {
						break;
					} else if (this.grid[this.xpos - 1 - i][this.ypos] == '^') {
						found = true;
					}
				}
			}
			return found;
		}
		return false;
	}

	/**
	 * Will determine in which direction the animal should move.
	 * Either in the direction a shelter is found or a random direction.
	 * @return - in which direction the Herbivore should move.
	 */
	public Direction getDirectionOfShelter() { //Will return the direction in which a shelter lies
		Direction direction = Direction.North;
		boolean found = false;

		for (Direction d : Direction.values()) {
			if (smellShelter(d)) {
				found = true;
				direction = d;
			}
		}
		if (found) {
			return direction;
		} else {
			return getRandomDirection(direction);
		}
	}

	/**
	 * This method will move the animal in the given direction of a shelter.
	 * @param d - the direction a shelter has been found.
	 */
	public void MoveHome(Direction d) { //Moves the bug towards the shelter
		if (this.grid[this.xpos][this.ypos] != '^') {
			this.grid[this.xpos][this.ypos] = ' ';
		}

		switch (d) {
		case North:
			if (this.ypos - 1 >= 0 && this.grid[this.xpos][this.ypos - 1] != 'X') {
				this.ypos -= 1;
				energy -= 1;
			}
			break;
		case East:
			if (this.xpos + 1 < size_x && this.grid[this.xpos + 1][this.ypos] != 'X') {
				this.xpos += 1;
				energy -= 1;
			}
			break;
		case South:
			if (this.ypos + 1 < size_y && this.grid[this.xpos][this.ypos + 1] != 'X') {
				this.ypos += 1;
				energy -= 1;
			}
			break;
		case West:
			if (this.xpos - 1 >= 0 && this.grid[this.xpos - 1][this.ypos] != 'X') {
				this.xpos -= 1;
				energy -= 1;
			}
			break;
		}
	}

	/**
	 * This is a overwrite method of the update method in the LifeForm class.
	 * It will determine if the animal should move towards food or a shelter depending on it energy level.
	 * And will update the bugs position on the grid.
	 * And remove any bugs with an energy value of 0, and replace them with food.
	 */
	public void update() {
		//Bug moves towards a shelter if its energy is below 20 toherwise it goes around looking for food
		if (this.energy < 20) {
			MoveHome(getDirectionOfShelter());
		} else {
			Move(getDirectionOfFood());
		}

		this.setSymbol(getName().charAt(0));

		if (this.grid[this.xpos][this.ypos] == '^') {
			this.setSymbol('^');
		}

		//If the position is a food value it will get the assigned food energy and add it to the animals energy
		if (this.grid[this.xpos][this.ypos] != ' ') {
			if (this.grid[this.xpos][this.ypos] == '*') {
				//If it is a * it is poison so it will generate a number between 0 and 9 and take thats away from the animals energy
				Random rand = new Random();
				int poison = (rand.nextInt(9) + 1);
				this.energy -= poison;
			} else {
				this.energy += Character.getNumericValue(this.grid[this.xpos][this.ypos]);
			}
		}
			System.out.println("\tenergy: " + energy);
			System.out.println("\tposition: x = " + xpos + " y = " + ypos);

			setGridpos();
	}

}
