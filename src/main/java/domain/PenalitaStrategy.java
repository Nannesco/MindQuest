package domain;

import java.util.List;

import controller.NotificatoreEvento;

public class PenalitaStrategy implements StrategiaEvento {

    private int puntiPersi;

    @Override
    public String getNomeEvento() { return "Trappola della Maledizione"; }

    @Override
    public void inizializza(Giocatore attivatore, List<Giocatore> tuttiGiocatori) {
        puntiPersi = Regole.getPenalitaTrappola();
        attivatore.getPedina().rimuoviPuntiConoscenza(puntiPersi);
    }

    @Override
    public void inviaNotifica(NotificatoreEvento notificatore, String nomeGiocatore) {
        notificatore.onPenalita(nomeGiocatore, puntiPersi);
    }
}
