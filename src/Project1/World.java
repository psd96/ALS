package Project1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

@SuppressWarnings("serial")
public class World implements Serializable {
	char[][] grid;
	List<LifeForm> animalList = new ArrayList<LifeForm>();
	List<Bug> bugList = new ArrayList<Bug>();
	List<Lion> lionList = new ArrayList<Lion>();
	List<LifeForm> hurdList = new ArrayList<LifeForm>();
	List<LifeForm> shelterList = new ArrayList<LifeForm>();
	int[][] foodArray = new int[100][3];
	int[][] obstaclesArray = new int[100][2];
	private int size_x;
	private int size_y;
	transient private Group bugGroup = new Group();
	transient private Group foodGroup = new Group();
	transient private Group obstacleGroup = new Group();
	transient private Group shelterGroup = new Group();
	private int foodLeft;
	private int bugs;
	private int lions;
	private int shelters = 0;
	private int Food = 0;
	private int Obstacles = 0;

	World(Group root, int food, int obstacles, int bugs, int lions, int sizex, int sizey, int shelters) {
		clearGroups();
		setFood(food);
		setObstacles(obstacles);
		setShelters(shelters);
		setXdimension(sizex);
		setYdimension(sizey);
		setBugs(bugs);
		setLions(lions);
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
		
		for(int i=0; i<animalList.size();i++){
			animalList.get(i).setGrid(grid);
			animalList.get(i).setGridpos();
		}

	}
	public int getBugs() {
		return bugs;
	}

	public void setBugs(int bugs) {
		this.bugs = bugs;
	}

	public int getLions() {
		return lions;
	}

