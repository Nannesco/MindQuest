package controller.contratti;


public interface EventoObserver {
    void onEventoCambioDado(String giocatoreCorrente, String dadoVecchio, String dadoNuovo);
    void onEventoAttivazioneMaledizione(String username, int numeroRound);
    void onEventoPenalita(String giocatoreCorrente, int puntiPersi);
    void onEventoModificaPunti(String username, int puntiDaAggiungere, int puntiConoscenza);
    void onCasella1v1(String nomeGiocator, int puntiInPalio);
    void onFineTabellone(String nomeGiocatore);
}
