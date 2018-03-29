import java.io.*;

public class ParserException extends Exception{
	
	
	public ParserException(String errorMessage, int line, int column){
		super(errorMessage + "\n" 
			+ "L'erreur a été repérer à la ligne " + line 
			+ " et à la colonne " + column + ".");
	}	
}