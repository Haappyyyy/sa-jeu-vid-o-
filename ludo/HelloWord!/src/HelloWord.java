import extensions.File;
import extensions.CSVFile;

class HelloWord extends Program{

    final String NOM_REPERTOIRE = "../ressources";
    String[] fichiers = getAllFilesFromDirectory(NOM_REPERTOIRE);

    boolean possèdeSession(){
        String[] Session = initialiseStringTable("Session");
        return choixSession(Session);
    }

    boolean choixSession(String[] Identifieur){
        affichage(Identifieur);
        println();
        print("Entrez votre choix : ");
        String reponse = readString();
        if(length(reponse)!=1){
            return choixSession(Identifieur);
        }
        else if(equals(reponse, "1")){
            return true;
        }
        else if(equals(reponse, "2")){
            return false;
        }
        else{
            return choixSession(Identifieur);
        }
    }

    int cptLig(File texte){
        int i = 0;
        while(ready(texte)){
            readLine(texte);
            i++;
        }
        return i;
    }

    String[] initialiseStringTable(String fichier){
        File cpt = newFile(NOM_REPERTOIRE +"/"+ fichier +".txt");
        int i = cptLig(cpt);
        File texte = newFile(NOM_REPERTOIRE +"/"+ fichier +".txt");
        String[] GUI = new String[i];
        int j = 0;
        while(j<i){
            if (ready(texte)) {
                GUI[j] = readLine(texte);
                j++;
            }
        }
        return GUI;
    }

    String[] recupererTableSimple(String[][] idList, int ligne){
        String[] identifiants = new String[length(idList, 2)];
        println(length(idList, 2));
        for(int i=0; i<length(identifiants); i++){
            identifiants[i] = idList[ligne][i];
        }
        return identifiants;
    }

    void affichage(String[] Identifieur){
        clear();
        for(int i=0; i<length(Identifieur); i++){
            println(Identifieur[i]);
        }                                         
    }

    void clear(){
        for(int i=0; i<45; i++){
            println();
        }
    }

    boolean estUnChiffreValide(String chaine){
        if(length(chaine)==0){
            return false;
        }
        else if(charAt(chaine, 0)>'0' && charAt(chaine, 0)<'7'){
            return true;
        }
        else{
            return false;
        }
    }

    int choixMenu(){
        clear();
        String[] Menu = initialiseStringTable("Menu");
        affichage(Menu);
        print("Entre le chiffre du menu que tu souhaites ouvrir : ");
        String reponse = readString();
        while(!estUnChiffreValide(reponse)){
            clear();
            affichage(Menu);
            print("Saisi incorecte, entre à nouveau ton chiffre : ");
            reponse = readString();
        }
        return charAt(reponse, 0)-'0';
    }

