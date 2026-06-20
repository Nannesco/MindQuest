package domain.strategie;

import domain.Giocatore;
import domain.Tabellone;
import domain.contratti.NotificatoreEvento;
import domain.regole.RegoleEvento;

public class PenalitaStrategy implements StrategiaEvento {

    private Giocatore attivatore;
    private int puntiPersi;

    @Override
    public String getNomeEvento() { return "Trappola della Maledizione"; }

    @Override
    public void inizializza(Giocatore attivatore, RegoleEvento regole, Tabellone tabellone) {
        this.attivatore = attivatore;
        puntiPersi = regole.penalitaTrappola();
        attivatore.getPedina().rimuoviPuntiConoscenza(puntiPersi);
    }

    @Override
    public void inviaNotifica(NotificatoreEvento notificatore) {
        notificatore.onPenalita(attivatore.getUsername(), puntiPersi);
    }
}
