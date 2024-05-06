package puissance4.controller;

import puissance4.model.Couleur;
import puissance4.model.EtatJeu;
import puissance4.model.GameEngine;
import puissance4.model.Model;
import puissance4.view.Puissance4View;

public class Controller {

    private final Puissance4View view;
    private final GameEngine gameEngine;
    private final Model model;

    public Controller(Puissance4View view, GameEngine gameEngine, Model model) {
        this.view = view;

        this.view.addPlayerActionListener(new PlayerActionListener() {
            @Override
            public void onPlayerMove(int index) {
                Controller.this.onPlayerMove(index);
            }

            @Override
            public void onPlayerQuit() {
                Controller.this.onPlayerQuit();
            }

            @Override
            public void onPlayerReplay() {
                Controller.this.onPlayerReplay();
            }
        });

        this.gameEngine = gameEngine;
        gameEngine.addGameEngineListener(new GameEngineListener() {
            @Override
            public void onPartieCommencee() {
                Controller.this.demanderCouleurJoueur1();
            }

            @Override
            public void onNouveauTour() {
                Controller.this.onNouveauTour();
            }

            @Override
            public void onPartieFinie(EtatJeu typeFin, Couleur joueurEnCours) {
                Controller.this.onPartieFinie(typeFin, joueurEnCours);
            }
        });

        this.model = model;
    }

    /**
     * Méthode appelée dans le main pour piloter le jeu
     */
    public void startGame(){
        gameEngine.runGame();
    }

    /**
     * Méthode qui vérifie si l'index est valide. Si c'est le cas on autorise le coup du joueur, sinon il lui demande
     * à nouveau de sélectionner un chiffre.
     * @param indexColonne
     */
    public void tryMove(int indexColonne){
        boolean indexValide = gameEngine.isMovePossible(indexColonne);

        if (indexValide) {
            gameEngine.placerJeton(indexColonne);
        } else {
            view.nouveauTour();
        }
    }

    /**
     * Méthode qui consiste à récupérer la couleur choisie par le joueur en début de partie. Définit la couleur du joueur
     * en cours si la sélection de la couleur est correcte, sinon la redemande.
     */
    public void demanderCouleurJoueur1() {
        String couleurChoisie = view.choisirCouleur();

        Couleur couleur = null;
        boolean bonneCouleur = false;

        try {
            couleur = Couleur.valueOf(couleurChoisie.toUpperCase());
        } catch (IllegalArgumentException ignored) {
        }

        bonneCouleur = (couleur == Couleur.JAUNE || couleur == Couleur.ROUGE);

        if (bonneCouleur) {
            model.setJoueurEnCours(couleur);
        } else{
            view.afficherCouleurInvalide();
            demanderCouleurJoueur1();
        }
    }

    /**
     * Fait en sorte d'afficher la vue avant chaque nouveau tour et de demander au joueur d'entrer l'index en console
     */
    public void onNouveauTour() {
        refreshView();
        view.nouveauTour();
    }

    /**
     * Fait en sorte d'afficher la vue après le dernier tour (la victoire d'un des deux joueurs) et procède
     * aux affichages de fin de partie (vainqueur et pour la GraphicalView Quitter ou Rejouer)
     * @param joueurEnCours
     */
    public void onPartieFinie(EtatJeu finPartie, Couleur joueurEnCours) {
        if (finPartie == EtatJeu.VAINQUEUR) {
            refreshView();
            view.afficherFinDePartieVainqueur(joueurEnCours);
        } else if (finPartie == EtatJeu.TABLEAU_PLEIN){
            refreshView();
            view.afficherFinDePartieRempli();
        }
    }

    private void refreshView() {
        view.display(model);
    }

    //Implémentations des méthodes de l'interface PlayerActionListener

    public void onPlayerMove(int index){
        tryMove(index);
    }

    public void onPlayerQuit(){
        System.exit(0);
    }

    public void onPlayerReplay(){
        this.startGame();
    }

}
