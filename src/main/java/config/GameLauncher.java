package config;

import domain.Partita;
import domain.Tabellone;
import domain.regole.Regole;
import services.foundation.PersistentManager;
import ui.ConsoleView;
import controller.GameController;
import controller.EventoController;
import controller.SetupController;
import controller.SfidaController;

public class GameLauncher {

    private GameLauncher() {}

    public static boolean avviaApplicazione() {
        DBConfig dbConfig = DBConfig.load();
        PersistentManager persistence = new PersistentManager(dbConfig.url(), dbConfig.user(), dbConfig.password());
        Regole regole = new Regole(persistence.caricaRegole());
        Tabellone tabellone = new Tabellone(persistence.getCaselle());
        Partita model = new Partita(persistence, tabellone, regole);
        SetupController setupCtrl = new SetupController(model);
        GameController  gameCtrl  = new GameController(model);
        EventoController eventoCtrl = new EventoController(model);
        SfidaController sfidaCtrl   = new SfidaController(model, persistence);

        ConsoleView view = new ConsoleView(gameCtrl, setupCtrl, eventoCtrl, sfidaCtrl);
        gameCtrl.addObserver(view);
        eventoCtrl.addObserver(view);
        setupCtrl.addObserver(view);
        sfidaCtrl.addObserver(view);

        boolean giocaAncora = view.onGiocoAvviato();
        if (!giocaAncora){
            view.chiudiScanner();
            persistence.chiudiConnessione();
        }
        return giocaAncora;
    }
}