	public void setLions(int lions) {
		this.lions = lions;
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

	public void AddShelter() {
		Random rand = new Random();
		int px, py;
		do {
			px = rand.nextInt(getXdimension());
			py = rand.nextInt(getYdimension());
		} while (this.grid[px][py] != ' ');
		grid[px][py] = '^';
	}


	public void AddFood() {
		Random rand = new Random();
		int px, py;
		do {
			px = rand.nextInt(getXdimension());
			py = rand.nextInt(getYdimension());
		} while (this.grid[px][py] != ' ');
		grid[px][py] = (char) ('0' + (rand.nextInt(9) + 1));
	}

	public void AddFood(int px, int py) {
		Random rand = new Random();
		grid[px][py] = (char) ('0' + (rand.nextInt(9) + 1));

	}

	public void AddObstacle() {
		Random rand = new Random();
		int px, py;
		do {
			px = rand.nextInt(getXdimension());
			py = rand.nextInt(getYdimension());
		} while (this.grid[px][py] != ' ');
		grid[px][py] = 'X';
	}

	public void AddBug() {
		Bug bug = new Bug(getXdimension(), getYdimension(), getBugs());
		bug.setGrid(this.grid);
		bug.setGridpos();
		animalList.add(bug);
		bugList.add(bug);
	}

	public void AddLion() {
		Lion lion = new Lion(getXdimension(), getYdimension(), getLions());
		lion.setGrid(this.grid);
		lion.setGridpos();
		animalList.add(lion);
		lionList.add(lion);
	}

	public void AddHurd(int xpos, int ypos, int energy) {
		Hurd hurd = new Hurd(energy, xpos, ypos, getXdimension(), getYdimension());
		hurd.setGrid(this.grid);
		hurd.setGridpos();
		animalList.add(hurd);
	}

	public void enterShelter() {
		for (int i = 0; i < animalList.size(); i++) {
			if (this.grid[animalList.get(i).getXpos()][animalList.get(i).getYpos()] == '^'
					&& animalList.get(i) instanceof Herbivore && animalList.get(i).getEnergy() < 20) {
				shelterList.add(animalList.get(i));
				animalList.remove(i);
			}
		}
	}

	public void checkShelter() {
		enterShelter();
		for (int i = 0; i < shelterList.size(); i++) {
			shelterList.get(i).setEnergy(shelterList.get(i).getEnergy() + 1);

			if (shelterList.get(i).getEnergy() > 60) {
				emptyShelter(i);
			}
		}
	}

	public void emptyShelter(int i) {
		animalList.add(shelterList.get(i));
		animalList.get(animalList.size() - 1).setSymbol(shelterList.get(i).getSymbol());
		shelterList.remove(i);
	}

	public void checkCollision() {
		for (int i = 0; i < animalList.size(); i++) {
			for (int j = 0; j < animalList.size(); j++) {
				if (i == j) {
					break;
				} else if (animalList.get(i).getXpos() == animalList.get(j).getXpos()
						&& animalList.get(i).getYpos() == animalList.get(j).getYpos()) {
					if (animalList.get(i) instanceof Herbivore) {
						animalList.get(j).setEnergy(animalList.get(i).getEnergy() + animalList.get(j).getEnergy());
						animalList.remove(i);

					} else if (animalList.get(j) instanceof Herbivore) {
						animalList.get(i).setEnergy(animalList.get(j).getEnergy() + animalList.get(i).getEnergy());
						animalList.remove(j);

					} else if (animalList.get(i) instanceof Carnivore && animalList.get(j) instanceof Carnivore) {
						if (animalList.get(i).getClass() == animalList.get(j).getClass()) {
							int x = animalList.get(i).getXpos();
							int y = animalList.get(i).getYpos();
							int energy = animalList.get(i).getEnergy() + animalList.get(j).getEnergy();
							hurdList.add(animalList.get(i));
							hurdList.add(animalList.get(j));
							animalList.remove(i);
							animalList.remove(j);
							AddHurd(x, y, energy);

						} else if (animalList.get(i) instanceof Lion && animalList.get(j) instanceof Hurd) {
							animalList.get(j).setEnergy(animalList.get(j).getEnergy() + animalList.get(i).getEnergy());
							hurdList.add(animalList.get(i));
							animalList.remove(i);

						} else if (animalList.get(i) instanceof Hurd && animalList.get(j) instanceof Lion) {
							animalList.get(i).setEnergy(animalList.get(j).getEnergy() + animalList.get(i).getEnergy());
							hurdList.add(animalList.get(j));
							animalList.remove(j);

						} else if (animalList.get(i).getEnergy() < animalList.get(j).getEnergy()) {
							animalList.get(i).setEnergy(animalList.get(j).getEnergy() + animalList.get(i).getEnergy());
							animalList.remove(j);

						} else {
							animalList.get(j).setEnergy(animalList.get(i).getEnergy() + animalList.get(j).getEnergy());
							animalList.remove(i);
						}
					}
				}
			}
		}

	}

	public void display() {
		clearGroups();

		for (int x = 0; x < size_x; x++) {
			for (int y = 0; y < size_y; y++) {
				if (grid[x][y] == 'X') {
					Circle obstacle = null;
					CreateCircle(x, y, obstacle, obstacleGroup, Color.GREEN);
				} else if (Character.isDigit(grid[x][y])) {
					Circle food = null;
					CreateCircle(x, y, food, foodGroup, Color.BLUE);
				} else if (grid[x][y] == '^') {
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

	public int foodLeft() {
		foodLeft = 0;
		for (int x = 0; x < getXdimension(); x++) {
			for (int y = 0; y < getYdimension(); y++) {
				if (Character.isDigit(grid[x][y])) {
					foodLeft++;
				}
			}
		}
		return foodLeft;
	}

	public void toggleDisplay(){

	}

	public void run() {
		checkCollision();
		checkShelter();
		display();
		for (int j = 0; j < animalList.size(); j++) {
			System.out.println("    bug: " + animalList.get(j).getSymbol());
			animalList.get(j).update();

			if (animalList.get(j).getEnergy() <= 0) {
				this.grid[animalList.get(j).getXpos()][animalList.get(j).getYpos()] = ' ';
				AddFood(animalList.get(j).getXpos(), animalList.get(j).getYpos());
				animalList.remove(j);
				bugGroup.getChildren().remove(j);
				j--;
			}
		}

		if (foodLeft() < getFood()) {
			AddFood();
		}
	}

	public void CreateCircle(double px, double py, Circle circle, Group root, Color fill) {
		double xpos = 20 * (px + 1.0);
		double ypos = 20 * (py + 2.0);
		circle = new Circle(xpos, ypos, 9);
		circle.setFill(fill);
		root.getChildren().add(circle);
	}

	public void clearGroups() {
		bugGroup.getChildren().clear();
		foodGroup.getChildren().clear();
		obstacleGroup.getChildren().clear();
		shelterGroup.getChildren().clear();
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

}
