package domain;

import java.util.ArrayList;
import java.util.List;

import domain.caselle.CasellaEvento;
import domain.contratti.GestoreDomanda;
import domain.dto.InfoGiocatori;
import domain.dto.InfoTabellone;
import domain.dto.RisultatoLancio;
import domain.dto.RisultatoRisposta;
import domain.regole.Regole;
import domain.strategie.StrategiaEvento;

public class Partita {


    private final GestoreDomanda gestoreDomanda;
    private final Regole regole;
    private final Tabellone   tabellone;

    private Lobby lobby;
    private Turno turnoAttivo;
    private int roundCorrente = 1;
    private int indiceGiocatoreCorrente = 0;
    private Giocatore giocatoreCorrente;

    private boolean isGiocoFinito = false;
    private Giocatore vincitore;

    private StrategiaEvento strategyAttiva;

    public Partita(GestoreDomanda gestoreDomanda, Tabellone tabellone, Regole regole) {
        this.gestoreDomanda = gestoreDomanda;
        this.tabellone   = tabellone;
        this.regole = regole;
    }

    // =========================================================================
    // SETUP
    // =========================================================================

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
        this.giocatoreCorrente = lobby.getGiocatori().get(0);
        this.indiceGiocatoreCorrente = 0;
    }

    // =========================================================================
    // ESECUZIONE TURNO
    // =========================================================================

    public RisultatoLancio eseguiLancioEAssegnaCasella() {
        turnoAttivo = new Turno(giocatoreCorrente, tabellone, gestoreDomanda, regole.getRegoleDomanda());
        return turnoAttivo.gioca();
    }

    public boolean haAltreDomande() { 
        return turnoAttivo.haAltreDomande(); 
    }

    public Domanda getProssimaDomanda() { 
        return turnoAttivo.pescaProssimaDomanda(); 
    }

    public RisultatoRisposta processaRisposta(String risposta) {
        turnoAttivo.incrementaSottoTurno();
        return turnoAttivo.verificaRisposta(risposta);
    }

    // =========================================================================
    // EVENTI / STRATEGY
    // =========================================================================

    public void preparaEvento() {
        CasellaEvento ce = (CasellaEvento) turnoAttivo.getCasellaArrivo();
        strategyAttiva = ce.getStrategia(tabellone.getStatoCaselle());
        if (strategyAttiva != null) strategyAttiva.inizializza(giocatoreCorrente, regole.getRegoleEvento(), tabellone);
    }

    public StrategiaEvento getStrategiaAttiva() { 
        return strategyAttiva; 
    }

    public boolean isEventoAttivo() { 
        return strategyAttiva != null; 
    }

    public void dichiaraVincitore(String nomeGiocatore) {
        Giocatore g = lobby.trovaGiocatorePerNome(nomeGiocatore);
        if (g != null) {
            this.vincitore     = g;
            this.isGiocoFinito = true;
        }
    }

    // =========================================================================
    // GESTIONE TURNO
    // =========================================================================

    public boolean avanzaTurnoGiocatore() {
        verificaCondizioneVittoria(giocatoreCorrente);
        indiceGiocatoreCorrente++;
        boolean roundAvanzato = false;
        if (indiceGiocatoreCorrente >= lobby.getNumeroGiocatori()) {
            indiceGiocatoreCorrente = 0;
            if (!isGiocoFinito) {
                tabellone.roundAvanzato();
                roundCorrente++;
                roundAvanzato = true;
            }
        }
        if (!isGiocoFinito) giocatoreCorrente = lobby.getGiocatoreByIndex(indiceGiocatoreCorrente);
        return roundAvanzato;
    }



    // =========================================================================
    // GETTER
    // =========================================================================

    public boolean isGiocoFinito() { 
        return isGiocoFinito; 
    }

    public Giocatore getVincitore() { 
        return vincitore; 
    }

    public int getRoundCorrente() { 
        return roundCorrente; 
    }

    public Turno getTurnoAttivo() { 
        return turnoAttivo; 
    
    }
    public Giocatore getGiocatoreCorrente() {
        return giocatoreCorrente; 
    }

    public List<Giocatore> getGiocatori() {
        return lobby.getGiocatori(); 
    }

    public List<String> getNomiGiocatori() { 
        return lobby.getNomiOrdinati(); 
    }

    public ArrayList<String> getNomiAvversari() {
        return lobby.getNomiAvversariEscluso(this.giocatoreCorrente);
    }

    public Giocatore trovaGiocatorePerNome(String nome){
        return lobby.trovaGiocatorePerNome(nome);
    }

    public InfoTabellone getInfoTabellone() {
    return tabellone.getInfo();
    }

    public InfoGiocatori getInfoGiocatori() {
        return lobby.getInfo();
    }

    public Regole getRegole(){
        return this.regole;
    }

    // =========================================================================
    // PRIVATI
    // =========================================================================

    private void verificaCondizioneVittoria(Giocatore g) {
        if (g.getPedina().getPuntiConoscenza() >= regole.getSogliaVittoria()) {
            isGiocoFinito = true;
            vincitore     = g;
        }
    }
}
