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

	public Lion(int dx, int dy, String name, int xpos, int ypos, int energy, int ID, int smellRange) { //Constrctor
		super(dx, dy, "Lion");
		setName(name);
		setBugID(ID);
		setEnergy(energy);
		setSymbol(name.charAt(0));
		setXpos(xpos);
		setYpos(ypos);
		setSmellrange(smellRange);
	}
	
	public Color getFill(){ //Sets the colour which lions should be displayed on the grid
		return Color.ORANGE;
	}

}
