#######################################
#     Création de module graphique    #
#    Makefile   -   DURAND | MARAIS   #
#######################################


# Variables
BIN = bin
SOURCE = src
file = dessin

# Show information
all:
	@echo "Pour dessiner votre fichier : make run file=\"<filename>\". Par défaut le fichier s'appelle dessin"

# Create Lexer
create_lexer:
	@echo "Création du Lexer"
	jflex $(SOURCE)/draw.flex

# Compilation java
compile: # create_lexer
	@echo "Compilation des fichiers java"
	mkdir $(BIN)
	javac -sourcepath $(SOURCE) -d $(BIN) $(SOURCE)/*.java

# Clear files
clear:
	@echo "Suppression du fichier contenant les .class"
	@rm -Rf $(BIN)

# Lancer la compilation
run:
	java -cp $(BIN) Main $(file)
