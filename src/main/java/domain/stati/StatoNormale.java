package domain.stati;

import domain.strategie.StrategiaEvento;

public class StatoNormale implements StatoCasellaEvento {

    @Override
    public StrategiaEvento getStrategiaDaEseguire(StrategiaEvento strategiaOriginale) {

        return strategiaOriginale; 
    }
}
