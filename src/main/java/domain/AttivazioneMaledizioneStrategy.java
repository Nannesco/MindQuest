package domain;

import java.util.List;

public class AttivazioneMaledizioneStrategy implements StrategiaEvento {

    @Override
    public String getNomeEvento() { return "Il Risveglio della Maledizione"; }

    @Override
    public void inizializza(Giocatore attivatore, List<Giocatore> tuttiGiocatori) {
        CasellaEvento.setStatoCondiviso(new StatoMaledetto(5));
    }

    @Override
    public void inviaNotifica(NotificatoreEvento notificatore, String nomeGiocatore) {
        notificatore.onMaledizione(nomeGiocatore);
    }
}
