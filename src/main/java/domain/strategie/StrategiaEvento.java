package domain.strategie;

import domain.Giocatore;
import domain.Tabellone;
import domain.contratti.NotificatoreEvento;
import domain.regole.RegoleEvento;

public interface StrategiaEvento {

    String getNomeEvento();

    void inviaNotifica(NotificatoreEvento notificatore);

    void inizializza(Giocatore attivatore, RegoleEvento regole, Tabellone tabellone);

}
