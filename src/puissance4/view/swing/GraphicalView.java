package puissance4.view.swing;

import puissance4.controller.PlayerActionListener;
import puissance4.model.Couleur;
import puissance4.model.Model;
import puissance4.view.Puissance4View;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GraphicalView extends JFrame implements Puissance4View {

    // Map pour associer chaque couleur de l'enum 'Couleur' des pions à sa correspondante dans la classe 'Color' de Swing
    private final Map<Couleur, Color> couleurMap;
    private final PionButton[][] buttons;
    private PlayerActionListener playerActionListener;

    /**
     * Constructeur qui construit la map et initialise la fenêtre
     */
    public GraphicalView() {
        couleurMap = new HashMap<>();
        couleurMap.put(Couleur.ROUGE, Color.RED);
        couleurMap.put(Couleur.JAUNE, Color.YELLOW);
        couleurMap.put(Couleur.VIDE, null);
        buttons = new PionButton[6][7];
        build();
    }


    private void build() {
        setTitle("Puissance 4"); //On donne un titre à l'application
        setSize(750, 550); //On donne une taille à notre fenêtre
        setLocationRelativeTo(this); //On centre la fenêtre sur l'écran
        setResizable(false); //On interdit ou pas la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
        setContentPane(buildContentPane());
        setVisible(true);
    }

    /**
     * méthode qui construit le tableau, associe un bouton à chaque cellule et un listener à chaque bouton
     * @return Container
     */
    private Container buildContentPane() {
        final GridLayout gridLayout = new GridLayout(6, 7);
        JPanel gridPanel = new JPanel(gridLayout);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                buttons[i][j] = new PionButton();
                gridPanel.add(buttons[i][j]);
                final int column = j;
                buttons[i][j].addActionListener(event -> {
                    if (playerActionListener != null) {
                        this.playerActionListener.onPlayerMove(column);
                    }
                });
            }
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    /**
     * Méthode qui sert à colorier les cellules sur lesquelles les joueurs ont cliqué
     * @param model
     */
    public void display(Model model){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++)  {

                Couleur cellule = model.getPlateau()[i][j];
                buttons[i][j].setPionColor(couleurMap.get(cellule));
                buttons[i][j].repaint();
            }
        }
    }

    /**
     * Méthode qui affiche une pop-up qui permet de sélectionner la couleur jaune ou rouge
     * @return String
     */
    @Override
    public String choisirCouleur() {
        String result = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionner une couleur ",
                "Sélection couleur",
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{"Jaune", "Rouge"},
                "Jaune"
        );
        return result;
    }

    /**
     * Méthode qui se charge de l'affichage du vainqueur et de la proposition de quitter ou de rejouer.
     * @param couleur
     */
    @Override
    public void afficherFinDePartieVainqueur(Couleur couleur) {
        afficherFinDePartie("le joueur " + couleur + " a gagné!");
    }

    /**
     * Méthode qui se charge de l'affichage de fin de partie car tableau plein et de la proposition de quitter ou de rejouer.
     */
    @Override
    public void afficherFinDePartieRempli() {
        afficherFinDePartie("Il n'y a pas de vainqueur, la partie est finie!");
    }

    /**
     * Méthode appelée dans afficherFinDePartieVainqueur() et afficherFinDePartieRempli(), qui constitue leur code commun
     * @param message
     */
    public void afficherFinDePartie(String message) {

        Object[] options = {"Quitter", "Rejouer"};

        int answer = JOptionPane.showOptionDialog(this,
                message, "Quitter:Continuer",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[1]);
        if (answer == JOptionPane.YES_OPTION) {
            playerActionListener.onPlayerQuit();

        } else if (answer == JOptionPane.NO_OPTION) {
            playerActionListener.onPlayerReplay();
        }

    }

    @Override
    public void addPlayerActionListener(PlayerActionListener listener) {
        this.playerActionListener = listener;
    }

// Pas d'implémentation de ces deux dernières méthodes issues de l'interface Puissance4View dans le cas de la GraphicalView

    @Override
    public void afficherCouleurInvalide() {
    }

    @Override
    public void nouveauTour() {
    }
}






