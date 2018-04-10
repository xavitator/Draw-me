package creator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class CreateImage {
	private BufferedImage image;
	private boolean[][] visitedPixel;
	private LinkedList<RectangleColor> list = new LinkedList<>();
	private LinkedList<Point> next = new LinkedList<>();
	private int width;
	private int height;
	
	public CreateImage(String path){
		try {
			File file = new File(path);
			System.out.println(file.getAbsolutePath());
			image = ImageIO.read(file);
			height = image.getHeight();
			width = image.getWidth();
			visitedPixel = new boolean[height][width];
			//next.add(new Point());
		}
		catch (IOException e){
			System.out.println("Le chemin de l'image n'existe pas.");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void createText(){
		int i = 0;
		boolean b = true;
		while (i <= 500*200 && b){
			System.out.println(i);
			b = fillPixels();
			//next.removeIf((Point point) -> visitedPixel[(int) point.getX()][(int) point.getY()]);
			//System.out.println(next.size());
			i++;
		}
	}

	private Point getPoint(){
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(! visitedPixel[i][j]) return new Point(i,j);
			}
		}
		return null;
	}

	private boolean fillPixels(){
		Point first = getPoint();
		if(first == null) return false;
		ColorOfPixel color = new ColorOfPixel((int) first.getX(), (int) first.getY(), image);
		visitedPixel[(int) first.getX()][(int) first.getY()] = true;
		boolean horizontal = true;
		boolean vertical = true;
		int transX = 1;
		int transY = 1;
		while (horizontal || vertical){
			if(horizontal) {
				horizontal = goodLineHorizontal(color, new Point((int) first.getX()+transX, (int) first.getY()), transY - 1);
				if (horizontal) transX++;
			}
			if(vertical) {
				vertical = goodLineVertical(color, new Point((int) first.getX(), (int) first.getY() + transY), transX - 1);
				if (vertical) transY++;
			}
		}
		list.add(new RectangleColor(first,transX,transY,color));
		return true;
	}

	private boolean goodLineHorizontal(ColorOfPixel color, Point depart, int distance){
		for(int i = 0; i <= distance; i++){
			if( ! compare((int) depart.getX(), (int) depart.getY()+i, color)) return false;
		}
		changeBoolean(depart, distance, true);
		return true;
	}

	private boolean goodLineVertical(ColorOfPixel color, Point depart, int distance){
		for(int i = 0; i <= distance; i++){
			if( ! compare((int) depart.getX()+i, (int) depart.getY(), color)) return false;
		}
		changeBoolean(depart, distance, false);
		return true;
	}

	private void changeBoolean(Point point, int distance, boolean horizontal){
		for (int i = 0; i <= distance; i++) {
			if(horizontal) {
				int x = (int) point.getX();
				int y = (int) point.getY() + i;
				if(inTableau(x,y))visitedPixel[x][y] = true;
			}
			else{
				int x = (int) point.getX() + i;
				int y = (int) point.getY();
				if(inTableau(x,y))visitedPixel[x][y] = true;
			}
		}
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
		if(inTableau(x,y)){
			ColorOfPixel pixel1 = new ColorOfPixel(x,y,image);
			return color.equals(pixel1);
		}
		else return false;
	}

	private boolean inTableau(int x, int y){
		return (y >= 0 && y < width && x < height && x >= 0);
	}

	@Override
	public String toString() {
		String res = "";
		int a = 0;
		int max = 200;
		for (RectangleColor rect : list){
			if(a == 0) res += "Begin";
			res += rect.toString()+"\n";
			a++;
			if(a == max){
				res += "End;";
				a = 0;
			}
		}
		if(a != 1) res += "End;";
		return res;
	}

	public static void main(String[] args){
		if(args.length >= 2){
			CreateImage image = new CreateImage(args[0]);
			image.createText();
			String res = image.toString();
			try{
				File ff=new File("test/"+args[1]);
				ff.createNewFile();
				FileWriter ffw=new FileWriter(ff);
				ffw.write(res);  // écrire une ligne dans le fichier resultat.txt
				ffw.close(); // fermer le fichier à la fin des traitements
			} catch (Exception e) {System.out.println("problème dans l'écriture dans le fichier");}
		}
		else {
			if(args.length < 1)	System.out.println("Rentrer un chemin vers une image svp.");
			else System.out.println("Rentrer le nom du fichier que vous voulez créer.");
			System.exit(-1);
		}
	}
	
}