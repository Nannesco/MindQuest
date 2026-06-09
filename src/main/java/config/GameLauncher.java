package config;

import domain.Partita;
import ui.ConsoleView;
import controller.GiocoController;
public class GameLauncher {

    private GameLauncher() {}

    public static void avviaApplicazione() {
        ConsoleView view = new ConsoleView();
        Partita model = Partita.getInstance();
        SetupGioco setup = new SetupGioco();
        GiocoController controller = new GiocoController(model, setup);

        view.setListener(controller);
        controller.addObserver(view); // il Controller è l'unico gestore degli observer

        view.onGiocoAvviato(); // Avvia la schermata iniziale

   }
}

