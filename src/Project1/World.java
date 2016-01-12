package Project1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class World implements Serializable {
	char[][] grid;
	List<LifeForm> animalList = new ArrayList<>();
	List<Bug> bugList = new ArrayList<>();
	List<Lion> lionList = new ArrayList<>();
	List<LifeForm> hurdList = new ArrayList<>();
	List<LifeForm> shelterList = new ArrayList<>();
	private int size_x;
	private int size_y;
	transient private Group bugGroup = new Group();
	transient private Group foodGroup = new Group();
	transient private Group obstacleGroup = new Group();
	transient private Group shelterGroup = new Group();
	private int foodLeft;
	private int shelters = 0;
	private int Food = 0;
	private int Obstacles = 0;

	//Constructor
	World(Group root, int food, int obstacles, int bugs, int lions, int sizex, int sizey, int shelters) {
		clearGroups();
		setFood(food);
		setObstacles(obstacles);
		setShelters(shelters);
		setXdimension(sizex);
		setYdimension(sizey);
		grid = new char[getXdimension()][getYdimension()];

		for (int i = 0; i < sizex; i++) {
			for (int j = 0; j < sizey; j++) {
				grid[i][j] = ' ';
			}
		}
		root.getChildren().add(bugGroup);
		root.getChildren().add(foodGroup);
		root.getChildren().add(obstacleGroup);
		root.getChildren().add(shelterGroup);

		for (int i = 0; i < bugs; i++) {
			AddBug();
		}

		for (int i = 0; i < lions; i++) {
			AddLion();
		}
		
		for (int i = 0; i < getFood(); i++) {
			AddFood();
		}

		for (int i = 0; i < getObstacles(); i++) {
			AddObstacle();
		}

		for (int i = 0; i < shelters; i++) {
			AddShelter();
		}

	}

	//Constructor - used when loading world from a config file
	public World(World w, Group root) {
		root.getChildren().add(bugGroup);
		root.getChildren().add(foodGroup);
		root.getChildren().add(obstacleGroup);
		root.getChildren().add(shelterGroup);
		animalList = w.animalList;
		setFood(w.getFood());
		setObstacles(w.getObstacles());
		setShelters(w.getShelters());
		setGrid(w.getGrid());
		setXdimension(w.getXdimension());
		setYdimension(w.getYdimension());

		//Gets the animalList and adds all the animals to the grid
		for(LifeForm i : animalList){
			i.setGrid(grid);
			i.setGridpos();
		}

	}
	public char[][] getGrid() {
		return grid;

	}

	public void setGrid(char grid[][]) {
		this.grid = grid;
	}

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
		this.size_y = size_y;
	}

	public int getFood() {
		return Food;
	}

	public void setFood(int food) {
		Food = food;
	}

	public int getObstacles() {
		return Obstacles;
	}

	public void setObstacles(int obstacles) {
		Obstacles = obstacles;
	}

	public int getShelters() {
		return shelters;
	}

	public void setShelters(int shelters) {
		this.shelters = shelters;
	}

	//Adds shelter to the grid
	public void AddShelter() {
		Random rand = new Random();
		int px, py;
		do {
			px = rand.nextInt(getXdimension());
			py = rand.nextInt(getYdimension());
		} while (this.grid[px][py] != ' ');
		grid[px][py] = '^';
	}

	//Adds food to the grid
	public void AddFood() {
		Random rand = new Random();
		int px, py;
		do {
			px = rand.nextInt(getXdimension());
			py = rand.nextInt(getYdimension());
		} while (this.grid[px][py] != ' ');
		//Randomly generate boolean to determine if to add food or poison
		if(rand.nextInt(3) > 0) {
			grid[px][py] = (char) ('0' + (rand.nextInt(9) + 1));
		} else{
			grid[px][py] = '*';
		}
	}

	//Adds food to the grid in a given position
	public void AddFood(int px, int py) {
		Random rand = new Random();
		grid[px][py] = (char) ('0' + (rand.nextInt(9) + 1));

	}

	//Adds obstacle to the grid
	public void AddObstacle() {
		Random rand = new Random();
		int px, py;
		do {
			px = rand.nextInt(getXdimension());
			py = rand.nextInt(getYdimension());
		} while (this.grid[px][py] != ' ');
		grid[px][py] = 'X';
	}

	//Adds Bug to the grid
	public void AddBug() {
		Bug bug = new Bug(getXdimension(), getYdimension());
		bug.setGrid(grid);
		bug.setGridpos();
		animalList.add(bug);
		bugList.add(bug);
	}

	//Adds Lion to the grid
	public void AddLion() {
		Lion lion = new Lion(getXdimension(), getYdimension());
		lion.setGrid(grid);
		lion.setGridpos();
		animalList.add(lion);
		lionList.add(lion);
	}

	//Adds a herd to the grid in a given position and with a given energy
	public void AddHerd(int xpos, int ypos, int energy, String specie) {
		Herd herd = new Herd(energy, xpos, ypos, getXdimension(), getYdimension());
		herd.setGrid(grid);
		herd.setGridpos();
		herd.setHerdType(specie);
		herd.setMembers(2);
		animalList.add(herd);
	}

	//Checks to see if the bug can enter a shelter - has to be a herbivore and energy < 20
	public void enterShelter() {
		for (int i = 0; i < animalList.size(); i++) {
			if (this.grid[animalList.get(i).getXpos()][animalList.get(i).getYpos()] == '^'
					&& animalList.get(i) instanceof Herbivore && animalList.get(i).getEnergy() < 20) {
				//Removes animal from animalList and adds to the shelterList
				shelterList.add(animalList.get(i));
				animalList.remove(i);
			}
		}
	}

	//Will check the shelters to see if any of the animals in there have an energy value greater than 60
	public void checkShelter() {
		enterShelter();
		for (int i = 0; i < shelterList.size(); i++) {
			shelterList.get(i).setEnergy(shelterList.get(i).getEnergy() + 1);

			if (shelterList.get(i).getEnergy() > 60) {
				//If greater that 60 then animal leaves the shelter
				emptyShelter(i);
			}
		}
	}

	//Removes bugs from the shelter
	public void emptyShelter(int i) {
		//Removes them from shelterList and adds them back to the animalList and they go back onto the grid
		animalList.add(shelterList.get(i));
		animalList.get(animalList.size() - 1).setSymbol(shelterList.get(i).getSymbol());
		shelterList.remove(i);
	}

	//Checks if animals are colliding with another animal
	public void checkCollision() {
		for (int i = 0; i < animalList.size(); i++) {
			for (int j = 0; j < animalList.size(); j++) {
				if (i == j) {
					break;
					//If they are and one is a carnivore, the carnivore will eat the herbivore and gain its energy
				} else if (animalList.get(i).getXpos() == animalList.get(j).getXpos()
						&& animalList.get(i).getYpos() == animalList.get(j).getYpos()) {
					if (animalList.get(i) instanceof Herbivore) {
						animalList.get(j).setEnergy(animalList.get(i).getEnergy() + animalList.get(j).getEnergy());
						animalList.remove(i);

					} else if (animalList.get(j) instanceof Herbivore) {
						animalList.get(i).setEnergy(animalList.get(j).getEnergy() + animalList.get(i).getEnergy());
						animalList.remove(j);

						//If both are carnivores
					} else if (animalList.get(i) instanceof Carnivore && animalList.get(j) instanceof Carnivore) {
						//If both are the same specie they will create a herd and energy will be combined
						if (animalList.get(i).getClass() == animalList.get(j).getClass()) {
							int x = animalList.get(i).getXpos();
							int y = animalList.get(i).getYpos();
							//Stores which species the herd is made up off
							String specie = animalList.get(i).getClass().getName();
							int energy = animalList.get(i).getEnergy() + animalList.get(j).getEnergy();
							hurdList.add(animalList.get(i));
							hurdList.add(animalList.get(j));
							animalList.remove(i);
							animalList.remove(j);
							AddHerd(x, y, energy, specie);

							//If one is a hurd and the other is the same specie it will join the herd
						} else if (animalList.get(j) instanceof Herd && animalList.get(i).getClass().getName() == animalList.get(j).getHerdType()) {
							animalList.get(j).setEnergy(animalList.get(j).getEnergy() + animalList.get(i).getEnergy());
							hurdList.add(animalList.get(i));
							(animalList.get(j)).setMembers(animalList.get(j).getMembers() + 1);
							animalList.remove(i);

						} else if (animalList.get(i) instanceof Herd && animalList.get(j).getClass().getName() == animalList.get(i).getHerdType()) {
							animalList.get(i).setEnergy(animalList.get(j).getEnergy() + animalList.get(i).getEnergy());
							hurdList.add(animalList.get(j));
							(animalList.get(i)).setMembers(animalList.get(i).getMembers() + 1);
							animalList.remove(j);

							//If both carnivores but different species, the one with a greater energy will eat the other
						} else if (animalList.get(i).getEnergy() < animalList.get(j).getEnergy()) {
							animalList.get(j).setEnergy(animalList.get(j).getEnergy() + animalList.get(i).getEnergy());
							animalList.remove(i);

						} else if(animalList.get(j).getEnergy() < animalList.get(i).getEnergy()) {
							animalList.get(i).setEnergy(animalList.get(i).getEnergy() + animalList.get(j).getEnergy());
							animalList.remove(j);
						}
					}
				}
			}
		}

	}

	public void display() {
		//Clears the groups
		clearGroups();

		//Loops through the grid and adds a circle in that position depending on the attribute in each position
		for (int x = 0; x < size_x; x++) {
			for (int y = 0; y < size_y; y++) {
				if (grid[x][y] == 'X') {
					Circle obstacle = null;
					CreateCircle(x, y, obstacle, obstacleGroup, Color.GREEN);
				} else if (Character.isDigit(grid[x][y])) {
					Circle food = null;
					CreateCircle(x, y, food, foodGroup, Color.BLUE);
				} else if(grid[x][y] == '*'){
					Circle poison = null;
					CreateCircle(x, y, poison, foodGroup, Color.DARKCYAN);
				} else if (grid[x][y] == '^' ) {
					Circle shelter = null;
					CreateCircle(x, y, shelter, shelterGroup, Color.BLACK);
				} else if (grid[x][y] != ' ') {
					for (int i = 0; i < animalList.size(); i++) {
						if (animalList.get(i).getXpos() == x && animalList.get(i).getYpos() == y) {
							Circle animal = null;
							CreateCircle(x, y, animal, bugGroup, animalList.get(i).getFill());
							break;
						}
					}
				}
			}

		}

	}

	//Caluclates how many food is left in the world
	public int foodLeft() {
		foodLeft = 0;
		for (int x = 0; x < getXdimension(); x++) {
			for (int y = 0; y < getYdimension(); y++) {
				if (Character.isDigit(grid[x][y]) || grid[x][y]=='*') {
					foodLeft++;
				}
			}
		}
		return foodLeft;
	}

	public void Toggle (boolean isToggle){
		if(isToggle){
			clearGroups();
		} else {
			display();
		}
	}

	//Runs the world
	public void run(boolean isToggle) {

		checkCollision();
		checkShelter();
		Toggle(isToggle);
		for (int j = 0; j < animalList.size(); j++) {
			System.out.println("    bug: " + animalList.get(j).getSymbol());
			//Updates each bug
			animalList.get(j).update();
			//If an animals energy has reached 0, the bus is removed and food is added in its position
			if (animalList.get(j).getEnergy() <= 0) {
				this.grid[animalList.get(j).getXpos()][animalList.get(j).getYpos()] = ' ';
				AddFood(animalList.get(j).getXpos(), animalList.get(j).getYpos());

				animalList.remove(j);
				bugGroup.getChildren().remove(j);
				j--;
			}
		}

		//If food left is less than the number of food in the original config, more food is added
		if (foodLeft() < getFood()) {
			AddFood();
		}
	}

	//Creates a circle and sets its colour
	public void CreateCircle(double px, double py, Circle circle, Group root, Color fill) {
		double xpos = 20 * (px + 1.0);
		double ypos = 20 * (py + 2.0);
		circle = new Circle(xpos, ypos, 9);
		circle.setFill(fill);
		root.getChildren().add(circle);
	}

	//Clears all the groups
	public void clearGroups() {
		bugGroup.getChildren().clear();
		foodGroup.getChildren().clear();
		obstacleGroup.getChildren().clear();
		shelterGroup.getChildren().clear();
	}


}
