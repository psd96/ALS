package Project1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

@SuppressWarnings("serial")
public class Lion extends Carnivore {

	public Lion(int dx, int dy) {
		super(dx, dy, 5);
		AddLion();
		// TODO Auto-generated constructor stub
	}
	
	public Color getFill(){
		return Color.ORANGE;
	}
	
	public String getType(){
		String type = "Lion";
		return type;
	}
	
	public void AddLion() {
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
		Scene scene = new Scene(root, 600, 600);
		stage.setTitle("Add Lion");
		final ComboBox<String> animalsComboBox = new ComboBox<String>();
		animalsComboBox.getItems().addAll("Bug", "Lion");
		final GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(new Label("Animals: "), 0, 0);
		grid.add(animalsComboBox, 1, 0);
		grid.add(button, 10, 0);
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
				// TODO Auto-generated method stub
				int selected = animalsComboBox.getSelectionModel().getSelectedIndex();
				if (animalsComboBox.getValue() == null ||Name.getText().isEmpty() || ID.getText().isEmpty() || Energy.getText().isEmpty() || Xpos.getText().isEmpty()
						|| Ypos.getText().isEmpty()) {
					notification.setText("ERROR!!: PLEASE FILL ALL FIELDS");

				} else {
					if (selected == 1) {
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
			}

		});
		root = (Group) scene.getRoot();
		root.getChildren().add(grid);
		stage.setScene(scene);
		stage.show();
	}


}
