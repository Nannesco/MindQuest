package domain;
public class StatoMaledetto implements StatoCasellaEvento {
    private int roundRimanenti;

    public StatoMaledetto(int roundRimanenti) {
        this.roundRimanenti = roundRimanenti;
    }

    @Override
    public StrategiaEvento getStrategiaDaEseguire(StrategiaEvento strategiaOriginale) {
        // IGNORA il gioco originale! Restituisce la Trappola!
        return new PenalitaStrategy(); 
    }

    @Override
    public StatoCasellaEvento avanzaRound() {
        this.roundRimanenti--;
        if (this.roundRimanenti <= 0) {
           
            return new StatoNormale(); 
        }
        return this; 
    }
}