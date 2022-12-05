class HelloWord extends Program{
    
    final int xTailleMenu = 150;
    final int yTailleMenu = 35;

    void initialiserIdentifieur(char[][] identifieur){
        for(int i=0; i<yTailleMenu; i++){
            for(int j=0; j<xTailleMenu; j++){
                identifieur[i][j]=' '; //╳
            }
        }
    }

    void initialiserCaractèreBordure(char[] caracteres, char c){
        if(c=='═'){
            caracteres[0] = '═';
            caracteres[1] = '║';
            caracteres[2] = '╔';
            caracteres[3] = '╗';
            caracteres[4] = '╚';
            caracteres[5] = '╝';
        }
        else if(c=='━'){
            caracteres[0] = '━';
            caracteres[1] = '┃';
            caracteres[2] = '┏';
            caracteres[3] = '┓';
            caracteres[4] = '┗';
            caracteres[5] = '┛';
        }
        else{
            for(int i=0; i<length(caracteres); i++){
                caracteres[i] = c;
            }
        }
    }

    void insérerLigneVerticale(char[][] menu, int x, int y, int longueur, char c){
        int max = longueur-y;
        for(int i=0; i<max; i++){
            menu[i+y][x] = c;
        }
    }

    void insérerLigneHorizontale(char[][] menu, int x, int y, int longueur, char c){
        int max = longueur-x;
        for(int i=0; i<max; i++){
            menu[y][i+x] = c;
        }
    }
    
    void insérerBordure(char[][] menu, int xmin, int ymin, int xmax, int ymax, char c){
        char[] caracteres = new char[6];
        initialiserCaractèreBordure(caracteres, c);

        insérerLigneHorizontale(menu, xmin, ymin, xmax, caracteres[0]);
        insérerLigneVerticale(menu, xmax, ymin, ymax, caracteres[1]);
        insérerLigneHorizontale(menu, xmin, ymax, xmax, caracteres[0]);
        insérerLigneVerticale(menu, xmin, ymin, ymax, caracteres[1]);
        menu[ymin][xmin] = caracteres[2];
        menu[ymin][xmax] = caracteres[3];
        menu[ymax][xmin] = caracteres[4];
        menu[ymax][xmax] = caracteres[5];
    }

    void afficherIdentifieur(char[][] identifieur){
        String menu = "";
        for(int i=0; i<yTailleMenu; i++){
            for(int j=0; j<xTailleMenu; j++){
                menu += identifieur[i][j];
            }
            menu+="\n";
        }
        println(menu);
    }

    void algorithm(){
        char[][] Identifieur = new char[yTailleMenu][xTailleMenu];
        initialiserIdentifieur(Identifieur);
        afficherIdentifieur(Identifieur);
        println("--------------------------------------------------------------------");
        insérerBordure(Identifieur, 0, 0, xTailleMenu-1, yTailleMenu-1, '═');
        insérerBordure(Identifieur, 20, 5, xTailleMenu-21, yTailleMenu-6, '━');
        afficherIdentifieur(Identifieur);
    }
}
