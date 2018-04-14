package creator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;

public class CreateImage {
	private BufferedImage image;
	private boolean[][] visitedPixel;
	private LinkedList<Point> list = new LinkedList<>();
	private FileWriter resultat;
	private String nomFile = "";
	private int indent = 0;
	private int width;
	private int height;
	private int nombreZone = 0;
	public static final int maxLine = 4000000;
	
	public CreateImage(String path){
		try {
			File file = new File(path);
			String[] tab = file.getAbsolutePath().split("/");
			nomFile = tab[tab.length-1].split("\\.")[0];
			image = ImageIO.read(file);
			height = image.getHeight();
			width = image.getWidth();
			visitedPixel = new boolean[height][width];
			list.add(new Point());
			File ff=new File("test/"+nomFile);
			ff.createNewFile();
			resultat = new FileWriter(ff);
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
		this.fillList();
		while (i <= maxLine && b){
			if(i != nombreZone){
				System.out.println(nombreZone);
				i = nombreZone;
			}
			b = fillPixels();
		}

		if(! b) System.out.println(numberOfNone());
	}

	private int numberOfNone(){
		int res = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(! visitedPixel[i][j]) res ++;
			}
		}
		return res;
	}

	private Point getPoint(){
		return list.pollFirst();
	}

	private void fillList(){
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				list.add(new Point(i,j));
			}
		}
	}

	private boolean fillPixels(){
		Point first = getPoint();
		if(first == null) return false;
		if(visitedPixel[(int) first.getX()][(int) first.getY()]) {
			return true;
		}
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
		ajoutResultat("FillRect("+(int) first.getY()+ "," +(int) first.getX()+","+transY+","+transX+","+color.toString()+");" + "\n");
		nombreZone++;
		return true;
	}

	private boolean goodLineHorizontal(ColorOfPixel color, Point depart, int distance){
		for(int i = 0; i <= distance; i++){
			if( ! compare((int) depart.getX(), (int) depart.getY()+i, color)) {
				return false;
			}
		}
		changeBoolean(depart, distance, true);
		return true;
	}

	private boolean goodLineVertical(ColorOfPixel color, Point depart, int distance){
		for(int i = 0; i <= distance; i++){
			if( ! compare((int) depart.getX()+i, (int) depart.getY(), color)) {
				return false;
			}
		}
		changeBoolean(depart, distance, false);
		return true;
	}

	private void changeBoolean(Point point, int distance, boolean horizontal){
		for (int i = 0; i <= distance; i++) {
			if(horizontal) {
				int x = (int) point.getX();
				int y = (int) point.getY() + i;
				if(inTableau(x,y)){
					visitedPixel[x][y] = true;
				}
			}
			else{
				int x = (int) point.getX() + i;
				int y = (int) point.getY();
				if(inTableau(x,y)){
					visitedPixel[x][y] = true;
				}
			}
		}
	}

	private boolean compare(int x, int y, ColorOfPixel color){
		if(inTableau(x,y)){
			ColorOfPixel pixel1 = new ColorOfPixel(x,y,image);
			return color.equals(pixel1);
		}
		else{
			return false;
		}
	}

	private boolean inTableau(int x, int y){
		return (y >= 0 && y < width && x < height && x >= 0);
	}

	private void ajoutResultat (String ajout){
		try{
			int max = 200;
			if(indent == 0) resultat.write("Begin\n");
			resultat.write("    "+ajout);
			indent++;
			if(indent == max){
				resultat.write("End;\n");
				indent = 0;
			}
		}
		catch (Exception e) {
			System.out.println("problème dans l'écriture dans le fichier.");
			System.exit(-1);
		}
	}

	public void createFile(){
		this.createText();
		String res = (indent > 0)? "End;" : "";
		try{
			resultat.write(res);  // écrire une ligne dans le fichier
			resultat.close(); // fermer le fichier à la fin des traitements
		} 
		catch (Exception e) {
			System.out.println("problème dans l'écriture dans le fichier.");
		}
	}
	
}