package controller;

import java.util.ArrayList;

public interface GiocoListener {
    // azioni utente dal setup iniziale
    void onInizioPremuto();
    void onSceltaRegoleEffettuata(boolean leggiRegole);
    void onAnagraficaGiocatoriInserita(ArrayList<String> nomiGiocatori);

    // Azioni di gioco
    void onLancioDadoRichiesto();
    void onRispostaDomandaInserita(String risposta);
    void onAvversarioScelto(String nomeAvversario);
    void onRispostaSfidaInserita(String risposta);
}