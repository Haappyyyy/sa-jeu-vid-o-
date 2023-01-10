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

        int choixMenu(Utilisateur user){
            clear();
            String[] Menu = initialiseStringTable("Menu");
            Menu[1] = integrerIdentifiantsMenu(Menu[1], user);
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

        String integrerIdentifiantsMenu(String ligne, Utilisateur user){
            int[] positions = chercheEmplacementReperes(ligne, '║', 2);
            String debut = substring(ligne, 0, positions[0]+1);
            String espaces = substring(ligne, positions[0]+2, positions[1]);
            String fin = substring(ligne, positions[1], positions[1]+1);
            ligne = completerVide(espaces, user.id +"  "+ user.prenom +"  "+ user.nom +"  "+ user.classe, 0);
            return (debut +" "+ ligne + fin);
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
            if(i==0){ // Condition pour positioner le prénom.
                String ligne = GUIOriginal[1];
                String debut = debutNouvelleSession(ligne);
                String fin = finNouvelleSession(ligne, length(debut)-1);
                String espaces = substring(ligne, length(debut), (length(ligne)-length(fin)));
                GUI[1] = debut + completerVide(espaces, chaine, 0) + fin;
            }
            else if(i==1){// Condition pour positioner le nom.
                String ligne = GUIOriginal[3];
                String debut = debutNouvelleSession(ligne);
                String fin = finNouvelleSession(ligne, length(debut)-1);
                String espaces = substring(ligne, length(debut), (length(ligne)-length(fin)));
                GUI[3] = debut + completerVide(espaces, chaine, 0) + fin;
            }
            else if(i==2){// Condition pour positioner la classe.
                String ligne = GUIOriginal[5];
                String debut = debutNouvelleSession(ligne);
                String fin = finNouvelleSession(ligne, length(debut)-1);
                String espaces = substring(ligne, length(debut), (length(ligne)-length(fin)));
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
                espaces = substring(espaces, length(chaine)-1,length(espaces)-1);
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
                idList[length(idList, 1)-1][i] = id[i];//                        | Une boucle for pour incorporer le nouveau type utilisateur passé en paramètre aux sauvegardes//    |
            }//                                                                 /
            //idList[length(idList, 1)-1][0] = intToString(length(idList, 1));// On Crée et insére un nouvel Id.
            saveCSV(idList, NOM_REPERTOIRE +"/ComptesEnregistrés.csv");// On sauvegarde.
            return idList;
        }

        String[][] sauvegarderUtilisateur(Utilisateur user, String[][] idList){
            if(user.id==length(idList, 1)){
                idList = ajouterLigne(idList);
                idList[user.id][0] = intToString(user.id);
                idList[user.id][1] = user.prenom;
                idList[user.id][2] = user.nom;
                idList[user.id][3] = user.classe;
            }
            idList[user.id][4] = intToString(user.premierjeu_vies);
            idList[user.id][5] = intToString(user.premierjeu_score);
            idList[user.id][6] = intToString(user.premierjeu_parties_gagner);
            idList[user.id][7] = intToString(user.premierjeu_parties_perdu);
            idList[user.id][8] = intToString(user.premierjeu_mots_traduits);
            idList[user.id][9] = intToString(user.JCJ_vies);
            idList[user.id][10] = intToString(user.JCJ_score);
            idList[user.id][11] = intToString(user.JCJ_manche_remporter);
            idList[user.id][12] = intToString(user.JCJ_manche_perdu);
            idList[user.id][13] = intToString(user.JCJ_mots_traduits);
            saveCSV(idList, NOM_REPERTOIRE +"/ComptesEnregistrés.csv");
            return idList;
        }

            /* ajouterLigne permet d'agrandir une table d'une ligne. Utile pour sauvegarder un nouvel utilisateur. */

        String[][] ajouterLigne(String[][] idList){
            String[][] nouvelIdList = new String[length(idList,1)+1][length(idList, 2)]; // On créer une nouvelle table d'une ligne supplémentaire.
            for(int i=0; i<length(idList, 1); i++){//      \
                for(int j=0; j<length(idList, 2); j++){//   |  
                    //println(idList[i][j]);//                | Deux boucles for pour remplir la nouvelle table 2 dimensions depuis l'ancienne.
                    nouvelIdList[i][j] = idList[i][j];//    |
                }//                                        /
            }
            idList = nouvelIdList;
            return idList; // On retourne la nouvelle table.
        }

        Utilisateur[] initialiserListeUtilisateur(String[][] idList){  
            Utilisateur[] listeDesUtilisateurs = new Utilisateur[length(idList, 1)-1];
            if((length(idList, 1)-1)>0){
                for(int i=1; i<length(idList, 1); i++){
                    //println("idlist, i "+ i);
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
            user.premierjeu_score = stringEnInt(identifiants[5]);
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
                //println(idList[i][1]);
                if(equals(idList[i][1], id[1])){            // Première condition qui vérifie si un prénom déjà enregistré est égal à celui du nouvel identifiant.
                    //println(idList[i][2]);
                    if(equals(idList[i][2], id[2])){        // Deuxième condition qui vérifie si un nom déjà enregistré est égal à celui du nouvel identifiant en plus du prénom.
                        //println(idList[i][3]);
                        if(equals(idList[i][3], id[3])){    // Troisième condition qui vérifie, dans le cas où le nom et prénom sont égaux à ceux du nouvel identifiant, si ils sont de la même classe.                        
                            trouver = true;
                        }
                    }
                }
                if(!trouver){
                    i++;
                }
            }
            //println(i);
            return i;
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////Debut jeu traduction///////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Utilisateur lancerJeu1(Utilisateur user){
            Mot[] allWords = loadWords();
            user = Traduction(allWords,user);
            return user;
        }

        Mot newMot(String motFr, String motEn){
            Mot mot = new Mot();
            mot.motfr = motFr;
            mot.moten = motEn;
            return mot;
        }

        Mot[] loadWords(){
            CSVFile voc = loadCSV("../ressources/voc.csv");
            Mot[] allWords = new Mot[rowCount(voc)];
            for(int y = 0; y < length(allWords, 1); y++){
                allWords[y] = newMot(getCell(voc, y, 1), getCell(voc, y, 0));
            }
            return allWords;
        }

        String choixlangue(){
            boolean alea = random() < 0.5;
            if(alea){
                return "français";
            }
            else{
                return "anglais";
            }
        }

        boolean bonresultat(String reponse, Mot motactuel, String langue){
            boolean gagné=false;
            if (equals(langue, "français")){
                
                if (equals(reponse,motactuel.motfr)){
                    gagné=true;
                    }
                
                else {
                    gagné=false;
                }
            }
            
            else {
                
                if (equals(reponse,motactuel.moten)){
                    gagné=true;
                    }
                
                else {
                    gagné=false;
                }
            }
            return gagné;
        }


        Utilisateur Traduction(Mot[] words,Utilisateur user){
            boolean recommencer=true;
            boolean gagné= false;
            while(recommencer){
                String[] GUI = initialiseStringTable("menuJeuTraduction");
                Mot motactuel;
                String reponse="";
                int question=0;
                boolean quitter=false;
                String langue = choixlangue();
                while (user.premierjeu_vies>0 && !(user.premierjeu_score==5) && !quitter){
                    GUI = insererInformationsIdentifiantsJeuTraduction(GUI, user.prenom, user.nom, user.classe, intToString(user.premierjeu_vies), intToString(user.premierjeu_score));
                    motactuel = words[(int)(random()*length(words,1))];
                    GUI = MAJ_AvanceJeuTraduction(GUI, langue, retournerMotATraduire(motactuel, langue));
                    affichage(GUI);
                    println();
                    print("Entre ta réponse : ");
                    reponse=readString();
                    if (bonresultat(reponse,motactuel,langue)){
                        user.premierjeu_score++;
                    }
                    else if(equals(reponse,"2")){;
                        quitter=true; 
                    }
                    else if(!bonresultat(reponse,motactuel,langue)){
                        user.premierjeu_vies--;
                    }
                    clear();
                    
                    
                }
        
            if (user.premierjeu_score==5){
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
                user.premierjeu_score=0;
                user.premierjeu_vies=5;
            }
            else if(question==2){
                recommencer=false;
            }
            
        }
        return user;
    }

    String[] insererInformationsIdentifiantsJeuTraduction(String[] GUI, String prenom, String nom, String classe, String vies, String score){
        GUI[1] = integrerPrenomJeuTraduction(GUI[1], prenom);
        GUI[2] = integrerNomJeuTraduction(GUI[2], nom);
        GUI[3] = integrerClasseJeuTraduction(GUI[3], classe);
        GUI[2] = integrerViesJeuTraduction(GUI[2], vies);
        GUI[3] = integrerScoreJeuTraduction(GUI[3], score);
        return GUI;
    }

    String integrerPrenomJeuTraduction(String ligne, String prenom){
        String partieGauche = substring(ligne, 0, 2);
        String espace = substring(ligne, 3, 23);
        String partieDroite = substring(ligne, 22, length(ligne));
        return (partieGauche + completerVide(espace, prenom, 0) + partieDroite);
    }

    String integrerNomJeuTraduction(String ligne, String nom){
        String partieGauche = substring(ligne, 0, 2);
        String espace = substring(ligne, 3, 23);
        String partieDroite = substring(ligne, 22, length(ligne));
        return (partieGauche + completerVide(espace, nom, 0) + partieDroite);
    }

    String integrerClasseJeuTraduction(String ligne, String classe){
        String partieGauche = substring(ligne, 0, 2);
        String espace = substring(ligne, 3, 23);
        String partieDroite = substring(ligne, 22, length(ligne));
        return (partieGauche + completerVide(espace, classe, 0) + partieDroite);
    }

    String integrerViesJeuTraduction(String ligne, String vies){
        int position = chercheEmplacementReperes(ligne, ':', 1)[0]+1;
        String partieGauche = substring(ligne, 0, position+1);
        String espace = substring(ligne, position+2, position+3);
        String partieDroite = substring(ligne, position+2, length(ligne));
        return (partieGauche + completerVide(espace, vies, 0) + partieDroite);
    }

    String integrerScoreJeuTraduction(String ligne, String score){
        int position = chercheEmplacementReperes(ligne, ':', 1)[0]+1;
        String partieGauche = substring(ligne, 0, position+1);
        String espace = substring(ligne, position+2, position+5);
        String partieDroite = substring(ligne, position+4, length(ligne));
        return (partieGauche + completerVide(espace, score, 0) + partieDroite);
    }

    String[] MAJ_AvanceJeuTraduction(String[] GUI, String langue, String mot){
        GUI[8] = insererLangueJeuTraduction(GUI[8], langue);
        GUI[11] = insererMotJeuTraduction(GUI[11], mot);
        return GUI;
    }

    String insererLangueJeuTraduction(String ligne, String langue){
        int position = chercheEmplacementReperes(ligne, ':', 1)[0]+1;
        String partieGauche = substring(ligne, 0, position+1);
        String espace = substring(ligne, position+2, position+10);
        String partieDroite = substring(ligne, position+9, length(ligne));
        return (partieGauche + completerVide(espace, langue, 0) + partieDroite);
    }

    String insererMotJeuTraduction(String ligne, String mot){
        int position = chercheEmplacementReperes(ligne, '║', 3)[2];
        String partieGauche = substring(ligne, 0, position+1);
        String espace = "                  ";
        String partieDroite = substring(ligne, position+19, length(ligne));
        return (partieGauche + completerVide(espace, mot, 0) + partieDroite);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////Fin jeu traduction/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    String intToString(int nbr){
        String res = "";
        res+=nbr;
        return res;
    }

    int stringEnInt(String nbr){
        int res = 0;
        for(int i=0; i<length(nbr); i++){
            res+=charEnInt(charAt(nbr ,i))*pow(10, length(nbr)-i-1);
        }
        return res;
    }

    int charEnInt(char chiffre){
        return ((int) chiffre-'0');
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
            if(user.premierjeu_score==5){
                g=true;
            }
            return g;
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////Debut jeu JCJ//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Utilisateur[] lancerJCJ(Utilisateur premierJoueur, Utilisateur deuxiemeJoueur){

            partie.GUI = initialiseStringTable("GUIJoueurContreJoueur");
            partie.GUI = initialiser_GUI_JCJ(partie.GUI, premierJoueur, deuxiemeJoueur);
            partie.GUI = maj_GUI_JCJ(partie.GUI, premierJoueur, deuxiemeJoueur);
            affichage(partie.GUI);
            partieJCJ partie = initialiserPartieJCJ(partie.GUI, premierJoueur, deuxiemeJoueur);
            String langue;
            String reponse;
            String reponseAnalysee;
            String infoAchat;
            Mot[] listeMots = loadWords();
            Mot motactuel;
            boolean tourFini = false;
            while(partie.recommencer){
                partie.premierJoueur.JCJ_score = 0;
                partie.deuxiemeJoueur.JCJ_score = 0;

                partie.premierJoueur.JCJ_vies = 5;
                partie.deuxiemeJoueur.JCJ_vies = 5;
                
                while(premierJoueur.JCJ_vies>0 && deuxiemeJoueur.JCJ_vies>0 && !quitter){
                    
                    partie = lancerTourPremierJoueur(partie, listeMots);
                    
                    if(!partie.quitter){
                        tourFini = false;
                    }
                    motactuel = listeMots[(int)(random()*length(listeMots,1))];
                    while(!tourFini){
                        GUI = MAJ_AvanceJCJ(GUI, motactuel, langue, 2);
                        do{
                            affichage(GUI);
                            println();
                            print("                                                                                                                                   Entre ta réponse : ");
                            reponse = readString();
                        }while(!reponseNonvide(reponse));
                        if(equals(reponse, "0")){// quitter
                            tourFini = true;
                            quitter = true;
                            recommencer = false;
                        }
                        else if(equals(reponse, "1")){// améliorations
                            finiAmelioration = false;
                            infoAchat = " ";
                            while(!finiAmelioration){
                                reponse = "";
                                do{
                                    afficherMenuJCJ(deuxiemeJoueur, "GUI_Ameliorations_JCJ");
                                    println(infoAchat);
                                    print("Entre ton choix : ");
                                    reponse = readString();
                                }while(!reponseNonvide(reponse));
                                if(equals(reponse, "0")){
                                    finiAmelioration = true;
                                }
                                else if(equals(reponse, "1")){
                                    if(deuxiemeJoueur.JCJ_score>=5){
                                        deuxiemeJoueur.JCJ_score-=5;
                                        infoAchat = "Achat effectuer avec succès !";
                                        partie.pointsAccorderDeuxiemeJoueur+=1;
                                    }
                                    else{
                                        infoAchat = "Tu n'as pas assez de point pour l'acheter.";
                                    }
                                }
                                else if(equals(reponse, "2")){
                                    if(deuxiemeJoueur.JCJ_score>=3){
                                        deuxiemeJoueur.JCJ_score-=3;
                                        infoAchat = "Achat effectuer avec succès !";
                                        deuxiemeJoueur.JCJ_vies+=1;
                                    }
                                    else{
                                        infoAchat = "Tu n'as pas assez de point pour l'acheter.";
                                    }
                                }
                                GUI = MAJ_InfoJeuJCJ(GUI, premierJoueur, deuxiemeJoueur);
                            }
                        }
                        else if(equals(reponse, "2")){// malus
                            finiMalus = false;
                            infoAchat = " ";
                            while(!finiMalus){
                                reponse = "";
                                do{
                                    afficherMenuJCJ(deuxiemeJoueur, "GUI_Malus_JCJ");
                                    println(infoAchat);
                                    print("Entre ton choix : ");
                                    reponse = readString();
                                }while(!reponseNonvide(reponse));
                                if(equals(reponse, "0")){
                                    finiMalus = true;
                                }
                                else if(equals(reponse, "1")){
                                    if(deuxiemeJoueur.JCJ_score>=5){
                                        deuxiemeJoueur.JCJ_score-=5;
                                        infoAchat = "Achat effectuer avec succès !";
                                        partie.pointsAccorderPremierJoueur-=1;
                                    }
                                    else{
                                        infoAchat = "Tu n'as pas assez de point pour l'acheter.";
                                    }
                                }
                                else if(equals(reponse, "2")){
                                    if(deuxiemeJoueur.JCJ_score>=3){
                                        deuxiemeJoueur.JCJ_score-=3;
                                        infoAchat = "Achat effectuer avec succès !";
                                        premierJoueur.JCJ_vies-=1;
                                    }
                                    else{
                                        infoAchat = "Tu n'as pas assez de point pour l'acheter.";
                                    }
                                }
                                GUI = MAJ_InfoJeuJCJ(GUI, premierJoueur, deuxiemeJoueur);
                            }
                        }
                        else{
                            if(bonresultat(reponse, motactuel, langue)){// jeu
                                deuxiemeJoueur.JCJ_score+=partie.pointsAccorderDeuxiemeJoueur;
                            }
                            else{
                                deuxiemeJoueur.JCJ_vies--;
                            }
                            GUI = MAJ_InfoJeuJCJ(GUI, premierJoueur, deuxiemeJoueur);
                            tourFini = true;
                        }
                    }
                }
            }
            Utilisateur[] userList = new Utilisateur[]{premierJoueur, deuxiemeJoueur};
            return userList;
        }

        partieJCJ initialiserPartieJCJ(String[] GUI, Utilisateur premierJoueur, Utilisateur deuxiemeJoueur){
            partieJCJ partie = new partieJCJ();
            partie.premierJoueur = premierJoueur;
            partie.deuxiemeJoueur = deuxiemeJoueur;
            partie.GUI = GUI;
            partie.pointsAccorderPremierJoueur = 1;
            partie.pointsAccorderDeuxiemeJoueur = 1;
            return partie;
        }

        partieJCJ lancerTourPremierJoueur(partieJCJ partie, Mot[] listeMots){
            boolean tourFini = false;
            String reponse;
            String langue = choixlangue();
            while(!tourFini){
                partie.GUI = MAJ_AvanceJCJ(GUI, motactuel, langue, 1);
                partie.GUI = MAJ_InfoJeuJCJ(GUI, premierJoueur, deuxiemeJoueur);
                Mot motactuel = listeMots[(int)(random()*length(listeMots,1))];

                reponse = questionnerReponse(partie.GUI);
                if(equals(reponse, "0")){// quitter
                    tourFini = true;
                    partie.quitter = true;
                    partie.recommencer = false;
                }
                else if(equals(reponse, "1")){// améliorations
                    partie = lancerMenuAmelioration(partie, 1);
                    
                }
                else if(equals(reponse, "2")){// malus
                    finiMalus = false;
                    infoAchat = " ";
                    while(!finiMalus){
                        reponse = "";
                        do{
                            afficherMenuJCJ(premierJoueur, "GUI_Malus_JCJ");
                            println(infoAchat);
                            print("Entre ton choix : ");
                            reponse = readString();
                        }while(!reponseNonvide(reponse));
                        if(equals(reponse, "0")){
                            finiMalus = true;
                        }
                        else if(equals(reponse, "1")){
                            if(premierJoueur.JCJ_score>=5){
                                premierJoueur.JCJ_score-=5;
                                infoAchat = "Achat effectuer avec succès !";
                                partie.pointsAccorderDeuxiemeJoueur-=1;
                            }
                            else{
                                infoAchat = "Tu n'as pas assez de point pour l'acheter.";
                            }
                        }
                        else if(equals(reponse, "2")){
                            if(premierJoueur.JCJ_score>=3){
                                premierJoueur.JCJ_score-=3;
                                infoAchat = "Achat effectuer avec succès !";
                                deuxiemeJoueur.JCJ_vies-=1;
                            }
                            else{
                                infoAchat = "Tu n'as pas assez de point pour l'acheter.";
                            }
                        }
                        GUI = MAJ_InfoJeuJCJ(GUI, premierJoueur, deuxiemeJoueur);
                    }
                }
                else{// Jeu
                    if(bonresultat(reponse, motactuel, langue)){
                        premierJoueur.JCJ_score+=partie.pointsAccorderPremierJoueur;
                    }
                    else{
                        premierJoueur.JCJ_vies--;
                    }
                    GUI = MAJ_InfoJeuJCJ(GUI, premierJoueur, deuxiemeJoueur);
                    tourFini = true;
                }
            }
        }

        String questionnerReponse(String[] GUI){
            String reponse;
            do{
                affichage(GUI);
                println();
                print("Entre ta réponse : ");
                reponse = readString();
            }while(!reponseNonvide(reponse));
            return reponse;
        }

        partieJCJ lancerMenuAmelioration(partieJCJ partie, int numJoueur){
            boolean finiAmelioration = false;
            String infoAchat;
            String reponse;
            while(!finiAmelioration){
                String[] menuAmelioration = initialiserMenuJCJ(partie.premierJoueur, "GUI_Amelioration_JCJ");
                reponse = questionnerReponse(menuAmelioration);
                if(equals(reponse, "0")){
                    finiAmelioration = true;
                }
                else if(equals(reponse, "1")){
                    partie = acheterPointAccorder(partie, numJoueur); 
                }
                else if(equals(reponse, "2")){
                    partie = acheterVie(partie, numJoueur);
                }
                partie.GUI = MAJ_InfoJeuJCJ(GUI, premierJoueur, deuxiemeJoueur);
            }
            return partie
        }

        partieJCJ acheterPointAccorder(partie, int numJoueur){
            if(numJoueur==1){// Si c'est le premier joueur.
                if(partie.premierJoueur.JCJ_score>=5){
                    partie.premierJoueur.JCJ_score-=5;
                    println("Achat effectuer avec succès !");
                    partie.pointsAccorderPremierJoueur+=1;
                }
                else{
                    println("Tu n'as pas assez de point pour l'acheter.");
                }
            }
            else if(numJoueur==2){// Si c'est le deuxième joueur.
                if(deuxiemeJoueur.JCJ_score>=5){
                    deuxiemeJoueur.JCJ_score-=5;
                    println("Achat effectuer avec succès !");
                    partie.pointsAccorderDeuxiemeJoueur+=1;
                }
                else{
                    println("Tu n'as pas assez de point pour l'acheter.");
                }
            }
            return partie;
        }

        partieJCJ acheterVie(partieJCJ partie, int numJoueur){
            if(numJoueur==1){// Si c'est le premier joueur.
                if(partie.premierJoueur.JCJ_score>=3){
                    partie.premierJoueur.JCJ_score-=3;
                    println("Achat effectuer avec succès !");
                    partie.premierJoueur.JCJ_vies+=1;
                }
                else{
                    println("Tu n'as pas assez de point pour l'acheter.");
                }
            }
            else if(numJoueur==2){
                if(partie.deuxiemeJoueur.JCJ_score>=3){
                    partie.deuxiemeJoueur.JCJ_score-=3;
                    println("Achat effectuer avec succès !");
                    partie.deuxiemeJoueur.JCJ_vies+=1;
                }
                else{
                    println("Tu n'as pas assez de point pour l'acheter.");
                }
            }
            return partie;
        }

        String[] initialiser_GUI_JCJ(String[] GUI, Utilisateur premierJoueur, Utilisateur deuxiemeJoueur){
            String ligne = GUI[3];                                                               //On récupère la ligne de prénom.
            int[] positionReperes = chercheEmplacementReperes(ligne, ':', 2);                           //On récupère les positions des petits points.
            ligne = integrerPrenomsJCJ(ligne, positionReperes, premierJoueur, deuxiemeJoueur);
            GUI[3] = ligne;
            return GUI;
        }

            /* maj_GUI_JCJ permet de mettre a jour le GUI pour le jeu JCJ et ainsi afficher les prenoms, vies, victoires et mots à traduire */

        String[] maj_GUI_JCJ(String[] GUI, Utilisateur premierJoueur, Utilisateur deuxiemeJoueur){
            GUI = MAJ_InfoJeuJCJ(GUI, premierJoueur, deuxiemeJoueur);

            return GUI;                                                                                 
        }

            /* chercherEmplacementPrenom permet de trouver l'emplacement des petits points dans la ligne en paramètre. Utile pour les interfaces JCJ. */

        int[] chercheEmplacementReperes(String ligne, char symbol, int nbrReperes){
            int[] listePosition = new int[nbrReperes];
            int nbrTrouver = 0;
            int i = 0;
            while(nbrTrouver<nbrReperes && i<length(ligne)){
                if(charAt(ligne, i)==symbol){
                    listePosition[nbrTrouver] = i;
                    nbrTrouver++;
                }
                i++;
            }
            return listePosition;
        }

            /* integrerPrenomsJCJ permet d'insérer les prénoms des joueurs dans la ligne du GUI JCJ par une succéssion de substring */

        String integrerPrenomsJCJ(String ligne, int[] positions, Utilisateur premierJoueur, Utilisateur deuxiemeJoueur){
            String prenomPremierJoueur = premierJoueur.prenom;
            String prenomDeuxiemeJoueur = deuxiemeJoueur.prenom;
            String premierEspace = substring(ligne, positions[0]-20, positions[0]);    // On récupère la zone de prénom du premier Joueur.
            String deuxiemeEspace = substring(ligne, positions[1]-20, positions[1]);    // On récupère la zone de prénom du deuxième Joueur.
            String partieGauche = substring(ligne, 0, positions[0]-19);                 // On récupère la partie à gauche de la ligne.
            String partieCentrale = substring(ligne, positions[0]+1, positions[1]-21);  // On récupère la partie entre les deux zones de prénom.
            String partieDroite = substring(ligne, positions[1]+1, length(ligne));      // On récupère la partie à droite de la ligne.
            return (partieGauche + completerVide(premierEspace, prenomPremierJoueur+":" ,1)+ partieCentrale +" "+ completerVide(deuxiemeEspace, prenomDeuxiemeJoueur+":" ,1) +" "+ partieDroite);
        }

        String[] MAJ_InfoJeuJCJ(String[] GUI, Utilisateur premierJoueur, Utilisateur deuxiemeJoueur){
            String ligne = GUI[4];
            int[] positions = chercheEmplacementReperes(ligne, ':', 4);
            println(positions[0]);   
            println(positions[1]);   
            println(positions[2]);   
            println(positions[3]);   


            String premierEspaceVie = completerVide("     ", intToString(premierJoueur.JCJ_vies), 0);
            String deuxiemeEspaceVie = completerVide("     ", intToString(deuxiemeJoueur.JCJ_vies), 0);
            String premierEspaceScore = completerVide("     ", intToString(premierJoueur.JCJ_score), 0);
            String deuxiemeEspaceScore = completerVide("     ", intToString(deuxiemeJoueur.JCJ_score), 0);

            String partieGauche = substring(ligne, 0, positions[0]+2);
            String partieCentraleGauche = substring(ligne, positions[0]+7, positions[1]+2);
            String partieCentrale = substring(ligne, positions[1]+7, positions[2]+2);
            String partieCentraleDroite = substring(ligne, positions[2]+7, positions[3]+2);
            String partieDroite = substring(ligne, positions[3]+7, length(ligne));

            GUI[4] = (partieGauche + premierEspaceVie + partieCentraleGauche + premierEspaceScore + partieCentrale + deuxiemeEspaceVie + partieCentraleDroite + deuxiemeEspaceScore + partieDroite);
            return GUI;
        }

        String[] MAJ_AvanceJCJ(String[] GUI, Mot motactuel, String langue, int numjoueur){

            String ligneLangue = GUI[9];
            String ligneMot = GUI[11];

            int[] positionsReperesLangue = chercheEmplacementReperes(ligneLangue, ':', 2);
            int[] positionsReperesMots = chercheEmplacementReperes(ligneMot, '║', 6);

            String mot = retournerMotATraduire(motactuel, langue);

            if(numjoueur==1){
                String partieGauche = substring(ligneLangue, 0, positionsReperesLangue[0]+2);
                String espaces = substring(ligneLangue, positionsReperesLangue[0]+2, positionsReperesLangue[0]+10);
                String partieDroite = substring(ligneLangue, positionsReperesLangue[0]+10, length(ligneLangue));
                GUI[9] = (partieGauche + completerVide(espaces, langue, 0) + partieDroite);
                partieGauche = substring(ligneMot, 0, positionsReperesMots[1]+1);
                espaces = "                    ";
                partieDroite = substring(ligneMot, positionsReperesMots[1]+21, length(ligneMot));
                GUI[11] = (partieGauche + completerVide(espaces, mot, 0) + partieDroite);
                return GUI;
            }
            else{
                String partieGauche = substring(ligneLangue, 0, positionsReperesLangue[1]+2);
                String espaces = substring(ligneLangue, positionsReperesLangue[1]+2, positionsReperesLangue[1]+10);
                String partieDroite = substring(ligneLangue, positionsReperesLangue[1]+10, length(ligneLangue));
                GUI[9] = (partieGauche + completerVide(espaces, langue, 0) + partieDroite);
                partieGauche = substring(ligneMot, 0, positionsReperesMots[5]+1);
                espaces = "                    ";
                partieDroite = substring(ligneMot, positionsReperesMots[5]+21, length(ligneMot));
                GUI[11] = (partieGauche + completerVide(espaces, mot, 0) + partieDroite);
                return GUI;
            }
        }

        boolean reponseNonvide(String reponse){
            return length(reponse)!=0;
        }

        String retournerMotATraduire(Mot motactuel, String langue){
            if(equals(langue, "français")){
                return motactuel.moten;
            }
            else{
                return motactuel.motfr;
            }
        }

        String[] initialiserMenuJCJ(Utilisateur joueur, String fichier){
            String[] GUI = initialiseStringTable(fichier);
            GUI[1] = integrerIdentifiantsMenu(GUI[1], joueur);
            return GUI;
        }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////Fin jeu JCJ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
                    String[] identifiants = recupererIdentifiants(); // On récupère les nouveaux identifiants.
                    if(!identifiantExiste(idList, identifiants)){ // Si les identifiants n'existent pas déjà, on les rajoutent aux sauvegardes.
                        identifiants[0]=intToString(length(idList, 1));// On crée et insère un tout nouvel id.
                        //idList = insererNouveauxIdentifiants(idList, identifiants); // On rejoute à la sauvegarde les nouveaux identifiants.
                        user = initialiserUtilisateur(identifiants);// On initialise enfin un type Utilisateur avec l'entièreté de ses données générés.
                        identifier = true;// On confirme son identification
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
                int[] positions = chercheEmplacementReperes(ligne, '║', 2);
                String partieGauche = substring(ligne, 0, positions[0]);
                String espaces = substring(ligne, positions[0]+1, positions[1]-1);
                String partieDroite = substring(ligne, positions[1], length(ligne));
                espaces = completerVide(espaces, "Aucune session n'a encore été créer.", 1);
                basMenu[6] = (partieGauche + espaces + partieDroite);
            }
            else if(nombreIdentifiants>=1 && nombreIdentifiants<14){
                for(int i=0; i<nombreIdentifiants; i++){
                    String ligne = basMenu[i];
                    basMenu[i] = insererIdentifiantsLigneMenu(ligne, listeDesUtilisateurs[i]);
                }
            }
            return basMenu;
        }

        String insererIdentifiantsLigneMenu(String ligne, Utilisateur user){
            int limite = chercheNouveauCharactere(ligne);
            String finLigne = substring(ligne, limite, length(ligne));
            String Id = completerVide("  ", intToString(user.id), 2);
            String prenom = completerVide("                    ", user.prenom, 0);
            String nom = completerVide("                    ", user.nom, 0);
            String classe = completerVide("      ", user.classe, 0);
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
            Utilisateur[] listeDesUtilisateurs;
            Utilisateur user = new Utilisateur();
            
            while(!quitter){ // Tant que l'on n'a pas demandé de quitter.
                while(!estConnecter){
                    listeDesUtilisateurs = initialiserListeUtilisateur(idList);
                    user = connectionUtilisateur(listeDesUtilisateurs, idList);
                    idList = sauvegarderUtilisateur(user,idList); 
                    estConnecter=true;
                }
                while(estConnecter && !quitter){ // Tant que l'on est connecté
                    int choix = choixMenu(user);
                    if(choix==1){
                        user = lancerJeu1(user); // jeu numéro 1
                        idList = sauvegarderUtilisateur(user,idList);
                    }
                    else if(choix==2){
                    }
                    else if(choix==3){
                        listeDesUtilisateurs = initialiserListeUtilisateur(idList);
                        Utilisateur userSecond = connectionUtilisateur(listeDesUtilisateurs, idList);
                        Utilisateur[] userList = lancerJCJ(user, userSecond);
                        idList = sauvegarderUtilisateur(userList[0],idList);
                        idList = sauvegarderUtilisateur(userList[1], idList);
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

//Ajout gameplay second joueur JCJ
