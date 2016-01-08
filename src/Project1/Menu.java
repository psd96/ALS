package Project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Menu {
	//Done stuff

	final TextField Energy = new TextField("");
	private MenuBar menuBar;
	private boolean isPaused = false;
	private Button button = new Button("Submit");
	private Label notification = new Label();
	private TextField SaveAs = new TextField("");
	private TextField Bugs = new TextField("");
	private TextField Lions = new TextField("");
	private TextField Food = new TextField("");
	private TextField Obstacles = new TextField("");
	private TextField Shelters = new TextField("");
	private TextField Xpos = new TextField("");
	private TextField Ypos = new TextField("");
	private TextField Ydimension = new TextField("");
	private TextField Xdimension = new TextField("");
	private String[] paths;
	private World world;
	private Group root;
	private String filename;
	private ObjectOutputStream os1;
	private ObjectInputStream is1;

	public Menu(Stage primaryStage, Group root) {
		setRoot(root);
		// Load latest config here to start
		try {
			loadLatest();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}

		//setWorld(new World(getRoot(), 0, 0, 0, 0, 25, 25, 0));

		// Top Menu Bar
		menuBar = new MenuBar();
		menuBar.setOpacity(0.8);
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

		fileNewConfig.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				final Stage stage = new Stage();
				Group root = new Group();
				Scene scene = new Scene(root, 600, 600);
				stage.setTitle("New Configuration");
				GridPane grid = new GridPane();
				grid.setVgap(4);
				grid.setHgap(10);
				grid.add(new Label("Save As:"), 0, 0);
				grid.setPadding(new Insets(5, 5, 5, 5));
				grid.add(SaveAs, 1, 0, 3, 1);
				grid.add(new Label("Bugs:"), 0, 1);
				grid.add(Bugs, 1, 1, 3, 1);
				grid.add(new Label("Lions:"), 0, 2);
				grid.add(Lions, 1, 2, 3, 1);
				grid.add(new Label("Food:"), 0, 3);
				grid.add(Food, 1, 3, 3, 1);
				grid.add(new Label("Obstacles:"), 0, 4);
				grid.add(Obstacles, 1, 4, 3, 1);
				grid.add(new Label("Shelters:"), 0, 5);
				grid.add(Shelters, 1, 5, 3, 1);
				grid.add(new Label("X Dimension:"), 0, 6);
				grid.add(Xdimension, 1, 6, 3, 1);
				grid.add(new Label("Y Dimension:"), 0, 7);
				grid.add(Ydimension, 1, 7, 3, 1);
				grid.add(notification, 0, 9, 3, 1);
				grid.add(button, 0, 10);

				button.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if (SaveAs.getText().isEmpty() ||Bugs.getText().isEmpty() || Lions.getText().isEmpty() || Food.getText().isEmpty() || Obstacles.getText().isEmpty()
								|| Shelters.getText().isEmpty() || Xdimension.getText().isEmpty() || Ydimension.getText().isEmpty()) {
							notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");

						} else{
							setFileName(SaveAs.getText());
							getWorld().clearGroups();
							setWorld(new World(getRoot(), Integer.parseInt(Food.getText()),
									Integer.parseInt(Obstacles.getText()), Integer.parseInt(Bugs.getText()), Integer.parseInt(Lions.getText()), Integer.parseInt(Xdimension.getText()),
									Integer.parseInt(Ydimension.getText()), Integer.parseInt(Shelters.getText())));

							try {
								os1 = new ObjectOutputStream(new FileOutputStream("Configurations/" + getFileName() + ".txt"));
								os1.writeObject(getWorld());
								os1.close();
								saveLatest(getFileName() + ".txt");
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							stage.close();
						}

					}

				});
				root = (Group) scene.getRoot();
				root.getChildren().add(grid);
				stage.setScene(scene);
				stage.show();
				System.out.println("NewConfig Clicked");
			}

		});
		fileOpenConfig.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Stage stage = new Stage();
				Group root = new Group();
				Scene scene = new Scene(root, 600, 600);
				stage.setTitle("Open Configuration");

				// Gets all files in root dir ending with .txt
				File file = new File("Configurations/");
				paths = file.list();
				final ArrayList<String> Files = new ArrayList<>();
				for (String path : paths) {
					if (path.endsWith(".txt")) {
						Files.add(path);
					}
				}
				
				
				ObservableList<String> obList = FXCollections.observableList(Files);

				final ComboBox<String> filesComboBox = new ComboBox<>();
				filesComboBox.setItems(obList);

				GridPane grid = new GridPane();
				grid.setVgap(4);
				grid.setHgap(10);
				grid.setPadding(new Insets(5, 5, 5, 5));
				grid.add(new Label("Files: "), 0, 0);
				grid.add(filesComboBox, 1, 0);
				grid.add(button, 3, 0);
				grid.add(notification, 0, 9, 3, 1);

				button.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						int selected = filesComboBox.getSelectionModel().getSelectedIndex();
						if (filesComboBox.getValue() == null) {
							notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");

						} else {
							try {
								is1 = new ObjectInputStream(new FileInputStream("Configurations/" + Files.get(selected)));
								World w;
								w = (World) is1.readObject();
								getWorld().clearGroups();
								setWorld(new World(w, getRoot()));
								saveLatest(Files.get(selected));

							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}

						}


					}

				});

				root = (Group) scene.getRoot();
				root.getChildren().add(grid);
				stage.setScene(scene);
				stage.show();
				System.out.println("OpenConfig Clicked");
			}
		});
		fileSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					os1 = new ObjectOutputStream(new FileOutputStream("Configurations/" + getFileName() + ".txt"));
					os1.writeObject(getWorld());
					os1.close();
					saveLatest(getFileName() + ".txt");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("Save Clicked");
			}
		});
		fileSaveAs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Stage stage = new Stage();
				Group root = new Group();
				Scene scene = new Scene(root, 600, 600);
				stage.setTitle("Save As");
				GridPane grid = new GridPane();
				grid.add(new Label("Save As:"), 0, 0);
				grid.setPadding(new Insets(5, 5, 5, 5));
				grid.add(SaveAs, 1, 0, 3, 1);
				grid.add(button, 0, 2);
				grid.add(notification, 0, 3, 3, 1);
				button.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						setFileName(SaveAs.getText());
						if(SaveAs.getText().isEmpty()){
							notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");
						} else {
							try {
								os1 = new ObjectOutputStream(
										new FileOutputStream("Configurations/" + getFileName() + ".txt"));
								os1.writeObject(getWorld());
								os1.close();
								saveLatest(getFileName() + ".txt");
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}

						}

					}

				});
				root = (Group) scene.getRoot();
				root.getChildren().add(grid);
				stage.setScene(scene);
				stage.show();
				System.out.println("SaveAs Clicked");
			}
		});
		fileExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
				System.out.println("Exit Clicked");
			}
		});

		// Edit
		javafx.scene.control.Menu edit = new javafx.scene.control.Menu("Edit");
		MenuItem modifyLifeForm = new MenuItem("Modify Life Form");
		MenuItem removeLifeForm = new MenuItem("Remove Life Form");
		MenuItem addLifeForm = new MenuItem("Add Life Form");
		edit.getItems().add(modifyLifeForm);
		edit.getItems().add(removeLifeForm);
		edit.getItems().add(addLifeForm);

		modifyLifeForm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				final Stage stage = new Stage();
				Group root = new Group();
				Scene scene = new Scene(root, 600, 600);
				stage.setTitle("Modify Lifeform");
				final ArrayList<String> animalNames = new ArrayList<>();
				for (int i = 0; i < getWorld().animalList.size(); i++) {
					animalNames.add(getWorld().animalList.get(i).getName());
				}
				ObservableList<String> obList = FXCollections.observableList(animalNames);

				final ComboBox<String> animalsComboBox = new ComboBox<>();
				animalsComboBox.setItems(obList);
				final GridPane grid = new GridPane();
				grid.setVgap(4);
				grid.setHgap(10);
				grid.setPadding(new Insets(5, 5, 5, 5));
				grid.add(new Label("Animals: "), 0, 0);
				grid.add(animalsComboBox, 1, 0);
				grid.add(button, 10, 0);
				grid.add(new Label("Xpos: "), 0, 5);
				grid.add(Xpos, 1, 5, 3, 1);
				grid.add(new Label("Ypos: "), 0, 6);
				grid.add(Ypos, 1, 6, 3, 1);
				grid.add(new Label("Energy: "), 0, 7);
				grid.add(Energy, 1, 7, 3, 1);
				grid.add(notification, 0, 9, 3, 1);


				animalsComboBox.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
							int selected = animalsComboBox.getSelectionModel().getSelectedIndex();
							String xpos = Integer.toString(getWorld().animalList.get(selected).getXpos());
							Xpos.setText(xpos);
							String ypos = Integer.toString(getWorld().animalList.get(selected).getYpos());
							Ypos.setText(ypos);
							String energy = Integer.toString(getWorld().animalList.get(selected).getEnergy());
							Energy.setText(energy);
					}

				});

				button.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if(Xpos.getText().isEmpty() || Ypos.getText().isEmpty() || Energy.getText().isEmpty()){
							notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");
						} else {
							int selected = animalsComboBox.getSelectionModel().getSelectedIndex();
							getWorld().grid[getWorld().animalList.get(selected).getXpos()][getWorld().animalList
									.get(selected).getYpos()] = ' ';
							getWorld().animalList.get(selected).setXpos(Integer.parseInt(Xpos.getText()));
							getWorld().animalList.get(selected).setYpos(Integer.parseInt(Ypos.getText()));
							stage.close();

						}
					}

				});

				root = (Group) scene.getRoot();
				root.getChildren().add(grid);
				stage.setScene(scene);
				stage.show();
				System.out.println("modify life form clicked");
			}
		});

		removeLifeForm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				final Stage stage = new Stage();
				Group root = new Group();
				Scene scene = new Scene(root, 300, 100);
				stage.setTitle("Remove Lifeform");

				ArrayList<String> animalNames = new ArrayList<>();
				for (int i = 0; i < getWorld().animalList.size(); i++) {
					animalNames.add(getWorld().animalList.get(i).getName());
				}
				ObservableList<String> obList = FXCollections.observableList(animalNames);

				final ComboBox<String> animalsComboBox = new ComboBox<>();
				animalsComboBox.setItems(obList);

				GridPane grid = new GridPane();
				grid.setVgap(4);
				grid.setHgap(10);
				grid.setPadding(new Insets(5, 5, 5, 5));
				grid.add(new Label("Animals: "), 0, 0);
				grid.add(animalsComboBox, 1, 0);
				grid.add(button, 3, 0);
				grid.add(notification, 0, 4, 3, 1);

				button.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if(animalsComboBox.getValue() == null){
							notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");
						} else {
							int selected = animalsComboBox.getSelectionModel().getSelectedIndex();
							getWorld().animalList.remove(selected);
							stage.close();
						}
					}

				});

				root = (Group) scene.getRoot();
				root.getChildren().add(grid);
				stage.setScene(scene);
				stage.show();
			}
		});
		addLifeForm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				final Stage stage = new Stage();
				Group root = new Group();
				Scene scene = new Scene(root, 600, 600);
				stage.setTitle("Add lifeform");
				final ComboBox<String> animalsComboBox = new ComboBox<>();
				animalsComboBox.getItems().addAll("Bug", "Lion");
				final GridPane grid = new GridPane();
				grid.setVgap(4);
				grid.setHgap(10);
				grid.setPadding(new Insets(5, 5, 5, 5));
				grid.add(new Label("Animals: "), 0, 0);
				grid.add(animalsComboBox, 1, 0);
				grid.add(button, 0, 1);
				grid.add(notification, 0, 2, 3, 1);

				button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						if(animalsComboBox.getValue() == null){
							notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");
						} else {
							int selected = animalsComboBox.getSelectionModel().getSelectedIndex();
							if (selected == 0) {
								getWorld().AddBug();
							} else if (selected == 1) {
								getWorld().AddLion();
							}
							stage.close();
						}
					}

				});

				root = (Group) scene.getRoot();
				root.getChildren().add(grid);
				stage.setScene(scene);
				stage.show();
				System.out.println("add life form clicked");
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
		view.getItems().add(displayMapInfo);

		displayConfig.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = new Stage();
				Group root = new Group();
				Scene scene = new Scene(root, 550, 300);
				stage.setTitle("Display Configuration");
				GridPane grid = new GridPane();
				grid.setVgap(4);
				grid.setHgap(10);
				grid.setPadding(new Insets(5, 5, 5, 5));
				grid.add(new Label("Food: " + getWorld().foodLeft()), 0, 0);
				grid.add(new Label("Obstacles: " + getWorld().getObstacles()), 0, 1);
				grid.add(new Label("Shelters: " + getWorld().getShelters()), 0, 2);
				grid.add(new Label("Animals: " + getWorld().animalList.size()), 0, 3);
				for (int i = 0; i < getWorld().animalList.size(); i++) {
					grid.add(new Label("ID: " + getWorld().animalList.get(i).getBugID()), 1, i + 4);
					grid.add(new Label("Specie: " + getWorld().animalList.get(i).getType()), 2, i + 4);
					grid.add(new Label("Name: " + getWorld().animalList.get(i).getName()), 3, i + 4);
					grid.add(new Label("X-position: " + getWorld().animalList.get(i).getXpos()), 4, i + 4);
					grid.add(new Label("Y-position: " + getWorld().animalList.get(i).getYpos()), 5, i + 4);
					grid.add(new Label("Energy: " + getWorld().animalList.get(i).getEnergy()), 6, i + 4);
				}

				root = (Group) scene.getRoot();
				root.getChildren().add(grid);
				stage.setScene(scene);
				stage.show();
				System.out.println("was clicked");
			}
		});
		editConfig.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				final Stage stage = new Stage();
				Group root = new Group();
				Scene scene = new Scene(root, 600, 600);
				stage.setTitle("Edit Configuration");
				GridPane grid = new GridPane();
				grid.setVgap(4);
				grid.setHgap(10);
				grid.setPadding(new Insets(5, 5, 5, 5));
				grid.add(new Label("Bugs:"), 0, 0);
				grid.add(Bugs, 1, 0, 3, 1);
				grid.add(new Label("Lions:"), 0, 1);
				grid.add(Lions, 1, 1, 3, 1);
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
				grid.add(button, 0, 10);
				grid.add(notification, 0, 8, 3, 1);


				String bugsSize = Integer.toString(getWorld().bugList.size());
				Bugs.setText(bugsSize);
				String lionsSize = Integer.toString(getWorld().lionList.size());
				Lions.setText(lionsSize);
				String food = Integer.toString(getWorld().getFood());
				Food.setText(food);
				String obstacles = Integer.toString(getWorld().getObstacles());
				Obstacles.setText(obstacles);
				String xDimension = Integer.toString(getWorld().getXdimension());
				Xdimension.setText(xDimension);
				String yDimension = Integer.toString(getWorld().getYdimension());
				Ydimension.setText(yDimension);

				button.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if(Bugs.getText().isEmpty() || Lions.getText().isEmpty() || Food.getText().isEmpty() || Obstacles.getText().isEmpty() || Xdimension.getText().isEmpty() || Ydimension.getText().isEmpty()){
							notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");
						}else {
							getWorld().clearGroups();
							setWorld(new World(getRoot(), Integer.parseInt(Food.getText()),
									Integer.parseInt(Obstacles.getText()), Integer.parseInt(Bugs.getText()),
									Integer.parseInt(Lions.getText()), Integer.parseInt(Xdimension.getText()),
									Integer.parseInt(Ydimension.getText()), Integer.parseInt(Shelters.getText())));
							stage.close();
						}
					}

				});
				root = (Group) scene.getRoot();
				root.getChildren().add(grid);
				stage.setScene(scene);
				stage.show();
				System.out.println("was  clicked");
			}
		});
		displayLifeForms.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = new Stage();
				Group root = new Group();
				Scene scene = new Scene(root, 600, 600);
				stage.setTitle("Display Configuration");
				GridPane grid = new GridPane();
				grid.setVgap(4);
				grid.setHgap(10);
				grid.setPadding(new Insets(5, 5, 5, 5));
				grid.add(new Label("Animals: " + world.animalList.size()), 0, 0);
				for (int i = 0; i < world.animalList.size(); i++) {
					grid.add(new Label("Name: " + world.animalList.get(i).getName()), 1, i + 1);
					grid.add(new Label("X-position: " + world.animalList.get(i).getXpos()), 2, i + 1);
					grid.add(new Label("Y-position: " + world.animalList.get(i).getYpos()), 3, i + 1);
					grid.add(new Label("Energy: " + world.animalList.get(i).getEnergy()), 4, i + 1);
				}
				root = (Group) scene.getRoot();
				root.getChildren().add(grid);
				stage.setScene(scene);
				stage.show();
				System.out.println("was clicked");
			}
		});
		displayMapInfo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = new Stage();
				Group root = new Group();
				Scene scene = new Scene(root, 600, 600);
				stage.setTitle("Map Information");
				GridPane grid = new GridPane();
				grid.setVgap(4);
				grid.setHgap(10);
				grid.setPadding(new Insets(5, 5, 5, 5));
				grid.add(new Label("Map Dimensions: "), 0, 0);
				grid.add(new Label("X-Dimension: " + world.getXdimension()), 1, 1);
				grid.add(new Label("Y-Dimension: " + world.getYdimension()), 1, 2);
				root = (Group) scene.getRoot();
				root.getChildren().add(grid);
				stage.setScene(scene);
				stage.show();
				System.out.println("was clicked");
			}
		});

		// Simulate
		javafx.scene.control.Menu simulate = new javafx.scene.control.Menu("Simulate");
		MenuItem run = new MenuItem("Run");
		MenuItem pausePlay = new MenuItem("Pause/Play");
		MenuItem restart = new MenuItem("Restart");
		MenuItem toggleMap = new MenuItem("Toggle Map");
		simulate.getItems().add(run);
		simulate.getItems().add(pausePlay);
		simulate.getItems().add(restart);
		simulate.getItems().add(toggleMap);
		run.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				getWorld().display();
			}
		});
		pausePlay.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				isPaused = !isPaused;
			}
		});
		restart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				getWorld().clearGroups();
				try {
					loadLatest();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				isPaused = false;
			}
		});
		toggleMap.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("was clicked");
			}
		});

		menuBar.getMenus().add(file);
		menuBar.getMenus().add(edit);
		menuBar.getMenus().add(view);
		menuBar.getMenus().add(simulate);

		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
	}

	public boolean isPaused() {
		return isPaused;
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

	public World getWorld() {
		return this.world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Group getRoot() {
		return this.root;
	}

	public void setRoot(Group root) {
		this.root = root;

	}

	public String getFileName() {
		return this.filename;
	}

	public void setFileName(String filename) {
		this.filename = filename;
	}
	
	public void saveLatest(String filename){
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
	
	public void loadLatest() throws FileNotFoundException {
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
			is1 = new ObjectInputStream(new FileInputStream("Configurations/" + filename));
			World w;
			w = (World) is1.readObject();
			setWorld(new World(w, getRoot()));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

	}

	

}
