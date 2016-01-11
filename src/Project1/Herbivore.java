package Project1;

import java.io.Serializable;
import java.util.Random;


public abstract class Herbivore extends LifeForm implements Serializable {
	private char original;

	public Herbivore(int dx,int dy, String specie) { //Constructor
		this.setXdimension(dx);
		this.setYdimension(dy);
		original = getSymbol();
		this.setType(specie);
		
	}
	

	public boolean smellShelter(Direction direction) { //Function thats finds the nearest shelter in a given smellRange
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

	public void update() {
		//Bug moves towards a shelter if its energy is below 20 toherwise it goes around looking for food
		if (energy < 20) {
			MoveHome(getDirectionOfShelter());
		} else {
			Move(getDirectionOfFood());
		}

		this.setSymbol(original);

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
