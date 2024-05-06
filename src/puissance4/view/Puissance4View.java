package puissance4.view;

import puissance4.controller.PlayerActionListener;
import puissance4.model.Couleur;
import puissance4.model.Model;

/**
 * Interface Puissance4View
 * Permet de créer des méthodes génériques communes à la ConsoleView et à la GraphicalView
 */
public interface Puissance4View {

    void display(Model model);

    String choisirCouleur();

    void afficherCouleurInvalide();

    void nouveauTour();

    void addPlayerActionListener(PlayerActionListener listener);

    void afficherFinDePartieVainqueur(Couleur couleur);

    void afficherFinDePartieRempli();



}

