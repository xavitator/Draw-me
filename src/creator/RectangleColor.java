package creator;

import java.awt.*;
import creator.ColorOfPixel;

public class RectangleColor{
    private Rectangle rectangle;
    private ColorOfPixel color;

    public RectangleColor(Point point, int height, int width, ColorOfPixel color) {
        this.color = color;
        rectangle = new Rectangle((int) point.getY(),(int) point.getX(),height,width);
    }

    @Override
    public String toString() {
        return "FillRect("+(int) rectangle.getX()+ "," +(int) rectangle.getY()+","+(int) rectangle.getHeight()+","+(int) rectangle.getWidth()+","+color.toString()+");";
    }
}
