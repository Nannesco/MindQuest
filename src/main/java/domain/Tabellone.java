package domain;

import java.util.ArrayList;
import java.util.List;



public class Tabellone {

    private List<Casella> caselle;
    private static int NUMERO_CASELLE;
    private List<Boolean> mappaCaselleEvento; 

    
    public Tabellone (int numeroCaselle, List<Casella> caselle) {
        this.caselle = caselle;
        NUMERO_CASELLE = numeroCaselle;
        this.mappaCaselleEvento = new ArrayList<>();
        for (Casella c : caselle) {
            mappaCaselleEvento.add(c instanceof CasellaEvento);
        }
    }

    public Casella calcolaNuovaPosizione(int posizioneCorrente, int distanza) {

        int nuovaPosizione = posizioneCorrente + distanza;

        if (nuovaPosizione > NUMERO_CASELLE) {
            nuovaPosizione = NUMERO_CASELLE;
        }
        
        if (nuovaPosizione < 1) {
            nuovaPosizione = 1;
        }

        // La casella 1 si trova all'indice 0, quindi facciamo -1.
        return caselle.get(nuovaPosizione - 1);
    }
    
    public Casella getCasella(int posizione) {
        if(posizione >= 1 && posizione <= NUMERO_CASELLE) {
            return caselle.get(posizione - 1);
        }
        return null;
    }

    public int getNumeroCaselle() {
        return NUMERO_CASELLE;
    }

    public List<Boolean> getMappaCaselleEvento() {
        return mappaCaselleEvento;
    }

}