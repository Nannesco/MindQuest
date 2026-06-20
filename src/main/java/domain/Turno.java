package domain;

import domain.regole.RegoleDomanda;
import domain.caselle.Casella;
import domain.contratti.GestoreDomanda;
import domain.dadi.Dado;
import domain.dto.RisultatoLancio;
import domain.dto.RisultatoRisposta;
import domain.pedine.Pedina;

public class Turno {

    private final Giocatore giocatore;
    private final Tabellone tabellone;
    private final GestoreDomanda gestoreDomanda;
    private final RegoleDomanda regole;
    private Domanda domandaCorrente;
    private Casella casellaArrivo;
    private int sottoTurnoDomanda = 0;

    public Turno(Giocatore giocatore, Tabellone tabellone, GestoreDomanda gestoreDomanda, RegoleDomanda regole ) {
        this.giocatore = giocatore;
        this.tabellone = tabellone;
        this.gestoreDomanda = gestoreDomanda;
        this.regole = regole;
    }

    public RisultatoLancio gioca() {
        Pedina pedina = giocatore.getPedina();
        Dado d = pedina.getDado();
        int passi = d.lancia();
        pedina.aggiungiPuntiConoscenza(d.getPuntiDado());
        String bonusMessage = pedina.onDadoLanciato(passi);
        pedina.muovi(passi, tabellone);
        this.casellaArrivo = pedina.getCasellaCorrente();

        return new RisultatoLancio(passi, casellaArrivo.getNumeroCasella(), d.getPuntiDado(), d.toString(), bonusMessage, casellaArrivo.ottieniDispatcher());
    }

    public boolean haAltreDomande() {
        return sottoTurnoDomanda < casellaArrivo.getMolteplicita();
    }

    public Domanda pescaProssimaDomanda() {
        this.domandaCorrente = gestoreDomanda.pescaDomanda(casellaArrivo.getMateria(), casellaArrivo.getDifficolta());
        return this.domandaCorrente;
    }

    public Casella getCasellaArrivo() { return casellaArrivo; }

    public void incrementaSottoTurno() {
        this.sottoTurnoDomanda++;
    }

    public RisultatoRisposta verificaRisposta(String risposta) {
        boolean isCorretta = risposta.equalsIgnoreCase(String.valueOf(domandaCorrente.getRispostaCorretta()));
        Pedina pedina = giocatore.getPedina();

        if (isCorretta) {
            int guadagno = domandaCorrente.getDifficolta().equals("Difficile") ? regole.puntiDifficile() : regole.puntiFacile();
            String bonusMessage = pedina.onRispostaCorretta(domandaCorrente);
            pedina.aggiungiPuntiConoscenza(guadagno);
            return new RisultatoRisposta(true, bonusMessage, guadagno, domandaCorrente.getRispostaCorretta(), giocatore.getPedina().getPuntiConoscenza());
        } else {
            int malus = domandaCorrente.getDifficolta().equals("Difficile") ? regole.malusDifficile() : regole.malusFacile();
            pedina.rimuoviPuntiConoscenza(malus);
            String bonusMessage = pedina.onRispostaErrata(domandaCorrente);
            return new RisultatoRisposta(false, bonusMessage, malus, domandaCorrente.getRispostaCorretta(), giocatore.getPedina().getPuntiConoscenza());
        }
    }
}
