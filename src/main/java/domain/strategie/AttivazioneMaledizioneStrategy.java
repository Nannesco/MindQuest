package domain.strategie;

import domain.Giocatore;
import domain.Tabellone;
import domain.contratti.NotificatoreEvento;
import domain.regole.RegoleEvento;
import domain.stati.StatoMaledetto;

public class AttivazioneMaledizioneStrategy implements StrategiaEvento {

    private Giocatore attivatore;
    private int roundMaledizione;

    @Override
    public String getNomeEvento() { return "Il Risveglio della Maledizione"; }

    @Override
    public void inizializza(Giocatore attivatore, RegoleEvento regole, Tabellone tabellone) {
        this.attivatore = attivatore;
        this.roundMaledizione = regole.maledizioneStreak(); 
        tabellone.impostaStatoCaselle(roundMaledizione, new StatoMaledetto());
    }

    @Override
    public void inviaNotifica(NotificatoreEvento notificatore) {
        notificatore.onMaledizione(attivatore.getUsername(), roundMaledizione);
    }
}
