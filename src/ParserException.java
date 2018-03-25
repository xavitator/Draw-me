import java.io.*;

public class ParserException extends Exception{
	
	
	public ParserException(String errorMessage, Token token){
		super(errorMessage + "\n" 
			+ "L'erreur a été repérer à la ligne " + token.line() 
			+ " et à la colonne " + token.column() + ".");
	}	
}