    String[] recupererIdentifiants(){
        String[] GUIOriginal = initialiseStringTable("ConnectionSession");
        String[] GUI = initialiseStringTable("ConnectionSession");
        String prenom = "";
        String nom = "";
        String classe = "";
        String reponse;
        boolean identifiantsCorrects = false;
        while(!identifiantsCorrects){
            do{
                affichage(GUI);
                println();
                print("Entre ton prénom : ");
                prenom = readString();
            }while(!NomCorrecte(prenom));
            metAJourGUI(GUIOriginal, GUI ,prenom, 0); //0=zone de prénom
            do{
                affichage(GUI);
                println();
                print("Entre ton nom : ");
                nom = readString();
            }while(!NomCorrecte(nom));
            metAJourGUI(GUIOriginal, GUI, nom, 1); //1=zone de nom
            do{
                affichage(GUI);
                println();
                print("Entre ta classe : ");
                classe = readString();
            }while(!classeCorrecte(classe));
            metAJourGUI(GUIOriginal, GUI, classe, 2); //2=zone de classe
            do{
                affichage(GUI);
                println();
                print("Es-tu sûr que tes identifiants sont correctes ? (oui/non) : ");
                reponse = readString();
            }while(!confirmeInscription(reponse));
            if(equals(reponse, "oui")){
                identifiantsCorrects = true;
            }
        }
        String[] identifiants = new String[]{"0",prenom, nom, classe, "5", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        
        return identifiants;
    }

    boolean NomCorrecte(String nom){
        boolean res=true;
        int i=0;
        while(res && i<length(nom)){
            if(!((charAt(nom, i)>='A' && charAt(nom, i)<='Z') || (charAt(nom, i)>='a' && charAt(nom, i)<='z'))){
                res=false;
            }
            i++;
        }
        return res;
    }

    void metAJourGUI(String[] GUIOriginal, String[] GUI, String chaine, int i){
        if(i==0){
            String ligne = GUIOriginal[1];
            String debut = debutNouvelleSession(ligne);
            String fin = finNouvelleSession(ligne, length(debut)-1);
            String espaces = substring(ligne, length(debut)-1, (length(ligne)-length(fin)));
            GUI[1] = debut + completerVide(espaces, chaine, 0) + fin;
        }
        else if(i==1){
            String ligne = GUIOriginal[3];
            String debut = debutNouvelleSession(ligne);
            String fin = finNouvelleSession(ligne, length(debut)-1);
            String espaces = substring(ligne, length(debut)-1, (length(ligne)-length(fin)));
            GUI[3] = debut + completerVide(espaces, chaine, 0) + fin;
        }
        else if(i==2){
            String ligne = GUIOriginal[5];
            String debut = debutNouvelleSession(ligne);
            String fin = finNouvelleSession(ligne, length(debut)-1);
            String espaces = substring(ligne, length(debut)-1, (length(ligne)-length(fin)));
            GUI[5] = debut + completerVide(espaces, chaine, 0) + fin;
        }
    }

    String debutNouvelleSession(String chaine){
        int i = 0;
        int nbEspace = 0;
        while(nbEspace<4){
            if(charAt(chaine, i)==' '){
                nbEspace++;
            }
            i++;
        }
        return substring(chaine, 0, i);
    }

    String finNouvelleSession(String chaine, int debut){
        boolean autreCaractere = false;
        int i = debut;        
        while(!autreCaractere){
            if(charAt(chaine, i)!=' '){
                autreCaractere = true;
            }
            i++;
        }
        return substring(chaine, i-1, length(chaine));
    }

    String completerVide(String espaces, String chaine, int position){
        if(position==0){// Condition pour position gauche.
            espaces = substring(espaces, length(chaine)-1,length(espaces)-2);
            return (chaine + espaces);
        }
        else if(position==1){// Condition pour position centrale
            //println(length(espaces)-length(chaine));
            //println(length(espaces));
            //println(length(chaine));
            String espacesGauche = substring(espaces, 0, ((length(espaces)-length(chaine))/2));
            String espacesDroit = substring(espaces, length(chaine)+((length(espaces)-length(chaine))/2), length(espaces));
            //println("I"+ espacesGauche +"I");
            //println("I"+ espacesDroit +"I");
            //println(length(chaine)+((length(espaces)-length(chaine))/2));
            return (espacesGauche + chaine + espacesDroit);
        }
        else{// Condition pour position droite.
            espaces = substring(espaces, 0, (length(espaces)-length(chaine)));
            return (espaces + chaine);
        }
        
    }

    boolean classeCorrecte(String classe){
        if(equals(classe, "CE2") || equals(classe, "CM1") || equals(classe, "CM2")){
            return true;
        }
        else{
            return false;
        }
    }

    boolean confirmeInscription(String reponse){
        if(equals(reponse, "oui") || equals(reponse, "non")){
            return true;
        }
        else{
            return false;
        }
    }

        /* identifiantExiste permet de vérifier l'intégralité des identifiants enregistrés pour éviter les doublons. */

    boolean identifiantExiste(String[][] idList, String[] id){
        boolean trouver = false;
        int i = 0;
        while(!trouver && i<length(idList, 1)){         // Boucle while qui redémare tant qu'elle n'est pas arrivée à la fin de la liste ou qu'elle n'a pas trouvée de doublon.
            if(equals(idList[i][1], id[1])){            // Première condition qui vérifie si un prénom déjà enregistré est égal à celui du nouvel identifiant.
                if(equals(idList[i][2], id[2])){        // Deuxième condition qui vérifie si un nom déjà enregistré est égal à celui du nouvel identifiant en plus du prénom.
                    if(equals(idList[i][3], id[3])){    // Troisième condition qui vérifie, dans le cas où le nom et prénom sont égaux à ceux du nouvel identifiant, si ils sont de la même classe.
                        trouver = true;
                    }
                }
            }
            i++;
        }
        return trouver;
    }

         /* initialiserSauvegardes permet de convertir un type CSVFile en une table de String en 2 dimensions. */

    void initialiserSauvegardes(CSVFile listeIdentifiants, String[][] idList){
        for(int i=0; i<length(idList, 2); i++){//                  \
            for(int j=0; j<length(idList, 1); j++){//               | Deux boucles for pour parcourir chaque "Cell" de l'entièreté du fichier CSV
                idList[j][i] = getCell(listeIdentifiants, j, i);//  | et les copies dans le même ordre dans la table deux dimensions.
            }//                                                     |
        }//                                                        /
    }

        /* insererNouveauxIdentifiants permet de rajouter un nouvel utilisateur aux sauvegardes du jeu. */

    String[][] insererNouveauxIdentifiants(String[][] idList, String[] id){
        idList = ajouterLigne(idList); // On agrandit la table pour qu'elle puisse acceuillir un type utilisateur supplémentaire.
        for(int i=0; i<length(id); i++){//                                  \
            idList[length(idList, 1)-1][i] = id[i];//                        | Une boucle for pour incorporer le nouveau type utilisateur passé en paramètre aux sauvegardes
            saveCSV(idList, NOM_REPERTOIRE +"/ComptesEnregistrés.csv");//    |
        }//                                                                 /
        idList[length(idList, 1)-1][0] = intToString(length(idList, 1)-1);// On Crée et inére un nouvel Id.
        saveCSV(idList, NOM_REPERTOIRE +"/ComptesEnregistrés.csv");// On sauvegarde.
        return idList;
    }

        /* ajouterLigne permet d'agrandir une table d'une ligne. Utile pour sauvegarder un nouvel utilisateur. */

    String[][] ajouterLigne(String[][] idList){
        String[][] nouvelIdList = new String[length(idList,1)+1][length(idList, 2)]; // On créer une nouvelle table d'une ligne supplémentaire.
        for(int i=0; i<length(idList, 1); i++){//      \
            for(int j=0; j<length(idList, 2); j++){//   |  
                println(idList[i][j]);//                | Deux boucles for pour remplir la nouvelle table 2 dimensions depuis l'ancienne.
                nouvelIdList[i][j] = idList[i][j];//    |
            }//                                        /
        }
        return nouvelIdList; // On retourne la nouvelle table.
    }

    Utilisateur[] initialiserListeUtilisateur(String[][] idList){  
        Utilisateur[] listeDesUtilisateurs = new Utilisateur[length(idList, 1)-1];
        if((length(idList, 1)-1)>0){
            for(int i=1; i<length(idList, 1); i++){
                println("idlist, i "+ i);
                listeDesUtilisateurs[i-1] = initialiserUtilisateur(recupererTableSimple(idList, i));
            }
        }
        return listeDesUtilisateurs;
    }

    Utilisateur initialiserUtilisateur(String[] identifiants){
        Utilisateur user = new Utilisateur();
        user.id = stringEnInt(identifiants[0]);
        user.prenom = identifiants[1];
        user.nom = identifiants[2];
        user.classe = identifiants[3];
        user.premierjeu_vies = stringEnInt(identifiants[4]);
        user.premierjeu_victoires = stringEnInt(identifiants[5]);
        user.premierjeu_parties_gagner = stringEnInt(identifiants[6]);
        user.premierjeu_parties_perdu = stringEnInt(identifiants[7]);
        user.premierjeu_mots_traduits = stringEnInt(identifiants[8]);
        user.JCJ_vies = stringEnInt(identifiants[9]);
        user.JCJ_score = stringEnInt(identifiants[10]);
        user.JCJ_manche_remporter = stringEnInt(identifiants[11]);
        user.JCJ_manche_perdu = stringEnInt(identifiants[12]);
        user.JCJ_mots_traduits = stringEnInt(identifiants[13]);

        return user;
    }

    int trouverNumLigneId(String[][] idList, String[] id){
        boolean trouver = false;
        int i = 0;
        while(!trouver && i<length(idList, 1)){         // Boucle while qui redémare tant qu'elle n'est pas arrivée à la fin de la liste ou qu'elle n'a pas trouvée de doublon.
            println(idList[i][1]);
            if(equals(idList[i][1], id[1])){            // Première condition qui vérifie si un prénom déjà enregistré est égal à celui du nouvel identifiant.
                println(idList[i][2]);
                if(equals(idList[i][2], id[2])){        // Deuxième condition qui vérifie si un nom déjà enregistré est égal à celui du nouvel identifiant en plus du prénom.
                    println(idList[i][3]);
                    if(equals(idList[i][3], id[3])){    // Troisième condition qui vérifie, dans le cas où le nom et prénom sont égaux à ceux du nouvel identifiant, si ils sont de la même classe.                        
                        trouver = true;
                    }
                }
            }
            if(!trouver){
                i++;
            }
        }
        println(i);
        return i;
    }

    Utilisateur lancerJeu1(Utilisateur user){
        CSVFile voc = loadCSV("../ressources/voc.csv");
        Mot[] allWords = new Mot[rowCount(voc)];
        loadWords(allWords, voc);
        user = Traduction(allWords,user);
        return user;
    }

    Mot newMot(String motFr, String motEn){
        Mot mot = new Mot();
        mot.motfr = motFr;
        mot.moten = motEn;
        return mot;
    }

    void loadWords(Mot[] words, CSVFile voc){
        for(int y = 0; y < length(words, 1); y++){
            words[y] = newMot(getCell(voc, y, 1), getCell(voc, y, 0));
        }
    }

    boolean choixlangue(){
        return random() < 0.5;
    }

    Utilisateur Traduction(Mot[] words,Utilisateur user){
        boolean recommencer=true;
        boolean gagné= false;
        while(recommencer){
            Mot motactuel;
            String reponse="";
            int question=0;
            boolean quitter=false;
            if (!choixlangue()){
            while (user.premierjeu_vies>0 && !(user.premierjeu_victoires==5) && !quitter){
                motactuel = words[(int)(random()*length(words,1))];
                println();
                print(user.premierjeu_vies + " ");
                print(user.premierjeu_victoires + " ");
                println("Trouve la traduction en français de " + "\"" + motactuel.moten + "\"" );
                println();
                println("Pour quitter le jeu appuie sur 2");
                reponse = readString();
                if (equals(reponse, motactuel.motfr)){
                    println();
                    println("Bravo tu as trouvé!");
                    user.premierjeu_victoires = user.premierjeu_victoires + 1;
                    println("Tu as " + user.premierjeu_victoires + " " + "points");
                    println();
                    
                }
                else if(equals(reponse,"2")){;
                    println("A une prochaine fois!");
                    quitter=true; 
                }
                else {
                    user.premierjeu_vies = user.premierjeu_vies - 1;
                    println();
                    println("C'est erroné. Il te reste " + user.premierjeu_vies + " " + "vies");
                    println();
                    
                }
            }
        }
        else {
            while (user.premierjeu_vies>0 && !(user.premierjeu_victoires==5)&& !quitter){
                motactuel = words[(int)(random()*length(words,1))];
                println();
                print(user.premierjeu_vies + " ");
                print(user.premierjeu_victoires + " ");
                println("Trouve la traduction en anglais de " + motactuel.motfr);
                println();
                println("Pour quitter le jeu appuie sur 2");
                reponse = readString();
                if (equals(reponse, motactuel.moten)){
                    println();
                    println("Bravo tu as trouvé!");
                    user.premierjeu_victoires = user.premierjeu_victoires + 1;
                    println("Tu as " + user.premierjeu_victoires + " " + "points");
                    println();
                }
                else if(equals(reponse,"2")){
                    println("A une prochaine fois !");
                    quitter=true;
                }
                else {
                    user.premierjeu_vies = user.premierjeu_vies - 1;
                    println("C'est erroné. Il te reste" + " " + user.premierjeu_vies + " " + "vies");
                    println();
                }
            }
        }
        if (user.premierjeu_victoires==5){
            println("Bravo tu as gagné le jeu!");
            gagné = true;
        }
        if (user.premierjeu_vies==0){
            println("Dommage, tu as perdu.");
            gagné=false;
        }

        println();
        println("Veux-tu recommencer la partie?");
        println("1. Oui");
        println("2. Non");
        question=readInt();
        if(question==1){
            recommencer=true;
            user.premierjeu_victoires=0;
            user.premierjeu_vies=5;
        }
        else if(question==2){
            recommencer=false;
        }
        
    }
    return user;
}

String intToString(int nbr){
    String res = "";
    res+=nbr;
    return res;
}

int stringEnInt(String nbr){
    return (((int) charAt(nbr, 0))-((int) '0'));
}
        
    boolean Perdu(Utilisateur user){
        boolean p=false;
        if(user.premierjeu_vies==0){
            p = true;
        }
        return p;
    }

    boolean Gagné(Utilisateur user){
        boolean g=false;
        if(user.premierjeu_victoires==5){
            g=true;
        }
        return g;
    }

    void lancerJCJ(Utilisateur premierJoueur, Utilisateur deuxiemeJoueur){

        premierJoueur.JCJ_vies = 5;
        deuxiemeJoueur.JCJ_vies = 5;

        premierJoueur.JCJ_score = 10;
        deuxiemeJoueur.JCJ_score = 10;

        String[] GUI = initialiseStringTable("GUIJoueurContreJoueur");
        affichage(GUI);
        GUI = maj_GUI_JCJ(GUI, 6, premierJoueur.prenom, deuxiemeJoueur.prenom);
        GUI = maj_GUI_JCJ(GUI, 8, intToString(premierJoueur.JCJ_vies), intToString(deuxiemeJoueur.JCJ_vies));
        GUI = maj_GUI_JCJ(GUI, 9, intToString(premierJoueur.JCJ_score), intToString(deuxiemeJoueur.JCJ_score));
        affichage(GUI);
    }

        /* maj_GUI_JCJ permet de mettre a jour le GUI pour le jeu JCJ et ainsi afficher les prenoms, vies, victoires et mots à traduire */

    String[] maj_GUI_JCJ(String[] GUI, int numLigne, String premierJoueur, String deuxiemeJoueur){
        if(numLigne==6){
            String ligne = GUI[numLigne];                                                               //On récupère la ligne de prénom.
            int[] positionReperes = chercheEmplacementReperes(ligne, ':');                                            //On récupère les positions des petits points.
            ligne = integrerPrenomsJCJ(ligne, positionReperes, premierJoueur, deuxiemeJoueur);
            GUI[numLigne] = ligne;
        }
        else if(numLigne==8){
            String ligne = GUI[numLigne];                                                               //On récupère la ligne de prénom.
            int[] positionReperes = chercheEmplacementReperes(ligne, ':');                                            //On récupère les positions des petits points.
            ligne = integrerViesJCJ(ligne, positionReperes, premierJoueur, deuxiemeJoueur);
            GUI[numLigne] = ligne;
        }
        else if(numLigne==9){
            String ligne = GUI[numLigne];                                                               //On récupère la ligne de prénom.
            int[] positionReperes = chercheEmplacementReperes(ligne, ':');                                            //On récupère les positions des petits points.
            ligne = integrerScoreJCJ(ligne, positionReperes, premierJoueur, deuxiemeJoueur);
            GUI[numLigne] = ligne;
        }
        else{
            println(" /!\\ ERREUR maj_GUI_JCJ /!\\ ");
        }
        return GUI;                                                                                 
    }

        /* chercherEmplacementPrenom permet de trouver l'emplacement des petits points dans la ligne en paramètre. Utile pour les interfaces JCJ. */

    int[] chercheEmplacementReperes(String ligne, char symbol){
        int[] listePosition = new int[2];
        int nbrTrouver = 0;
        int i = 0;
        while(nbrTrouver<2 && i<length(ligne)){
            if(charAt(ligne, i)==symbol){
                listePosition[nbrTrouver] = i;
                nbrTrouver++;
            }
            i++;
        }
        return listePosition;
    }

        /* integrerPrenomsJCJ permet d'insérer les prénoms des joueurs dans la ligne du GUI JCJ par une succéssion de substring */

    String integrerPrenomsJCJ(String ligne, int[] positions, String premierPrenom, String deuxiemePrenom){
        String premierEspace = substring(ligne, positions[0]-20, positions[0]);     // On récupère la zone de prénom du premier Joueur.
        String deuxiemeEspace = substring(ligne, positions[1]-20, positions[1]);    // On récupère la zone de prénom du deuxième Joueur.
        String partieGauche = substring(ligne, 0, positions[0]-20);                 // On récupère la partie à gauche de la ligne.
        String partieCentrale = substring(ligne, positions[0]+1, positions[1]-20);  // On récupère la partie entre les deux zones de prénom.
        String partieDroite = substring(ligne, positions[1]+1, length(ligne));      // On récupère la partie à droite de la ligne.
        return (partieGauche + completerVide(premierEspace, premierPrenom+":" ,1)+ partieCentrale +" "+ completerVide(deuxiemeEspace, deuxiemePrenom+":" ,1) +" "+ partieDroite);
    }

    String integrerViesJCJ(String ligne, int[] positions, String premierVie, String deuxiemeVie){
        String partieGauche = substring(ligne, 0, positions[0]+2);
        String partieCentrale = substring(ligne, positions[0]+3, positions[1]+2);
        String partieDroite = substring(ligne, positions[1]+3, length(ligne));
        return (partieGauche + premierVie + partieCentrale + deuxiemeVie + partieDroite);
    }

    String integrerScoreJCJ(String ligne, int[] positions, String premierScore, String deuxiemeScore){
        String partieGauche = substring(ligne, 0, positions[0]+2);
        String partieCentrale = substring(ligne, positions[0]+3, positions[1]+2);
        String partieDroite = substring(ligne, positions[1]+3, length(ligne));
        return (partieGauche + premierScore + partieCentrale + deuxiemeScore + partieDroite);
    }

        /* connectionUtilisateur permet de retourner les identifiants de l'utilisateur sois en se connectant ou en créant un nouveau compte */

    Utilisateur connectionUtilisateur(Utilisateur[] listeDesUtilisateurs, String[][] idList){
        String[] GUI = initialiserMenuConnection(listeDesUtilisateurs);// On initialise me menu dans une table de String. 
        affichage(GUI);
        println();
        print("Entre le numéro de ton compte : ");
        int choix = readInt();
        Utilisateur user = new Utilisateur();
        if(choix==0){
            boolean identifier = false;
            do{
                String[] identifiants = recupererIdentifiants(); // On récupère les nouveaux identifiants
                if(!identifiantExiste(idList, identifiants)){ // Si les identifiants n'existent pas déjà, on les rajoutent aux sauvegardes
                    user = initialiserUtilisateur(identifiants);
                    identifier = true;
                    idList = insererNouveauxIdentifiants(idList, identifiants); // On rejoute à la sauvegarde les nouveaux identifiants
                    println("Identifiants enreistrés avec succès !");
                    user = initialiserUtilisateur(identifiants);

                }
            }while(!identifier);
        }
        else{
            user = listeDesUtilisateurs[choix-1];
        }
        return user;
    }

    String[] initialiserMenuConnection(Utilisateur[] listeDesUtilisateurs){
        String[] hautMenu = initialiseStringTable("hautMenuConnectionSession");
        String[] basMenu = initialiseStringTable("menuConnectionSession");
        String[] GUI = creerMenuConnection(hautMenu, basMenu, listeDesUtilisateurs);
        return GUI;
    }

    String[] creerMenuConnection(String[] hautMenu, String[] basMenu, Utilisateur[] listeDesUtilisateurs){
        basMenu = insererIdentifiantsBasMenu(basMenu, listeDesUtilisateurs);
        String[] GUI = new String[length(hautMenu)+length(basMenu)];
        int index = 0;
        for(int i=0; i<length(hautMenu); i++){
            GUI[index]=hautMenu[i];
            index++;
        }
        for(int i=0; i<length(basMenu); i++){
            GUI[index]=basMenu[i];
            index++;
        }
        return GUI;
    }

    String[] insererIdentifiantsBasMenu(String[] basMenu, Utilisateur[] listeDesUtilisateurs){
        int nombreIdentifiants = length(listeDesUtilisateurs);
        if(nombreIdentifiants==0){
            String ligne = basMenu[6];
            int[] positions = chercheEmplacementReperes(ligne, '║');
            String partieGauche = substring(ligne, 0, positions[0]);
            String espaces = substring(ligne, positions[0]+1, positions[1]-1);
            String partieDroite = substring(ligne, positions[1], length(ligne));
            espaces = completerVide(espaces, "Aucune session n'a encore été créer.", 1);
            basMenu[6] = (partieGauche + espaces + partieDroite);
        }
        else if(nombreIdentifiants>=1 && nombreIdentifiants<14){
            for(int i=0; i<nombreIdentifiants; i++){
                String ligne = basMenu[i+2];
                println(i);
                println(nombreIdentifiants);
                basMenu[i] = insererIdentifiantsLigneMenu(ligne, listeDesUtilisateurs[i]);
            }
        }
        return basMenu;
    }

    String insererIdentifiantsLigneMenu(String ligne, Utilisateur user){
        int limite = chercheNouveauCharactere(ligne);
        String finLigne = substring(ligne, limite, length(ligne));
        String Id = completerVide("  ", intToString(user.id), 2);
        String prenom = completerVide("                     ", user.prenom, 0);
        String nom = completerVide("                     ", user.nom, 0);
        String classe = completerVide("       ", user.classe, 0);
        return ("║ "+ Id +" "+ prenom +" "+ nom +" "+ classe +" "+ finLigne);
    }

    int chercheNouveauCharactere(String ligne){
        int res = 1;
        boolean trouver = false;
        while(!trouver && res<length(ligne)){
            if(charAt(ligne, res)!=' '){
                trouver = true;
            }
            res++;
        }
        return res-1;
    }

    void algorithm(){
        CSVFile listeIdentifiants = loadCSV(NOM_REPERTOIRE +"/ComptesEnregistrés.csv");                     // On récupère les anciennes sauvegardes.
        String[][] idList = new String[rowCount(listeIdentifiants)][columnCount(listeIdentifiants)];        // On crée une table double de String à la taille du fichier CSV de sauvegarde.
        initialiserSauvegardes(listeIdentifiants, idList);                                                  // On rentre les données du CSVFile dans la table double.
        boolean quitter = false;
        boolean estConnecter = false;
        boolean identifier = false;
        Utilisateur[] listeDesUtilisateurs = initialiserListeUtilisateur(idList);
        Utilisateur user = new Utilisateur();
        
        while(!quitter){ // Tant que l'on n'a pas demandé de quitter.
            String[] identifiants = new String[]{"0","Ludovic", "Bernard", "CM2", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
            while(!estConnecter){
                user = connectionUtilisateur(listeDesUtilisateurs, idList);
                estConnecter=true;
            }
            while(estConnecter && !quitter){ // Tant que l'on est connecté
                int choix = choixMenu();
                if(choix==1){
                    user = lancerJeu1(user); // jeu numéro 1
                }
                else if(choix==2){
                }
                else if(choix==3){
                    String[] identifiantsDeuxiemeJoueur = recupererIdentifiants();
                    Utilisateur deuxiemeUser = initialiserUtilisateur(identifiantsDeuxiemeJoueur);
                    lancerJCJ(user, deuxiemeUser);
                    //for(int i=0; i<length(identifiants); i++){          
                    //    idList[stringEnInt(identifiants[0])][i] = identifiants[i];
                    //}
                    //saveCSV(idList, NOM_REPERTOIRE +"/ComptesEnregistrés.csv");
                }
                else if(choix==4){
                }
                else if(choix==5){
                    estConnecter = false; // déconnexion
                }
                else if(choix==6){
                    quitter=true;    // quitter

                    clear();
                }
            }
        }
        println();
    }
}
