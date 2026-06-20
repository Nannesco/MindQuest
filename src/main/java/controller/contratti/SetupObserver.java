package controller.contratti;

import java.util.List;

public interface SetupObserver {
    // eventi di setup iniziale 
    boolean onGiocoAvviato();
    void onRichiestaLetturaRegole();
    void onRegoleMostrate();
    void onRichiestaConfigurazioneGiocatori();
    void onGiocatoriInizializzati(List<String> nomiPedine, List<String> nomiGiocatori, List<String> ordineTurni);
    void onInizioPrimoRound();
}
