package controller;

import domain.Partita;
import config.SetupGioco;
import java.util.ArrayList;
import java.util.List;

public class GiocoController implements GiocoListener, Coordinatore {

    private final List<PartitaObserver> observers = new ArrayList<>();

    private final SetupController setupCtrl;
    private final GameController gameCtrl;
    private final EventoController eventoCtrl;

    public GiocoController(Partita model, SetupGioco setup) {
        // eventoCtrl non dipende da gameCtrl
        // gameCtrl dipende da eventoCtrl, setupCtrl usa this come Coordinatore
        eventoCtrl = new EventoController(model, observers, this);
        gameCtrl = new GameController(model, observers, eventoCtrl);
        setupCtrl = new SetupController(model, setup, observers, this);
    }

    public void addObserver(PartitaObserver observer) {
        observers.add(observer);
    }

    // Coordinatore 

    @Override public void avviaTurnoCorrente() { gameCtrl.avviaTurnoCorrente(); }
    @Override public void concludiTurno() { gameCtrl.concludiTurno(); }

    // Avvio 

    // public void avviaApplicazione() { setupCtrl.avviaApplicazione(); }

    // GiocoListener: setup 
    @Override public void onInizioPremuto() { setupCtrl.onInizioPremuto(); }
    @Override public void onSceltaRegoleEffettuata(boolean leggiRegole) { setupCtrl.onSceltaRegoleEffettuata(leggiRegole); }
    @Override public void onAnagraficaGiocatoriInserita(ArrayList<String> nomi) { setupCtrl.onAnagraficaGiocatoriInserita(nomi); }

    // GiocoListener: gioco 

    @Override public void onLancioDadoRichiesto() { gameCtrl.onLancioDadoRichiesto(); }
    @Override public void onRispostaDomandaInserita(String risposta) { gameCtrl.onRispostaDomandaInserita(risposta); }

    // GiocoListener: eventi

    @Override public void onAvversarioScelto(String nomeAvversario) { eventoCtrl.onAvversarioScelto(nomeAvversario); }
    @Override public void onRispostaSfidaInserita(String risposta) { eventoCtrl.onRispostaSfidaInserita(risposta); }
}
