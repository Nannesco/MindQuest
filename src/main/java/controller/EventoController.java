package controller;

import domain.*;
import domain.contratti.NotificatoreEvento;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import controller.contratti.EventoObserver;
import controller.contratti.EventoSubject;

public class EventoController implements NotificatoreEvento, EventoSubject {

    private final Partita model;
    private final List<EventoObserver> observers;


    public EventoController(Partita model) {
        this.model = model;
        this.observers = new ArrayList<>();
    }

    public void addObserver(EventoObserver observer) {
        observers.add(observer);
    }

    private void notifica(Consumer<EventoObserver> azione) {
        for (EventoObserver obs : observers) azione.accept(obs);
    }

    // EVENTI SEMPLICI

    public void eseguiEvento() {
        model.getStrategiaAttiva().inviaNotifica(this);
    }

 


    // NotificatoreEvento — dispatch polimorfico dalle strategy semplici

    @Override
    public void onCambioDado(String nomeGiocatore, String dadoVecchio, String dadoNuovo) {
        notifica(obs -> obs.onEventoCambioDado(nomeGiocatore, dadoVecchio, dadoNuovo));
    }

    @Override
    public void onModificaPunti(String nomeGiocatore, int variazione, int puntiTotali) {
        notifica(obs -> obs.onEventoModificaPunti(nomeGiocatore, variazione, puntiTotali));
    }

    @Override
    public void onPenalita(String nomeGiocatore, int puntiPersi) {
        notifica(obs -> obs.onEventoPenalita(nomeGiocatore, puntiPersi));
    }

    @Override
    public void onMaledizione(String nomeGiocatore, int numeroRound) {
        notifica(obs -> obs.onEventoAttivazioneMaledizione(nomeGiocatore, numeroRound));
    }

    @Override
    public void onFineGioco(String nomeGiocatore) {
        model.dichiaraVincitore(nomeGiocatore);
        notifica(obs -> obs.onFineTabellone(nomeGiocatore));
    }

    @Override
    public void onSfida1v1(String nomeGiocatore, int puntiInPalio) {
        notifica(obs -> obs.onCasella1v1(nomeGiocatore, puntiInPalio));
    }
}
