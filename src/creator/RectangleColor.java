package creator;

import java.awt.*;

public class RectangleColor{
    Rectangle rectangle;
    ColorOfPixel color;

    public RectangleColor(int x, int y, int height, int width, ColorOfPixel color) {
        this.color = color;
        rectangle = new Rectangle(x,y,height,width);
    }

    @Override
    public String toString() {
        return "FillRect("+(int) rectangle.getX()+ "," +(int) rectangle.getY()+","+(int) rectangle.getWidth()+","+(int) rectangle.getHeight()+","+color.toString()+");";
    }
}
