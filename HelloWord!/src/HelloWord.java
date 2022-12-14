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
        String niveau = "";
        String reponse;
        boolean identifiantsCorrects = false;
        while(!identifiantsCorrects){
            do{
                affichage(GUI);
                println();
                print("Entre ton prénom : ");
                prenom = readString();
            }while(!NomCorrecte(prenom));
            jepensequecettefonctionvamettreajourlanouvellesessionparunnouveaustringprenomnomniveaupourquelejoueursereperemieux(GUIOriginal, GUI ,prenom, 0); //0=zone de prénom
            do{
                affichage(GUI);
                println();
                print("Entre ton nom : ");
                nom = readString();
            }while(!NomCorrecte(nom));
            jepensequecettefonctionvamettreajourlanouvellesessionparunnouveaustringprenomnomniveaupourquelejoueursereperemieux(GUIOriginal, GUI, nom, 1); //1=zone de nom
            do{
                affichage(GUI);
                println();
                print("Entre ta classe : ");
                niveau = readString();
            }while(!classeCorrecte(niveau));
            jepensequecettefonctionvamettreajourlanouvellesessionparunnouveaustringprenomnomniveaupourquelejoueursereperemieux(GUIOriginal, GUI, niveau, 2); //2=zone de classe
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
        String[] identifiants = new String[]{prenom, nom, niveau};
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

    void jepensequecettefonctionvamettreajourlanouvellesessionparunnouveaustringprenomnomniveaupourquelejoueursereperemieux(String[] GUIOriginal, String[] GUI, String chaine, int i){
        if(i==0){
            String ligne = GUIOriginal[1];
            String debut = debutNouvelleSession(ligne);
            String fin = finNouvelleSession(ligne, length(debut)-1);
            String espaces = substring(ligne, length(debut)-1, (length(ligne)-length(fin)));
            GUI[1] = debut + completerVide(espaces, chaine) + fin;
        }
        else if(i==1){
            String ligne = GUIOriginal[3];
            String debut = debutNouvelleSession(ligne);
            String fin = finNouvelleSession(ligne, length(debut)-1);
            String espaces = substring(ligne, length(debut)-1, (length(ligne)-length(fin)));
            GUI[3] = debut + completerVide(espaces, chaine) + fin;
        }
        else if(i==2){
            String ligne = GUIOriginal[5];
            String debut = debutNouvelleSession(ligne);
            String fin = finNouvelleSession(ligne, length(debut)-1);
            String espaces = substring(ligne, length(debut)-1, (length(ligne)-length(fin)));
            GUI[5] = debut + completerVide(espaces, chaine) + fin;
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

    String completerVide(String espaces, String chaine){
        espaces = substring(espaces, length(chaine)-1,length(espaces)-2);
        return (chaine + espaces);
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
            if(equals(idList[i][0], id[0])){            // Première condition qui vérifie si un prénom déjà enregistré est égal à celui du nouvel identifiant.
                if(equals(idList[i][1], id[1])){        // Deuxième condition qui vérifie si un nom déjà enregistré est égal à celui du nouvel identifiant en plus du prénom.
                    if(equals(idList[i][2], id[2])){    // Troisième condition qui vérifie, dans le cas où le nom et prénom sont égaux à ceux du nouvel identifiant, si ils sont de la même classe.
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
        for(int i=0; i<length(id); i++){//                      \
            idList[length(idList, 1)-1][i] = id[i];//            | Une boucle for pour incorporer le nouveau type utilisateur passé en paramètre aux sauvegardes
            saveCSV(idList, NOM_REPERTOIRE +"/saves2.csv");//    |
        }//                                                     /
        return idList;
    }

        /* ajouterLigne permet d'agrandir une table d'une ligne. Utile pour sauvegarder un nouvel utilisateur. */

    String[][] ajouterLigne(String[][] idList){
        String[][] nouvelIdList = new String[length(idList,1)+1][length(idList, 2)]; // On créer une nouvelle table d'une ligne supplémentaire.
        //println(length(idList, 1)+1);
        //println(length(idList,2));
        for(int i=0; i<length(idList, 1); i++){//      \
            for(int j=0; j<length(idList, 2); j++){//   |  
                println(idList[i][j]);//                | Deux boucles for pour remplir la nouvelle table 2 dimensions depuis l'ancienne.
                nouvelIdList[i][j] = idList[i][j];//    |
            }//                                        /
        }
        return nouvelIdList; // On retourne la nouvelle table.
    }

    Utilisateur initialiserUtilisateur(String[] identifiants){
        Utilisateur user = new Utilisateur();
        user.prenom = identifiants[0];
        user.nom = identifiants[1];
        user.niveau = identifiants[2];
        return user;
    }

    void lancerJeu1(Utilisateur user){
        CSVFile voc = loadCSV("../ressources/voc.csv");
        Mot[] allWords = new Mot[rowCount(voc)];
        loadWords(allWords, voc);
        //Utilisateur user = new Utilisateur();                 \
        //println("Bonjour! Quel est ton nom?");                 |
        //user.prenom=readString();                              |
        //println("Dans quel niveau d'enseignement es-tu?");     |  Incorporer dans le void algorithm
        //println("1-CE2");                                      |  Mit en paramètre de la fonction lanceJeu1
        //println("2-CM1");                                      |
        //println("3-CM2");                                      |
        //user.niveau=readString();                             /
        Traduction(allWords,user);
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

    void algorithm(){
        CSVFile listeIdentifiants = loadCSV(NOM_REPERTOIRE +"/ComptesEnregistrés.csv");                     // On récupère les anciennes sauvegardes.
        String[][] idList = new String[rowCount(listeIdentifiants)][columnCount(listeIdentifiants)];        // On crée une table double de String à la taille du fichier CSV de sauvegarde.
        initialiserSauvegardes(listeIdentifiants, idList);                                                  // On rentre les données du CSVFile dans la table double.
        boolean quitter = false;
        boolean estConnecter = false;
        boolean identifier = false;
        String[] identifiants = new String[]{"","",""};
        Utilisateur user = initialiserUtilisateur(identifiants);
        
        while(!quitter){ // Tant que l'on n'a pas demandé de quitter.
            if(possèdeSession()){ // Demande si on n'a une session.
                identifier = false;
                do{
                    identifiants = recupererIdentifiants();
                    if(identifiantExiste(idList, identifiants)){ // Si les identifiants n'existent pas déjà, on les rajoutent aux sauvegardes
                        identifier = true;
                        println("Identifiants corrects !");
                        user = initialiserUtilisateur(identifiants);
                        estConnecter = true;
                    }

                }while(!identifier);
            }
            else{ // Si on n'en a pas.
                identifier = false;
                do{
                    identifiants = recupererIdentifiants(); // On récupère les nouveaux identifiants
                    if(!identifiantExiste(idList, identifiants)){ // Si les identifiants n'existent pas déjà, on les rajoutent aux sauvegardes
                        identifier = true;
                        idList = insererNouveauxIdentifiants(idList, identifiants); // On rejoute à la sauvegarde les nouveaux identifiants
                        println("Identifiants enreistrés avec succès !");
                        user = initialiserUtilisateur(identifiants);
                        estConnecter = true;
                    }
                }while(!identifier); // Tant que l'on ne s'est pas identifié

            }

            while(estConnecter && !quitter){ // Tant que l'on est connecté
                int choix = choixMenu();
                if(choix==1){
                    lancerJeu1(user); // jeu numéro 1
                }
                else if(choix==2){
                }
                else if(choix==3){
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