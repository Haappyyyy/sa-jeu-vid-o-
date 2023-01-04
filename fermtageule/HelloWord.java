import extensions.File;

class HelloWord extends Program{

    final String NOM_REPERTOIRE = "ressource";
    String[] fichiers = getAllFilesFromDirectory(NOM_REPERTOIRE);

    String nom;
    String prénom;

    boolean NomCorrecte(String nom){
        boolean res=true;
        int i=0;
        while(res && i<length(nom)){
            if(!(charAt(nom, i)>='A' || charAt(nom, i)<='z')){
                res=false;            
            }
        }
        return res;
    }

    void lanceIdentifieur(){
        String[] Session = initialiseStringTable("Session");
        int a = readInt();
        if(choixSession(Session)){

        }
        else{

        }
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
            return true;
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
        File texte = newFile(NOM_REPERTOIRE +"/"+ fichier +".txt");
        int i = cptLig(texte);
        println(i);
        String[] GUI = new String[i];
        int j = 0;
        while(j<i){
            if (ready(texte)) {
                GUI[j] = readLine(texte);
                //println(GUI[j]);
                if (GUI[j] == null) {
                    println(j + " caca");
                } else {
                    println(j + " " + GUI[j]);
                }
                j++;
            }
        }
        //for(int j=0; j<i; j++){
        //    GUI[j] = readLine(texte);
        //    println(GUI[j]);
        //}
        return GUI;
    }

    void affichage(String[] Identifieur){
        clear();
        for(int i=0; i<length(Identifieur); i++){
            println(Identifieur[i]);
        }                                         
    }

    void afficherMenu(){
        File Menu = newFile(NOM_REPERTOIRE +"/Menu.txt");
        println();
        while(ready(Menu)){
            println(readLine(Menu));
        }
        println();

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
        //afficherMenu();
        print("Entre le chiffre du menu que tu souhaites ouvrir : ");
        String reponse = readString();
        while(!estUnChiffreValide(reponse)){
            clear();
            affichage(Menu);
            //afficherMenu();
            print("Saisi incorecte, entre à nouveau ton chiffre : ");
            reponse = readString();
        }
        return charAt(reponse, 0)-'0';
    }

    void algorithm(){
        boolean quitter = false;
        while(!quitter){
            lanceIdentifieur();
            int choix = choixMenu();
            if(choix==1){
            }
            else if(choix==2){
            }
            else if(choix==3){
            }
            else if(choix==4){
            }
            else if(choix==5){
            }
            else if(choix==6){
                quitter=true;
                clear();
            }
        }
        println();
    }
}