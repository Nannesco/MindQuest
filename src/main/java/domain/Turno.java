package domain;

import java.util.ArrayList;
import java.util.Arrays;

import services.foundation.DomandaRepository;

public class Turno {

    private final Giocatore giocatore;
    private final Tabellone tabellone;
    private final DomandaRepository domandaRepo;
    private Domanda domandaCorrente;
    private Casella casellaArrivo;
    private int sottoTurnoDomanda = 0;

    public Turno(Giocatore giocatore, Tabellone tabellone) {
        this.giocatore = giocatore;
        this.tabellone = tabellone;
        this.domandaRepo = DomandaRepository.getInstance();
    }

    public ArrayList<Object> gioca() {
        Pedina pedina = giocatore.getPedina();
        Dado d = pedina.getDado();
        int passi = d.lancia();
        pedina.aggiungiPuntiConoscenza(d.getPuntiDado());
        String bonusMessage = pedina.onDadoLanciato(passi);
        pedina.muovi(passi, tabellone);
        this.casellaArrivo = pedina.getCasellaCorrente();

        ArrayList<Object> risultati = new ArrayList<>();
        risultati.add(passi);
        risultati.add(casellaArrivo);
        risultati.add(pedina.getDado());
        risultati.add(bonusMessage);
        return risultati;
    }

    public boolean haAltreDomande() {
        return sottoTurnoDomanda < casellaArrivo.getMolteplicita();
    }

    public Domanda pescaProssimaDomanda() {
        this.domandaCorrente = domandaRepo.pescaDomanda(casellaArrivo.getMateria(), casellaArrivo.getDifficolta());
        return this.domandaCorrente;
    }

    public void incrementaSottoTurno() {
        this.sottoTurnoDomanda++;
    }

    public ArrayList<Object> verificaRisposta(String risposta) {
        boolean isCorretta = risposta.equalsIgnoreCase(String.valueOf(domandaCorrente.getRispostaCorretta()));
        Pedina pedina = giocatore.getPedina();

        if (isCorretta) {
            int guadagno = domandaCorrente.getDifficolta().equals("Difficile") ? Regole.getPuntiDomandaDifficile() : Regole.getPuntiDomandaFacile();
            String bonusMessage = pedina.onRispostaCorretta(domandaCorrente);
            pedina.aggiungiPuntiConoscenza(guadagno);
            return new ArrayList<>(Arrays.asList(true, bonusMessage, guadagno));
        } else {
            int malus = domandaCorrente.getDifficolta().equals("Difficile") ? Regole.getMalusDomandaDifficile() : Regole.getMalusDomandaFacile();
            pedina.rimuoviPuntiConoscenza(malus);
            String bonusMessage = pedina.onRispostaErrata(domandaCorrente);
            return new ArrayList<>(Arrays.asList(false, bonusMessage, malus, domandaCorrente.getRispostaCorretta()));
        }
    }

    public Domanda ottieniDomandaCasuale(String difficolta) {
        return domandaRepo.pescaDomandabyDifficolta(difficolta);
    }

    public boolean verificaRispostaEvento(Domanda dom, String risposta) {
        return risposta.equalsIgnoreCase(String.valueOf(dom.getRispostaCorretta()));
    }
}
