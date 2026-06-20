package domain.dto;
import java.util.function.Consumer;

import domain.contratti.GestoreCasella;

public record RisultatoLancio(
    int passi, 
    int numeroCasella, 
    int puntiDado, 
    String nomeDado, 
    String bonusDado, 
    Consumer<GestoreCasella> dispatch) {

    public void gestisci(GestoreCasella gestore) {
        dispatch.accept(gestore);
    }
}
