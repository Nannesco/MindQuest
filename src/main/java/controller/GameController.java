package controller;

import domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class GameController implements GestoreCasella {

    private final Partita model;
    private final List<PartitaObserver> observers;
    private final EventoController eventoCtrl;

    GameController(Partita model, List<PartitaObserver> observers, EventoController eventoCtrl) {
        this.model = model;
        this.observers = observers;
        this.eventoCtrl = eventoCtrl;
    }

    private void notifica(Consumer<PartitaObserver> azione) {
        for (PartitaObserver obs : observers) azione.accept(obs);
    }

    // FLUSSO TURNO

    void avviaTurnoCorrente() {
        if (model.isGiocoFinito()) { 
            model.chiudiDB();
            String nome = model.getVincitore().getUsername();
            notifica(obs -> obs.onGiocoTerminato(nome));
            return;
        }
        Giocatore g = model.getGiocatoreCorrente();
        int turno = model.getTurnoCorrente();
        notifica(obs -> obs.onTurnoIniziato(turno, g.toString()));
    }

    void concludiTurno() {
        boolean roundAvanzato = model.avanzaTurnoGiocatore();
        if (roundAvanzato) {
            int turno = model.getTurnoCorrente();
            notifica(obs -> obs.onAvanzamentoTurno(turno));
        }
        avviaTurnoCorrente();
    }


    // AZIONI UTENTE

    void onLancioDadoRichiesto() {
        RisultatoLancio r = model.eseguiLancioEAssegnaCasella();

        StatoTabellone stato = model.getStatoTabellone();
        notifica(obs -> obs.onTabelloneAggiornato(stato.nomi, stato.id, stato.posizioni, stato.punti, stato.numeroCaselle, stato.mappaEventi));
        notifica(obs -> obs.onDadoLanciato(r.passi, r.numeroCasella, r.puntiDado, r.nomeDado, r.bonusDado));

        r.gestisci(this); // dispatch polimorfico: nessun switch
    }

    void onRispostaDomandaInserita(String risposta) {
        RisultatoRisposta esito = model.processaRisposta(risposta);
        notifica(obs -> obs.onEsitoDomandaElaborato(esito.corretta, esito.bonusMessage, esito.puntiGuadagnati, esito.rispostaCorretta, esito.puntiTotali));
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
            StrategiaEvento strategia = model.getStrategiaAttiva();
            if (strategia.richiedeAvversario()) {
                ArrayList<String> avversari = model.getNomiAvversariEscluso(model.getGiocatoreCorrente());
                String sfidante = model.getGiocatoreCorrente().getUsername();
                notifica(obs -> obs.onRichiestaSfidato(sfidante, avversari));
                // flusso sospeso: riprende via EventoController.onAvversarioScelto()
            } else {
                eventoCtrl.eseguiEventoSemplice();
            }
        } else {
            concludiTurno();
        }
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
