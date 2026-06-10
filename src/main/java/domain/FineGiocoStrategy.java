package domain;

import java.util.List;

public class FineGiocoStrategy implements StrategiaEvento {

    @Override
    public String getNomeEvento() { return "**TRAGUARDO**"; }

    @Override
    public void inizializza(Giocatore giocatoreCorrente, List<Giocatore> tuttiGiocatori) {

    }

    @Override
    public void inviaNotifica(NotificatoreEvento notificatore, String nomeGiocatore) {
        notificatore.onFineGioco(nomeGiocatore);
    }
}
