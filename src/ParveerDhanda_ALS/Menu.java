package ParveerDhanda_ALS;

import java.io.*;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class contains all the attributes for the Menu.
 */
public class Menu {
	private MenuBar menuBar;
	private VBox topConatiner;
	private BorderPane border;
	private boolean Pause = true;
	private boolean Toggle = false;
	private boolean Stop = false;
	private boolean Editted = false;
	private Button button = new Button("Submit");
	private Button refresh = new Button("Refresh");
	private Button runbtn = new Button("Run");
	private Button pausebtn = new Button("Pause");
	private Button restartbtn = new Button("Restart");
	private Button stopbtn = new Button("Stop");
	private Button resetbtn = new Button("Reset");
	private Label notification = new Label();
	private TextField SaveAs = new TextField("");
	private TextField Food = new TextField("");
	private TextField Obstacles = new TextField("");
	private TextField Shelters = new TextField("");
	private TextField Xpos = new TextField("");
	private TextField Ypos = new TextField("");
	private TextField Ydimension = new TextField("");
	private TextField Xdimension = new TextField("");
	private TextField Energy = new TextField("");
	private TextField Name = new TextField("");
	private TextField ID = new TextField("");
	private TextField SmellRange= new TextField("");
	private String[] paths;
	private World world;
	private Group root;
	private String filename;
	private ObjectOutputStream os1;
	private ObjectInputStream is1;

