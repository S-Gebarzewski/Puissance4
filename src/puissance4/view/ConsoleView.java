package puissance4.view;

import puissance4.controller.PlayerActionListener;
import puissance4.model.Couleur;
import puissance4.model.Model;

import java.util.Scanner;


public class ConsoleView implements Puissance4View {

    private static final Scanner scan = new Scanner(System.in);
    private PlayerActionListener listener;


    @Override
    public void display(Model model) {
        for (Couleur[] ligne : model.getPlateau()) {
            System.out.print(" |");

            for (Couleur cellule : ligne) {
                System.out.print(cellule.getGraphic());
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.print("=");
        for (int i = 1; i <= model.getPlateau()[0].length; i ++){
            System.out.print("="+i);
        }
        System.out.print("==\n");

    }

    @Override
    public String choisirCouleur(){
        System.out.println("Choisissez la couleur Jaune ou Rouge : ");
        return scan.nextLine().toLowerCase();
    }

    @Override
    public void afficherCouleurInvalide(){
        System.out.println("Couleur invalide. Veuillez choisir rouge ou jaune.");
    }

    /**
     * Cette méthode crée un Thread, et appelle la méthode demanderColonneConsole()
     * Cette dernière demande à l'utilisateur de choisir la colonne où il souhaite placer son jeton
     * L'utilisation d'un Thread séparé permet de ne pas bloquer le reste de l'application pendant que l'utilisateur fait son choix
     */
    @Override
    public void nouveauTour(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                demanderColonneConsole();
            }
        }).start();
    }

    private void demanderColonneConsole(){
        int indexcolonne;
        System.out.println("Choisissez la colonne où vous souhaitez placer votre jeton : ");
        indexcolonne = scan.nextInt() - 1;
        listener.onPlayerMove(indexcolonne);
    }

    @Override
    public void addPlayerActionListener(PlayerActionListener listener){
        this.listener = listener;
    }

    @Override
    public void afficherFinDePartieVainqueur(Couleur couleur){
        System.out.println("Le joueur " + couleur + " a gagné!");
    }

    @Override
    public void afficherFinDePartieRempli(){
        System.out.println("Il n'y a auncun vainqueur, la partie est finie!");
    }

}
