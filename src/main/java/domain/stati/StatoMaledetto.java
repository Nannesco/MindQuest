package domain.stati;

import domain.strategie.PenalitaStrategy;
import domain.strategie.StrategiaEvento;

public class StatoMaledetto implements StatoCasellaEvento {

    private final StrategiaEvento penalita = new PenalitaStrategy();

    @Override
    public StrategiaEvento getStrategiaDaEseguire(StrategiaEvento strategiaOriginale) {
        
        // IGNORA il gioco originale! Restituisce la Trappola!
        return penalita; 
    }

}