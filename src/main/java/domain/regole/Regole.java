package domain.regole;

import java.util.Map;

public class Regole {


    
    private final int sogliaVittoria;
    private final RegoleDomanda domanda;
    private final RegolePedina pedina;
    private final RegoleEvento evento;

    public Regole(Map<String, String> datiDb) {
        this.sogliaVittoria = parse(datiDb, "soglia_vittoria");
        this.domanda = new RegoleDomanda(
            parse(datiDb, "punti_facile"),
            parse(datiDb, "malus_facile"),
            parse(datiDb, "punti_difficile"),
            parse(datiDb, "malus_difficile"));
        this.pedina = new RegolePedina(
            parse(datiDb, "saggio_bonus"),
            parse(datiDb, "studioso_streak"),
            parse(datiDb, "sperimentatore_bonus"),
            parse(datiDb, "fortunato_trigger"),
            parse(datiDb, "fortunato_bonus"));
        this.evento = new RegoleEvento(parse(datiDb, "penalita_trappola"),
            parse(datiDb, "punti_sfida"),
            parse(datiDb, "maledizione_streak"),
            parse(datiDb, "roulette_min"),
            parse(datiDb, "roulette_max"));
    }

    private int parse(Map<String, String> map, String chiave) {
        String valore = map.get(chiave);
        if (valore == null)
            throw new IllegalArgumentException("Regola mancante nel DB: " + chiave);
        return Integer.parseInt(valore);
    }

    public int getSogliaVittoria() { 
        return sogliaVittoria; 
    }

    public RegoleDomanda getRegoleDomanda() { 
        return domanda; 
    }

    public RegolePedina  getRegolePedina() { 
        return pedina; 
    }

    public RegoleEvento  getRegoleEvento() { 
        return evento; 
    }
}