	/**
	 * This method will create the menu and load it with all of its items.
	 * @param primaryStage - all roots are added to the primaryStage
	 * @param root - what will be displayed onto the stage
	 */
	public Menu(Stage primaryStage, Group root) {
		setRoot(root);
		// Load latest config here to start
		/*try {
			loadLatest();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			setWorld(new World(getRoot(), 0, 0, 25, 25, 0));
		}*/

		setWorld(new World(getRoot(), 0, 0, 25, 25, 0));


		// Top Menu Bar
		menuBar = new MenuBar();
		menuBar.setOpacity(0.8);
		notification.setTextFill(Color.RED);

		// File
		javafx.scene.control.Menu file = new javafx.scene.control.Menu("File");
		MenuItem fileNewConfig = new MenuItem("New Configuration");
		MenuItem fileOpenConfig = new MenuItem("Open Configuration");
		MenuItem fileSave = new MenuItem("Save");
		MenuItem fileSaveAs = new MenuItem("Save As");
		MenuItem fileExit = new MenuItem("Exit");

		file.getItems().add(fileNewConfig);
		file.getItems().add(fileOpenConfig);
		file.getItems().add(fileSave);
		file.getItems().add(fileSaveAs);
		file.getItems().add(fileExit);

		fileNewConfig.setOnAction(new EventHandler<ActionEvent>() { //Allows the user to set a new world configuration
			@Override
			public void handle(ActionEvent e) {
				newConfig();
			}

		});

		// Opens a saved configuration
		fileOpenConfig.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				openConfig();
			}
		});

		//Saves the configuration under the current name
		fileSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Save();

			}
		});

		// Saves the current config under a new name
		fileSaveAs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				saveAs();
			}
		});

		// Exits the application
		fileExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Exit();
			}
		});

		// Edit
		javafx.scene.control.Menu edit = new javafx.scene.control.Menu("Edit");
		final MenuItem modifyLifeForm = new MenuItem("Modify Life Form");
		MenuItem removeLifeForm = new MenuItem("Remove Life Form");
		final MenuItem addLifeForm = new MenuItem("Add Life Form");
		edit.getItems().add(modifyLifeForm);
		edit.getItems().add(removeLifeForm);
		edit.getItems().add(addLifeForm);

		modifyLifeForm.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				modifyLife();
			}
		});

		//Removes a animal from the current world
		removeLifeForm.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				removeLife();
			}
		});

		//Adds a life form to the current world
		addLifeForm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				AddAnimal();
			}
		});

		// View
		javafx.scene.control.Menu view = new javafx.scene.control.Menu("View");
		MenuItem displayConfig = new MenuItem("Display Configuration");
		MenuItem editConfig = new MenuItem("Edit Configuration");
		MenuItem displayLifeForms = new MenuItem("Display Life Form Stats");
		MenuItem displayMapInfo = new MenuItem("Display Map Stats");

		view.getItems().add(displayConfig);
		view.getItems().add(editConfig);
		view.getItems().add(displayLifeForms);
		view.getItems().add(displayMapInfo);

		// Will display all the attributes in the current config, food, obstacles, shelters and animals
		displayConfig.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = new Stage();
				notification.setText("");
				stage.setTitle("Display Configuration");
				final Text title = new Text("Display Configuration");
				title.setFont(Font.font("Verdana", FontWeight.NORMAL,20));
				final GridPane grid = new GridPane();
				grid.setVgap(4);
				grid.setHgap(10);
				grid.setPadding(new Insets(5, 5, 5, 5));

				//Will print out the number of food, obstacles and shelters in the current world
				grid.add(refresh, 9,0);
				displayConfig(grid, title);

				refresh.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						grid.getChildren().clear();
						grid.add(refresh, 9,0);
						displayConfig(grid, title);
					}

				});

				Scene scene = new Scene(grid, 800, 400);
				stage.setScene(scene);
				stage.show();
				System.out.println("was clicked");
			}
		});

		//Allows the user to edit the current config
		editConfig.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				editConfig();
			}
		});

		//Displays all of the animals in the current world
		displayLifeForms.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				displayLife();
			}

		});

		//Will display the dimensions, number of food, obstacles and shelters in the current world
		displayMapInfo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				mapStats();
			}
		});

		// Simulate
		javafx.scene.control.Menu simulate = new javafx.scene.control.Menu("Simulate");
		final MenuItem run = new MenuItem("Run");
		MenuItem pause = new MenuItem("Pause");
		MenuItem restart = new MenuItem("Restart");
		final MenuItem stop = new MenuItem("Stop");
		MenuItem reset = new MenuItem("Reset");
		final MenuItem toggleMap = new MenuItem("Toggle Map : OFF");
		simulate.getItems().add(run);
		simulate.getItems().add(pause);
		simulate.getItems().add(restart);
		simulate.getItems().add(reset);
		simulate.getItems().add(toggleMap);

		//Will run the application by setting Pause to false
		run.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				run();
			}
		});

		//Will pause the application by setting Pause to true
		pause.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Pause = true;
			}
		});

		//Will restart the application
		restart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				restart();
			}
		});

		//Will stop the application by clearing the stage
		stop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				stop();
			}
		});

		//Will start the application from the beginning and pause it
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				reset();
			}
		});

		//Will toggle the display between iterations
		toggleMap.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Toggle = !Toggle;
				if(Toggle){
					toggleMap.setText("Toggle Map : ON");
				} else {
					toggleMap.setText("Toggle Map : OFF");
				}
			}
		});

		javafx.scene.control.Menu help = new javafx.scene.control.Menu("Help");
		MenuItem appHelp = new MenuItem("Application Help");
		MenuItem author = new MenuItem("Information on Author");
		help.getItems().add(appHelp);
		help.getItems().add(author);

		//Displays information about the application
		appHelp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Stage stage = new Stage();
				stage.setTitle("Application Help");
				Label text = new Label("This application is a 2D artificial life simulator.\n" + "You can create multiple life forms and add \n" + "objects to a world and watch them simulate life.");
				text.setWrapText(true);
				text.setAlignment(Pos.TOP_CENTER);
				Scene scene = new Scene(text, 300, 200);
				stage.setScene(scene);
				stage.show();
			}
		});

		//Displays information about the author
		author.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Stage stage = new Stage();
				stage.setTitle("Information on Author");
				Label text = new Label("This application is a 2D artificial life simulator.\n" + "Created by Parveer Dhanda.");
				text.setWrapText(true);
				text.setAlignment(Pos.TOP_CENTER);
				Scene scene = new Scene(text, 300, 80);
				stage.setScene(scene);
				stage.show();
			}

		});

		ToolBar toolBar = new ToolBar(runbtn, pausebtn, restartbtn, resetbtn, stopbtn);
		BorderPane pane = new BorderPane();
		pane.setTop(toolBar);
		toolBar.setOpacity(0.8);

		runbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				run();
			}
		});

		pausebtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Pause = true;
			}
		});

		restartbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				restart();
			}
		});

		resetbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				reset();
			}
		});

		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				stop();
			}
		});


		//Adds the menu options to the menu bar
		menuBar.getMenus().add(file);
		menuBar.getMenus().add(edit);
		menuBar.getMenus().add(view);
		menuBar.getMenus().add(simulate);
		menuBar.getMenus().add(help);

		topConatiner = new VBox();
		topConatiner.prefWidthProperty().bind(primaryStage.widthProperty());
		topConatiner.getChildren().add(menuBar);
		topConatiner.getChildren().add(toolBar);

		border = new BorderPane();
		border.setTop(topConatiner);
	}

	/**
	 * Method is used to determine if the simulation should be paused.
	 * @return - Returns the pause value

	 */
	public boolean Pause() {
		return Pause;
	}

	/**
	 * Methos is used to determine if the loaded configuration has been edited.
	 * @return - Returns the edited value
	 */
	public boolean isEditted() {
		return Editted;
	}

	/**
	 * Method is used to determine if the display should hide between iterations or not.
	 * @return - Returns the toggle value
	 */
	public boolean Toggle(){
		return Toggle;
	}

	/**
	 * Method used to return the border and add it to the scene.
	 * @return - returns the border
	 */
	//Returns the border
	public BorderPane getBorder(){
		return border;
	}

	/**
	 * This is method is used to get the current world loaded onto the scene
	 * @return - returns the current loaded world
	 */
	public World getWorld() {
		return this.world;
	}

	/**
	 * Method is used to set the world which is currently loaded.
	 * @param world - passes in the world which is currently loaded
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * @return - Returns the current Group
	 */
	public Group getRoot() {
		return this.root;
	}

	/**
	 * @param root - Sets the root passed in to the current root
	 */
	public void setRoot(Group root) {
		this.root = root;

	}

	/**
	 * Used to get the filename of the current configuration loaded.
	 * @return - returns the current filename
	 */
	public String getFileName() {
		return this.filename;
	}

	/**
	 * Used to set the filename of the current configuration loaded
	 * @param filename - the name of the current configuration
	 */
	public void setFileName(String filename) {
		this.filename = filename;
	}

	/**
	 * Used to save the last loaded configuration.
	 * @param filename - the name of the last configuration loaded
	 */
	public void saveLatest(String filename){
		//Saves the last file loaded into a file called last
		File file = new File("last.txt");
		try {
			FileWriter filewriter = new FileWriter(file);
			filewriter.write("");
			filewriter.append(filename);
			filewriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Loads the last configuration that was loaded.
	 * @throws FileNotFoundException
	 */
	public void loadLatest() throws FileNotFoundException {
		//Will load the last configuration file opened from the last.txt file
		File file = new File("last.txt");
		String line;
		StringBuilder  sb = new StringBuilder();
		BufferedReader reader = new BufferedReader( new FileReader (file));			
		try {
			while((line = reader.readLine()) != null){
				sb.append(line);
			}
			reader.close();

			String filename = sb.toString();
			setFileName(filename);
			is1 = new ObjectInputStream(new FileInputStream("Configurations/" + filename));
			World w;
			w = (World) is1.readObject();
			setWorld(new World(w, getRoot()));
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

	}

	/**
	 * Used to check if he input for a integer field is valid
	 * @param text - the text field which being checked.
	 * @return - will return a true or false value
	 */
	public boolean checkValid(TextField text) {
		if (Integer.parseInt(text.getText()) >= 0) {
			try {
				//If input is a integer return true
				Integer.parseInt(text.getText());
				return true;
			} catch (NumberFormatException e) {
				//Else return false
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Will save the current configuration under a temporary file if the current configuration
	 * has been edited.
	 */
	public void saveEdit(){
		//Will save the eddited version of the current world in a temp file
		try {
			os1 = new ObjectOutputStream(new FileOutputStream("Temp/temp.txt"));
			os1.writeObject(getWorld());
			os1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Will load the last edit of the current configuration
	 */
	public void loadEdit(){
		//Will load the edited world from the temp.txt file
		World w;
		try {
			is1 = new ObjectInputStream(new FileInputStream("Temp/temp.txt"));
			w = (World) is1.readObject();
			getWorld().clearGroups();
			// Creates new world with the info in the config file
			setWorld(new World(w, getRoot()));
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	public void addSubmit(GridPane grid, int x, int y){
		HBox hbtn = new HBox(10);
		hbtn.setAlignment(Pos.BOTTOM_LEFT);
		hbtn.getChildren().add(button);
		grid.add(hbtn, x, y);
	}

	/**
	 * Method displays a form for the user to add a animal to the world.
	 */
	public void AddAnimal() { //This method displays the form for user to enter bugs details and sets them.
		final Stage stage = new Stage();
		stage.setTitle("Add Animal");

		final GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.setAlignment(Pos.CENTER);
		final ComboBox<String> animalsComboBox = new ComboBox<>();
		animalsComboBox.getItems().addAll("Bug", "Lion");
		Text title = new Text("Animal: ");
		title.setFont(Font.font("Verdana", FontWeight.NORMAL,15));
		grid.add(title,0,0,3,1);
		grid.add(animalsComboBox,1,0);
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
				//Checks if all fields are filled in
				if (animalsComboBox.getSelectionModel() == null || Name.getText().isEmpty() || ID.getText().isEmpty() || Energy.getText().isEmpty() || Xpos.getText().isEmpty()
						|| Ypos.getText().isEmpty()) {
					notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");

					//Checks if inputs are valid
				} else if(!checkValid(ID)|| !checkValid(Energy) || !checkValid(SmellRange) || !checkValid(Xpos) || !checkValid(Ypos)) {
					notification.setText("ERROR!!: PLEASE ENTER AN INTEGER");
				}else if (Integer.parseInt(Xpos.getText()) > getWorld().getXdimension() || Integer.parseInt(Ypos.getText()) > getWorld().getYdimension()){
					notification.setText("ERROR!!: CO-ORDINATES LIE OUTSIDE THE DIMENSIONS OF THE MAP");
				}
				else {
					//Creates the animals and adds them to the worl
					int selection = animalsComboBox.getSelectionModel().getSelectedIndex();
					if(selection == 0){
						String name = (Name.getText());
						int BugID = (Integer.parseInt(ID.getText()));
						int energy = (Integer.parseInt(Energy.getText()));
						int xpos = (Integer.parseInt(Xpos.getText()));
						int ypos = (Integer.parseInt(Ypos.getText()));
						int smellrange = (Integer.parseInt(SmellRange.getText()));
						getWorld().AddBug(name, xpos, ypos, energy, BugID, smellrange);
					} else if(selection == 1){
						String name = (Name.getText());
						int lionID = (Integer.parseInt(ID.getText()));
						int energy = (Integer.parseInt(Energy.getText()));
						int xpos = (Integer.parseInt(Xpos.getText()));
						int ypos = (Integer.parseInt(Ypos.getText()));
						int smellrange = (Integer.parseInt(SmellRange.getText()));
						getWorld().AddLion(name, xpos, ypos, energy, lionID, smellrange);
					}

					//If the file has been edited it will save it as an edit
					if(isEditted()){
						saveEdit();
						//Else it will save as a configuration file
					} else {
						try {
							os1 = new ObjectOutputStream(new FileOutputStream("Configurations/" + getFileName() + ".txt"));
							os1.writeObject(getWorld());
							os1.close();
							saveLatest(getFileName() + ".txt");
						}catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					addAnother();
					stage.close();
				}
			}



		});

		Scene scene = new Scene(grid, 300, 300);
		stage.setScene(scene);
		stage.show();
	}


	/**
	 * Checks if the user wants to add another animal to the world
	 */
	public void addAnother(){
		final Stage stage = new Stage();
		stage.setTitle("Add another animal?");

		//Sets the tile of the scene
		Text title = new Text("Add another animal?");
		title.setFont(Font.font("Verdana", FontWeight.NORMAL,15));

		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.setAlignment(Pos.CENTER);

		Button yes = new Button("Yes");
		Button no = new Button("No");

		grid.add(title, 0,0,2,1);
		grid.add(yes, 0,1);
		grid.add(no,1,1);

		yes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				AddAnimal();
				stage.close();
			}
		});

		no.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				stage.close();
			}
		});

		Scene scene = new Scene(grid, 200, 200);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Displays the current stats of the configuration.
	 * @param grid - add objects to the grid
	 * @param title - title to be displayed
	 */
	public void displayConfig(GridPane grid, Text title){
		//Print out the number animals and the details of each animal
		grid.add(title, 0, 0, 8, 1);
		grid.add(new Label("Food: " + getWorld().foodLeft()), 0, 1);
		grid.add(new Label("Obstacles: " + getWorld().getObstacles()), 0, 2);
		grid.add(new Label("Shelters: " + getWorld().getShelters()), 0, 3);
		grid.add(new Label("Animals: " + getWorld().animalList.size()), 0, 4);
		for (int i = 0; i < getWorld().animalList.size(); i++) {
			grid.add(new Label("ID: " + getWorld().animalList.get(i).getBugID()), 1, i + 5);
			grid.add(new Label("Specie: " + getWorld().animalList.get(i).getType()), 2, i + 5);
			grid.add(new Label("Name: " + getWorld().animalList.get(i).getName()), 3, i + 5);
			grid.add(new Label("X-position: " + getWorld().animalList.get(i).getXpos()), 4, i + 5);
			grid.add(new Label("Y-position: " + getWorld().animalList.get(i).getYpos()), 5, i + 5);
			grid.add(new Label("Energy: " + getWorld().animalList.get(i).getEnergy()), 6, i + 5);
			if(getWorld().animalList.get(i) instanceof Herd){
				grid.add(new Label("Members: " + (getWorld().animalList.get(i)).getMembers()), 7, i + 5);
			}
		}
		// Prints all the animals with in a shelter
		grid.add(new Label("Animals in Shelters: " + getWorld().shelterList.size()), 0, getWorld().animalList.size() + 6);
		for (int i = 0; i < getWorld().shelterList.size(); i++) {
			int ypos = i + getWorld().animalList.size() + 7;
			grid.add(new Label("ID: " + getWorld().shelterList.get(i).getBugID()), 1, ypos);
			grid.add(new Label("Specie: " + getWorld().shelterList.get(i).getType()), 2, ypos);
			grid.add(new Label("Name: " + getWorld().shelterList.get(i).getName()), 3, ypos);
			grid.add(new Label("X-position: " + getWorld().shelterList.get(i).getXpos()), 4, ypos);
			grid.add(new Label("Y-position: " + getWorld().shelterList.get(i).getYpos()), 5, ypos);
			grid.add(new Label("Energy: " + getWorld().shelterList.get(i).getEnergy()), 6, ypos);

		}

	}

	/**
	 * Loads a form for the user to create a new configuration.
	 */
	public void newConfig(){
		final Stage stage = new Stage();
		stage.setTitle("New Configuration");
		notification.setText("");
		Text title = new Text("New Configuration");
		title.setFont(Font.font("Verdana", FontWeight.NORMAL,20));
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(4);
		grid.setHgap(10);
		grid.add(title,0,0,2,1);
		grid.add(new Label("Save As:"), 0, 1);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(SaveAs, 1, 1, 3, 1);
		grid.add(new Label("Food:"), 0, 2);
		grid.add(Food, 1, 2, 3, 1);
		grid.add(new Label("Obstacles:"), 0, 3);
		grid.add(Obstacles, 1, 3, 3, 1);
		grid.add(new Label("Shelters:"), 0, 4);
		grid.add(Shelters, 1, 4, 3, 1);
		grid.add(new Label("X Dimension:"), 0, 5);
		grid.add(Xdimension, 1, 5, 3, 1);
		grid.add(new Label("Y Dimension:"), 0, 6);
		grid.add(Ydimension, 1, 6, 3, 1);
		grid.add(notification, 0, 7, 3, 1);
		addSubmit(grid, 0, 10);

		//When the button is clicked the following is done
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				//Checks if all inputs are filled
				if (!SaveAs.getText().isEmpty() && !Food.getText().isEmpty() && !Obstacles.getText().isEmpty()
						&& !Shelters.getText().isEmpty() && !Xdimension.getText().isEmpty() && !Ydimension.getText().isEmpty()) {
					//Checks if the inputs are valid
					if(!checkValid(Xdimension) || !checkValid(Ydimension) || !checkValid(Shelters) || !checkValid(Obstacles)|| !checkValid(Food)) {
						notification.setText("ERROR!!: PLEASE ENTER AN INTEGER");
					} else {
						if (Integer.parseInt(Food.getText()) + Integer.parseInt(Obstacles.getText()) + Integer.parseInt(Shelters.getText()) >
								Integer.parseInt(Xdimension.getText()) * Integer.parseInt(Ydimension.getText())) {
							notification.setText("ERROR!!: TOO MANY OBJECT FOR WORLD DIMENSIONS");
						} else {
							// Will save the configs name, and then set the new world constructor with the users data
							setFileName(SaveAs.getText());
							getWorld().clearGroups();
							setWorld(new World(getRoot(), Integer.parseInt(Food.getText()),
									Integer.parseInt(Obstacles.getText()), Integer.parseInt(Xdimension.getText()),
									Integer.parseInt(Ydimension.getText()), Integer.parseInt(Shelters.getText())));

							AddAnimal();
							// Closes the stage
							stage.close();
							Editted = false;
						}
					}
				} else {
					notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");
				}

			}
		});
		Scene scene = new Scene(grid, 300, 300);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Will open a user selected configuration file
	 */
	public void openConfig(){
		final Stage stage = new Stage();
		notification.setText("");
		stage.setTitle("Open Configuration");
		Text title = new Text("Open Configuration");
		title.setFont(Font.font("Verdana", FontWeight.NORMAL,20));
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(4);
		grid.setHgap(10);
		grid.add(title,0,0,4,1);

		// Gets all files in configuration file ending with .txt
		File file = new File("Configurations/");
		paths = file.list();
		final ArrayList<String> Files = new ArrayList<>();
		for (String path : paths) {
			if (path.endsWith(".txt")) {
				Files.add(path);
			}
		}

		//Adds the files to ObservableList so can be displayed in ComboBox
		ObservableList<String> obList = FXCollections.observableList(Files);
		final ComboBox<String> filesComboBox = new ComboBox<>();
		filesComboBox.setItems(obList);

		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(new Label("Files: "), 0, 1);
		grid.add(filesComboBox, 1, 1);
		addSubmit(grid, 3, 1);
		grid.add(notification, 0, 5, 4, 1);

		//Once the button is clicked, the foloowing is done
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				int selected = filesComboBox.getSelectionModel().getSelectedIndex();
				if (filesComboBox.getValue() == null) {
					notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");

				} else {
					try {
						//Reads the world saved in the file
						is1 = new ObjectInputStream(new FileInputStream("Configurations/" + Files.get(selected)));
						World w;
						w = (World) is1.readObject();
						getWorld().clearGroups();
						// Creates new world with the info in the config file
						setWorld(new World(w, getRoot()));
						saveLatest(Files.get(selected));

					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
					Editted = false;
					//Closes stage
					stage.close();


				}
			}
		});
		Scene scene = new Scene(grid, 300, 100);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Will save the current world under the current configuration name.
	 * Overwriting the previous.
	 */
	public void Save(){
		try {
			//Saves the config to a file
			os1 = new ObjectOutputStream(new FileOutputStream("Configurations/" + getFileName() + ".txt"));
			os1.writeObject(getWorld());
			os1.close();
			saveLatest(getFileName() + ".txt");

			//Prints if save is successful
			final Stage stage = new Stage();
			GridPane grid = new GridPane();
			grid.setVgap(4);
			grid.setHgap(10);
			stage.setTitle("Saved");
			Label text = new Label("Save Successful");
			text.setFont(Font.font("Verdana", FontWeight.NORMAL, 13));
			grid.add(text,0,0);
			grid.setAlignment(Pos.CENTER);
			Button ok = new Button("Ok");
			HBox hbtn = new HBox(10);
			hbtn.setAlignment(Pos.BOTTOM_CENTER);
			hbtn.getChildren().add(ok);
			grid.add(hbtn,0,1);

			Editted = false;
			ok.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					stage.close();
				}
			});
			Scene scene = new Scene(grid, 150, 100);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e1) {
			//Prints if save is unsuccessful
			e1.printStackTrace();
			final Stage stage = new Stage();
			GridPane grid = new GridPane();
			grid.setVgap(4);
			grid.setHgap(10);
			stage.setTitle("Save Failed");
			Label text = new Label("Save Unsuccessful");
			text.setFont(Font.font("Verdana", FontWeight.NORMAL, 13));
			grid.add(text,0,0);
			grid.setAlignment(Pos.CENTER);
			Button ok = new Button("Ok");
			HBox hbtn = new HBox(10);
			hbtn.setAlignment(Pos.BOTTOM_CENTER);
			hbtn.getChildren().add(ok);
			grid.add(hbtn,0,1);

			ok.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					stage.close();
				}
			});
			Scene scene = new Scene(grid, 150, 100);
			stage.setScene(scene);
			stage.show();
		}
	}

	/**
	 * Will save the current configuration, under a new user defined file name
	 */
	public void saveAs(){
		Stage stage = new Stage();
		notification.setText("");
		stage.setTitle("Save As");
		GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.add(new Label("Save As:"), 0, 0);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(SaveAs, 1, 0, 3, 1);
		grid.add(button, 0, 2);
		addSubmit(grid,0,2);
		grid.add(notification, 0, 3, 3, 1);

		// Once the button is pressed, the following is done
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				//Sets the file name from what the user entered
				setFileName(SaveAs.getText());
				if(SaveAs.getText().isEmpty()){
					notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");
				} else {
					// Saves the current config under the file name entered
					try {
						os1 = new ObjectOutputStream(
								new FileOutputStream("Configurations/" + getFileName() + ".txt"));
						os1.writeObject(getWorld());
						os1.close();
						saveLatest(getFileName() + ".txt");
						Editted = false;
						final Stage stage = new Stage();
						GridPane grid = new GridPane();
						grid.setVgap(4);
						grid.setHgap(10);
						stage.setTitle("Saved");
						Label text = new Label("Save Successful");
						text.setFont(Font.font("Verdana", FontWeight.NORMAL, 13));
						grid.add(text,0,0);
						grid.setAlignment(Pos.CENTER);
						Button ok = new Button("Ok");
						HBox hbtn = new HBox(10);
						hbtn.setAlignment(Pos.BOTTOM_CENTER);
						hbtn.getChildren().add(ok);
						grid.add(hbtn,0,1);

						ok.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent actionEvent) {
								stage.close();
							}
						});
						Scene scene = new Scene(grid, 150, 100);
						stage.setScene(scene);
						stage.show();
					} catch (IOException e) {
						//Prints if save is unsuccessful
						e.printStackTrace();
						final Stage stage = new Stage();
						GridPane grid = new GridPane();
						grid.setVgap(4);
						grid.setHgap(10);
						stage.setTitle("Save Failed");
						Label text = new Label("Save Unsuccessful");
						text.setFont(Font.font("Verdana", FontWeight.NORMAL, 13));
						grid.add(text,0,0);
						grid.setAlignment(Pos.CENTER);
						Button ok = new Button("Ok");
						HBox hbtn = new HBox(10);
						hbtn.setAlignment(Pos.BOTTOM_CENTER);
						hbtn.getChildren().add(ok);
						grid.add(hbtn,0,1);

						ok.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent actionEvent) {
								stage.close();
							}
						});
						Scene scene = new Scene(grid, 150, 100);
						stage.setScene(scene);
						stage.show();
					}

				}

			}

		});
		Scene scene = new Scene(grid, 240, 100);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Will exit the application
	 */
	public void Exit(){
		Platform.exit();
	}

	/**
	 * Will allow the user to modify a selected lifeform attributes
	 */
	public void modifyLife(){
		final Stage stage = new Stage();
		notification.setText("");
		stage.setTitle("Modify Lifeform");
		Text title = new Text("Modify Lifeform");
		title.setFont(Font.font("Verdana", FontWeight.NORMAL,20));

		//Adds all the animals names into a ArrayList
		final ArrayList<String> animalNames = new ArrayList<>();
		for (int i = 0; i < getWorld().animalList.size(); i++) {
			animalNames.add(getWorld().animalList.get(i).getName());
		}

		//ArrayList is then passed to ObservableList so it can be diaplayed in the ComboBox
		ObservableList<String> obList = FXCollections.observableList(animalNames);
		final ComboBox<String> animalsComboBox = new ComboBox<>();
		animalsComboBox.setItems(obList);

		//Adds the labels and textfeilds to the grid in the positions entered
		final GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(title,0,0,2,1);
		grid.add(new Label("Animals: "), 0, 1);
		grid.add(animalsComboBox, 1, 1);
		addSubmit(grid, 0, 9);
		grid.add(refresh, 0, 3);
		grid.add(new Label("Xpos: "), 0, 5);
		grid.add(Xpos, 1, 5, 3, 1);
		grid.add(new Label("Ypos: "), 0, 6);
		grid.add(Ypos, 1, 6, 3, 1);
		grid.add(new Label("Energy: "), 0, 7);
		grid.add(Energy, 1, 7, 3, 1);
		grid.add(notification, 0, 10, 3, 1);


		//When refresh button is clicked the following is done
		refresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//Will load the X-position, Y-position and energy of the animal selected
				if(animalsComboBox.getValue() == null){
					notification.setText("ERROR!!: SELECT A LIFEFORM");

				} else {
					int selected = animalsComboBox.getSelectionModel().getSelectedIndex();

					String xpos = Integer.toString(getWorld().animalList.get(selected).getXpos());
					Xpos.setText(xpos);
					String ypos = Integer.toString(getWorld().animalList.get(selected).getYpos());
					Ypos.setText(ypos);
					String energy = Integer.toString(getWorld().animalList.get(selected).getEnergy());
					Energy.setText(energy);
				}
			}

		});


		//When the submit button is clicked the following is done
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(Xpos.getText().isEmpty() || Ypos.getText().isEmpty() || Energy.getText().isEmpty() || animalsComboBox.getValue() == null ){
					notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");
				} else if (!checkValid(Xpos) || !checkValid(Ypos) || !checkValid(Energy)) {
					notification.setText("ERROR!!: PLEASE ENTER AN INTEGER");
				} else {
					if (Integer.parseInt(Xpos.getText()) <= getWorld().getXdimension() && Integer.parseInt(Ypos.getText()) <= getWorld().getYdimension()) {
						// Edit the animals attributes to what the user has entered
						int selected = animalsComboBox.getSelectionModel().getSelectedIndex();
						getWorld().grid[getWorld().animalList.get(selected).getXpos()][getWorld().animalList
								.get(selected).getYpos()] = ' ';
						getWorld().animalList.get(selected).setXpos(Integer.parseInt(Xpos.getText()));
						getWorld().animalList.get(selected).setYpos(Integer.parseInt(Ypos.getText()));
						getWorld().animalList.get(selected).setEnergy(Integer.parseInt(Energy.getText()));


						Editted = true;
						saveEdit();
						//Closes stage
						stage.close();
					} else {
						notification.setText("ERROR!!: CO-ORDINATES LIE OUTSIDE\n" +  "THE DIMENSIONS OF THE MAP");
					}
				}
			}

		});
		Scene scene = new Scene(grid, 400, 250);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Willa allow the user to remove a selected lifeform from the world
	 */
	public void removeLife(){
		final Stage stage = new Stage();
		notification.setText("");
		stage.setTitle("Remove Lifeform");
		Text title = new Text("Remove Lifeform");
		title.setFont(Font.font("Verdana", FontWeight.NORMAL,20));

		//Adds all the animals names into a ArrayList
		ArrayList<String> animalNames = new ArrayList<>();
		for (int i = 0; i < getWorld().animalList.size(); i++) {
			animalNames.add(getWorld().animalList.get(i).getName());
		}
		//ArrayList is then passed to ObservableList so it can be displayed in the ComboBox
		ObservableList<String> obList = FXCollections.observableList(animalNames);
		final ComboBox<String> animalsComboBox = new ComboBox<>();
		animalsComboBox.setItems(obList);

		//Adds the labels and textfeilds to the grid in the positions entered
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(title,0,0,4,1);
		grid.add(new Label("Animals: "), 0, 1);
		grid.add(animalsComboBox, 1, 1);
		addSubmit(grid,3,1);
		grid.add(notification, 0, 4, 4, 1);

		// When the button is clicked the following is done
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(animalsComboBox.getValue() == null){
					notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");
				} else {
					//Remove the selected animal from the current world
					int selected = animalsComboBox.getSelectionModel().getSelectedIndex();
					getWorld().animalList.remove(selected);
					//Closes the stage
					Editted = true;
					saveEdit();
					stage.close();
				}
			}

		});

		Scene scene = new Scene(grid, 300, 100);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Will allow the user to edit the current configuration.
	 */
	public void editConfig(){
		final Stage stage = new Stage();
		notification.setText("");
		stage.setTitle("Edit Configuration");
		Text title = new Text("Edit Configuration");
		title.setFont(Font.font("Verdana", FontWeight.NORMAL,20));
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(4);
		grid.setHgap(10);
		grid.add(title,0,0,2,1);
		grid.add(new Label("Save As:"), 0, 1);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(SaveAs, 1, 1, 3, 1);
		grid.add(new Label("Food:"), 0, 2);
		grid.add(Food, 1, 2, 3, 1);
		grid.add(new Label("Obstacles:"), 0, 3);
		grid.add(Obstacles, 1, 3, 3, 1);
		grid.add(new Label("Shelters:"), 0, 4);
		grid.add(Shelters, 1, 4, 3, 1);
		grid.add(new Label("X Dimension:"), 0, 5);
		grid.add(Xdimension, 1, 5, 3, 1);
		grid.add(new Label("Y Dimension:"), 0, 6);
		grid.add(Ydimension, 1, 6, 3, 1);
		grid.add(notification, 0, 7, 3, 1);
		addSubmit(grid, 0, 10);

		//Will display the current configuration
		SaveAs.setText(getFileName());
		String food = Integer.toString(getWorld().getFood());
		Food.setText(food);
		String obstacles = Integer.toString(getWorld().getObstacles());
		Obstacles.setText(obstacles);
		String shelters = Integer.toString(getWorld().getShelters());
		Shelters.setText(shelters);
		String xDimension = Integer.toString(getWorld().getXdimension());
		Xdimension.setText(xDimension);
		String yDimension = Integer.toString(getWorld().getYdimension());
		Ydimension.setText(yDimension);

		//The following is run once the button is clicked
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (!Food.getText().isEmpty() && !Obstacles.getText().isEmpty()
						&& !Shelters.getText().isEmpty() && !Xdimension.getText().isEmpty() && !Ydimension.getText().isEmpty()) {
					if (!checkValid(Xdimension) || !checkValid(Ydimension) || !checkValid(Shelters) || !checkValid(Obstacles) || !checkValid(Food)) {
						notification.setText("ERROR!!: PLEASE ENTER AN INTEGER");
					} else {
						//Checks if number of objects is greater than max allowed in the entered dimensions
						if (Integer.parseInt(Food.getText()) + Integer.parseInt(Obstacles.getText()) + Integer.parseInt(Shelters.getText()) >
								Integer.parseInt(Xdimension.getText()) * Integer.parseInt(Ydimension.getText())) {
							notification.setText("ERROR!!: TOO MANY OBJECT FOR WORLD DIMENSIONS");
						} else {
							// Will save the configs name, and then set the new world constructor with the users data
							setFileName(SaveAs.getText());
							getWorld().clearGroups();
							setWorld(new World(getRoot(), Integer.parseInt(Food.getText()),
									Integer.parseInt(Obstacles.getText()), Integer.parseInt(Xdimension.getText()),
									Integer.parseInt(Ydimension.getText()), Integer.parseInt(Shelters.getText())));

							Editted = true;
							// Closes the stage
							stage.close();
						}
					}
				} else {
					notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");
				}
			}

		});
		Scene scene = new Scene(grid, 300, 300);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Will diaplys all the lifeforms in the world and all of their attributes.
	 */
	public void displayLife(){
		Stage stage = new Stage();
		notification.setText("");
		stage.setTitle("Display Configuration");
		Text title = new Text("Display Configuration");
		title.setFont(Font.font("Verdana", FontWeight.NORMAL,20));
		GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		//Print out the number of animals in the current and all the attributes about them
		grid.add(title,0,0,6,1);
		grid.add(new Label("Animals: " + world.animalList.size()), 0, 1);
		for (int i = 0; i < getWorld().animalList.size(); i++) {
			grid.add(new Label("ID: " + getWorld().animalList.get(i).getBugID()), 1, i + 2);
			grid.add(new Label("Specie: " + getWorld().animalList.get(i).getType()), 2, i + 2);
			grid.add(new Label("Name: " + getWorld().animalList.get(i).getName()), 3, i + 2);
			grid.add(new Label("X-position: " + getWorld().animalList.get(i).getXpos()), 4, i + 2);
			grid.add(new Label("Y-position: " + getWorld().animalList.get(i).getYpos()), 5, i + 2);
			grid.add(new Label("Energy: " + getWorld().animalList.get(i).getEnergy()), 6, i + 2);
			if(getWorld().animalList.get(i) instanceof Herd){
				grid.add(new Label("Members: " + (getWorld().animalList.get(i)).getMembers()), 7, i + 2);
			}
		}
		Scene scene = new Scene(grid, 600, 600);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Will display the map statistics. So the dimensions, number of food, obstacles and shelters.
	 */
	public void mapStats(){
		Stage stage = new Stage();
		notification.setText("");
		stage.setTitle("Map Information");
		Text title = new Text("Map Information");
		title.setFont(Font.font("Verdana", FontWeight.NORMAL,20));
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(title,0,0);
		grid.add(new Label("Map Dimensions: "), 0, 1);
		grid.add(new Label("\tX-Dimension: " + world.getXdimension()), 0, 2);
		grid.add(new Label("\tY-Dimension: " + world.getYdimension()), 0, 3);
		grid.add(new Label("Food: " + getWorld().foodLeft()), 0, 4);
		grid.add(new Label("Obstacles: " + getWorld().getObstacles()), 0, 5);
		grid.add(new Label("Shelters: " + getWorld().getShelters()), 0, 6);
		Scene scene = new Scene(grid, 200, 200);
		stage.setScene(scene);
		stage.show();
		System.out.println("was clicked");
	}

	/**
	 * Will run the simulation
	 */
	public void run(){
		if(Stop == true){
			try {
				loadLatest();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Stop = false;
		}
		Pause = false;
	}

	/**
	 * Will restart the simulation from the beginning.
	 * If configuration has been edited it will restart the simulation with the edits.
	 */
	public void	restart(){
		getWorld().clearGroups();
		if(Editted){
			loadEdit();
		} else{
			try {
				loadLatest();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		Editted = false;
		Pause = true;
	}

	/**
	 * Will stop the simulation.
	 * It will clear the world.
	 */
	public void stop(){
		int x = getWorld().getXdimension();
		int y = getWorld().getYdimension();
		getWorld().clearGroups();
		setWorld(new World(getRoot(), 0, 0, x, y, 0));
	}

	/**
	 * Will reset the current configuration and remove any edits.
	 */
	public void reset(){
		getWorld().clearGroups();
		try {
			loadLatest();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Pause = true;
	}
}

