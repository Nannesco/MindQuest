package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import controller.contratti.SfidaObserver;
import controller.contratti.SfidaSubject;
import domain.Domanda;
import domain.Giocatore;
import domain.Partita;
import domain.Sfida1v1;
import domain.contratti.GestoreDomanda;

public class SfidaController implements SfidaSubject {

    private final Partita model;
    private final List<SfidaObserver> observers;
    private Sfida1v1 sfida;
    private final GestoreDomanda persistence;

    private Sfida1v1 sfida() {
        if (sfida == null) throw new IllegalStateException("Sfida non ancora inizializzata");
        return sfida;
    }

    public SfidaController(Partita model, GestoreDomanda persistence) {
        this.model = model;
        this.observers = new ArrayList<>();
        this.persistence = persistence;
    }

    @Override
    public void addObserver(SfidaObserver observer) {
        observers.add(observer);
    }

    private void notifica(Consumer<SfidaObserver> azione) {
        for (SfidaObserver obs : observers) azione.accept(obs);
    }

    @Override
    public void onSfida1v1(String nomeGiocatore, int puntiInPalio) {
        this.sfida = new Sfida1v1(model.getGiocatoreCorrente(),  persistence, puntiInPalio);
        ArrayList<String> avversari = model.getNomiAvversari();
        notifica(obs -> obs.onRichiestaSfidato(nomeGiocatore, avversari));
    }

    @Override
    public void onAvversarioScelto(String nomeAvversario) {
        Giocatore sfidato = model.trovaGiocatorePerNome(nomeAvversario);
        sfida().setAvversarioScelto(sfidato);
        String sfidante = sfida().getGiocatoreSfidante().getUsername();
        if (sfidato != null) notifica(obs -> obs.onSfidaIniziata(sfidante, sfidato.getUsername()));
        onTurnoSfidaSfidante();
    }

    @Override
    public void onTurnoSfidaSfidante() {
        Domanda dom = sfida().giocaTurnoG1();
        String nomeG = sfida().getGiocatoreSfidante().getUsername();
        notifica(obs -> obs.onTurnoSfidaG1(nomeG, dom.getTesto(), dom.getMateria(), dom.getDifficolta(), dom.getOpzioni()));
    }

    @Override
    public void onTurnoSfidaSfidato() {
        Domanda dom = sfida().giocaTurnoG2();
        String nomeG = sfida().getGiocatoreSfidato().getUsername();
        notifica(obs -> obs.onTurnoSfidaG2(nomeG, dom.getTesto(), dom.getMateria(), dom.getDifficolta(), dom.getOpzioni()));
    }

    @Override
    public void onRispostaSfidaG1Inserita(String risposta) {
        boolean corretto = sfida().verificaRispostaG1(risposta);
        char rc = sfida().getDomandaCorrente().getRispostaCorretta();
        notifica(obs -> obs.onEsitoTurnoSfidaG1(corretto, rc));
    }

    @Override
    public void onRispostaSfidaG2Inserita(String risposta) {
        boolean corretto = sfida().verificaRispostaG2(risposta);
        char rc = sfida().getDomandaCorrente().getRispostaCorretta();
        notifica(obs -> obs.onEsitoTurnoSfidaG2(corretto, rc));
    }

    @Override
    public void onFineRoundSfida() {
        if (sfida().isFinito()) {
            sfida().concludiSfida();
            notifica(obs -> obs.onSfidaTerminata(
                sfida().getVincitoreSfida().getUsername(),
                sfida().getPerdenteSfida().getUsername(),
                sfida().getPuntiInPalio()));
        } else {
            sfida().incrementaRoundCounter();
            notifica(obs -> obs.onPareggioSfida(sfida().getRoundCounter()));
        }
    }
}
