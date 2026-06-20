package domain;

import java.util.List;

import domain.caselle.Casella;
import domain.caselle.CasellaEvento;
import domain.dto.InfoTabellone;
import domain.stati.StatoCasellaEvento;
import domain.stati.StatoNormale;

public class Tabellone {

    private final List<Casella>  caselle;
    private final int            numeroCaselle;
    private final List<Boolean>  mappaCaselleEvento;
    private final StatoCasellaEvento statoNormale;
    private StatoCasellaEvento statoInnescato;
    private int contatoreTurni;

public Tabellone(List<Casella> caselle) {
    this.caselle = caselle;
    this.numeroCaselle = caselle.size();
    this.mappaCaselleEvento = caselle.stream().map(c -> c instanceof CasellaEvento).toList();
    this.statoNormale = new StatoNormale();
    this.contatoreTurni = 0;
}

    public Casella calcolaNuovaPosizione(int posizioneCorrente, int distanza) {
        int nuovaPosizione = Math.max(1, Math.min(posizioneCorrente + distanza, numeroCaselle));
        return caselle.get(nuovaPosizione - 1);
    }

    public Casella getCasella(int posizione) {
        if (posizione >= 1 && posizione <= numeroCaselle)
            return caselle.get(posizione - 1);
        return null;
    }

    public InfoTabellone getInfo(){
        return new InfoTabellone(numeroCaselle, mappaCaselleEvento);
    }

    public void impostaStatoCaselle(int numeroTurni, StatoCasellaEvento stato){
        this.statoInnescato = stato;
        this.contatoreTurni = numeroTurni;
    }

    public StatoCasellaEvento getStatoCaselle(){
        if (contatoreTurni == 0) {
            return this.statoNormale;
        }
        return this.statoInnescato;
    }

    public void roundAvanzato(){
        if (contatoreTurni > 0)  this.contatoreTurni--;
    }

    public int           getNumeroCaselle()      { return numeroCaselle; }
    public List<Boolean> getMappaCaselleEvento() { return mappaCaselleEvento; }
}
