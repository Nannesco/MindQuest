package domain;

import java.util.ArrayList;
import java.util.List;

import config.DBConfig;
import services.foundation.GestoreDB;

public class Partita {

    private static Partita istanza;

    private Tabellone tabellone;
    private List<String> nomiGiocatori;
    private List<Integer> idGiocatori;
    private List<Giocatore> giocatori;

    private Turno turnoAttivo;
    private int turnoCorrente = 1;
    private int indiceGiocatoreCorrente = 0;
    private Giocatore giocatoreCorrente;

    private boolean   isGiocoFinito = false;
    private Giocatore vincitore;

    private StrategiaEvento strategyAttiva;
    private Casella ultimaCasella;

    private Partita() {
        this.giocatori = new ArrayList<>();
        this.nomiGiocatori = new ArrayList<>();
        this.idGiocatori = new ArrayList<>();
    }

    public static Partita getInstance() {
        if (istanza == null) istanza = new Partita();
        return istanza;
    }

    // =========================================================================
    // SETUP
    // =========================================================================

    public void impostaMondoDiGioco(Tabellone tabellone, List<Giocatore> giocatoriPronti) {
        this.tabellone  = tabellone;
        this.giocatori  = giocatoriPronti;
        ArrayList<String> ordineNomi = new ArrayList<>();
        this.idGiocatori = new ArrayList<>();
        for (Giocatore g : giocatoriPronti) {
            ordineNomi.add(g.getUsername());
            idGiocatori.add(g.getIdGiocatore());
        }
        this.nomiGiocatori = ordineNomi;
        this.giocatoreCorrente = giocatori.get(0);
    }

    // =========================================================================
    // ESECUZIONE TURNO
    // =========================================================================

    public RisultatoLancio eseguiLancioEAssegnaCasella() {
        turnoAttivo = new Turno(giocatoreCorrente, tabellone);
        ArrayList<Object> r = turnoAttivo.gioca();

        int passi = (int) r.get(0);
        Casella casella = (Casella) r.get(1);
        Dado dado = (Dado) r.get(2);
        String bonus = r.size() > 3 ? (String) r.get(3) : "";
        this.ultimaCasella = casella;


        return new RisultatoLancio(passi, casella.getNumeroCasella(),
                dado.getPuntiDado(), dado.toString(), bonus, casella.ottieniDispatcher());
    }

    // =========================================================================
    // DOMANDE
    // =========================================================================

    public boolean haAltreDomande() { return turnoAttivo.haAltreDomande(); }
    public Domanda getProssimaDomanda() { return turnoAttivo.pescaProssimaDomanda(); }

    public RisultatoRisposta processaRisposta(String risposta) {
        ArrayList<Object> esito = turnoAttivo.verificaRisposta(risposta);
        turnoAttivo.incrementaSottoTurno();
        boolean corretta = (boolean) esito.get(0);
        String  bonusMsg = (String)  esito.get(1);
        int punti = (int) esito.get(2);
        char rc = esito.size() > 3 ? (char) esito.get(3) : ' ';
        int totali = giocatoreCorrente.getPedina().getPuntiConoscenza();
        return new RisultatoRisposta(corretta, bonusMsg, punti, rc, totali);
    }

    // =========================================================================
    // EVENTI / STRATEGY
    // =========================================================================

    public void preparaEvento() {
        CasellaEvento ce = (CasellaEvento) ultimaCasella;
        strategyAttiva = ce.getStrategia();
        if (strategyAttiva != null) strategyAttiva.inizializza(giocatoreCorrente, giocatori);
    }

    /** Restituisce la strategy attiva: il controller ci parla direttamente. */
    public StrategiaEvento getStrategiaAttiva() { return strategyAttiva; }

    public boolean isEventoAttivo() { return strategyAttiva != null; }

    /**
     * Operazione composta di dominio: trova il giocatore per nome nella lista
     * e lo imposta come avversario sulla strategy.
     */
    public Giocatore setAvversarioEvento(String nome) {
        Giocatore sfidato = trovaGiocatorePerNome(nome);
        if (strategyAttiva != null) strategyAttiva.setAvversarioScelto(sfidato);
        return sfidato;
    }

    /**
     * Dichiara esplicitamente un vincitore: usato da FineGiocoStrategy quando
     * il giocatore raggiunge il traguardo, indipendentemente dalla soglia punti.
     */
    public void dichiaraVincitore(String nomeGiocatore) {
        Giocatore g = trovaGiocatorePerNome(nomeGiocatore);
        if (g != null) {
            this.vincitore    = g;
            this.isGiocoFinito = true;
        }
    }

    // =========================================================================
    // GESTIONE TURNO
    // =========================================================================

    /** Avanza al giocatore successivo; restituisce true se si è completato un round globale. */
    public boolean avanzaTurnoGiocatore() {
        verificaCondizioneVittoria(giocatoreCorrente);
        indiceGiocatoreCorrente++;
        boolean roundAvanzato = false;
        if (indiceGiocatoreCorrente >= giocatori.size()) {
            indiceGiocatoreCorrente = 0;
            if (!isGiocoFinito) {
                CasellaEvento.avanzaRoundGlobale();
                turnoCorrente++;
                roundAvanzato = true;
            }
        }
        if (!isGiocoFinito) giocatoreCorrente = giocatori.get(indiceGiocatoreCorrente);
        return roundAvanzato;
    }

    public StatoTabellone getStatoTabellone() {
        ArrayList<Integer> posizioni = new ArrayList<>();
        ArrayList<Integer> punti     = new ArrayList<>();
        for (Giocatore g : giocatori) {
            posizioni.add(g.getPedina().getCasellaCorrente().getNumeroCasella());
            punti.add(g.getPedina().getPuntiConoscenza());
        }
        return new StatoTabellone(nomiGiocatori, idGiocatori, posizioni, punti,
                tabellone.getNumeroCaselle(), tabellone.getMappaCaselleEvento());
    }

    public void chiudiDB() { /*db.chiudiConnessione(); */ }

    // =========================================================================
    // GETTER
    // =========================================================================

    public boolean isGiocoFinito() { return isGiocoFinito; }
    public Giocatore getVincitore() { return vincitore; }
    public int getTurnoCorrente() { return turnoCorrente; }
    public Giocatore getGiocatoreCorrente() { return giocatoreCorrente; }
    public List<Giocatore> getGiocatori() { return giocatori; }
    public List<String> getNomiGiocatori() { return nomiGiocatori; }

    public ArrayList<String> getNomiAvversariEscluso(Giocatore g) {
        ArrayList<String> lista = new ArrayList<>();
        for (Giocatore giac : giocatori) if (!giac.equals(g)) lista.add(giac.getUsername());
        return lista;
    }

    // =========================================================================
    // PRIVATI
    // =========================================================================

    private Giocatore trovaGiocatorePerNome(String nome) {
        for (Giocatore g : giocatori) if (g.getUsername().equals(nome)) return g;
        return null;
    }

    private void verificaCondizioneVittoria(Giocatore g) {
        if (g.getPedina().getPuntiConoscenza() >= Regole.getSogliaVittoria()) {
            isGiocoFinito = true;
            vincitore     = g;
        }
    }
}
