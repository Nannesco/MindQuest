package domain.stati;

import domain.strategie.StrategiaEvento;

public interface StatoCasellaEvento {

    StrategiaEvento getStrategiaDaEseguire(StrategiaEvento strategiaOriginale);
    
}
