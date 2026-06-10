package domain;

import java.util.function.Consumer;

/**
 * DTO immutabile restituito da Partita.eseguiLancioEAssegnaCasella().
 * Il Consumer<GestoreCasella> cattura i dati specifici del tipo di casella
 * e li dispatcha polimorficamente: il GameController non vede mai switch né instanceof.
 */
public class RisultatoLancio {

    public final int passi;
    public final int numeroCasella;
    public final int puntiDado;
    public final String nomeDado;
    public final String bonusDado;

    private final Consumer<GestoreCasella> dispatch;

    public RisultatoLancio(int passi, int numeroCasella, int puntiDado, String nomeDado, String bonusDado, Consumer<GestoreCasella> dispatch) {
        this.passi = passi;
        this.numeroCasella = numeroCasella;
        this.puntiDado = puntiDado;
        this.nomeDado = nomeDado;
        this.bonusDado = bonusDado;
        this.dispatch = dispatch;
    }

    /** Dispatcha polimorficamente al metodo corretto di GestoreCasella. */
    public void gestisci(GestoreCasella gestore) {
        dispatch.accept(gestore);
    }
}
