package domain;

import java.util.List;

/**
 * DTO immutabile con lo snapshot del tabellone da inviare alla View.
 * Calcolato da Partita.getStatoTabellone() e consumato dal GameController.
 */
public class StatoTabellone {

    public final List<String>  nomi;
    public final List<Integer> id;
    public final List<Integer> posizioni;
    public final List<Integer> punti;
    public final int numeroCaselle;
    public final List<Boolean> mappaEventi;

    public StatoTabellone(List<String> nomi, List<Integer> id, List<Integer> posizioni, List<Integer> punti, int numeroCaselle, List<Boolean> mappaEventi) {
        this.nomi = nomi;
        this.id = id;
        this.posizioni = posizioni;
        this.punti = punti;
        this.numeroCaselle = numeroCaselle;
        this.mappaEventi = mappaEventi;
    }
}
