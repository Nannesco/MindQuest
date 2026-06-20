package domain.contratti;

import domain.Domanda;

public interface GestoreDomanda {

    Domanda pescaDomanda(String materia, String difficolta);

    Domanda pescaDomandaCasuale();

    Domanda pescaDomandaByDifficolta(String difficolta);
    
}
