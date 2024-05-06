package puissance4;

import puissance4.controller.Controller;
import puissance4.model.GameEngine;
import puissance4.model.Model;
import puissance4.view.ConsoleView;
import puissance4.view.Puissance4View;
import puissance4.view.swing.GraphicalView;

public class Main {

    public static void main (String [] args) {
        boolean consoleMode = false;
        //vérifie si "console" est passé en argument du main
        if (args.length > 0 && args[0].equalsIgnoreCase("console")) {
            consoleMode = true;
        }

        // Init
        Model model = new Model();
        GameEngine gameEngine = new GameEngine(model);
        Puissance4View view = createView(consoleMode);
        Controller controller = new Controller(view, gameEngine, model);

        // Start
        controller.startGame();
    }

    /**
     * @param consoleMode Si true, on crée la vue console, sinon on crée la GraphicalView
     */
    private static Puissance4View createView(boolean consoleMode) {
        if (consoleMode) {
            return new ConsoleView();
        } else {
            return new GraphicalView();
        }
    }
}
