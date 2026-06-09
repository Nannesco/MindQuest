package domain;

import java.util.List;
import java.util.Random;

import controller.NotificatoreEvento;

public class ModificaPuntiStrategy implements StrategiaEvento {

    private final Random random = new Random();
    private int variazione;
    private int puntiTotali;

    @Override
    public String getNomeEvento() { return "ROULETTE DEI PUNTI CONOSCENZA"; }

    @Override
    public void inizializza(Giocatore giocatoreCorrente, List<Giocatore> tuttiGiocatori) {
        Pedina pedina = giocatoreCorrente.getPedina();
        variazione = random.nextInt(11) - 5;
        if (variazione >= 0) pedina.aggiungiPuntiConoscenza(variazione);
        else pedina.rimuoviPuntiConoscenza(Math.abs(variazione));
        puntiTotali = pedina.getPuntiConoscenza();
    }

    @Override
    public void inviaNotifica(NotificatoreEvento notificatore, String nomeGiocatore) {
        notificatore.onModificaPunti(nomeGiocatore, variazione, puntiTotali);
    }
}
