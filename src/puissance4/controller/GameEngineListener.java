package puissance4.controller;

import puissance4.model.Couleur;
import puissance4.model.EtatJeu;

/**
 * Interface GameEngineListener.
 * Cette interface définit les méthodes de rappel (listeners) pour le GameEngine.
 * Elle permet de décorréler le GameEngine du Controller, en définissant un contrat
 * que le Controller doit respecter pour recevoir les notifications du GameEngine.
 *
 * Les méthodes de cette interface sont appelées par le GameEngine lorsqu'un événement
 * important se produit dans le jeu, comme le début d'une partie, le début d'un nouveau tour,
 * ou la fin d'une partie.
 */

public interface GameEngineListener {

     void onPartieCommencee();

     void onNouveauTour();

     void onPartieFinie(EtatJeu finPartie, Couleur joueurEnCours);

}


