package controller;

import domain.*;
import domain.contratti.GestoreCasella;
import domain.dto.InfoGiocatori;
import domain.dto.InfoTabellone;
import domain.dto.RisultatoLancio;
import domain.dto.RisultatoRisposta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import controller.contratti.GameObserver;
import controller.contratti.GameSubject;

public class GameController implements GestoreCasella, GameSubject {

    private final Partita model;
    private final List<GameObserver> observers;

    public GameController( Partita model) {
        this.model = model;
        this.observers = new ArrayList<>();
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    private void notifica(Consumer<GameObserver> azione) {
        for (GameObserver obs : observers) azione.accept(obs);
    }

    // FLUSSO TURNO

    void avviaTurnoCorrente() {
        if (model.isGiocoFinito()) { 
            String nome = model.getVincitore().getUsername();
            notifica(obs -> obs.onGiocoTerminato(nome));
            return;
        }
        Giocatore g = model.getGiocatoreCorrente();
        int turno = model.getRoundCorrente();
        notifica(obs -> obs.onTurnoIniziato(turno, g.toString()));
    }

    void concludiTurno() {
        boolean roundAvanzato = model.avanzaTurnoGiocatore();
        if (roundAvanzato) {
            int round = model.getRoundCorrente();
            notifica(obs -> obs.onAvanzamentoRound(round));
        }
        avviaTurnoCorrente();
    }


    // AZIONI UTENTE

    public void onPrimoTurnoIniziato() {
        avviaTurnoCorrente();
    }

    @Override
    public void onLancioDadoRichiesto() {
        RisultatoLancio r = model.eseguiLancioEAssegnaCasella();

        InfoTabellone statoT = model.getInfoTabellone();
        InfoGiocatori statoG = model.getInfoGiocatori();
        notifica(obs -> obs.onTabelloneAggiornato(statoG.nomi(), statoG.id(), statoG.posizioni(), statoG.punti(), statoT.numeroCaselle(), statoT.mappaEventi()));
        notifica(obs -> obs.onDadoLanciato(r.passi(), r.numeroCasella(), r.puntiDado(), r.nomeDado(), r.bonusDado()));

        r.gestisci(this); // dispatch polimorfico: nessun switch
    }
    
    @Override
    public void onRispostaDomandaInserita(String risposta) {
        RisultatoRisposta esito = model.processaRisposta(risposta);
        notifica(obs -> obs.onEsitoDomandaElaborato(esito.corretta(), esito.bonusMessage(), esito.puntiGuadagnati(), esito.rispostaCorretta(), esito.puntiTotali()));
        avviaProssimaDomanda();
    }



    @Override
    public void gestisciConoscenza(int molteplicita) {
        notifica(obs -> obs.onCasellaConoscenzaRaggiunta(molteplicita));
        avviaProssimaDomanda();
    }

    @Override
    public void gestisciEvento(String tipoEvento) {
        model.preparaEvento();
        if (model.isEventoAttivo()) {
            notifica(obs -> obs.onCasellaEventoRaggiunta(tipoEvento));
        } else {
            concludiTurno();
        }
    }

    public void onEventoTerminato() {
        concludiTurno();
    }

    private void avviaProssimaDomanda() {
        if (model.haAltreDomande()) {
            Domanda dom = model.getProssimaDomanda();
            notifica(obs -> obs.onDomandaRicevuta(dom.getTesto(), dom.getMateria(), dom.getDifficolta(), dom.getOpzioni()));
        } else {
            concludiTurno();
        }
    }
}
