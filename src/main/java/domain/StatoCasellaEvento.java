package domain;

public interface StatoCasellaEvento {
    StrategiaEvento getStrategiaDaEseguire(StrategiaEvento strategiaOriginale);
    StatoCasellaEvento avanzaRound();
}
