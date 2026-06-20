package domain.strategie;

import domain.Giocatore;
import domain.Tabellone;
import domain.contratti.NotificatoreEvento;
import domain.regole.RegoleEvento;

public class SfidaUnoControUnoStrategy implements StrategiaEvento {

    private Giocatore attivatore;
    private RegoleEvento regole;

    @Override
    public String getNomeEvento() { return "Sfida 1 VS 1 a oltranza"; }

    @Override
    public void inizializza(Giocatore attivatore, RegoleEvento regole, Tabellone tabellone) {
        this.attivatore = attivatore;
        this.regole = regole;
    }

    @Override
    public void inviaNotifica(NotificatoreEvento notificatore) {
        notificatore.onSfida1v1(attivatore.getUsername(), regole.puntiInPalioSfida());
    }
}
