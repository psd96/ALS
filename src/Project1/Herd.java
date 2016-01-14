package Project1;

import javafx.scene.paint.Color;

public class Herd extends Carnivore {

    public Herd(int energy, int xpos, int ypos, int dx, int dy) {
        super("Herd", 'H', energy, xpos, ypos, 003, 5, dx, dy, "Herd");
    }

    public Color getFill(){
        return Color.DEEPPINK;
    }

    @Override
    public String getType() {
        String type = getHerdType() + " Herd";
        return type;
    }


}
