package puissance4.view.swing;

import javax.swing.*;
import java.awt.*;

// Création d'une classe qui extends JButton et donc hérite de ses fontionnalités
public class PionButton extends JButton {

    private Color pionColor;

    public PionButton() {
        super();
        this.pionColor = null;
    }

    public void setPionColor(Color color) {
        this.pionColor = color;
    }

    /**
     * Méthode qui surcharge une méthode de JComponent dont JButton est donc PionButton sont des sous-classes
     * Elle est appelée dans la GraphicalView lorsque Repaint est appelée.
     * Elle permet de dessiner un cerle de couleur lorsque une couleur est donnée
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pionColor != null) {
            g.setColor(pionColor);
            int diameter = Math.min(getWidth(), getHeight()) - 20;
            g.fillOval(10, 10, diameter,diameter);
        }
    }

}
