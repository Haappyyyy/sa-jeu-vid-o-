import extensions.CSVFile;



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

    String toString(String[][] content){
        String res ="";

        for(int y = 0; y < length(content, 1); y++){
                println(content[y][0] + " = " + content[y][1]);
        }

        return res;

        
    }

    boolean Perdu(Utilisateur user){
        boolean p=false;
        if(user.vies==0){
            p = true;
        }
        return p;
    }

    boolean Gagné(Utilisateur user){
        boolean g=false;
        if(user.victoires==5){
            g=true;
        }
        return g;
    }




    boolean Traduction(Mot[] words,Utilisateur user){
        Mot motactuel;
        boolean gagné= false;
        String reponse="";
        int victoires=0;
        int vies=5;
        if (!choixlangue()){
            while (vies>0 && !(victoires==5)){
                motactuel = words[(int)(random()*length(words,1))];
                println("Trouve la traduction en français de " + "\"" + motactuel.moten + "\"" );
                reponse = readString();
                if (equals(reponse, motactuel.motfr)){
                    println("Bravo tu as trouvé!");
                    victoires = victoires + 1;
                    println("Tu as " + victoires + " " + "points");
                    
                }
                else {
                    vies = vies - 1;
                    println("C'est erroné. Il te reste " + vies + " " + "vies");
                    
                }
            }
        }
        else {
            while (vies>0 && !(victoires==5)){
                motactuel = words[(int)(random()*length(words,1))];
                println("Trouve la traduction en anglais de " + motactuel.motfr);
                reponse = readString();
                if (equals(reponse, motactuel.moten)){
                    println("Bravo tu as trouvé!");
                    victoires = victoires + 1;
                    println("Tu as " + victoires + "points");
                }
                else {
                    vies = vies - 1;
                    println("C'est erroné. Il te reste" + vies + " " + "vies");

                }
            }
        }
        if (victoires==5){
            println("Bravo tu as gagné le jeu!");
            gagné = true;
        }
        if (vies==0){
            println("Dommage, tu as perdu.");
            gagné=false;
        }

        return gagné;
    }

    boolean choixlangue(){
        return random() < 0.5;
    }

    
    void loadWords(Mot[] words, CSVFile voc){
        for(int y = 0; y < length(words, 1); y++){
            words[y] = newMot(getCell(voc, y, 1), getCell(voc, y, 0));
        }
    }

    Mot newMot(String motFr, String motEn){
        Mot mot = new Mot();
        mot.motfr = motFr;
        mot.moten = motEn;
        return mot;
    }


    void algorithm(){
        CSVFile voc = loadCSV("fichiers/voc.csv");
        Mot[] allWords = new Mot[rowCount(voc)];
        loadWords(allWords, voc);
        Utilisateur user = new Utilisateur();
        println("Bonjour! Quel est ton nom?");
        user.prenom=readString();
        println("Dans quel niveau d'enseignement es-tu?");
        println("1-CE2");
        println("2-CM1");
        println("3-CM2");
        user.niveau=readInt();
        Traduction(allWords,user);
        
       
    }
}
