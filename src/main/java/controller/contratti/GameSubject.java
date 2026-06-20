package controller.contratti;

public interface GameSubject {
    void addObserver(GameObserver observer);
    void onLancioDadoRichiesto();
    void onRispostaDomandaInserita(String risposta);
    void onEventoTerminato();
    void onPrimoTurnoIniziato();
}
