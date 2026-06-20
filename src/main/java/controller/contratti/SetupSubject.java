package controller.contratti;

import java.util.ArrayList;

public interface SetupSubject {
    void addObserver(SetupObserver observer);
    void onInizioPremuto();
    void onSceltaRegoleEffettuata(boolean leggiRegole);
    void onAnagraficaGiocatoriInserita(ArrayList<String> nomiGiocatori);
}
