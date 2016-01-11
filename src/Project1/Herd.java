package Project1;

import javafx.scene.paint.Color;

public class Herd extends Carnivore {

    private String herdType;
    private int members;

    public Herd(int energy, int xpos, int ypos, int dx, int dy) {
        super("Herd", 'H', energy, xpos, ypos, 003, 5, dx, dy);
    }

    public Color getFill(){
        return Color.DEEPPINK;
    }

    @Override
    public String getType() {
        String type = "Herd";
        return type;
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

}
