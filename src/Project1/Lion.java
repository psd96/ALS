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

public class Lion extends Carnivore {

	public Lion(int dx, int dy) { //Constrctor
		super(dx, dy, "Lion");
		AddAnimal(getType());
	}
	
	public Color getFill(){ //Sets the colour which lions should be displayed on the grid
		return Color.ORANGE;
	}

}
