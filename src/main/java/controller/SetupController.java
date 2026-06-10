package controller;

import domain.*;
import config.SetupGioco;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class SetupController {

    private final Partita model;
    private final SetupGioco setup;
    private final List<PartitaObserver> observers;
    private final Coordinatore coordinatore;

    SetupController(Partita model, SetupGioco setup, List<PartitaObserver> observers, Coordinatore coordinatore) {
        this.model = model;
        this.setup = setup;
        this.observers = observers;
        this.coordinatore = coordinatore;
    }

    private void notifica(Consumer<PartitaObserver> azione) {
        for (PartitaObserver obs : observers) azione.accept(obs);
    }

    /* 
    void avviaApplicazione() {
        notifica(PartitaObserver::onGiocoAvviato);
    }

    */

    void onInizioPremuto() {
        notifica(PartitaObserver::onRichiestaLetturaRegole);
    }

    void onSceltaRegoleEffettuata(boolean leggiRegole) {
        if (leggiRegole) notifica(obs -> obs.onRegoleMostrate());
        notifica(PartitaObserver::onRichiestaConfigurazioneGiocatori);
    }

    void onAnagraficaGiocatoriInserita(ArrayList<String> nomiGiocatori) {
        Tabellone       tabellone = setup.creaTabellone();
        List<Giocatore> giocatori = setup.creaEOrdinaGiocatori(nomiGiocatori);
        model.impostaMondoDiGioco(tabellone, giocatori);

        List<String> ordine = model.getNomiGiocatori();
        notifica(obs -> obs.onGiocatoriInizializzati(model.getGiocatori(), ordine));
        notifica(obs -> obs.onAvanzamentoTurno(model.getTurnoCorrente()));
        coordinatore.avviaTurnoCorrente();
    }
}
