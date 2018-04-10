package creator;

import java.awt.image.BufferedImage;

public class ColorOfPixel{
    int red;
    int green;
    int blue;
    int hascode;


    public ColorOfPixel(int x, int y, BufferedImage image) {
        int color = image.getRGB(x,y);
        hascode = color;
        blue = color & 0xff;
        green = (color & 0xff00) >> 8;
        red = (color & 0xff0000) >> 16;
        blue =(blue >= 2)? blue - 2 + blue%4 -1: blue;
        red =(red >= 2)? red - 2 + red%4 -1: red;
        green =(green >= 2)? green - 2 + green%4 -1: green;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ColorOfPixel){
            ColorOfPixel compare = (ColorOfPixel) obj;
            return this.red == compare.red
                    && this.green == compare.green
                    && this.blue == compare.blue;

        }
        else return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return hascode;
    }

    @Override
    public String toString() {
        String r = Integer.toHexString(red).toUpperCase();
        String g = Integer.toHexString(green).toUpperCase();
        String b = Integer.toHexString(blue).toUpperCase();
        r = (r.length() <= 1)? "0"+r : r;
        g = (g.length() <= 1)? "0"+g : g;
        b = (b.length() <= 1)? "0"+b : b;
        return "#"+r+g+b;
    }
}
