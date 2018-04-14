import ast.AST;
import ast.ValueEnv;
import lexer.Lexer;
import parser.LookAhead1;
import parser.Parser;
import creator.CreateImage;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.lang.Exception;

public class Main {

    private static void initAndShow(String filename) {
        JFrame frame = new JFrame("ADS4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        frame.getContentPane().setPreferredSize(new Dimension(800,600));
        frame.getContentPane().add(new MyCanvas(filename));

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Il manque des arguments.");
        }
        else{
            if(args[1].equals("0")){
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        initAndShow(args[0]);
                    }
                });
            }
            else{
                CreateImage image = new CreateImage(args[0]);
                image.createFile();
            }
        }
    }
}

@SuppressWarnings("serial")
class MyCanvas extends JComponent {

    private AST ast;

    public MyCanvas(String filename) {
        ast = null;
        if(new AST(0,0).debugMode()) {System.out.println("\n=== Mode debug ===");}
        ValueEnv registre = new ValueEnv();
        try {
            File input = new File(filename);
            Reader reader = new FileReader(input);
            Lexer lexer = new Lexer(reader);
            LookAhead1 look = new LookAhead1(lexer);
            Parser parser = new Parser(look);
            ast = parser.progNonTerm(); // Axiome 

        } catch (Exception e) {
            System.out.println("** Erreur de compilation **\n" + e.getMessage()+"\n");
            System.exit(-1);
        }
        try {
            ast.verifyAll(registre);
        }
        catch(Exception e){
            System.out.println("** Erreur de type **\n" + e.getMessage()+"\n");
            System.exit(-1);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (g instanceof Graphics2D)
            {
                Graphics2D g2d = (Graphics2D)g;

                // A compléter.
                // Appelez ici votre analyseur et interpréteur, en leur fournissant
                // l'objet g2d de type Graphics2D. Ils pourront ainsi appeler les fonctions
                // g2d.drawCircle, g2d.setColor, etc...
                //
                // Par exemple :
                try{
                    ValueEnv registre = new ValueEnv();
                    ast.exec(g2d,registre);
                    System.out.println("============================\n\nFin de l'exécution\n\n============================\n");

                    System.out.println("Fin de l'exécution");
                } catch (Exception e) {
                    System.out.println("** Erreur d'exécution **\n" + e.getMessage()+"\n");
                    System.exit(-1);
                }
            }
    }

}
