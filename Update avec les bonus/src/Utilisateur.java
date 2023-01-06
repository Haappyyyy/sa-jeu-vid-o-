class Utilisateur {
    /*-------------------------------Infos de base-------------------------------*/
    int id;
    String prenom;
    String nom;
    String classe;
    /*-------------------------------Stats premier jeu-------------------------------*/
    int premierjeu_vies = 5;
    int premierjeu_victoires = 0;
    int premierjeu_parties_gagner;
    int premierjeu_parties_perdu;
    int premierjeu_mots_traduits;
    Bonus[] listebonus = new Bonus[5];
    /*-------------------------------Stats JoueurContreJoueur-------------------------------*/
    int JCJ_vies;
    int JCJ_score;
    int JCJ_manche_remporter;
    int JCJ_manche_perdu;
    int JCJ_mots_traduits;
    /*-------------------------------Stats troisième jeu-------------------------------*/

}