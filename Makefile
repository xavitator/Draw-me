#######################################
#     Création de module graphique    #
#    Makefile   -   DURAND | MARAIS   #
#######################################


# Variables
file = simple2 

# Show information
all:
	@echo -e "Pour dessiner votre fichier : make run file=\"<filename>\".\nCe fichier doit être dans le répertoire test.\nPar défaut le fichier testé est simple"

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
	@java -cp bin Main test/$(file)
