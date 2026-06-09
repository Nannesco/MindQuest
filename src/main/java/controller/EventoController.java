package controller;

import domain.*;
import java.util.List;
import java.util.function.Consumer;

class EventoController implements NotificatoreEvento {

    private final Partita model;
    private final List<PartitaObserver> observers;
    private final Coordinatore coordinatore;

    EventoController(Partita model, List<PartitaObserver> observers, Coordinatore coordinatore) {
        this.model = model;
        this.observers = observers;
        this.coordinatore = coordinatore;
    }

    private void notifica(Consumer<PartitaObserver> azione) {
        for (PartitaObserver obs : observers) azione.accept(obs);
    }

    // EVENTI SEMPLICI

    void eseguiEventoSemplice() {
        String nomeG = model.getGiocatoreCorrente().getUsername();
        model.getStrategiaAttiva().inviaNotifica(this, nomeG); // dispatch diretto alla strategy
        coordinatore.concludiTurno();
    }

    // SFIDA 1V1 

    void onAvversarioScelto(String nomeAvversario) {
        Giocatore sfidato = model.setAvversarioEvento(nomeAvversario);
        String sfidante = model.getGiocatoreCorrente().getUsername();
        if (sfidato != null) notifica(obs -> obs.onSfidaIniziata(sfidante, sfidato.getUsername()));
        avanzaTurnoSfida(model.getStrategiaAttiva());
    }

    void onRispostaSfidaInserita(String risposta) {
        StrategiaEvento sfida    = model.getStrategiaAttiva();
        boolean corretto = sfida.verificaRispostaSfida(risposta);
        char rc = sfida.getDomandaCorrente().getRispostaCorretta();
        notifica(obs -> obs.onEsitoTurnoSfida(corretto, rc));

        sfida.registraEsitoRisposta(corretto);

        if (sfida.isFinito()) {
            notifica(obs -> obs.onSfidaTerminata( sfida.getVincitoreSfida().getUsername(), sfida.getPerdenteSfida().getUsername(), sfida.getPuntiInPalio()));
            coordinatore.concludiTurno();
        } else if (sfida.isRoundConcluso()) {
            notifica(obs -> obs.onMessaggioStato(sfida.getMessaggioRound()));
            avanzaTurnoSfida(sfida);
        } else {
            avanzaTurnoSfida(sfida); // stesso round, turno dell'avversario
        }
    }

    private void avanzaTurnoSfida(StrategiaEvento sfida) {
        sfida.giocaProssimoTurno();
        Domanda dom = sfida.getDomandaCorrente();
        String nomeG = sfida.getGiocatoreCorrenteSfida().getUsername();
        notifica(obs -> obs.onNuovoTurnoSfida(nomeG, dom.getTesto(), dom.getMateria(), dom.getDifficolta(), dom.getOpzioni()));
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
    public void onMaledizione(String nomeGiocatore) {
        notifica(obs -> obs.onEventoAttivazioneMaledizione(nomeGiocatore));
    }

    @Override
    public void onFineGioco(String nomeGiocatore) {
        model.dichiaraVincitore(nomeGiocatore);
    }
}
