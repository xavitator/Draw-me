package creator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class CreateImage {
	BufferedImage image;
	ColorOfPixel[][] pixels;
	boolean[][] visitedPixel;
	LinkedList<RectangleColor> list = new LinkedList<>();
	LinkedList<Point> next = new LinkedList<>();
	
	public CreateImage(String path){
		try {
			image = ImageIO.read(new File(path));
			//pixels = new ColorOfPixel[image.getHeight()][image.getWidth()];
			visitedPixel = new boolean[image.getHeight()][image.getWidth()];
			next.add(new Point());
		}
		catch (IOException e){
			System.out.println("Le chemin de l'image n'existe pas.");
			e.printStackTrace();
		}
	}

	private void fillPixels(){
		next.removeIf((Point point) -> visitedPixel[(int) point.getY()][(int) point.getX()]);
		Point first = next.getFirst();
		boolean horizontal = true;
		boolean vertical = true;
		int transX = 0;
		int transY = 0;
		while (horizontal && vertical){
			for (int i = 0; i < transY; i++) {

			}
		}
	}

	private boolean goodLine(ColorOfPixel color, Point point, int distance){
		for (int i = 0; i <= distance; i++) {
			if(! color.equals(new ColorOfPixel((int) point.getX(), (int) point.getY()+i, image))){
				return false;
			}
		}
		changeBoolean(point,distance);
		return true;
	}

	private void changeBoolean(Point point, int distance){

	}

	/*private void findZone(){
		ColorOfPixel color = pixels[0][0];
		Zone zone = new Zone(color);
		zones.add(zone);
		boolean[] tab = new boolean[4];
		tab[0] = false;
		tab[1] = false;
		tab[2] = compare(0,0,1,0);
		tab[3] = compare(0,0,0,1);
		zone.add(new Frontiere(new Point(),tab));
		visitedPixel[0][0] = true;
		boolean[] suite = {false,true, true,true};
		boolean[] suitebis = {true,false, true,true};
		if(tab[2]) this.testIsIn(1,0,suitebis,zone);
		else {
			Zone zone1 = new Zone(pixels[0][1]);
			zones.add(zone1);
			this.testIsIn(1,0,suitebis,zone1);
		}
		if(tab[3]) this.testIsIn(0,1,suite,zone);
		else {
			Zone zone1 = new Zone(pixels[0][1]);
			zones.add(zone1);
			this.testIsIn(1,0,suitebis,zone1);
		}
	}*/

	private boolean compare(int x, int y, ColorOfPixel color){
		ColorOfPixel pixel1 = new ColorOfPixel(x,y,image);
		return inTableau(x,y) && color.equals(pixel1);
	}

	private boolean inTableau(int x, int y){
		return (y >= 0 && y < pixels.length && x < pixels[0].length && x >= 0);
	}

	@Override
	public String toString() {
		String res = "";
		for (RectangleColor rect : list){
			res += rect.toString()+"\n";
		}
		return res;
	}

	public static void main(String[] args){
		
	}
	
}