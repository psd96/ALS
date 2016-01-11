package Project1;

import java.io.Serializable;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class LifeForm implements Serializable {

	//Encapulated variables
	protected int energy;
	protected int xpos;
	protected int ypos;
	protected int smellrange;
	protected int size_x;
	protected int size_y;
	protected char grid[][];
	private String name;
	private char symbol;
	private int BugID;
	private String herdType;
	private int members;
	private String type;

	//Getter and Setter methods
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

	public void setGrid() {
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

	public String getHerdType() {
		return herdType;
	}

	public void setHerdType(String herdType) {
		this.herdType = herdType;
	}


	public int getMembers() {
		return members;
	}

	public void setMembers(int members) {
		this.members = members;
	}

	public String getType(){
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	//Abstract methods
	public abstract Color getFill();

		//Main methods
	public boolean smellFood(Direction direction) { //Returns the direction if food is found
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
				if (this.xpos + i + 1 < getXdimension()) {
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
				if (this.ypos + 1 + i < getYdimension()) {
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

	public Direction getRandomDirection(Direction d) { //Picks a random direction and returns it
		Random rand = new Random();
		int temp = rand.nextInt(Direction.values().length);
		d = Direction.values()[temp];
		return d;
	}

	public Direction getDirectionOfFood() { //Will return the direction of the food
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

	public void Move(Direction d){//Move the animals towards the food
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

	public void update() { //Updates the attributes of the animals
		Move(getDirectionOfFood());//Moves animal towards the food

		if (this.grid[this.xpos][this.ypos] == '^') {
			this.setSymbol('^');
		}

		//If food is found it will assign the food energy value and add it to the animals energy
		if (this.grid[this.xpos][this.ypos] != ' ') {
			this.energy += Character.getNumericValue(this.grid[this.xpos][this.ypos]);
		}
		System.out.println("\tenergy: " + energy);
		System.out.println("\tposition: x = " + xpos + " y = " + ypos);

		setGridpos();
	}

	public void AddAnimal(String specie) { //This method displays the form for user to enter bugs details and sets them.
		final TextField Xpos = new TextField("");
		final TextField Ypos = new TextField("");
		final TextField Energy = new TextField("");
		final TextField Name = new TextField("");
		final TextField ID = new TextField("");
		final TextField SmellRange= new TextField("");
		final Label notification = new Label("");
		Button button = new Button("Submit");
		final Stage stage = new Stage();
		Group root = new Group();
		Scene scene = new Scene(root, 300, 300);
		stage.setTitle("Add " + specie);

		final GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(new Label("Animals: " + specie), 0, 0);
		grid.add(button, 0, 11);
		grid.add(new Label("Name: "), 0, 3);
		grid.add(Name, 1, 3, 3, 1);
		grid.add(new Label("ID: "), 0, 4);
		grid.add(ID, 1, 4, 3, 1);
		grid.add(new Label("Energy: "), 0, 5);
		grid.add(Energy, 1, 5, 3, 1);
		grid.add(new Label("Smell Range: "), 0, 6);
		grid.add(SmellRange, 1, 6, 3, 1);
		grid.add(new Label("Xpos: "), 0, 7);
		grid.add(Xpos, 1, 7, 3, 1);
		grid.add(new Label("Ypos: "), 0, 8);
		grid.add(Ypos, 1, 8, 3, 1);
		grid.add(notification, 0, 9, 3, 1);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (Name.getText().isEmpty() || ID.getText().isEmpty() || Energy.getText().isEmpty() || Xpos.getText().isEmpty()
						|| Ypos.getText().isEmpty()) {
					notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");

				} else if(!checkValid(ID)|| !checkValid(Energy) || !checkValid(SmellRange) || !checkValid(Xpos) || !checkValid(Ypos)) {
					notification.setText("ERROR!!: PLEASE ENTER AN INTEGER");
				}else if (Integer.parseInt(Xpos.getText()) > getXdimension() || Integer.parseInt(Ypos.getText()) > getYdimension()){
					notification.setText("ERROR!!: CO-ORDINATES LIE OUTSIDE THE DIMENSIONS OF THE MAP");
				}
				else {
					setName(Name.getText());
					setBugID(Integer.parseInt(ID.getText()));
					setEnergy(Integer.parseInt(Energy.getText()));
					setSymbol(Name.getText().charAt(0));
					setXpos(Integer.parseInt(Xpos.getText()));
					setYpos(Integer.parseInt(Ypos.getText()));
					setXdimension(getXdimension());
					setSmellrange(Integer.parseInt(SmellRange.getText()));
					stage.close();
				}
			}

		});

		root = (Group) scene.getRoot();
		root.getChildren().add(grid);
		stage.setScene(scene);
		stage.show();
	}

	public boolean checkValid(TextField text) {
		if (Integer.parseInt(text.getText()) >= 0) {
			try {
				Integer.parseInt(text.getText());
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

public enum Direction {
		North, East, South, West
	}

}