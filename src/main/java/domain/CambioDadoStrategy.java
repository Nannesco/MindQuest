package domain;

import java.util.List;

import controller.NotificatoreEvento;

public class CambioDadoStrategy implements StrategiaEvento {

    private String dadoVecchio;
    private String dadoNuovo;

    @Override
    public String getNomeEvento() { return "Cambio Dado"; }

    @Override
    public void inizializza(Giocatore giocatore, List<Giocatore> tuttiGiocatori) {
        Pedina pedina = giocatore.getPedina();
        Dado dadoAttuale = pedina.getDado();
        Dado nuovoDado = DadoFactory.getInstance().creaDadoDiverso(dadoAttuale);
        pedina.setDado(nuovoDado);
        dadoVecchio = dadoAttuale.getClass().getSimpleName();
        dadoNuovo = nuovoDado.getClass().getSimpleName();
    }

    @Override
    public void inviaNotifica(NotificatoreEvento notificatore, String nomeGiocatore) {
        notificatore.onCambioDado(nomeGiocatore, dadoVecchio, dadoNuovo);
    }
}
