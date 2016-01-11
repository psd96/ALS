package Project1;


public abstract class Carnivore extends LifeForm {
	
	
	public Carnivore(String name, char symbol, int energy, int xpos, int ypos, int bugID, int smellrange, int dx, int dy) {//Constructor
		this.setName(name);
		this.setSymbol(symbol);
		this.setEnergy(energy);
		this.setXpos(xpos);
		this.setYpos(ypos);
		this.setBugID(bugID);
		this.setSmellrange(smellrange);
		this.setXdimension(dx);
		this.setYdimension(dy);
	}
	
	public Carnivore(int dx, int dy) { //Constructor
		this.setXdimension(dx);
		this.setYdimension(dy);

	}

	public boolean smellFood(Direction direction) { //Smaell food fuction detects food or other lifeforms in a given direction
		boolean found;
		switch (direction) {
		case North:
			found = false;
			for (int i = 0; i < this.smellrange; i++) {
				if (this.ypos - i - 1 >= 0) {
					if (this.grid[this.xpos][this.ypos - i - 1] == 'X' || this.grid[this.xpos][this.ypos - i - 1] == '^') {
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
					if (this.grid[this.xpos + 1 + i][this.ypos] == 'X' || this.grid[this.xpos + 1 + i][this.ypos] == '^') {
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
					if (this.grid[this.xpos][this.ypos + 1 + i] == 'X' || this.grid[this.xpos][this.ypos + 1 + i] == '^') {
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
					if (this.grid[this.xpos - 1 - i][this.ypos] == 'X' || this.grid[this.xpos - 1 - i][this.ypos] == '^') {
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
	
}
