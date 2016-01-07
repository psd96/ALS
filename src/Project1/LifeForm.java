package Project1;

import java.io.Serializable;
import java.util.Random;

import javafx.scene.paint.Color;

@SuppressWarnings("serial")
public abstract class LifeForm implements Serializable {

	private String name;
	private char symbol;
	protected int energy;
	protected int xpos;
	protected int ypos;
	private int BugID;
	protected int smellrange;
	protected int size_x = 25;
	protected int size_y = 25;
	protected char grid[][];

	public int getXdimension() {
		return size_x;
	}

	public void setXdimension(int size_x) {
		this.size_x = size_x;
	}

	public int getYdimension() {
		return size_y;
	}

	public void setYdimension(int size_y) {
		this.size_y = getYdimension();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public char[][] getGrid() {
		return grid;
	}

	public void setGrid(char grid[][]) {
		this.grid = grid;
	}

	public int getXpos() {
		return xpos;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}

	public int getBugID() {
		return BugID;
	}

	public void setBugID(int bugID) {
		BugID = bugID;
	}

	public int getSmellrange() {
		return smellrange;
	}

	public void setSmellrange(int smellrange) {
		this.smellrange = smellrange;
	}

	public void setGridpos() {
		grid[xpos][ypos] = symbol;
	}

	public enum Direction {
		North, East, South, West;
	}

	public boolean smellFood(Direction direction) {
		boolean found;
		switch (direction) {
		case North:
			found = false;
			for (int i = 0; i < this.smellrange; i++) {
				if (this.ypos - i - 1 >= 0) {
					if (Character.isUpperCase(this.grid[this.xpos][this.ypos - i - 1]) || this.grid[this.xpos][this.ypos - i - 1] == '^') {
						break;
					} else if (this.grid[this.xpos][this.ypos - i - 1] != ' ') {
							found = true;
						}
					}
				}
			return found;
		case East:
			found = false;
			for (int i = 0; i < this.smellrange; i++) {
				if (this.xpos + i + 1 < size_x) {
					if (Character.isUpperCase(this.grid[this.xpos + 1 + i][this.ypos]) || this.grid[this.xpos + 1 + i][this.ypos] == '^') {
						break;
					}else if (this.grid[this.xpos + 1 + i][this.ypos] != ' ') {
							found = true;
						}
					}
				}
			return found;
		case South:
			found = false;
			for (int i = 0; i < this.smellrange; i++) {
				if (this.ypos + 1 + i < size_y) {
					if (Character.isUpperCase(this.grid[this.xpos][this.ypos + 1 + i]) || this.grid[this.xpos][this.ypos + 1 + i] == '^') {
						break;
					}else if (this.grid[this.xpos][this.ypos + 1 + i] != ' ') {
							found = true;
						}
					}
				}
			return found;
		case West:
			found = false;
			for (int i = 0; i < smellrange; i++) {
				if (this.xpos - 1 - i >= 0) {
					if (Character.isUpperCase(this.grid[this.xpos - 1 - i][this.ypos]) || this.grid[this.xpos - 1 - i][this.ypos] == '^') {
						break;
					} else if (this.grid[this.xpos - 1 - i][this.ypos] != ' ') {
							found = true;
						}
					}
				}
			return found;
		}
		return false;
	}

	public Direction getRandomDirection(Direction d) {
		Random rand = new Random();
		int temp = rand.nextInt(Direction.values().length);
		d = Direction.values()[temp];
		return d;
	}

	public Direction getDirectionOfFood() {
		Direction direction = Direction.North;
		boolean found = false;

		for (Direction d : Direction.values()) {
			if (smellFood(d)) {
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


	public void Move(Direction d){
		this.grid[this.xpos][this.ypos] = ' ';

		switch (d) {
		case North:
			if (this.ypos - 1 >= 0 && this.grid[this.xpos][this.ypos - 1] != 'X' && this.grid[this.xpos][this.ypos - 1] != '^') {
				this.ypos -= 1;
				energy -= 1;
			}
			break;
		case East:
			if (this.xpos + 1 < size_x && this.grid[this.xpos + 1][this.ypos] != 'X' && this.grid[this.xpos + 1][this.ypos] != '^') {
				this.xpos += 1;
				energy -= 1;
			}
			break;
		case South:
			if (this.ypos + 1 < size_y && this.grid[this.xpos][this.ypos + 1] != 'X' && this.grid[this.xpos][this.ypos + 1 ] != '^') {
				this.ypos += 1;
				energy -= 1;
			}
			break;
		case West:
			if (this.xpos - 1 >= 0 && this.grid[this.xpos - 1][this.ypos] != 'X' && this.grid[this.xpos - 1][this.ypos] != '^') {
				this.xpos -= 1;
				energy -= 1;
			}
			break;
		}
	}


	public void update() {
		Move(getDirectionOfFood());
		
		if (this.grid[this.xpos][this.ypos] == '^') {
			this.setSymbol('^');
		}
		
		if (this.grid[this.xpos][this.ypos] != ' ') {
			this.energy += Character.getNumericValue(this.grid[this.xpos][this.ypos]);
		}
		System.out.println("\tenergy: " + energy);
		System.out.println("\tposition: x = " + xpos + " y = " + ypos);

		setGridpos();
	}
	
	public abstract Color getFill();;
	public abstract String getType();;
	
}