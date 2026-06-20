package controller;

import domain.*;
import domain.regole.RegolePedina;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import controller.contratti.SetupObserver;
import controller.contratti.SetupSubject;

public class SetupController implements SetupSubject {

    private final Partita model;
    private final List<SetupObserver> observers;

    public SetupController(Partita model) {
        this.model     = model;
        this.observers = new ArrayList<>();
    }

    private void notifica(Consumer<SetupObserver> azione) {
        for (SetupObserver obs : observers) azione.accept(obs);
    }

    public void addObserver(SetupObserver observer) {
        observers.add(observer);
    }

    @Override
    public void onInizioPremuto() {
        notifica(SetupObserver::onRichiestaLetturaRegole);
    }

    @Override
    public void onSceltaRegoleEffettuata(boolean leggiRegole) {
        if (leggiRegole) notifica(obs -> obs.onRegoleMostrate());
        notifica(SetupObserver::onRichiestaConfigurazioneGiocatori);
    }

    @Override
    public void onAnagraficaGiocatoriInserita(ArrayList<String> nomiGiocatori) {
        RegolePedina regolePedina = model.getRegole().getRegolePedina();
        Lobby lobby = new Lobby(nomiGiocatori, regolePedina);
        model.setLobby(lobby);

        List<String> nomiPedine = nomiGiocatori.stream()
            .map(lobby::getNomePedina)
            .toList();

        List<String> ordine = lobby.getNomiOrdinati();
        notifica(obs -> obs.onGiocatoriInizializzati(nomiPedine, nomiGiocatori, ordine));
        notifica(obs -> obs.onInizioPrimoRound());
    }
}
