package controller.contratti;

public interface SfidaSubject {
    void addObserver(SfidaObserver observer);
    void onAvversarioScelto(String nomeAvversario);
    void onRispostaSfidaG1Inserita(String risposta);
    void onRispostaSfidaG2Inserita(String risposta);
    void onTurnoSfidaSfidante();
    void onTurnoSfidaSfidato();
    void onFineRoundSfida();
    void onSfida1v1(String nomeGiocatore, int puntiInPalio);
}
