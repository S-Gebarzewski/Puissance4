package puissance4.model;

import puissance4.controller.Controller;
import puissance4.controller.GameEngineListener;

import java.util.Arrays;
import java.util.Objects;

public class Model {

    private Couleur joueurEnCours;
    private Couleur[][] plateau = new Couleur[6][7];

    /**
     * Initialisation du tableau avec des cases vides
     */
    public void init() {
        for (int li = 0; li < this.getPlateau().length; li++) {
            Arrays.fill(this.getPlateau()[li], Couleur.VIDE);
        }
    }

    public Couleur[][] getPlateau() {
        return plateau;
    }

    public Couleur getJoueurEnCours() {
        return joueurEnCours;
    }

    /**
     * Attribution d'une couleur au joueur lors du premier tour
     * @param joueurEnCours
     */
    public void setJoueurEnCours(Couleur joueurEnCours) {
        this.joueurEnCours = joueurEnCours;
    }

    /**
     * Attribution d'une couleur au joueur en cours en fonction de la première couleur attribuée
     */
    public void inverserJoueur() {
        this.setJoueurEnCours(this.joueurEnCours == Couleur.ROUGE ? Couleur.JAUNE : Couleur.ROUGE);
    }
}