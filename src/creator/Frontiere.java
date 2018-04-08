package creator;

import java.awt.*;

public class Frontiere{
    Point position;
    boolean[] isGoodCouleur = {false, false, false, false}; // {droite, haut, gauche, bas}

    public Frontiere(Point position, boolean[] isGoodCouleur) {
        this.position = position;
        this.isGoodCouleur = isGoodCouleur;
    }
}
