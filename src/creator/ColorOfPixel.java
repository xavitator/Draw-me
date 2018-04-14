package creator;

import java.awt.image.BufferedImage;

public class ColorOfPixel{
    private int red;
    private int green;
    private int blue;
    private int hascode;

    public static final int diffOfPixel = 8;

    public ColorOfPixel(int x, int y, BufferedImage image) {
        int color = image.getRGB(y,x);
        hascode = color;
        blue = color & 0xff;
        green = (color & 0xff00) >> 8;
        red = (color & 0xff0000) >> 16;
        blue =(blue >= (diffOfPixel/2))? blue + (diffOfPixel/2) - (blue + (diffOfPixel/2)) % diffOfPixel - 1 : 0;
        red =(red >= (diffOfPixel/2))? red + (diffOfPixel/2) - (red + (diffOfPixel/2))%diffOfPixel -1: 0;
        green =(green >= (diffOfPixel/2))? green + (diffOfPixel/2) - (green + (diffOfPixel/2))%diffOfPixel -1: 0;
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
