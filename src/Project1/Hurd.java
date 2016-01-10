package Project1;

import javafx.scene.paint.Color;

@SuppressWarnings("serial")
public class Hurd extends Carnivore {

	public Hurd(int energy, int xpos, int ypos, int dx, int dy) {
		super("Hurd", 'H', energy, xpos, ypos, 003, 5, dx, dy);
	}
	
	public Color getFill(){
		return Color.DEEPPINK;
	}

	@Override
	public String getType() {
		String type = "Herd";
		return type;
	}

}
