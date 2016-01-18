package Project1;

import javafx.scene.paint.Color;

public class Herd extends Carnivore {

    public Herd(int energy, int xpos, int ypos, int dx, int dy) {
        super(dx, dy, "Herd");
        this.setName("Herd");
        this.setSymbol('H');
        this.setEnergy(energy);
        this.setXpos(xpos);
        this.setYpos(ypos);
        this.setBugID(003);
        this.setSmellrange(5);
    }

    public Color getFill(){
        return Color.DEEPPINK;
    }

    @Override
    public String getType() {
        int start = getHerdType().lastIndexOf('.') + 1;
        String herdType = getHerdType().substring(start);
        String type = herdType + " Herd";
        return type;
    }


}
