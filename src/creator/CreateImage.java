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
	private String resultat = "";
	private int indent = 0;
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
		ajoutResultat("FillRect("+(int) first.getX()+ "," +(int) first.getY()+","+transX+","+transY+","+color.toString()+");" + "\n");
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

	private void ajoutResultat (String ajout){
		int max = 200;
		if(indent == 0) resultat += "Begin";
		resultat += ajout+"\n";
		indent++;
		if(indent == max){
			resultat += "End;";
			indent = 0;
		}
	}
	@Override
	public String toString() {
		return resultat;
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