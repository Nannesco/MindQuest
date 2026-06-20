package domain.strategie;

import java.util.Random;

import domain.Giocatore;
import domain.Tabellone;
import domain.contratti.NotificatoreEvento;
import domain.pedine.Pedina;
import domain.regole.RegoleEvento;

public class ModificaPuntiStrategy implements StrategiaEvento {

    private Giocatore attivatore;
    private final Random random = new Random();
    private int variazione;
    private int puntiTotali;

    @Override
    public String getNomeEvento() { return "ROULETTE DEI PUNTI CONOSCENZA"; }

    @Override
    public void inizializza(Giocatore attivatore, RegoleEvento regole, Tabellone tabellone) {
        this.attivatore = attivatore;
        Pedina pedina = attivatore.getPedina();
        variazione = random.nextInt(regole.rouletteMax() - regole.rouletteMin() + 1) + regole.rouletteMin();
        if (variazione >= 0) pedina.aggiungiPuntiConoscenza(variazione);
        else pedina.rimuoviPuntiConoscenza(Math.abs(variazione));
        puntiTotali = pedina.getPuntiConoscenza();
    }

    @Override
    public void inviaNotifica(NotificatoreEvento notificatore) {
        notificatore.onModificaPunti(attivatore.getUsername(), variazione, puntiTotali);
    }
}
