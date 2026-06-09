package domain;

import java.util.List;
import java.util.Random;
import services.foundation.DomandaRepository;

public class SfidaUnoControUnoStrategy implements StrategiaEvento {

    private static final int PUNTI_IN_PALIO = Regole.getPuntiInPalioSfida();

    private Giocatore sfidante;
    private Giocatore sfidato;
    private Giocatore giocatoreCorrenteSfida;

    private boolean esitoSfidante;
    private boolean esitoSfidato;

    private String  difficoltaCorrente;
    private int roundCounter;
    private boolean finito;

    // Stato interrogabile da EventoController dopo ogni registraEsitoRisposta()
    private boolean roundConcluso  = false;
    private String messaggioRound = null;
    private Giocatore vincitoreSfida = null;
    private Giocatore perdenteSfida  = null;

    private final Random random = new Random();
    private final String[] livelli = {"Facile", "Difficile"};
    private final DomandaRepository domandaRepo = DomandaRepository.getInstance();
    private Domanda domandaCorrente;

    @Override public String  getNomeEvento() { return "Sfida 1 VS 1 a oltranza"; }
    @Override public boolean richiedeAvversario() { return true; }

    @Override
    public void inizializza(Giocatore attivatore, List<Giocatore> tuttiGiocatori) {
        this.sfidante = attivatore;
        this.finito = false;
        this.roundCounter = 1;
        this.roundConcluso = false;
        this.messaggioRound = null;
        this.difficoltaCorrente = livelli[random.nextInt(livelli.length)];
    }

    @Override
    public void setAvversarioScelto(Giocatore avversario) {
        this.sfidato = avversario;
        this.giocatoreCorrenteSfida = this.sfidante;
    }

    /** Pesca la domanda per il giocatore corrente (senza notifiche: ci pensa EventoController). */
    @Override
    public void giocaProssimoTurno() {
        this.roundConcluso  = false;
        this.messaggioRound = null;
        this.domandaCorrente = domandaRepo.pescaDomandabyDifficolta(this.difficoltaCorrente);
        if (this.domandaCorrente == null) this.domandaCorrente = domandaRepo.pescaDomandaCasuale();
    }

    @Override
    public boolean verificaRispostaSfida(String risposta) {
        if (this.domandaCorrente == null) return false;
        return risposta.equalsIgnoreCase(String.valueOf(this.domandaCorrente.getRispostaCorretta()));
    }

    @Override
    public void registraEsitoRisposta(boolean esito) {
        if (this.giocatoreCorrenteSfida == this.sfidante) {
            this.esitoSfidante = esito;
            this.giocatoreCorrenteSfida = this.sfidato;
        } else if (this.giocatoreCorrenteSfida == this.sfidato) {
            this.esitoSfidato = esito;
            valutaRisultatoRound();
        }
    }

    private void valutaRisultatoRound() {
        this.roundConcluso = true;
        if (this.esitoSfidante && !this.esitoSfidato) {
            concludiSfida(sfidante, sfidato);
        } else if (!this.esitoSfidante && this.esitoSfidato) {
            concludiSfida(sfidato, sfidante);
        } else {
            this.roundCounter++;
            this.difficoltaCorrente = livelli[random.nextInt(livelli.length)];
            this.giocatoreCorrenteSfida = this.sfidante;
            this.messaggioRound = "Pareggio! Prepararsi per il round " + this.roundCounter + ".";
        }
    }

    private void concludiSfida(Giocatore vincitore, Giocatore perdente) {
        this.finito = true;
        this.vincitoreSfida = vincitore;
        this.perdenteSfida  = perdente;
        perdente.getPedina().rimuoviPuntiConoscenza(PUNTI_IN_PALIO);
        vincitore.getPedina().aggiungiPuntiConoscenza(PUNTI_IN_PALIO);
    }

    // inviaNotifica() usa il default no-op: la sfida è interattiva,
    // non passa per eseguiEventoSemplice()

    @Override public boolean isFinito() { return finito; }
    @Override public Domanda getDomandaCorrente() { return domandaCorrente; }
    @Override public Giocatore getGiocatoreCorrenteSfida() { return giocatoreCorrenteSfida; }
    @Override public boolean isRoundConcluso() { return roundConcluso; }
    @Override public String getMessaggioRound() { return messaggioRound; }
    @Override public Giocatore getVincitoreSfida() { return vincitoreSfida; }
    @Override public Giocatore getPerdenteSfida() { return perdenteSfida; }
    @Override public int getPuntiInPalio() { return PUNTI_IN_PALIO; }
}
