package lexer;

import parser.*;
import parser.Sym;
import parser.token.*;

%%
%public
%class Lexer
%line
%column
%unicode
%type Token

%{
  class LexerException extends Exception{
    public LexerException(int line, int column, String caract){
      super("Le caractere "+caract.replace("\\","\\\\")+" a la ligne "+line+" et a la colonne "+column+" n'est pas reconnu par la grammaire.");
    }
  }
%}

%yylexthrow{
  //Exception qu’on va créer nous meme pour l’analyse lexicale
  Exception, LexerException
%yylexthrow}

//definition des differentes variables

commentaire = ("/*"[^]"*/") | ("//"[^\n\r]*)

hex = [0-9A-F]
nombre = [0-9]+ 
couleur = "#"{hex}{hex}{hex}{hex}{hex}{hex}
ordre = ">" | "<" | "<=" | ">="  
equal = "==" | "!="
identificateur = [a-z][a-zA-Z_]*
string = "\""[^\"]+[^\\]"\""
blanc = [\n\ \t\r]

%%
{commentaire}              {}
{couleur}  		   {return new ColorToken(Sym.COULEUR,yyline + 1,yycolumn +1 ,yytext());}
{nombre}  		   {return new IntToken(Sym.INT,yyline + 1,yycolumn +1 ,yytext());}
"+"                        {return new Token(Sym.PLUS,yyline + 1,yycolumn +1 );}
"-"                        {return new Token(Sym.MINUS,yyline + 1,yycolumn +1 );}
"*"                        {return new Token(Sym.TIMES,yyline + 1,yycolumn +1 );}
"/"                        {return new Token(Sym.PLUS,yyline + 1,yycolumn +1 );}
"&&"                       {return new Token(Sym.AND,yyline + 1,yycolumn +1 );}
"||"                       {return new Token(Sym.OR,yyline + 1,yycolumn +1 );}
{ordre}         	   {return new StringToken(Sym.COMPARATOR,yyline + 1,yycolumn +1 , yytext());}
{equal}         	   {return new StringToken(Sym.EQ,yyline + 1,yycolumn +1 , yytext());}
";"                	   {return new Token(Sym.POINTVIRGULE,yyline + 1,yycolumn +1 );}
[Tt]"rue"     		   {return new BooleanToken(Sym.BOOLEAN,yyline + 1,yycolumn +1 ,yytext());}
[Ff]"alse"    		   {return new BooleanToken(Sym.BOOLEAN,yyline + 1,yycolumn +1 ,yytext());}
"Begin"         	   {return new Token(Sym.BEGIN,yyline + 1,yycolumn +1 );}
"End"           	   {return new Token(Sym.END,yyline + 1,yycolumn +1 );}
"While"              {return new Token(Sym.WHILE,yyline + 1,yycolumn +1 );}
"Do"                 {return new Token(Sym.DO,yyline + 1,yycolumn +1 );}
"If"            	   {return new Token(Sym.IF,yyline + 1,yycolumn +1 );} 
"Else"          	   {return new Token(Sym.ELSE,yyline + 1,yycolumn +1 );}
"Then"          	   {return new Token(Sym.THEN,yyline + 1,yycolumn +1 );}
"("             	   {return new Token(Sym.LPAR,yyline + 1,yycolumn +1 );}
")"             	   {return new Token(Sym.RPAR,yyline + 1,yycolumn +1 );}
"DrawCircle"    	   {return new Token(Sym.DRAWCIRCLE,yyline + 1,yycolumn +1 );}
"FillCircle"    	   {return new Token(Sym.FILLCIRCLE,yyline + 1,yycolumn +1 );}
"DrawRect"      	   {return new Token(Sym.DRAWRECT,yyline + 1,yycolumn +1 );}
"FillRect"      	   {return new Token(Sym.FILLRECT,yyline + 1,yycolumn +1 );}
"="             	   {return new Token(Sym.ASSIGNATION,yyline + 1,yycolumn +1 );}
"Const"         	   {return new Token(Sym.CONST,yyline + 1,yycolumn +1 );}
"Var"           	   {return new Token(Sym.VAR,yyline + 1,yycolumn +1 );}
"Proc"				   {return new Token(Sym.PROC, yyline + 1, yycolumn +1 );} 
{identificateur}	   {return new StringToken(Sym.IDENT,yyline + 1,yycolumn +1 , yytext());}
{string}	           {return new StringToken(Sym.STRING,yyline + 1,yycolumn +1 ,yytext().replace("\"",""));}
{blanc}+|","  		   {}
<<EOF>>                    {return new Token(Sym.EOF,yyline + 1,yycolumn +1 );}
[^]                        {throw new LexerException(yyline + 1,yycolumn +1 , yytext());}
