package domain.strategie;

import domain.Giocatore;
import domain.Tabellone;
import domain.contratti.NotificatoreEvento;
import domain.dadi.Dado;
import domain.dadi.DadoFactory;
import domain.pedine.Pedina;
import domain.regole.RegoleEvento;

public class CambioDadoStrategy implements StrategiaEvento {

    private Giocatore attivatore;
    private String dadoVecchio;
    private String dadoNuovo;

    @Override
    public String getNomeEvento() { return "Cambio Dado"; }

    @Override
    public void inizializza(Giocatore attivatore, RegoleEvento regole, Tabellone tabellone) {
        this.attivatore = attivatore;
        Pedina pedina = attivatore.getPedina();
        Dado dadoAttuale = pedina.getDado();
        DadoFactory dadoFactory = new DadoFactory();
        Dado nuovoDado = dadoFactory.creaDadoDiverso(dadoAttuale);
        pedina.setDado(nuovoDado);
        dadoVecchio = dadoAttuale.getClass().getSimpleName();
        dadoNuovo = nuovoDado.getClass().getSimpleName();
    }

    @Override
    public void inviaNotifica(NotificatoreEvento notificatore) {
        notificatore.onCambioDado(attivatore.getUsername(), dadoVecchio, dadoNuovo);
    }
}
