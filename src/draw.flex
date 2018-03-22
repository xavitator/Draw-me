%public
%class Lexer
%line
%column
%unicode
%type Token
%yylexthrow{
Exception, LexerException (Exception qu’on va créer nous meme pour l’analyse lexicale)
%yylexthrow}


%state COMMENT 

# definition des differentes variables

beginCommentaire = "/*"
endCommentaire = "*/"
simpleCommentaire = //[^\n\r]*
hex = [0-9A-F]
nombre = [0-9]+ 
couleur = "#"{hex}{hex}{hex}{hex}{hex}{hex}
operateur = "+" | "-" | "/" | "*"
relation = ">" | "<" | "==" | "<=" | ">="
identificateur = [a-z][a-zA-Z_]*
string = "\""[a-zA-Z]+"\""
blanc = [\n\ \t\r]

%%

<COMMENT> {
  {endCommentaire}   {yybegin(YYINITIAL);}
  [^]    {}
}

<YYINITIAL> {
  {simpleCommentaire}	{}
  {beginCommentaire}	{yybegin(COMMENT);}
  {couleur}  			{}
  {nombre}  			{}
  {operateur}  			{}
  {relation}  			{}
  {identificateur}  	{}
  {string}  			{}
  {blanc}*  			{}
}
