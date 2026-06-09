package domain;
public class StatoNormale implements StatoCasellaEvento {
    @Override
    public StrategiaEvento getStrategiaDaEseguire(StrategiaEvento strategiaOriginale) {

        return strategiaOriginale; 
    }

    @Override
    public StatoCasellaEvento avanzaRound() {
        return this; 
    }
    
}
