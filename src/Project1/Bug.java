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

public class Bug extends Herbivore {


	public Bug(int dx, int dy) { //Constructor
		super(dx, dy, "Bug");
		AddAnimal(getType());
	}

	public Color getFill() { //Sets the colour which bugs should be displayed on the grid
		return Color.RED;
	}


}
