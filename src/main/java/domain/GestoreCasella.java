package domain;

// Interfaccia implementata da GameController.
//RisultatoLancio.gestisci() chiama il metodo corretto eliminando
//qualsiasi switch/instanceof nel controller.
 
public interface GestoreCasella {
    void gestisciConoscenza(int molteplicita);
    void gestisciEvento(String tipoEvento);
}
