package puissance4.model;

import puissance4.controller.Controller;
import puissance4.controller.GameEngineListener;


public class GameEngine {

    private Model model;
    private GameEngineListener gameEngineListener;


    public GameEngine(Model model) {
        this.model = model;
    }

    /**
     * Méthode qui appelle toutes les méthodes nécessaires au fonctionnement du jeu
     */
    public void runGame(){
        model.init();
        gameEngineListener.onPartieCommencee();
        nouveauTour();
    }

    /**
     * Méthode qui permet de placer les jetons en partant de la ligne du bas du tableau.
     * @param colonne
     */
    public void placerJeton(int colonne){
        //Parcourt les cellules depuis le bas du tableau et remonte tant qu'il y en a une occupée
        // et que la dernière cellule n'est pas pleine.
        int ligne = model.getPlateau().length -1;
        while (model.getPlateau()[ligne][colonne] != Couleur.VIDE) {
            ligne--;
        }

        // Recupère la couleur du joueur en cours pour placer le bon pion
        model.getPlateau()[ligne][colonne] = model.getJoueurEnCours();

//         Si l'état du jeu n'est pas CONTINUE (c'est-à-dire que le jeu est terminé soit par une victoire, soit par un tableau plein),
//         alors on prévient le listener (le Controller ici ) en appelant la méthode onPartieFinie().
//         Cette méthode dans le Controller va gérer la fin de la partie en fonction de l'état du jeu et du joueur en cours.
        EtatJeu etatJeu = verifierFinDeJeu();
        if (etatJeu != EtatJeu.CONTINUE){
            gameEngineListener.onPartieFinie(etatJeu, model.getJoueurEnCours());
            return;
        }

        // Tant que la partie n'est pas finie on inverse la couleur du dernier joueur qui a joué
        model.inverserJoueur();

        // on lance le tour de jeu suivant
        this.nouveauTour();
    }

    /**
     * Méthode qui prévient le listener en appelant la méthode onNouveauTour()
     * Cette méthode dans le Controller : - rafraîchit la vue
     * - lance un nouveau tour de jeu en demandant un nouvel index au joueur pour choisir la colonne en appelant la
     * méthode nouveauTour() depuis la view.
     */
    public void nouveauTour(){
        gameEngineListener.onNouveauTour();
    }

    /**
     * Méthode qui répertorie les différents cas où une victoire est possible
     * @param couleur
     * @param ligne
     * @param colonne
     * @param directionLigne
     * @param directionColonne
     * @return
     */
    public boolean aGagneDirection(Couleur couleur, int ligne, int colonne, int directionLigne, int directionColonne) {

        int compteur = 0;

        for (int i = 0; i < 4 ; i ++ ){

            //indique la direction à parcourir à partir de la cellule jouée
            int nouvelleLigne = ligne + i * directionLigne;
            int nouvelleColonne = colonne + i * directionColonne;

            //Vérifie à partir de la cellule si 4 pions de même couleur se suivent en tenant compte de la taille du tableau
            if (nouvelleLigne >= 0 && nouvelleLigne < model.getPlateau().length &&
                    nouvelleColonne>= 0 && nouvelleColonne < model.getPlateau()[0].length &&
                    model.getPlateau()[nouvelleLigne][nouvelleColonne] == couleur){
                compteur ++;
                if (compteur == 4){
                    return true;
                }
            } else {
                //Si 4 pions ne se suivent pas, remet le compteur à 0 en attendant le prochain tour et la prochaine vérification
                compteur = 0;
            }

        }
        return false;

    }

    /**
     * Appelle la méthode générique aGagneDirection pour lui passer en paramètre la direction pour vérifier la victoire
     * horizontale, verticale, diagonale ascendante, diagonale descendante.
     * @param couleur
     * @param ligne
     * @param colonne
     * @return
     */
    public boolean aGagne(Couleur couleur, int ligne, int colonne){

        return aGagneDirection(couleur, ligne, colonne, 0,1)||
                aGagneDirection(couleur, ligne, colonne, 1,0)||
                aGagneDirection(couleur, ligne, colonne, -1,1)||
                aGagneDirection(couleur, ligne, colonne, 1,1);

    }

    /**
     * Méthode qui vérifie l'état de la partie : soit un des deux joueurs a gagné, soit le tableau est plein,
     * soit la partie continue.
     * @return EtatJeu
     */
    public EtatJeu verifierFinDeJeu(){
        for  (int i = 0; i < model.getPlateau().length; i++) {
            for (int j = 0; j < model.getPlateau()[i].length; j++) {

                if (aGagne(Couleur.ROUGE,i,j) || aGagne(Couleur.JAUNE,i,j) ){
                    return EtatJeu.VAINQUEUR;
                }

                if (plateauEstRempli()){
                    return EtatJeu.TABLEAU_PLEIN;
                }
            }
        }
        return EtatJeu.CONTINUE;
    }

    /**
     * Méthode qui renvoie true si le tableau est rempli.
     * @return boolean
     */
    public boolean plateauEstRempli(){
        for (Couleur [] ligne : model.getPlateau()){
            for (Couleur cellule : ligne){
                if (cellule == Couleur.VIDE){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Méthode passée dans TryMove() au controller et qui donne les régles d'un coup possible. Ce n'est que quand le coup
     * est valide que placerJeton() du gameEngine est appelée.
     * @param indexColonne
     * @return boolean
     */
    public boolean isMovePossible(int indexColonne){
            if (indexColonne < 0 || indexColonne >= model.getPlateau()[0].length) {
                // outOfBounds
                return false;
            } else if (model.getPlateau()[0][indexColonne] != Couleur.VIDE) {
               // colonnne remplie
                return false;
            }
        return true;
    }

    public void addGameEngineListener(GameEngineListener listener){
        this.gameEngineListener = listener;
    }
}


