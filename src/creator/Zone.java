package creator;

import java.util.LinkedList;

public class Zone{
    LinkedList<Frontiere> frontieres = new LinkedList<>();
    ColorOfPixel color;

    public Zone(ColorOfPixel color) {
        this.color = color;
    }

    public void add(Frontiere frontiere){
        frontieres.add(frontiere);
    }

    public boolean contains(Frontiere frontiere){
        return frontieres.contains(frontiere);
    }
}
