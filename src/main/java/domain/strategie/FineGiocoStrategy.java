package domain.strategie;

import domain.Giocatore;
import domain.Tabellone;
import domain.contratti.NotificatoreEvento;
import domain.regole.RegoleEvento;

public class FineGiocoStrategy implements StrategiaEvento {

    private Giocatore attivatore;

    @Override
    public String getNomeEvento() { return "**TRAGUARDO**"; }

    @Override
    public void inizializza(Giocatore attivatore, RegoleEvento regole, Tabellone tabellone) {
        this.attivatore = attivatore;
    }

    @Override
    public void inviaNotifica(NotificatoreEvento notificatore) {
        notificatore.onFineGioco(attivatore.getUsername());
    }
}
