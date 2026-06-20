package domain;

import domain.contratti.GestoreDomanda;

public class Sfida1v1 {

    private int PUNTI_IN_PALIO; 

    private Giocatore sfidante;
    private Giocatore sfidato;

    private boolean esitoSfidante;
    private boolean esitoSfidato;

    private String difficoltaCorrente;
    private int roundCounter;



    private GestoreDomanda gestoreDomanda;
    private Domanda domandaCorrente;



    public Sfida1v1(Giocatore attivatore, GestoreDomanda gestoreDomanda, int puntiInPalio) {
        this.sfidante = attivatore;
        this.esitoSfidante = false;
        this.esitoSfidato = false;
        this.roundCounter = 1;
        this.gestoreDomanda = gestoreDomanda;
        this.PUNTI_IN_PALIO = puntiInPalio;
    }

    public void setAvversarioScelto(Giocatore avversario) {
        this.sfidato = avversario;
    }

    public Domanda giocaTurnoG1() {
        this.domandaCorrente = gestoreDomanda.pescaDomandaCasuale();
        this.difficoltaCorrente = domandaCorrente.getDifficolta();
        return this.domandaCorrente;
    }

    public Domanda giocaTurnoG2() {
        this.domandaCorrente = gestoreDomanda.pescaDomandaByDifficolta(this.difficoltaCorrente);
        return this.domandaCorrente;
    }

    public boolean verificaRispostaG1(String risposta) {
        if (this.domandaCorrente == null) return false;
        boolean esito = risposta.equalsIgnoreCase(String.valueOf(this.domandaCorrente.getRispostaCorretta()));
        this.esitoSfidante = esito;
        return esito;
    }

    public boolean verificaRispostaG2(String risposta) {
        if (this.domandaCorrente == null) return false;
        boolean esito = risposta.equalsIgnoreCase(String.valueOf(this.domandaCorrente.getRispostaCorretta()));
        this.esitoSfidato = esito;
        return esito;
    }

    public boolean isFinito() {
        return esitoSfidante != esitoSfidato;
    }

    public void incrementaRoundCounter() {
        this.roundCounter++;
    }

    public Giocatore getVincitoreSfida() {
        if (!isFinito()) return null;
        return this.esitoSfidante ? this.sfidante : this.sfidato;
    }

    public Giocatore getPerdenteSfida() {
        if (!isFinito()) return null;
        return this.esitoSfidante ? this.sfidato : this.sfidante;
    }

    public void concludiSfida() {
        if (this.esitoSfidante && !this.esitoSfidato) {
            this.sfidante.getPedina().aggiungiPuntiConoscenza(PUNTI_IN_PALIO);
            this.sfidato.getPedina().rimuoviPuntiConoscenza(PUNTI_IN_PALIO);
        } else if (!this.esitoSfidante && this.esitoSfidato) {
            this.sfidato.getPedina().aggiungiPuntiConoscenza(PUNTI_IN_PALIO);
            this.sfidante.getPedina().rimuoviPuntiConoscenza(PUNTI_IN_PALIO);
        }
    }

    public Giocatore getGiocatoreSfidante() { 
        return sfidante; 
    }

    public Giocatore getGiocatoreSfidato() { 
        return sfidato; 
    }

    public Domanda getDomandaCorrente() { 
        return domandaCorrente; 
    }

    public int getPuntiInPalio() { 
        return PUNTI_IN_PALIO; 
    }

    public int getRoundCounter() { 
        return roundCounter; 
    }
}
