package Project1;

import javafx.scene.paint.Color;

/**
 * This class creates a Herd of a given specie. It extends from the Carnivore class and inherits all of its methods.
 */
public class Herd extends Carnivore {

    /**
     * Will create a Herd.
     * @param dx - X dimensions of the map
     * @param dy - X dimensions of the map
     * @param xpos -  the X-position of the bug
     * @param ypos - the Y-position of the bug
     * @param energy -the energy level of the bug
     */
    public Herd(int dx,int dy, int xpos, int ypos, int energy) {
        super(dx, dy, "Herd","Herd",003,xpos,ypos,10,energy);

    }

    /**
     * Sets the colour which bugs should be displayed on the grid
     * @return - the color which a herd is represented by
     */
    public Color getFill(){
        return Color.DEEPPINK;
    }


    /**
     * This method gets the Type of species the herd is made up of.
     * @return - the species which the herd is made up of
     */
    public String getType() {
        int start = getHerdType().lastIndexOf('.') + 1;
        String herdType = getHerdType().substring(start);
        String type = herdType + " Herd";
        return type;
    }


}
