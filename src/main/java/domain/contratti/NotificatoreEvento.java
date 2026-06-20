package domain.contratti;

/**
Interfaccia implementata da EventoController.
Ogni StrategiaEvento chiama il metodo corretto su questa interfaccia
tramite inviaNotifica(), eliminando qualsiasi switch nell'EventoController.
 */
public interface NotificatoreEvento {

    void onCambioDado(String nomeGiocatore, String dadoVecchio, String dadoNuovo);

    void onModificaPunti(String nomeGiocatore, int variazione, int puntiTotali);

    void onPenalita(String nomeGiocatore, int puntiPersi);

    void onMaledizione(String nomeGiocatore, int roundMaledizione);

    void onFineGioco(String nomeGiocatore);

    void onSfida1v1(String nomeGiocatore, int puntiInPalio);
    
}
