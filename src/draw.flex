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

  private Token token(Sym type, int line, int column) {
      return new Token(type,line,column);
  }

  private ColorToken colorToken(Sym type, int line, int column, String color){
      return new ColorToken(type,line,column,color);
  }

  private IntToken intToken(Sym type, int line, int column, String value) {
      return new IntToken(type,line,column,Integer.parseInt(value));
  }      

  private StringToken stringToken(Sym type, int line, int column, String value) {
      return new StringToken(type,line,column,value);
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
operateur = "+" | "-" | "/" | "*"
relation = ">" | "<" | "==" | "<=" | ">="
identificateur = [a-z][a-zA-Z_]*
string = "\""[a-zA-Z]+"\""
blanc = [\n\ \t\r]

%%
{commentaire} 	   {}
{couleur}  			   {return colorToken(Sym.COULEUR,yyline,yycolumn,yytext());}
{nombre}  			   {return intToken(Sym.INT,yyline,yycolumn,yytext());}
{operateur}  		   {return stringToken(Sym.OPERATEUR,yyline,yycolumn,yytext());}
{relation}         {return stringToken(Sym.RELATION,yyline,yycolumn, yytext());}
";"                {return token(Sym.POINTVIRGULE,yyline,yycolumn);}
("T"|"t")"rue"     {return intToken(Sym.INT,yyline,yycolumn,1);}
("F"|"f")"alse"    {return intToken(Sym.INT,yyline,yycolumn,0);}
"Begin"            {return token(Sym.BEGIN,yyline,yycolumn);}
"End"              {return token(Sym.END,yyline,yycolumn);}
"If"               {return token(Sym.IF,yyline,yycolumn);} 
"Else"             {return token(Sym.ELSE,yyline,yycolumn);}
"Elseif"           {return token(Sym.ELSEIF,yyline,yycolumn);} 
"Then"             {return token(Sym.THEN,yyline,yycolumn);}
"("                {return token(Sym.LPAR,yyline,yycolumn);}
")"                {return token(Sym.RPAR,yyline,yycolumn);}
"DrawCircle"       {return token(Sym.DRAWCIRCLE,yyline,yycolumn);}
"FillCircle"       {return token(Sym.FILLCIRCLE,yyline,yycolumn);}
"DrawRect"         {return token(Sym.DRAWRECT,yyline,yycolumn);}
"FillRect"         {return token(Sym.FILLRECT,yyline,yycolumn);}
"="                {return token(Sym.ASSIGNATION,yyline,yycolumn);}
"Const"            {return token(Sym.CONST,yyline,yycolumn);}
"Var"              {return token(Sym.VAR,yyline,yycolumn);}
{identificateur}   {return stringToken(Sym.STRING,yyline,yycolumn, yytext());}
{string}  			   {return stringToken(Sym.STRING,yyline,yycolumn,yytext().replace("\"",""));}
{blanc}*  			   {}
[^]                {throw new LexerException(yyline,yycolumn, yytext());}
