#######################################
#     Création de module graphique    #
#    Makefile   -   DURAND | MARAIS   #
#######################################


# Variables
file = simple2 
image = braddock.jpg
# Show information
all:
	@echo -e "Pour dessiner votre fichier : make run file=\"<filename>\".\nCe fichier doit être dans le répertoire test.\nPar défaut le fichier testé est simple2.\n"
	@echo -e "Pour contruire le dessin de votre image : make creator image=\"<filename>\".\nCe fichier doit être dans le répertoire img.\nLe dessin produit est placé dans le dossier test.\nPar défaut le fichier testé est braddock.jpg"

# Create Lexer
create_lexer:
	@echo "Création du Lexer"
	@jflex src/lexer/draw.flex 1> /dev/null
	@rm src/lexer/Lexer.java~


# Compilation java
compile: create_lexer
	@echo "Compilation des fichiers java"
	@mkdir -p bin
	@javac -sourcepath src -d bin src/*.java

# Clear files
clear:
	@echo "Suppression du fichier contenant les .class"
	@rm -Rf bin

$(file):
	@echo -e "============================\n\n    Interpréteur     \n fichier> test/"$(file)"\n\n============================\n"

# Lancer la compilation
run: $(file)
	@java -cp bin Main test/$(file) 0

creator: 
	@echo -e "============================\n\n    Créateur     \n fichier> img/"$(image)"\n\n============================\n"
	@java -cp bin Main img/$(image) 1
