package domain;

import java.util.List;

public interface StrategiaEvento {

    String getNomeEvento();

    default boolean richiedeAvversario() { return false; }

    void inizializza(Giocatore attivatore, List<Giocatore> tuttiGiocatori);

    default void setAvversarioScelto(Giocatore avversario) {}
    default void giocaProssimoTurno() {}
    default boolean verificaRispostaSfida(String risposta) { return false;}
    default void registraEsitoRisposta(boolean esito) {}
    default boolean isFinito() { return true; }

    /**
     * Ogni strategy chiama il metodo corretto su NotificatoreEvento.
     * Default no-op per strategy interattive (SfidaUnoControUno)
     * e per FineGioco (la fine partita è gestita da GameController.concludiTurno).
     */
    default void inviaNotifica(NotificatoreEvento notificatore, String nomeGiocatore) {}

    // Getter per stato sfida 1v1 (default neutri, override solo in SfidaUnoControUnoStrategy)
    default boolean isRoundConcluso() { return false; }
    default String getMessaggioRound() { return null; }
    default Giocatore getVincitoreSfida() { return null; }
    default Giocatore getPerdenteSfida() { return null; }
    default int getPuntiInPalio() { return 0; }
    default Domanda getDomandaCorrente() { return null; }
    default Giocatore getGiocatoreCorrenteSfida() { return null; }
}
